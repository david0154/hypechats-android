# 🎉 Hypechats Android App

**Native Android application for the Hypechats social media platform**

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.x-blue)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Latest-green)](https://developer.android.com/jetpack/compose)
[![Material Design 3](https://img.shields.io/badge/Material%20Design-3-purple)](https://m3.material.io)
[![Min SDK](https://img.shields.io/badge/Min%20SDK-24-yellow)](https://developer.android.com/studio)
[![GitHub Stars](https://img.shields.io/github/stars/david0154/hypechats-android?style=flat)](https://github.com/david0154/hypechats-android)

---

## ✨ Features

### Phase 1 - Core (✅ Complete)
- 🔐 **Authentication System**
  - Email/Password login & registration
  - Google OAuth integration
  - Facebook OAuth integration
  - Password reset functionality
  - Secure token storage with Android Keystore

- 📰 **News Feed**
  - Infinite scroll with pagination
  - Real-time post updates
  - Post actions (like, dislike, wonder)
  - Comment system
  - Share functionality

- 👤 **User Profiles**
  - Profile picture & cover image
  - Bio & user information
  - Follower/Following counts
  - Edit profile settings
  - Profile privacy settings

- 🧭 **Navigation**
  - Bottom navigation bar
  - Drawer menu
  - Deep linking support
  - Smooth screen transitions

- 🎨 **UI/UX**
  - Material Design 3 compliance
  - Dark/Light theme support
  - Responsive layouts
  - Smooth animations

### Phase 2 - Social Core (🔄 In Progress)
- [ ] Friends & Follow System
- [ ] Profile Visit Notifications
- [ ] User Last Seen
- [ ] Comments & Replies
- [ ] Likes / Dislike Reactions
- [ ] Wonder (Unique Feature)
- [ ] Post Publisher
- [ ] Delete & Edit Posts
- [ ] Save Posts
- [ ] Post Privacy Settings
- [ ] @Mentions
- [ ] #Hashtags
- [ ] Verified Profiles/Pages Badge
- [ ] User Timeline
- [ ] User Privacy Settings

### Upcoming Phases (📅 Planned)
- Phase 3: Content & Media (Video, Photo Albums)
- Phase 4: Groups & Communities
- Phase 5: Events & Calendar
- Phase 6: Messaging & Communication (WebSocket Chat)
- Phase 7: Video Calls & Live Streaming (Agora SDK)
- Phase 8: Marketplace (eCommerce)
- Phase 9: Advanced Search & Discovery
- Phase 10: Stories & Content
- Phase 11: Internationalization (Multi-language)
- Phase 12: Advanced Authentication (2FA, Biometric)
- Phase 13: URL Sharing & Deep Links
- Phase 14: Analytics & Admin Dashboard
- Phase 15: Games & AI Features

---

## 🚀 Quick Start

### Prerequisites
- Android Studio Giraffe or later
- Kotlin 1.9.x
- Java 17+
- Your Hypechats API server running

### Installation

1. **Clone Repository**
```bash
git clone https://github.com/david0154/hypechats-android.git
cd hypechats-android
```

2. **Configure API**
   - Edit `app/src/main/java/com/nexuzy/hypechats/util/ApiConfig.kt`
   - Add your server URL: `https://your-domain.com/`

3. **Setup Firebase**
   - Download `google-services.json` from Firebase Console
   - Place in `app/` directory

4. **Build & Run**
```bash
./gradlew assembleDebug
./gradlew installDebug
```

**Full Setup Guide:** See [SETUP_GUIDE.md](SETUP_GUIDE.md)

---

## 📋 Project Structure

```
hypechats-android/
├── app/
│   ├── src/main/java/com/nexuzy/hypechats/
│   │   ├── data/
│   │   │   ├── model/          # API response models
│   │   │   ├── api/            # Retrofit services
│   │   │   ├── db/             # Room database
│   │   │   └── repository/     # Repository pattern
│   │   ├── ui/
│   │   │   ├── screens/        # Complete screens
│   │   │   ├── components/     # Reusable Compose components
│   │   │   ├── navigation/     # Navigation setup
│   │   │   └── theme/          # Material Design 3 theme
│   │   ├── viewmodel/          # MVVM ViewModels
│   │   ├── util/               # Utilities & Constants
│   │   └── HypechatsApp.kt     # Main app class
│   ├── build.gradle.kts        # Gradle dependencies
│   └── proguard-rules.pro      # ProGuard rules
├── build.gradle.kts
├── settings.gradle.kts
├── SETUP_GUIDE.md              # Detailed setup instructions
└── README.md
```

---

## 🏗️ Tech Stack

### UI & Presentation
- **Jetpack Compose** - Modern UI framework
- **Material Design 3** - Latest Material design system
- **MVVM Architecture** - Clean separation of concerns
- **Navigation Compose** - Type-safe navigation

### Networking & Data
- **Retrofit 2** - REST API client
- **OkHttp 3** - HTTP client with interceptors
- **Gson** - JSON serialization/deserialization
- **Coil** - Image loading library

### Local Data
- **Room Database** - Local SQLite persistence
- **DataStore** - Encrypted key-value storage
- **Android Keystore** - Secure credential storage

### Async & Concurrency
- **Kotlin Coroutines** - Asynchronous operations
- **Flow** - Reactive streams
- **StateFlow** - State management

### Authentication & Security
- **Firebase Authentication** - OAuth providers
- **EncryptedSharedPreferences** - Secure storage
- **BiometricPrompt** - Fingerprint/Face unlock

### Other
- **Firebase Cloud Messaging** - Push notifications
- **Google Play Services** - Location & device info
- **WorkManager** - Background tasks

---

## 🔒 Security

- ✅ All API calls over HTTPS
- ✅ Tokens stored in Android Keystore
- ✅ Certificate pinning implemented
- ✅ Encrypted SharedPreferences
- ✅ Proguard/R8 code obfuscation
- ✅ No sensitive data in logs
- ✅ OWASP compliance

---

## 📱 App Requirements

| Requirement | Version |
|-------------|---------|
| Min SDK | 24 (Android 7.0) |
| Target SDK | 35 (Android 15) |
| Kotlin | 1.9.x |
| Gradle | 8.0+ |
| Java | 17+ |

---

## 🛠️ Development

### Build Variants
- **Debug** - Development with Logcat logging
- **Release** - Production optimized with ProGuard

### Build APK
```bash
# Debug APK
./gradlew assembleDebug

# Release APK (requires keystore)
./gradlew assembleRelease
```

### Run Tests
```bash
# Unit tests
./gradlew test

# UI tests
./gradlew connectedAndroidTest

# Code quality
./gradlew lint
```

### Code Formatting
```bash
./gradlew spotlessApply
```

---

## 📊 Architecture

```
┌─────────────────────────────────┐
│         User Interface          │
│  (Jetpack Compose Screens)      │
└────────────┬────────────────────┘
             │
┌────────────▼────────────────────┐
│      ViewModel Layer            │
│  (MVVM Architecture)            │
└────────────┬────────────────────┘
             │
┌────────────▼────────────────────┐
│   Repository Layer              │
│  (Data abstraction)             │
└────────────┬────────────────────┘
             │
      ┌──────┴──────┐
      │             │
┌─────▼──────┐  ┌──▼────────────┐
│ Remote Data│  │ Local Data     │
│ (API)      │  │ (Room DB)      │
└────────────┘  └────────────────┘
```

---

## 🚀 Deployment

### Google Play Store
1. Generate signed release APK
2. Upload to Google Play Console
3. Set store listing details
4. Submit for review (~2-4 hours)

### Direct Distribution
```bash
./gradlew assembleRelease
# APK at: app/build/outputs/apk/release/
```

---

## 📚 Documentation

- [Setup Guide](SETUP_GUIDE.md) - Installation & configuration
- [API Documentation](Documentation.html) - API endpoints reference
- [Architecture Guide](docs/ARCHITECTURE.md) - Project structure & patterns
- [Contributing Guidelines](docs/CONTRIBUTING.md) - How to contribute

---

## 🐛 Troubleshooting

| Issue | Solution |
|-------|----------|
| Build fails | Run `./gradlew clean` then rebuild |
| API errors | Check `ApiConfig.BASE_URL` and server status |
| Firebase not working | Ensure `google-services.json` is in `app/` |
| OAuth fails | Verify API keys and SHA-1 certificate |

See [SETUP_GUIDE.md](SETUP_GUIDE.md#troubleshooting) for more details.

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/amazing-feature`
3. Commit changes: `git commit -m 'Add amazing feature'`
4. Push to branch: `git push origin feature/amazing-feature`
5. Open a Pull Request

See [CONTRIBUTING.md](docs/CONTRIBUTING.md) for detailed guidelines.

---

## 📝 Changelog

### v1.0.0 - Initial Release
- Phase 1 features complete
- Material Design 3 implementation
- Authentication system
- News feed
- User profiles
- Navigation

---

## 📄 License

This project is licensed under the MIT License - see [LICENSE](LICENSE) file for details.

---

## 👨‍💻 Authors

- **David K** - Initial development
- Part of **Nexuzy Tech** projects

---

## 🙏 Acknowledgments

- WoWonder team for API structure
- Material Design 3 documentation
- Jetpack Compose community
- Firebase team

---

## 📞 Support

- 📧 **Email:** support@hypechats.com
- 🐛 **Issues:** [GitHub Issues](https://github.com/david0154/hypechats-android/issues)
- 💬 **Discussions:** [GitHub Discussions](https://github.com/david0154/hypechats-android/discussions)

---

## 🌟 Star History

[![Star History Chart](https://api.star-history.com/svg?repos=david0154/hypechats-android&type=Date)](https://star-history.com/#david0154/hypechats-android&Date)

---

**Made with ❤️ for the Hypechats Community**

**Last Updated:** January 9, 2026  
**Status:** 🟢 Active Development
