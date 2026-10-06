package au.edu.jcu.assessment.utilityapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import au.edu.jcu.assessment.utilityapp.ui.theme.MobileTech_UtilityAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobileTech_UtilityAppTheme {
                UtilityApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UtilityAppPreview() {
    MobileTech_UtilityAppTheme {
        UtilityApp()
    }
}

@Composable
fun UtilityApp() {
    var selectedTab by remember { mutableStateOf("Utility") }
    var useFahrenheit by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Utility") },
                    label = { Text("Utility") },
                    selected = selectedTab == "Utility",
                    onClick = { selectedTab = "Utility" }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") },
                    selected = selectedTab == "Settings",
                    onClick = { selectedTab = "Settings" }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTab) {
                "Utility" -> UtilityScreen(useFahrenheit)
                "Settings" -> SettingsScreen(
                    useFahrenheit = useFahrenheit,
                    onUnitChange = { useFahrenheit = it }
                )
            }
        }
    }
}

@Composable
fun UtilityScreen(useFahrenheit: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Weather Utility",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Singapore",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = if (useFahrenheit) "☀️ 86°F" else "☀️ 30°C",
            style = MaterialTheme.typography.displayMedium
        )

        Text(
            text = "Partly Cloudy",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            if (useFahrenheit) "Feels like: 93°F" else "Feels like: 34°C"
        )

        Text("Humidity: 75%")
        Text("Wind: 12 km/h")
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
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Temperature Unit",
            style = MaterialTheme.typography.titleMedium
        )

        Button(
            onClick = { onUnitChange(false) }
        ) {
            Text(
                if (!useFahrenheit) "✓ Celsius (°C)" else "Celsius (°C)"
            )
        }

        Button(
            onClick = { onUnitChange(true) }
        ) {
            Text(
                if (useFahrenheit) "✓ Fahrenheit (°F)" else "Fahrenheit (°F)"
            )
        }
    }
}