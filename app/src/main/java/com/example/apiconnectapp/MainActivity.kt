package com.example.apiconnectapp

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var cityInput: EditText
    private lateinit var fetchButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var weatherText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Apply system bar insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        cityInput = findViewById(R.id.cityInput)
        fetchButton = findViewById(R.id.fetchButton)
        progressBar = findViewById(R.id.progressBar)
        weatherText = findViewById(R.id.weatherText)

        fetchButton.setOnClickListener {
            val city = cityInput.text.toString().trim()
            if (city.isNotEmpty()) {
                fetchWeather(city)
            } else {
                Toast.makeText(this, "Please enter a city name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchWeather(city: String) {
        progressBar.visibility = View.VISIBLE
        weatherText.text = ""

        // ✅ Log response status only (no API key)
        Log.d("WeatherDebug", "Fetching weather for: $city")

        RetrofitClient.instance.getWeatherByCity(city, BuildConfig.OPEN_WEATHER_API_KEY)
            .enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                    progressBar.visibility = View.GONE
                    if (response.isSuccessful) {
                        val weather = response.body()
                        val info = "City: ${weather?.name}\n" +
                                "Temp: ${weather?.main?.temp}°C\n" +
                                "Condition: ${weather?.weather?.firstOrNull()?.description}"
                        weatherText.text = info

                        // ✅ Show success toast
                        Toast.makeText(this@MainActivity, "Weather data loaded successfully!", Toast.LENGTH_SHORT).show()

                        // ✅ Log success
                        Log.d("WeatherDebug", "Response code: ${response.code()}")
                        Log.d("WeatherDebug", "Weather info: $info")
                    } else {
                        Toast.makeText(this@MainActivity, "City not found", Toast.LENGTH_SHORT).show()
                        Log.d("WeatherDebug", "Response code: ${response.code()} - City not found")
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    Log.e("WeatherDebug", "API call failed: ${t.message}")
                }
            })
    }
}