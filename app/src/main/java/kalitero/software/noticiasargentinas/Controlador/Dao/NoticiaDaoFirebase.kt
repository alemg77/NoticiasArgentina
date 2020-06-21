package kalitero.software.noticiasargentinas.Controlador.Dao

import android.content.ContentValues
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import kalitero.software.noticiasargentinas.Modelo.ListaNoticias
import kalitero.software.noticiasargentinas.Modelo.Noticia
import kalitero.software.noticiasargentinas.util.ResultListener
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
        referenciaColeccion.document().set(noticia)
                .addOnSuccessListener {
                    Log.d(TAG, "Guardamos una noticia")
                }
    }

    fun guardarNoticia(noticia: Noticia, imagen: Bitmap) {
        val fechayHora = Calendar.getInstance().time.toString()
        val uid = FirebaseAuth.getInstance().uid
        val riversRef = storage.reference.child(uid + fechayHora)
        val imagenComprimida = comprimir_imagen(imagen, 1024, 70)
        val uploadTask = riversRef.putBytes(imagenComprimida)
        uploadTask.addOnFailureListener { Log.d(ContentValues.TAG, "Fallo al subir archivo en FireStore") }.addOnSuccessListener { taskSnapshot ->
            Log.d(ContentValues.TAG, "Subio archivo en Firestore")
            val storage = taskSnapshot.storage
            val path = taskSnapshot.metadata!!.path
            noticia.urlImagenStorage = path
            guardarNoticia(noticia)
        }.addOnProgressListener { taskSnapshot ->
            val aux = taskSnapshot.bytesTransferred * 100 / taskSnapshot.totalByteCount
//            progreso!!.setValue(aux.toInt())
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

    /*
    fun buscarNoticias() {
        referenciaColeccion.get()
                .addOnSuccessListener { queryDocumentSnapshots ->
                    val listNoticia = queryDocumentSnapshots.toObjects(Noticia::class.java)
                    val listaNoticias = ListaNoticias(listNoticia, "Firebase")
                    Log.d(TAG, "Que paso?")
                }
    }
     */

    /*************************
     * Busca en la collection de animales todos los animales
     * @param resultListener
     *********/
    fun buscarNoticias(resultListener: ResultListener<ListaNoticias>) {
        referenciaColeccion.get()
                .addOnSuccessListener(OnSuccessListener<QuerySnapshot> { queryDocumentSnapshots ->
                    val listNoticia = queryDocumentSnapshots.toObjects(Noticia::class.java)
                    val listaNoticias = ListaNoticias(listNoticia, "Firebase")
                    resultListener.onFinish(listaNoticias)
                }).addOnFailureListener(OnFailureListener { e ->
                    resultListener.onError("Ha ocurrido un error al obtener los animales " + e.message)
                })
    }


}