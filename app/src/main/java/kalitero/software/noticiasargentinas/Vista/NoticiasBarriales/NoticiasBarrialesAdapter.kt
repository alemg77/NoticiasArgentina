package kalitero.software.noticiasargentinas.Vista.NoticiasBarriales

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kalitero.software.noticiasargentinas.Controlador.Dao.NoticiaDaoFirebase
import kalitero.software.noticiasargentinas.modelo.Noticia
import kalitero.software.noticiasargentinas.modelo.Voto
import kalitero.software.noticiasargentinas.databinding.CeldaNoticiaBarrialBinding
import kalitero.software.noticiasargentinas.util.ResultListener

class NoticiasBarrialesAdapter(private val listaNoticias: List<Noticia>, private val listener: AvisoRecyclerViewkt) :
        RecyclerView.Adapter<NoticiasBarrialesAdapter.NoticiaViewHolder>() {


    companion object {
        private val TAG = javaClass.toString()
        private lateinit var escuchador: AvisoRecyclerViewkt
        private lateinit var listaDeNoticias: List<Noticia>
    }

    override fun onBindViewHolder(holder: NoticiaViewHolder, position: Int) {
        holder.cargarNoticia(listaDeNoticias[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticiaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        listaDeNoticias = listaNoticias
        escuchador = listener
        val celdaNoticiaBinding: CeldaNoticiaBarrialBinding = CeldaNoticiaBarrialBinding.inflate(layoutInflater, parent, false)
        return NoticiaViewHolder(celdaNoticiaBinding)
    }

    override fun getItemCount(): Int {
        return listaNoticias.size
    }

    class NoticiaViewHolder(celdaNoticiaBinding: CeldaNoticiaBarrialBinding) : RecyclerView.ViewHolder(celdaNoticiaBinding.root) {

        private val binding: CeldaNoticiaBarrialBinding = celdaNoticiaBinding
        private lateinit var noticia:Noticia

        fun cargarNoticia(unaNoticia: Noticia) {
            binding.CeldaNoticiaTitulo.text = unaNoticia.titulo
            noticia = unaNoticia
            val urlImagenStorage = unaNoticia.urlImagenStorage
            val child = FirebaseStorage.getInstance().reference.child(urlImagenStorage)
            Glide.with(binding.root).load(child).into(binding.CeldaNoticiaImagen)
            actualizarContadores()
        }

        init {
            binding.CeldaNoticiaImagen.setOnClickListener {
                escuchador.recyclerViewClick(adapterPosition)
                Log.d(TAG, "Seleccionaron una celda: $adapterPosition")
            }
            binding.votoNegativo.setOnClickListener {
                val email = FirebaseAuth.getInstance().currentUser?.email
                if ( email == null) {
                    Snackbar.make(binding.root, "Es necesario registrarse para votar", Snackbar.LENGTH_LONG).show()
                } else {
                    val voto: Voto = Voto(email, false)
                    NoticiaDaoFirebase.getIntancia().votoNoticia(noticia, voto)
                    actualizarContadores()
                }
            }
            binding.votoPositivo.setOnClickListener {
                val email = FirebaseAuth.getInstance().currentUser?.email
                if ( email == null) {
                    Snackbar.make(binding.root, "Es necesario registrarse para votar", Snackbar.LENGTH_LONG).show()
                } else {
                    val voto: Voto = Voto(email, true)
                    NoticiaDaoFirebase.getIntancia().votoNoticia(noticia, voto)
                    actualizarContadores()
                }
            }
        }

        fun actualizarContadores(){
            NoticiaDaoFirebase.getIntancia()
                    .buscarVotosNoticia(noticia, object : ResultListener<List<Voto>> {
                        override fun onFinish(result: List<Voto>) {
                            var positivos = 0
                            var negativos = 0
                            for (voto in result) {
                                if (voto.positivo) {
                                    positivos++
                                } else {
                                    negativos++
                                }
                                binding.contadorVotosNegativos.text = negativos.toString()
                                binding.contadorVotosPositivos.text = positivos.toString()
                            }
                        }
                        override fun onError(message: String) {}
                    })
        }


    }

    interface AvisoRecyclerViewkt {
        fun recyclerViewClick(posicion: Int)
    }


}