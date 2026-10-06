package au.edu.jcu.assessment.utilityapp

import android.content.Context

class SettingsRepository(context: Context) {

    private val preferences = context.getSharedPreferences(
        "weather_settings",
        Context.MODE_PRIVATE
    )

    fun saveTemperatureUnit(useFahrenheit: Boolean) {
        preferences.edit()
            .putBoolean("use_fahrenheit", useFahrenheit)
            .apply()
    }

    fun getTemperatureUnit(): Boolean {
        return preferences.getBoolean(
            "use_fahrenheit",
            false
        )
    }
}