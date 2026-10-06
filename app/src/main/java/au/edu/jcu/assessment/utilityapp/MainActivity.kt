package au.edu.jcu.assessment.utilityapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import au.edu.jcu.assessment.utilityapp.ui.theme.MobileTech_UtilityAppTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MobileTech_UtilityAppTheme {

                val weatherViewModel: WeatherViewModel = viewModel()

                UtilityApp(weatherViewModel)
            }
        }
    }
}


@Composable
fun UtilityApp(viewModel: WeatherViewModel) {

    var selectedTab by remember {
        mutableStateOf("Utility")
    }

    Scaffold(
        bottomBar = {
            NavigationBar {

                NavigationBarItem(
                    icon = {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = "Utility"
                        )
                    },
                    label = {
                        Text("Utility")
                    },
                    selected = selectedTab == "Utility",
                    onClick = {
                        selectedTab = "Utility"
                    }
                )

                NavigationBarItem(
                    icon = {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = "Settings"
                        )
                    },
                    label = {
                        Text("Settings")
                    },
                    selected = selectedTab == "Settings",
                    onClick = {
                        selectedTab = "Settings"
                    }
                )
            }
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier.padding(innerPadding)
        ) {

            when (selectedTab) {

                "Utility" -> {
                    UtilityScreen(viewModel)
                }

                "Settings" -> {
                    SettingsScreen(
                        useFahrenheit = viewModel.useFahrenheit,
                        onUnitChange = {
                            viewModel.setTemperatureUnit(it)
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun UtilityScreen(viewModel: WeatherViewModel) {

    val useFahrenheit = viewModel.useFahrenheit

    var cityInput by rememberSaveable {
        mutableStateOf("")
    }

    val weatherIcon = when {

        viewModel.condition.contains(
            "snow",
            ignoreCase = true
        ) -> "❄️"

        viewModel.condition.contains(
            "rain",
            ignoreCase = true
        ) -> "🌧️"

        viewModel.condition.contains(
            "cloud",
            ignoreCase = true
        ) -> "☁️"

        viewModel.condition.contains(
            "clear",
            ignoreCase = true
        ) -> "☀️"

        else -> "🌤️"
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),

        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Weather Utility",
            style = MaterialTheme.typography.headlineMedium
        )


        // City input
        OutlinedTextField(
            value = cityInput,

            onValueChange = {
                cityInput = it
            },

            label = {
                Text("Enter city")
            },

            singleLine = true,

            modifier = Modifier.fillMaxWidth()
        )


        // Search button
        Button(
            onClick = {

                if (cityInput.isNotBlank()) {

                    viewModel.updateCity(
                        cityInput.trim()
                    )

                    viewModel.loadWeather()

                    cityInput = ""
                }
            },

            enabled =
                cityInput.isNotBlank() &&
                        !viewModel.isLoading,

            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Search City")
        }


        // City name
        Text(
            text = viewModel.city,
            style = MaterialTheme.typography.titleLarge
        )


        // Weather information card
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp),

                verticalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {

                // Temperature
                Text(
                    text = if (useFahrenheit) {

                        "$weatherIcon ${
                            String.format(
                                "%.1f",
                                viewModel.temperature * 9 / 5 + 32
                            )
                        }°F"

                    } else {

                        "$weatherIcon ${
                            String.format(
                                "%.1f",
                                viewModel.temperature
                            )
                        }°C"
                    },

                    style =
                        MaterialTheme.typography.displayMedium
                )


                // Weather condition
                Text(
                    text = viewModel.condition,
                    style =
                        MaterialTheme.typography.titleMedium
                )


                // Feels like
                Text(
                    text = if (useFahrenheit) {

                        "Feels like: ${
                            String.format(
                                "%.1f",
                                viewModel.feelsLike * 9 / 5 + 32
                            )
                        }°F"

                    } else {

                        "Feels like: ${
                            String.format(
                                "%.1f",
                                viewModel.feelsLike
                            )
                        }°C"
                    }
                )


                Text(
                    "Humidity: ${viewModel.humidity}%"
                )

                Text(
                    "Wind: ${viewModel.windSpeed} km/h"
                )


                if (viewModel.isLoading) {
                    Text("Updating weather...")
                }


                if (viewModel.errorMessage != null) {

                    Text(
                        viewModel.errorMessage!!
                    )
                }
            }
        }


        // Refresh button
        Button(
            onClick = {
                viewModel.loadWeather()
            },

            enabled = !viewModel.isLoading,

            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                if (viewModel.isLoading)
                    "Updating..."
                else
                    "Refresh Weather"
            )
        }
    }
}


@Composable
fun SettingsScreen(
    useFahrenheit: Boolean,
    onUnitChange: (Boolean) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        verticalArrangement =
            Arrangement.spacedBy(20.dp)
    ) {

        Text(
            text = "Settings",
            style =
                MaterialTheme.typography.headlineMedium
        )


        Text(
            text = "Temperature Unit",
            style =
                MaterialTheme.typography.titleLarge
        )


        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(20.dp),

                verticalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = if (useFahrenheit)

                        "Current unit: Fahrenheit (°F)"

                    else

                        "Current unit: Celsius (°C)"
                )


                Button(
                    onClick = {

                        onUnitChange(
                            !useFahrenheit
                        )
                    },

                    modifier =
                        Modifier.fillMaxWidth()
                ) {

                    Text(
                        if (useFahrenheit)

                            "Switch to Celsius"

                        else

                            "Switch to Fahrenheit"
                    )
                }
            }
        }


        Text(
            text = "Weather data for selected city",
            style =
                MaterialTheme.typography.bodyMedium
        )
    }
}