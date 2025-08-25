# InternSearchApp (Android, Java, MVVM)

Single-screen address search app for Mobile Intern Test.

## Features
- Address search via LocationIQ REST API
- 1-second debounce on search input
- Highlight matched keywords in results (case-insensitive)
- Tap result to open in Google Maps (geo: intent with fallback to browser)
- MVVM (ViewModel + Repository), Retrofit, LiveData, RecyclerView

## Quick Start
1. Open this project in Android Studio.
2. In `app/build.gradle`, replace `REPLACE_WITH_YOUR_API_KEY` with your **LocationIQ** API key.
   - Or add to `local.properties`: `LOCATIONIQ_API_KEY=your_key` and replace the BuildConfig line accordingly.
3. Sync Gradle and run on device/emulator (API 23+).

## Notes
- Endpoint: `https://us1.locationiq.com/v1/search.php?key=API_KEY&q=QUERY&format=json&limit=20`
- If you prefer HERE API, only replace `RetrofitClient.baseUrl` and `ApiService` accordingly.
