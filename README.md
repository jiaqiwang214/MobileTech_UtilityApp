# MobileTech Utility App

This is a Weather Utility Android application developed for **Assessment 1: Utility App**.

The application allows users to search for a city and view current weather information. It uses **Jetpack Compose**, **Material Design 3**, **ViewModel**, **Retrofit**, and weather/geocoding APIs.

---

## Getting Started

### How to Run

1. Clone or download this repository
2. Open the project in Android Studio
3. Wait for Gradle Sync to complete
4. Run the application on an Android emulator or physical device
5. Make sure the device has an Internet connection

---

## Main Features

- View current weather information
- Search weather by city name
- Display temperature and feels-like temperature
- Display weather condition
- Display humidity
- Display wind speed
- Weather condition icons
- Switch between Celsius (°C) and Fahrenheit (°F)
- Save the selected temperature unit
- Refresh weather data
- Display loading and error messages
- Handle invalid city searches without crashing

---

## Screens

### Utility Screen

The Utility screen is the main screen of the application.

Users can:

- Enter a city name
- Search for weather information
- View the current temperature
- View the weather condition
- View the feels-like temperature
- View humidity and wind speed
- Refresh the weather information

### Settings Screen

The Settings screen allows users to:

- Switch between Celsius and Fahrenheit
- Save the selected temperature unit

The selected temperature unit remains after the application is closed and reopened.

---

## Architecture

The application separates the user interface, application state, and data access.

### MainActivity.kt

Contains the Jetpack Compose user interface and navigation between the Utility and Settings screens.

### WeatherViewModel.kt

Manages weather data and application state.

It handles:

- Weather loading
- City selection
- Temperature unit selection
- Loading state
- Error state

### WeatherRepository.kt

Handles communication with the weather and geocoding services.

### WeatherApiService.kt

Defines the Retrofit API request for weather information.

### GeocodingApiService.kt

Defines the API request used to convert a city name into geographic coordinates.

### SettingsRepository.kt

Stores the user's temperature unit preference so that the setting remains after the application is restarted.

---

## Technologies Used

| Technology | Purpose |
|------------|---------|
| Kotlin | Application programming language |
| Android Studio | Development environment |
| Jetpack Compose | User interface |
| Material Design 3 | UI components and styling |
| ViewModel | State management |
| Retrofit | API communication |
| Gson | JSON data conversion |
| Open-Meteo | Weather data |
| Geocoding API | City search and coordinates |
| SharedPreferences | Persistent temperature unit setting |
| Git & GitHub | Version control and source code management |

---

## Key Concepts Covered

| Concept | Implementation |
|---------|----------------|
| Kotlin | Application logic |
| Jetpack Compose | Utility and Settings screens |
| Material Design 3 | Buttons, Cards, Navigation and Text Fields |
| ViewModel | WeatherViewModel |
| Retrofit | Weather and geocoding API requests |
| State Management | Weather data, loading and error states |
| Persistent Settings | Celsius/Fahrenheit preference |
| Error Handling | Invalid city and API errors |
| Git Version Control | Commits and GitHub repository |

---

## Error Handling

The application handles errors such as invalid city searches.

For example, when a city cannot be found, the application displays:

`City not found`

instead of crashing.

---

## Future Improvements

Possible future improvements include:

- Weather forecasts for multiple days
- GPS-based current location
- More detailed weather information
- Improved weather icons and UI design
- Search history
- Dark mode support

---

## License

This project was developed for educational purposes as part of **CP3406 Assessment 1**.