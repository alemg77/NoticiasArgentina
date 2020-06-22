package kalitero.software.noticiasargentinas.Controlador.Dao

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import kalitero.software.noticiasargentinas.Modelo.ListaNoticias
import kalitero.software.noticiasargentinas.Modelo.Noticia
import kalitero.software.noticiasargentinas.util.ResultListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.*

class NoticiaDaoFirebase {


    private val TAG = javaClass.toString()

    companion object {
        private var progreso: MutableLiveData<Int>? = null
        val COLECCION_NOTICIAS = "noticias"
        private var instancia: NoticiaDaoFirebase? = null
        private lateinit var storage: FirebaseStorage
        private lateinit var referenciaColeccion: CollectionReference

        fun getIntancia(): NoticiaDaoFirebase {
            if (instancia == null) {
                instancia = NoticiaDaoFirebase()
                storage = FirebaseStorage.getInstance()
                referenciaColeccion = FirebaseFirestore.getInstance().collection(COLECCION_NOTICIAS)
                progreso = MutableLiveData<Int>()
            }
            return instancia!!
        }
    }

    fun getProgreso(): MutableLiveData<Int> {
        if (progreso == null) {
            progreso = MutableLiveData()
        }
        return progreso!!
    }

    fun guardarNoticia(noticia: Noticia) {
        GlobalScope.launch {
            try {
                val await = referenciaColeccion.document().set(noticia).await()
                Log.d(TAG, "Guardamos una noticia")
            } catch (e: FirebaseFirestoreException) {
                Log.d(TAG, "Error guardando noticia:$e")
            }
        }
    }

    fun guardarNoticia(noticia: Noticia, imagen: Bitmap) {
        GlobalScope.launch(Dispatchers.IO) {
            val usuarioFirebase = FirebaseAuth.getInstance().uid
            val fechaHora = Calendar.getInstance().time.toString()
            val pathImagen = usuarioFirebase + fechaHora
            val imagenComprimida = comprimir_imagen(imagen, 1024, 70)
            subirArchivo(imagenComprimida,pathImagen )
            noticia.urlImagenStorage = pathImagen
            guardarNoticia(noticia)
        }
    }

    fun subirArchivo(archivo: ByteArray, pathArchivo:String) {
        GlobalScope.launch {
            val riversRef = storage.reference.child(pathArchivo)
            try {
                val uploadTask = riversRef.putBytes(archivo).await()
            } catch (e: FirebaseFirestoreException) {
                Log.d(TAG, "Error subiendo archivo a firebase: $e")
            }
        }
    }

    fun comprimir_imagen(bitmap: Bitmap, anchoMaximo: Int, calidad: Int): ByteArray {
        val width = bitmap.width
        val scaledBitmap = if (width > anchoMaximo) {
            val escala = bitmap.width.toFloat() / anchoMaximo.toFloat()
            val alto = (bitmap.height / escala).toInt()
            Bitmap.createScaledBitmap(bitmap, anchoMaximo, alto, true)
        } else {
            bitmap
        }
        val byteArrayOutputStream = ByteArrayOutputStream()
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, calidad, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

    fun buscarNoticias() {
        GlobalScope.launch {
            val resultado = referenciaColeccion.get().await()
            val toObjects = resultado.toObjects(Noticia::class.java)
            val documents = resultado.documents
            Log.d(TAG, "Que paso?")
        }
    }

    fun buscarNoticias(resultListener: ResultListener<ListaNoticias>) {
        referenciaColeccion.get()
                .addOnSuccessListener(OnSuccessListener<QuerySnapshot> { queryDocumentSnapshots ->
                    // TODO: ESTO ROMPE CUANDO HAY UNA LISTA EN EL DOCUMENTO DE FIREBASE
                    val listNoticia = queryDocumentSnapshots.toObjects(Noticia::class.java)
                    val listaNoticias = ListaNoticias(listNoticia, "Firebase")
                    resultListener.onFinish(listaNoticias)
                }).addOnFailureListener(OnFailureListener { e ->
                    resultListener.onError("Ha ocurrido un error al obtener los animales " + e.message)
                })
    }


}