# BloodConnect

BloodConnect is an Android application designed to improve communication between blood donors, hospitals, and patients in urgent donation scenarios.

## Features

- Secure login and user access flows
- Real-time announcements and request updates
- Donor and hospital matching workflows
- Location-aware features to improve coordination
- Firebase-based data handling
- Modular structure built with MVVM architecture

## Technologies

- Kotlin
- Android Studio
- Firebase
- Hilt
- MVVM
- Location Services
- RecyclerView
- Navigation Component

## Setup

This project uses Firebase. For security reasons, the `google-services.json`
configuration file is **not** included in the repository.

To build and run the app:

1. Clone the repository.
2. Create (or open) the Firebase project for this app and register an Android
   app with the package name `com.softwarengineering.bloodconnect`.
3. Download the generated `google-services.json` from the Firebase console.
4. Place it in the `app/` directory (`app/google-services.json`).
5. Build and run the project in Android Studio.

> `google-services.json` is listed in `.gitignore` and must never be committed.

## Purpose

This project was developed to support faster and more organized communication in blood donation processes by combining secure mobile access, real-time updates, and practical coordination features.
