package au.edu.jcu.assessment.utilityapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WeatherRepository()
    private val settingsRepository = SettingsRepository(application)

    var useFahrenheit by mutableStateOf(
        settingsRepository.getTemperatureUnit()
    )
        private set

    var city by mutableStateOf("Singapore")
        private set

    fun updateCity(newCity: String) {
        city = newCity
    }
    var temperature by mutableStateOf(0.0)
        private set

    var feelsLike by mutableStateOf(0.0)
        private set

    var humidity by mutableStateOf(0)
        private set

    var windSpeed by mutableStateOf(0.0)
        private set

    var condition by mutableStateOf("Loading...")
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        loadWeather()
    }

    fun loadWeather() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                val location = repository.searchCity(city)

                if (location == null) {
                    errorMessage = "City not found"
                    return@launch
                }

                val response = repository.getWeather(
                    latitude = location.latitude,
                    longitude = location.longitude
                )

                city = location.name
                temperature = response.current.temperature
                feelsLike = response.current.feelsLike
                humidity = response.current.humidity
                windSpeed = response.current.windSpeed

                condition = getWeatherCondition(
                    response.current.weatherCode
                )

            } catch (e: Exception) {
                errorMessage = "Unable to load weather"
                condition = "Weather unavailable"
            } finally {
                isLoading = false
            }
        }
    }

     fun setTemperatureUnit(useFahrenheit: Boolean) {
        this.useFahrenheit = useFahrenheit
        settingsRepository.saveTemperatureUnit(useFahrenheit)
    }

    private fun getWeatherCondition(code: Int): String {
        return when (code) {
            0 -> "Clear Sky"
            1, 2 -> "Partly Cloudy"
            3 -> "Cloudy"
            45, 48 -> "Fog"
            51, 53, 55 -> "Drizzle"
            61, 63, 65 -> "Rain"
            71, 73, 75 -> "Snow"
            80, 81, 82 -> "Rain Showers"
            95, 96, 99 -> "Thunderstorm"
            else -> "Unknown"
        }
    }
}