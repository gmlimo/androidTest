package org.bedu.gps

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.bedu.gps.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object{
        const val PERMISSION_ID = 33
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding.btnLocate.setOnClickListener {
            getLocation()
        }
    }

    private fun checkGranted(permission: String): Boolean{
        return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }

   //Revisamos permisos del Manifest
    private fun checkPermissions() =
        checkGranted(android.Manifest.permission.ACCESS_COARSE_LOCATION) &&
                checkGranted(android.Manifest.permission.ACCESS_FINE_LOCATION)


    //Pedir los permisos requeridos para que funcione la localización
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }

    //Revisamos si el GPS esta prendido
    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    binding.tvLatitude.text = location.latitude.toString()
                    binding.tvLongitude.text = location.longitude.toString()
                }
            }
            else {
                //Aquí va el reto 1 ver solucion se hace con un intent
                Toast.makeText(this, "GPS is off", Toast.LENGTH_SHORT).show()}
        }
        else {
            requestPermissions()
        }
    }
}