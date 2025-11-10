# Short Reflection

## 1. What API did you choose and why?
I chose the OpenWeatherMap API because it’s beginner-friendly, well-documented, and provides useful weather data that’s easy to integrate into a mobile app.

## 2. How did you implement data fetching and JSON parsing?
I used Retrofit to handle the API requests and Gson to automatically parse the JSON response into Kotlin data classes.

## 3. What challenges did you face when handling errors or slow connections?
One challenge I faced was making sure the API key loaded correctly from local.properties. At first, it returned null but I fixed it by adjusting the build.gradle.kts file to properly inject the key. I also added error handling for failed responses and slow connections using Toast messages and Logcat logs to help with debugging and user feedback.

## 4. How would you improve your app's UI or performance in future versions?
I’d improve the UI by adding weather icons, animations, and a cleaner layout. For performance, I’d consider caching data and optimizing network calls to reduce loading time.
