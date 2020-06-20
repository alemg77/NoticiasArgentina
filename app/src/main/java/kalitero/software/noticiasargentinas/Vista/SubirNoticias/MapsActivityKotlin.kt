package kalitero.software.noticiasargentinas.Vista.SubirNoticias

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMapClickListener
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import kalitero.software.noticiasargentinas.R
import kalitero.software.noticiasargentinas.Vista.SubirNoticias.SubirNoticias.Companion.binding
import kalitero.software.noticiasargentinas.databinding.ActivityMapsKotlinBinding

class MapsActivityKotlin : AppCompatActivity(), OnMapReadyCallback, OnMarkerClickListener, OnMapClickListener {

    private val coordenadas: LatLng? = null
    private lateinit var locationManager: LocationManager
    private var locationListener: LocationListener? = null


    private lateinit var mMap: GoogleMap
    private val TAG = javaClass.toString()


    private val PERMISSION_REQUEST = 10
    private var permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    private var hasGps = false
    private var hasNetwork = false
    private var coordenadasUsuario: Location? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps_kotlin)

        if (checkPermission(permissions)){
            Log.d(TAG, "Tenemos permisos para usar el GPS, vamos a buscar la posicion")
            getLocation()
        } else {
            Log.d(TAG, "No tenemos permisos para el GPS, vamos a pedirlos")
            requestPermissions(permissions, PERMISSION_REQUEST)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST) {
            var allSuccess = true
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    allSuccess = false
                }
            }
            if (allSuccess) {
                Log.d(TAG, "Acaban de darnos permisos, vamos a buscar la posicion")
                getLocation()
            } else {
                // TODO: Decidir que hacer si no tenemos permisos
            }
        }
    }

    private fun iniciarMapa() {
        Log.d(TAG, "Iniciando mapa.....")
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun checkPermission(permissionArray: Array<String>): Boolean {
        var allSuccess = true
        for (i in permissionArray.indices) {
            if (checkCallingOrSelfPermission(permissionArray[i]) == PackageManager.PERMISSION_DENIED)
                allSuccess = false
        }
        return allSuccess
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

        var latLngUsuario = LatLng(coordenadasUsuario!!.latitude, coordenadasUsuario!!.longitude)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngUsuario, 15f))
    }

    override fun onMapClick(p0: LatLng?) {
        Log.d(TAG, "onMapClick!!!!")
        Snackbar.make(window.decorView.rootView, "Guardar posicion?", Snackbar.LENGTH_SHORT)
                .setAction("SI", View.OnClickListener {
                    Log.d(TAG, "Falta guardar las coordenadas");
                }).show()

    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        Log.d(TAG, "onMarkerClick!!!!")
        return false
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (hasGps || hasNetwork) {
            if (hasGps) {
                Log.d(TAG, "hasGps")
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0F, object : LocationListener {
                    override fun onLocationChanged(location: Location?) {
                        Log.d(TAG, "Tenemos coordenadas por GPS")
                        if (coordenadasUsuario == null) {
                            iniciarMapa()
                        }
                        coordenadasUsuario = location
                    }

                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

                    }

                    override fun onProviderEnabled(provider: String?) {

                    }

                    override fun onProviderDisabled(provider: String?) {

                    }

                })
                val localGpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            }

            if (hasNetwork) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0F, object : LocationListener {
                    override fun onLocationChanged(location: Location?) {
                        if (location != null) {
                            Log.d(TAG, "Tenemos coordenadas por red")
                            if (coordenadasUsuario == null) {
                                iniciarMapa()
                            }
                            coordenadasUsuario = location

                        }
                    }

                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

                    }

                    override fun onProviderEnabled(provider: String?) {

                    }

                    override fun onProviderDisabled(provider: String?) {

                    }

                })
                val localNetworkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            }
        } else {
            // TODO: No tenemos GPS ni RED, ¿Que hacemos?
            //startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
    }



}