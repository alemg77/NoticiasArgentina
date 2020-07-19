package kalitero.software.noticiasargentinas.Vista.NoticiasBarriales

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kalitero.software.noticiasargentinas.Controlador.RecepcionNoticias
import kalitero.software.noticiasargentinas.modelo.ListaNoticias
import kalitero.software.noticiasargentinas.Vista.NoticiasGenerales.FragmentListaNoticiasCompacto
import kalitero.software.noticiasargentinas.databinding.FragmentNoticiasBarrialesBinding


class FragmentNoticiasBarriales(listerner: RecepcionNoticias) : Fragment(), NoticiasBarrialesAdapter.AvisoRecyclerViewkt {

    var _binding: FragmentNoticiasBarrialesBinding? = null
    private val binding get() = _binding!!
    private lateinit var listaNoticias: ListaNoticias

    private var escuchador: RecepcionNoticias = listerner

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentNoticiasBarrialesBinding.inflate(inflater, container, false)

        val bundle = arguments
        listaNoticias = bundle!!.getSerializable(FragmentListaNoticiasCompacto.LISTA_NOTICIAS) as ListaNoticias

        viewManager = LinearLayoutManager(context)
        viewAdapter = NoticiasBarrialesAdapter(listaNoticias.arrayListNoticias, this)

        binding.RecyclerNoticiasBarriales.apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }
        return binding.root
    }

    companion object {
        val LISTA_NOTICIAS = ListaNoticias::class.java.toString()
        private val TAG = javaClass.toString()
    }

    override fun recyclerViewClick(posicion: Int) {
        listaNoticias.posicionInicial = posicion
        escuchador.mostrarDetalleDeNoticias(listaNoticias)
        Log.d(TAG, "Llego?")
    }
}