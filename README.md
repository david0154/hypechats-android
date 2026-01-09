# Hypechats Android App

A modern Android social media app built with Jetpack Compose, Kotlin, and Retrofit.

## Features

- User Authentication (Login/Signup)
- Feed with Posts
- User Profiles
- Follow/Unfollow Users
- Comments and Likes

## Tech Stack

- **UI**: Jetpack Compose
- **Navigation**: Compose Navigation
- **Networking**: Retrofit + OkHttp
- **Dependency Injection**: Hilt
- **Database**: Room
- **Image Loading**: Coil
- **Preferences**: DataStore

## Getting Started

1. Clone the repository
2. Open in Android Studio
3. Update API configuration in config files
4. Build and run

## Project Structure

```
app/src/main/java/com/nexuzy/hypechats/
├── ui/
│   ├── screens/
│   ├── navigation/
│   └── theme/
├── data/
│   ├── api/
│   ├── local/
│   ├── model/
│   └── repository/
├── MainActivity.kt
└── HypechatsApp.kt
```

## API Configuration

Update the API base URL in your configuration files.

## License

MIT License
