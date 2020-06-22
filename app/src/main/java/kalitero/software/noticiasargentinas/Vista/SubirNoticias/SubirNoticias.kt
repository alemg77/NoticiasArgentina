package kalitero.software.noticiasargentinas.Vista.SubirNoticias

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kalitero.software.noticiasargentinas.R
import kalitero.software.noticiasargentinas.databinding.ActivitySubirNoticiasBinding


class SubirNoticias : AppCompatActivity(), FragmentIngresoBarrial.Aviso {

    companion object {
        var binding: ActivitySubirNoticiasBinding? = null
        val TAG = javaClass.toString()
        val KEY_LOCAL = "key local"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubirNoticiasBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        if (savedInstanceState == null) {
            pegarFragment(FragmentIngresoBarrial(), R.id.fragmentLayoutSubirNoticia)
        }
    }

    private fun pegarFragment(fragmentAPegar: Fragment, containerViewId: Int) {
        if (fragmentAPegar is FragmentIngresoBarrial) {
            fragmentAPegar.fijarEschuchador(this)
        }
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.replace(containerViewId, fragmentAPegar).commit()
    }

    fun mensajito(mensaje: String) {
        Snackbar.make(binding!!.root, mensaje, Snackbar.LENGTH_SHORT).show();
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_LOCAL, "Esto ya esta echo")
    }

    override fun mostrarMapa() {
        val intent = Intent(this, MapsActivityKotlin::class.java).apply {
            //putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }

}