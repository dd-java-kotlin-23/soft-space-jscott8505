# Firebase setup

1. Create or select a Firebase project and register an Android application with package name
   `edu.cnm.deepdive.softspace`.
2. Download its `google-services.json` into `app/google-services.json`. Do not reuse another app's
   configuration file; its package registration will not match.
3. In Firebase Authentication, enable the Email/Password provider.
4. Create a Cloud Firestore database and deploy the repository's `firestore.rules` using the
   Firebase CLI or Firebase Console.
5. Rebuild and run the app. Without the configuration file, the project still compiles and shows a
   setup message, but Firebase operations intentionally fail.

The Java implementation contains `Kotlin migration` comments at the service, repository, view-model,
fragment, and model boundaries. They identify direct Kotlin replacements such as data classes,
coroutine `await()`, `StateFlow`, `viewModelScope`, and Fragment KTX delegates.
