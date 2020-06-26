package kalitero.software.noticiasargentinas.Controlador.Dao

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.storage.FirebaseStorage
import kalitero.software.noticiasargentinas.Modelo.Comentario
import kalitero.software.noticiasargentinas.Modelo.ListaNoticias
import kalitero.software.noticiasargentinas.Modelo.Noticia
import kalitero.software.noticiasargentinas.util.ResultListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList

class NoticiaDaoFirebase {


    private val TAG = javaClass.toString()
    private val COMENTARIOS = "Comentarios"

    companion object {
        private var progreso: MutableLiveData<Int>? = null
        val COLECCION_NOTICIAS = "noticias"
        private var instancia: NoticiaDaoFirebase? = null
        private var noticiaDaoRoom: NoticiaDaoRoom? = null
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
            noticia.origenFirebase = true
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

    /*
    fun buscarNoticias() {
        GlobalScope.launch {
            val resultado = referenciaColeccion.get().await()
            val toObjects = resultado.toObjects(Noticia::class.java)
            val documents = resultado.documents
            Log.d(TAG, "Que paso?")
        }
    }
     */

    fun agregarComentario(documento: String, comentario: Comentario){
        GlobalScope.launch {
            try {
                val await = referenciaColeccion
                        .document(documento)
                        .collection(COMENTARIOS)
                        .add(comentario).await()
            } catch (e: FirebaseFirestoreException) {
                Log.d(TAG, "Error guardando comentario:$e")
            }
        }
    }

    fun buscarComentarios ( documento: String, resultListener: ResultListener<List<Comentario>>) {
        referenciaColeccion
                .document(documento)
                .collection(COMENTARIOS)
                .get()
                .addOnSuccessListener { result ->
                    var listaComentarios: ArrayList<Comentario> = ArrayList()
                    for ( document in result ) {
                        var comentario: Comentario = document.toObject(Comentario::class.java)
                        listaComentarios.add(comentario)
                    }
                    resultListener.onFinish(listaComentarios)
                }
                .addOnFailureListener{ e->
                    resultListener.onError("Error en la lectura de los comentarios"+e.message)
                }
    }


    fun buscarNoticias(resultListener: ResultListener<ListaNoticias>) {
        referenciaColeccion.get()
                .addOnSuccessListener { result ->
                    var listNoticias: ArrayList<Noticia> = ArrayList()
                    for (document in result) {
                        var noticia: Noticia = document.toObject(Noticia::class.java)
                        noticia.documentoFirebase = document.id
                        listNoticias.add(noticia)
                    }

                    noticiaDaoRoom?.insertAll(listNoticias)

                    val listaDeNoticias:ListaNoticias = ListaNoticias(listNoticias, "Firebase")
                    resultListener.onFinish(listaDeNoticias)
                }
                .addOnFailureListener { e ->
                    resultListener.onError("Error en la lectura de noticias " + e.message)
                }

    }


}