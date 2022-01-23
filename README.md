<p align="center">
  <img src="https://raw.githubusercontent.com/amrmsaraya/ExpiryTracker/master/app/src/main/res/mipmap-xxxhdpi/ic_launcher_round.png">
</p>

# Expiry Tracker

Android mobile application that track products expiry date and notify user about it.

## Features

- Real-Time product expiry date tracking
- Scan barcode using camera
- Get notified when a product is expired
- Mock expiry date to be 6, 12, 12, 24 hours from now
- Light / Dark theme support (Depends on your mobile theme)

## Libraries and Frameworks

- [Material You](https://m3.material.io) - Design System
- [Splash Screen](https://developer.android.com/reference/android/window/SplashScreen) - Newly
  introduced splash screen API
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Reactive Programming
- [Room](https://developer.android.com/jetpack/androidx/releases/room) - Local Database
- [Hilt](http://google.github.io/hilt/) - Dependency Injection
- [Work Manager](https://developer.android.com/reference/androidx/work/WorkManager) - Schedule
  background tasks
- [Zxing Embedded Android](https://github.com/journeyapps/zxing-android-embedded/) - Barcode Scanner
- [JUnit](https://junit.org/junit4/) - Unit Testing
- [Truth](https://truth.dev/) - Fluent Assertions
- [Mockk](https://mockk.io/) - Mocking

## Architecture and Design Patterns

- [Clean Architecture](https://koenig-media.raywenderlich.com/uploads/2019/02/Clean-Architecture-Bob-650x454.png)
  - Application architecture pattern
	- :app module - Presentation layer that contains UI related code and dependency injection
	- :data module - Data layer that contains DAOs, DTOs, Mapper, Data sources and Repository
	  Implementation
	- :domain module - Business layer that contains Repository interfaces, Models (Entities) and Use
	  cases
	- :buildSrc module - Better management for dependencies using Kotlin-DSL
- [MVI](https://miro.medium.com/max/5152/1*iFis87B9sIfpsgQeFkgu8Q.png) - Model-View-Intent design
  pattern


