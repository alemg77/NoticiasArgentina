package kalitero.software.noticiasargentinas.Controlador.Dao

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.storage.FirebaseStorage
import kalitero.software.noticiasargentinas.modelo.Comentario
import kalitero.software.noticiasargentinas.modelo.ListaNoticias
import kalitero.software.noticiasargentinas.modelo.Noticia
import kalitero.software.noticiasargentinas.modelo.Voto
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
    private val VOTACION = "Votacion"

    companion object {
        val FIREBASE = "Firebase"
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
            subirArchivo(imagenComprimida, pathImagen)
            noticia.urlImagenStorage = pathImagen
            noticia.origenFirebase = true
            guardarNoticia(noticia)
        }
    }

    fun subirArchivo(archivo: ByteArray, pathArchivo: String) {
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

    fun agregarComentario(noticia: Noticia, comentario: Comentario, resultListener: ResultListener<String>) {
        referenciaColeccion
                .document(noticia.documentoFirebase)
                .collection(COMENTARIOS)
                .add(comentario)
                .addOnCompleteListener {
                    resultListener.onFinish("Termino")
                }
    }
    
    fun agregarComentario(noticia: Noticia, comentario: Comentario) {
        GlobalScope.launch {
            try {
                val await = referenciaColeccion
                        .document(noticia.documentoFirebase)
                        .collection(COMENTARIOS)
                        .add(comentario).await()
            } catch (e: FirebaseFirestoreException) {
                Log.d(TAG, "Error guardando comentario:$e")
            }
        }
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
                .addOnSuccessListener { result ->
                    var listNoticias: ArrayList<Noticia> = ArrayList()
                    for (document in result) {
                        var noticia: Noticia = document.toObject(Noticia::class.java)
                        noticia.documentoFirebase = document.id
                        listNoticias.add(noticia)
                    }
                    val listaDeNoticias: ListaNoticias = ListaNoticias(listNoticias, FIREBASE)
                    resultListener.onFinish(listaDeNoticias)
                }
                .addOnFailureListener { e ->
                    resultListener.onError("Error en la lectura de noticias " + e.message)
                }
    }

    fun buscarComentarios(noticia: Noticia, resultListener: ResultListener<List<Comentario>>) {
        if (noticia.documentoFirebase == null) {
            // La noticia no esta en firebase
            return;
        }
        referenciaColeccion
                .document(noticia.documentoFirebase)
                .collection(COMENTARIOS)
                .get()
                .addOnSuccessListener { result ->
                    var lista: ArrayList<Comentario> = ArrayList()
                    for (documento in result) {
                        val comentario = documento.toObject(Comentario::class.java)
                        comentario.documentoFirebase = documento.id
                        lista.add(comentario)
                    }
                    resultListener.onFinish(lista)
                }
    }


    fun buscarVotosComentario(noticia: Noticia, comentario: Comentario, resultListener: ResultListener<List<Voto>>) {
        referenciaColeccion
                .document(noticia.documentoFirebase)
                .collection(COMENTARIOS)
                .document(comentario.documentoFirebase)
                .collection(VOTACION)
                .get()
                .addOnSuccessListener { result ->
                    var lista: ArrayList<Voto> = ArrayList()
                    for (document in result) {
                        lista.add(document.toObject(Voto::class.java))
                    }
                    resultListener.onFinish(lista)
                }
    }

    fun buscarVotosNoticia(noticia: Noticia, resultListener: ResultListener<List<Voto>>) {
        referenciaColeccion
                .document(noticia.documentoFirebase)
                .collection(VOTACION)
                .get()
                .addOnSuccessListener { result ->
                    var lista: ArrayList<Voto> = ArrayList()
                    for (document in result) {
                        lista.add(document.toObject(Voto::class.java))
                    }
                    resultListener.onFinish(lista)
                }
    }


    fun leerVotos(noticia: Noticia, comentario: Comentario, resultListener: ResultListener<List<Voto>>) {
        referenciaColeccion
                .document(noticia.documentoFirebase)
                .collection(VOTACION)
                .get()
                .addOnSuccessListener { result ->
                    var listaVotos: List<Voto> = ArrayList()
                    for (document in result) {
                        var voto: Voto = document.toObject(Voto::class.java)
                    }
                }
                .addOnFailureListener { e ->
                    resultListener.onError("Error en la lectura de votos " + e.message)
                }
    }


    fun votoNoticia(noticia: Noticia, voto: Voto) {
        referenciaColeccion
                .document(noticia.documentoFirebase)
                .collection(VOTACION)
                .whereEqualTo("usuario", voto.usuario)
                .get()
                .addOnSuccessListener { result ->
                    if (result.documents.size == 0) {
                        // Si no hay documento, significa que no voto
                        guargarVotoNuevo(noticia, voto)
                    } else {
                        Log.d(TAG, "Ya voto")
                    }
                }
                .addOnFailureListener {
                    Log.d(TAG, "Error en firebase?")
                }
    }


    fun votoComentario(noticia: Noticia, comentario: Comentario, voto: Voto) {
        referenciaColeccion
                .document(noticia.documentoFirebase)
                .collection(COMENTARIOS)
                .document(comentario.documentoFirebase)
                .collection(VOTACION)
                .whereEqualTo("usuario", voto.usuario)
                .get()
                .addOnSuccessListener { result ->
                    if (result.documents.size == 0) {
                        // Si no hay documento, significa que no voto
                        guargarVotoNuevo(noticia, comentario, voto)
                    } else {
                        Log.d(TAG, "Ya voto")
                    }
                }
                .addOnFailureListener {
                    Log.d(TAG, "Error en firebase?")
                }
    }

    fun guargarVotoNuevo(noticia: Noticia, comentario: Comentario, voto: Voto) {
        GlobalScope.launch {
            try {
                val await = referenciaColeccion
                        .document(noticia.documentoFirebase)
                        .collection(COMENTARIOS)
                        .document(comentario.documentoFirebase)
                        .collection(VOTACION)
                        .add(voto).await()
            } catch (e: FirebaseFirestoreException) {
                Log.d(TAG, "Error guardando comentario:$e")
            }
        }
    }


    fun guargarVotoNuevo(noticia: Noticia, voto: Voto) {
        GlobalScope.launch {
            try {
                val await = referenciaColeccion
                        .document(noticia.documentoFirebase)
                        .collection(VOTACION)
                        .add(voto).await()
            } catch (e: FirebaseFirestoreException) {
                Log.d(TAG, "Error guardando comentario:$e")
            }
        }
    }

}