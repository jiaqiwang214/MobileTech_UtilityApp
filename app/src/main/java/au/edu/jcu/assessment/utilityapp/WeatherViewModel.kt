package au.edu.jcu.assessment.utilityapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class WeatherViewModel : ViewModel() {

    var useFahrenheit by mutableStateOf(false)
        private set

    var city by mutableStateOf("Singapore")
        private set

    var temperature by mutableStateOf(30)
        private set

    var feelsLike by mutableStateOf(34)
        private set

    var humidity by mutableStateOf(75)
        private set

    var windSpeed by mutableStateOf(12)
        private set

    var condition by mutableStateOf("Partly Cloudy")
        private set

    fun setTemperatureUnit(useFahrenheit: Boolean) {
        this.useFahrenheit = useFahrenheit
    }
}