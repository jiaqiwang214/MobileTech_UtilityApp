package au.edu.jcu.assessment.utilityapp

data class GeocodingResponse(
    val results: List<LocationResult>?
)

data class LocationResult(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String?
)