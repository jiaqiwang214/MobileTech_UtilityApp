package au.edu.jcu.assessment.utilityapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val repository = WeatherRepository()

    var useFahrenheit by mutableStateOf(false)
        private set

    var city by mutableStateOf("Singapore")
        private set

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
                val response = repository.getWeather(
                    latitude = 1.3521,
                    longitude = 103.8198
                )

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