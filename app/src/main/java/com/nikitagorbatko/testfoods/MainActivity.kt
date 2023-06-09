package com.nikitagorbatko.testfoods

import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationRequest
import android.os.Bundle
import android.os.Looper
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.location.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nikitagorbatko.testfoods.databinding.ActivityMainBinding
import java.util.Locale


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var fusedClient: FusedLocationProviderClient
    private val launcher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            if (map.values.isNotEmpty() && map.values.all { it }) {
                Toast.makeText(this, "Разрешения разрешены", Toast.LENGTH_LONG).show()
            } else {
                shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)
            }
        }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            if (p0.lastLocation != null) {
                p0.lastLocation!!.latitude,
                p0.lastLocation!!.longitude,
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedClient = LocationServices.getFusedLocationProviderClient(this)
        val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()

        if (!checkPermission()) return
        fusedClient.requestLocationUpdates(request, locationCallback, Looper.getMainLooper())

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses: List<Address>? = geocoder.getFromLocation(MyLat, MyLong, 1)
        val cityName: String = addresses!![0].getAddressLine(0)
        val stateName: String = addresses!![0].getAddressLine(1)
        val countryName: String = addresses!![0].getAddressLine(2)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_main,
//                R.id.navigation_search,
//                R.id.navigation_cart,
//                R.id.navigation_account
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun checkPermission(): Boolean {
        val isAllGranted = REQUEST_PERMISSIONS.all { permission ->
            ContextCompat
                .checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
        }
        return isAllGranted
    }

    companion object {
        private val REQUEST_PERMISSIONS: Array<String> = arrayOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }
}