package kalitero.software.noticiasargentinas.Vista.SubirNoticias

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kalitero.software.noticiasargentinas.Controlador.Dao.NoticiaDaoFirebase
import kalitero.software.noticiasargentinas.Modelo.Noticia
import kalitero.software.noticiasargentinas.databinding.FragmentIngresoBarrialBinding
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.EasyImage.ImageSource
import java.io.File
import java.util.*


class FragmentIngresoBarrial : Fragment() {

    private var imagen1: Bitmap? = null
    private val binding get() = _binding!!
    var _binding: FragmentIngresoBarrialBinding? = null

    internal lateinit var callback: Aviso

    fun fijarEschuchador(callback: Aviso) {
        this.callback = callback
    }

    companion object {
        val TAG = javaClass.toString()
        private const val PERMISO_FINE_LOCATION = 69
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentIngresoBarrialBinding.inflate(inflater, container, false)
        escucharBotonBuscarImagen()
        escucharBotonBuscarMapa()
        escucharBotonPublicar()
        escucharBotonCategoria()
        return binding.root
    }

    private fun escucharBotonPublicar() {
        binding.botonPublicar.setOnClickListener {
            val tituloNoticia = binding.editTextTituloNoticia.text.toString()
            if ( tituloNoticia.length < 6 ) {
                Snackbar.make(binding.root, "El titulo debe ser mas largo", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val descripcionNoticia = binding.editTextTextoNoticia.text.toString()
            if ( descripcionNoticia.length < 10 ){
                Snackbar.make(binding.root, "La descripcion debe ser mas larga", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if ( imagen1 == null ) {
                Snackbar.make(binding.root, "Es necesario una imagen", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            var usuarioFirebase = FirebaseAuth.getInstance().currentUser
            var mailusuario = usuarioFirebase!!.email
            var noticia = Noticia()
            noticia.autor=mailusuario
            noticia.titulo = tituloNoticia
            noticia.descripcion = descripcionNoticia
            noticia.fecha = Date()
            noticia.fuente = "Usuario"
            NoticiaDaoFirebase.getIntancia().guardarNoticia(noticia, imagen1!!)
            Snackbar.make(binding.root, "Tenemos todo?", Snackbar.LENGTH_LONG).show()
            // TODO: Hacer que muestre el progreso de suvir la foto
            // TODO: Al terminar de subir deberia agradecer y salir.
            binding.editTextTextoNoticia.text = null
            binding.editTextTituloNoticia.text = null

        }
    }

    private fun escucharBotonBuscarMapa() {
        binding.fragmentDetalleIngresoBarrialFABubicacion.setOnClickListener {
            callback.mostrarMapa()
        }
    }

    private fun escucharBotonBuscarImagen() {
        binding.fragmentIngresoBarrialFABsubirFoto.setOnClickListener {
            EasyImage.openChooserWithGallery(this@FragmentIngresoBarrial, "Elige una foto", 1)
        }
    }

    private fun escucharBotonCategoria(){
        binding.fragmentIngresoBarrialFABcategoria.setOnClickListener {
            val builder = AlertDialog.Builder(context!!)

            // Set the alert dialog title
            builder.setTitle("Seleccione Categoría")
            val temasNoticias = arrayOf(
                    "Sociales",
                    "Policiales",
                    "Economía",
                    "Deportes",
                    "Artes"
            )
            builder.setSingleChoiceItems(
                    temasNoticias,  // Items list
                    -1  // Index of checked item (-1 = no selection)
            ) { dialogInterface, i ->  // Item click listener
                // Get the alert dialog selected item's text
                val selectedItem = Arrays.asList(*temasNoticias)[i]

                // Display the selected item's text on snack bar
                binding.fragmentDetalleBarrialCategoriaTextView.text = selectedItem
                Snackbar.make(
                        view!!,
                        "Seleccionó : $selectedItem",
                        Snackbar.LENGTH_LONG
                ).show()
            }

            // Set the a;ert dialog positive button
            builder.setPositiveButton("OK") { dialogInterface, i -> // Just dismiss the alert dialog after selection
                // Or do something now
                dialogInterface.dismiss()
            }

            // Create the alert dialog
            val dialog = builder.create()

            // Finally, display the alert dialog
            dialog.show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        EasyImage.handleActivityResult(requestCode, resultCode, data, activity, object : DefaultCallback() {
            override fun onImagesPicked(imageFiles: List<File>, source: ImageSource, type: Int) {

                imagen1 = BitmapFactory.decodeFile(imageFiles[0].absolutePath)
                binding.fragmentIngresoBarrialImageViewFoto1.setImageBitmap(imagen1)
            }

            override fun onCanceled(source: ImageSource, type: Int) {
                super.onCanceled(source, type)
                Log.d(TAG, "Cancelo el usuario")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    interface Aviso {
        fun mostrarMapa()
    }


}