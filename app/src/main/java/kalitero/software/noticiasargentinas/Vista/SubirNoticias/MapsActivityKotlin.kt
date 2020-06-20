package kalitero.software.noticiasargentinas.Vista.SubirNoticias

import android.Manifest
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMapClickListener
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kalitero.software.noticiasargentinas.R

class MapsActivityKotlin : AppCompatActivity(), OnMapReadyCallback, OnMarkerClickListener, OnMapClickListener {

    private val coordenadas: LatLng? = null
    private var locationManager: LocationManager? = null
    private var locationListener: LocationListener? = null


    private lateinit var mMap: GoogleMap
    private val PERMISO_FINE_LOCATION = 69
    private val TAG = javaClass.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps_kotlin)
        iniciarMapa()
    }

    private fun iniciarMapa() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    fun verificarPermisos() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "No tenemos permiso, vamos a pedirlo")
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISO_FINE_LOCATION)
        } else {
            Log.d(TAG, "Ya tenemos permiso de antes")
           // ubicacionGeografica()
           // iniciarMapa()
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setOnMarkerClickListener(this)
        mMap.setOnMapClickListener(this)

        // Add a marker in Sydney and move the cameralat/lng:
        val bsas = LatLng(-34.59228,-58.4446)
        mMap.addMarker(MarkerOptions().position(bsas).title("Buenos Aires"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bsas))
    }


    override fun onMapClick(p0: LatLng?) {
        Log.d(TAG, "onMapClick!!!!")
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        Log.d(TAG, "onMarkerClick!!!!")
        return false
    }

}