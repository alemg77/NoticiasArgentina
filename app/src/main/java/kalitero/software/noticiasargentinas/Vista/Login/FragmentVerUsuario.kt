package kalitero.software.noticiasargentinas.Vista.Login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import kalitero.software.noticiasargentinas.Vista.Regresar
import kalitero.software.noticiasargentinas.databinding.FragmentVerUsuarioBinding


class FragmentVerUsuario : Fragment() {

    var _binding:FragmentVerUsuarioBinding? = null
    private val binding get() = _binding!!
    private lateinit var listener: Regresar

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as Regresar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentVerUsuarioBinding.inflate(inflater, container, false)
        esucharBotonSalir()
        return binding.root
    }

    private fun esucharBotonSalir() {
        binding.botonSalirUsario.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            listener.regresarAFragmentAnterior()
        }
    }

    companion object {
    }
}