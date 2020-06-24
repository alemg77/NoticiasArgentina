package kalitero.software.noticiasargentinas.Vista.NoticiasBarriales

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kalitero.software.noticiasargentinas.Modelo.ListaNoticias
import kalitero.software.noticiasargentinas.Vista.NoticiasGenerales.FragmentListaNoticiasCompacto
import kalitero.software.noticiasargentinas.databinding.FragmentNoticiasBarrialesBinding


class FragmentNoticiasBarriales : Fragment() {

    var _binding:FragmentNoticiasBarrialesBinding? = null
    private val binding get() = _binding!!
    private lateinit var listaNoticias: ListaNoticias

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentNoticiasBarrialesBinding.inflate(inflater, container, false)

        val bundle = arguments
        listaNoticias = bundle!!.getSerializable(FragmentListaNoticiasCompacto.LISTA_NOTICIAS) as ListaNoticias

        return binding.root
    }

    companion object {
        val LISTA_NOTICIAS = ListaNoticias::class.java.toString()
    }
}