package au.edu.jcu.assessment.utilityapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRepository {

    private val api: WeatherApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(WeatherApiService::class.java)
    }

    suspend fun getWeather(
        latitude: Double,
        longitude: Double
    ): WeatherResponse {
        return api.getWeather(
            latitude = latitude,
            longitude = longitude
        )
    }
}