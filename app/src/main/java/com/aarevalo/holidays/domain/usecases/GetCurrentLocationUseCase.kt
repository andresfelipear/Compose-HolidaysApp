package com.aarevalo.holidays.domain.usecases

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import com.aarevalo.holidays.domain.model.Country
import com.aarevalo.holidays.domain.model.State
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@Suppress("DEPRECATION")
class GetCurrentLocationUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    private val geocoder = Geocoder(context, Locale.getDefault())

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(): Pair<Country, State> {
        return try {
            val location = getLastLocation()
            if (location != null) {
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                val address = addresses?.firstOrNull()
                val country = Country(
                    name = address?.countryName ?: "Canada",
                    code = address?.countryCode ?: "CA"
                )
                val state = State(
                    name = address?.adminArea ?: "British Columbia",
                    code = State.getCountryStateFromName(address?.adminArea) ?: "BC"
                )
                println("Country: ${country.name}-${country.code}, State: ${state.name}-${state.code}")
                Pair(country, state)
            } else {
                Pair(Country(name = "Canada", code = "CA"), State(name = "British Columbia", code = "BC"))
            }
        } catch (e: Exception) {
            println("Error Fetching Location: ${e.message}")
            Pair(Country(name = "Canada", code = "CA"), State(name = "British Columbia", code = "BC"))
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun getLastLocation() = suspendCancellableCoroutine<Location?> { continuation ->
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            continuation.resume(location)
        }.addOnFailureListener { exception ->
            continuation.resumeWithException(exception)
        }
    }
}
