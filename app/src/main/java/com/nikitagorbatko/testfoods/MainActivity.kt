package com.nikitagorbatko.testfoods

import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.location.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nikitagorbatko.main.SharedViewModel
import com.nikitagorbatko.testfoods.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: SharedViewModel by inject()

    private var fusedLocationProvider: FusedLocationProviderClient? = null
    private val request =
        LocationRequest.Builder(Priority.PRIORITY_BALANCED_POWER_ACCURACY, 3000).build()

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val locationList = locationResult.locations
            if (locationList.isNotEmpty()) {
                setToolbar(locationList.last())
                fusedLocationProvider?.removeLocationUpdates(this)
            }
        }
    }

    private fun setToolbar(location: Location) {
        val geocoder = Geocoder(applicationContext, Locale.getDefault())
        val addresses: List<Address>? =
            geocoder.getFromLocation(location.latitude, location.longitude, 1)
        val cityName: String = addresses?.get(0)?.locality ?: ""

        val calendar = Calendar.getInstance().time

        val df = SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault())
        val formattedDate: String = df.format(calendar)
        binding.textCity.text = cityName
        binding.textDate.text = formattedDate
    }

    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    COARSE_LOCATION
                )
            ) {
                AlertDialog.Builder(this)
                    .setTitle("Location Permission Needed")
                    .setMessage("This app needs the Location permission, please accept to use location functionality")
                    .setPositiveButton(
                        "OK"
                    ) { _, _ ->
                        requestLocationPermission()
                    }
                    .create()
                    .show()
            } else {
                requestLocationPermission()
            }
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(COARSE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            COARSE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        fusedLocationProvider?.requestLocationUpdates(
                            request,
                            locationCallback,
                            Looper.getMainLooper()
                        )
                    }
                    return
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this)
        checkLocationPermission()

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        binding.imageBack.setOnClickListener {
            navController.popBackStack()
        }

        navView.setupWithNavController(navController)

        viewModel.viewModelScope.launch {
            viewModel.name.collectLatest {
                binding.textCategory.text = it
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_main -> {
                    navView.menu.findItem(R.id.navigation_main).isChecked = true
                    binding.linearCategory.visibility = View.GONE
                    binding.linearCityDate.visibility = View.VISIBLE
                }
                R.id.navigation_dishes -> {
                    binding.linearCategory.visibility = View.VISIBLE
                    binding.linearCityDate.visibility = View.GONE
                    navView.menu.findItem(R.id.navigation_main).isChecked = true
                }
                else -> {
                    binding.linearCategory.visibility = View.GONE
                    binding.linearCityDate.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission(
                this,
                COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProvider?.requestLocationUpdates(
                request,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    override fun onStop() {
        super.onStop()
        fusedLocationProvider?.removeLocationUpdates(locationCallback)
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 99
        private const val COARSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION
    }
}