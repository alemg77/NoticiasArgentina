package kalitero.software.noticiasargentinas.Vista.SubirNoticias

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kalitero.software.noticiasargentinas.Controlador.Dao.NoticiaDaoFirebase
import kalitero.software.noticiasargentinas.Modelo.Noticia
import kalitero.software.noticiasargentinas.R
import kalitero.software.noticiasargentinas.databinding.FragmentIngresoBarrialBinding
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.EasyImage.ImageSource
import java.io.File
import java.util.*
import kotlin.math.sign
import com.bumptech.glide.util.Util as Util


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
            if (tituloNoticia.length < 6) {
                Snackbar.make(binding.root, "El titulo debe ser mas largo", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val descripcionNoticia = binding.editTextTextoNoticia.text.toString()
            if (descripcionNoticia.length < 10) {
                Snackbar.make(binding.root, "La descripcion debe ser mas larga", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (imagen1 == null) {
                Snackbar.make(binding.root, "Es necesario una imagen", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            var usuarioFirebase = FirebaseAuth.getInstance().currentUser
            var mailusuario = usuarioFirebase!!.email
            var noticia = Noticia()
            noticia.autor = mailusuario
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        EasyImage.handleActivityResult(requestCode, resultCode, data, activity, object : DefaultCallback() {
            override fun onImagesPicked(imageFiles: List<File>, source: ImageSource, type: Int) {

                imagen1 = BitmapFactory.decodeFile(imageFiles[0].absolutePath)
                binding.fragmentIngresoBarrialImageViewFoto1.setImageBitmap(imagen1)
                escucharImagenIngresada(imagen1!!)

            }

            override fun onCanceled(source: ImageSource, type: Int) {
                super.onCanceled(source, type)
                Log.d(TAG, "Cancelo el usuario")
            }
        })
    }

    private fun escucharBotonCategoria() {
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

    private fun escucharImagenIngresada(imagen1: Bitmap) {

        val signDialog = dialogSignature(context)


        binding.fragmentIngresoBarrialImageViewFoto1.setOnClickListener {

            signDialog.show()
            if (imagen1 != null) {
               // signDialog.imagenIngresadaGrande.addView(imagen1)
                val imgSignature = signDialog.findViewById<ImageView>(R.id.imagenIngresadaGrande)
                Glide.with(context!!)
                        .load(imagen1)
                        .into(imgSignature)
            }



//                val dialogBuilder = AlertDialog.Builder(context!!)
//                val inflater = this.layoutInflater
//                val dialogView = inflater.inflate(R.layout.fragment_imagen_grande, null)
//                dialogBuilder.setView(dialogView)
//                dialogBuilder.setPositiveButton("OK") { dialogInterface, i -> // Just dismiss the alert dialog after selection
//                    // Or do something now
//                    dialogInterface.dismiss()
//
//                }
//                dialogBuilder.setNegativeButton("Borrar"){ dialogInterface, i ->
//                    binding.fragmentIngresoBarrialImageViewFoto1.setImageResource(R.drawable.ic_baseline_camera_alt_24)
//                    imagen1 == null
//                    dialogInterface.dismiss()
//
//                }
//
//                val alertDialog = dialogBuilder.create()
//                alertDialog.show()
//
//                   alertDialog.setOnShowListener(){
//                       alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//                       alertDialog.setContentView(R.layout.fragment_imagen_grande)
//                       alertDialog.getWindow()?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
//
//                       Glide.with(dialogView)
//                               .load(imagen1)
//                               .into(alertDialog.binding.imagenIngresadaGrande)
//                   }
//
//
//
//            }
        }


    }


    fun dialogSignature(context: Context?): Dialog {

        var dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.fragment_imagen_grande)
        dialog.getWindow()?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return dialog

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    interface Aviso {
        fun mostrarMapa()
    }
}