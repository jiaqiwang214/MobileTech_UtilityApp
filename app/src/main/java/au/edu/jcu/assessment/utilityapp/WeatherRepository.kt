package au.edu.jcu.assessment.utilityapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRepository {

    private val weatherApi: WeatherApiService
    private val geocodingApi: GeocodingApiService

    init {
        val weatherRetrofit = Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        weatherApi = weatherRetrofit.create(WeatherApiService::class.java)

        val geocodingRetrofit = Retrofit.Builder()
            .baseUrl("https://geocoding-api.open-meteo.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        geocodingApi =
            geocodingRetrofit.create(GeocodingApiService::class.java)
    }

    suspend fun getWeather(
        latitude: Double,
        longitude: Double
    ): WeatherResponse {
        return weatherApi.getWeather(
            latitude = latitude,
            longitude = longitude
        )
    }

    suspend fun searchCity(cityName: String): LocationResult? {
        val response = geocodingApi.searchCity(cityName)
        return response.results?.firstOrNull()
    }
}