package wsr.base.presentation.app.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import wsr.base.R
import wsr.base.databinding.LocationBinding

class LocationFragment : Fragment(R.layout.location), LocationListener {

    private val binding: LocationBinding by viewBinding()
    private lateinit var activityResultLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var locationManager: LocationManager

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { isGrantedMap ->
                isGrantedMap.forEach {
                    if (it.value) {
                        Toast.makeText(
                            requireContext(),
                            "Permission ${it.key} granted!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Permission ${it.key} not granted!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBinding()
    }

    private fun setupBinding() = with(binding) {
        permissionButton.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                getPermission()
            } else {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0,
                    0f,
                    this@LocationFragment
                )
            }
        }

    }

    private fun getPermission() {
        activityResultLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    override fun onProviderEnabled(provider: String) {
        Toast.makeText(requireContext(), "enabled: $provider", Toast.LENGTH_SHORT).show()
    }

    override fun onProviderDisabled(provider: String) {
        Toast.makeText(requireContext(), "disabled: $provider", Toast.LENGTH_SHORT).show()
    }

    override fun onLocationChanged(location: Location) {
        binding.currentLocationTextView.text = "latitude: ${location.latitude}, longitude: ${location.longitude}"
    }
}