# AnixDataStore

An Android app that implements a tag-driven media datastore (Stars, Links, Playlists).

Tech stack:
- Kotlin + AndroidX
- Room for persistence
- XML layouts

Project structure: see `app/src/main/java` and `app/src/main/res`.

How to run:
1. Open the project in Android Studio.
2. Build and run on an Android device or emulator (minSdk 33).

Notes:
- Tag categories and tags must be created (via your own UI or by code) before adding items requiring tags.
- Filtering uses intersection (AND) logic across selected tags.
