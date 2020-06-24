package kalitero.software.noticiasargentinas.Vista.NoticiasBarriales

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import kalitero.software.noticiasargentinas.Modelo.Noticia
import kalitero.software.noticiasargentinas.databinding.CeldaNoticiaBarrialBinding

class NoticiasBarrialesAdapter(private val lista: List<Noticia>) :
        RecyclerView.Adapter<NoticiasBarrialesAdapter.NoticiaViewHolder>() {

    private var listaDeNoticias: List<Noticia> = lista

    companion object {
        private val TAG = javaClass.toString()
    }

    override fun onBindViewHolder(holder: NoticiaViewHolder, position: Int) {
        holder.cargarNoticia(listaDeNoticias[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticiaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val celdaNoticiaBinding: CeldaNoticiaBarrialBinding = CeldaNoticiaBarrialBinding.inflate(layoutInflater, parent, false)
        return NoticiaViewHolder(celdaNoticiaBinding)
    }

    override fun getItemCount(): Int {
        return listaDeNoticias.size
    }

    class NoticiaViewHolder(celdaNoticiaBinding: CeldaNoticiaBarrialBinding) : RecyclerView.ViewHolder(celdaNoticiaBinding.root) {

        private val binding: CeldaNoticiaBarrialBinding = celdaNoticiaBinding

        fun cargarNoticia(unaNoticia: Noticia) {
            binding.CeldaNoticiaTitulo.text = unaNoticia.titulo
            val urlImagenStorage = unaNoticia.urlImagenStorage
            val child = FirebaseStorage.getInstance().reference.child(urlImagenStorage)
            Glide.with(binding.root).load(child).into(binding.CeldaNoticiaImagen)
        }

        init {
            binding.CeldaNoticiaImagen.setOnClickListener {
                Log.d(TAG, "Seleccionaron una celda: $adapterPosition")
            }
        }
    }

    interface AvisoRecyclerView {
        fun recyclerViewClick(posicion: Int)
    }


}