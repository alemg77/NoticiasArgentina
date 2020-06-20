package kalitero.software.noticiasargentinas.Vista.SubirNoticias

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kalitero.software.noticiasargentinas.databinding.FragmentIngresoBarrialBinding
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.EasyImage.ImageSource
import java.io.ByteArrayOutputStream
import java.io.File


class FragmentIngresoBarrial : Fragment() {


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
        return binding.root
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
                val imagenPublicacion1 = BitmapFactory.decodeFile(imageFiles[0].absolutePath)
                binding.fragmentIngresoBarrialImageViewFoto1.setImageBitmap(imagenPublicacion1)
            }

            override fun onCanceled(source: ImageSource, type: Int) {
                super.onCanceled(source, type)
                Log.d(TAG, "Cancelo el usuario")
            }
        })
    }

    fun comprimir_imagen(bitmap: Bitmap, anchoMaximo: Int, calidad: Int): ByteArray? {
        var scaledBitmap: Bitmap? = null
        val width = bitmap.width
        scaledBitmap = if (width > anchoMaximo) {
            val escala = bitmap.width.toFloat() / anchoMaximo.toFloat()
            val alto = (bitmap.height / escala).toInt()
            Bitmap.createScaledBitmap(bitmap, anchoMaximo, alto, true)
        } else {
            bitmap
        }
        val byteArrayOutputStream = ByteArrayOutputStream()
        scaledBitmap!!.compress(Bitmap.CompressFormat.JPEG, calidad, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    interface Aviso {
        fun mostrarMapa()
    }


}