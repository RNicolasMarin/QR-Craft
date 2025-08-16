
# QR Craft

An Android application built with Jetpack Compose for scanning QR codes. Created as Milestone 1 of the August–September App Challenge on Philipp Lackner’s Mobile Dev Campus.

<img width="885" height="183" alt="Github Readme Logo QR Craft" src="https://github.com/user-attachments/assets/0b3e12a8-5f41-4bc5-a0a9-a7457f607010" />

## Features

In Milestone 1, the application consists of the following features:
- Splash Screen: Displays the app icon.
  
<img width="350" height="2270" alt="Screenshot_20250816_165820" src="https://github.com/user-attachments/assets/5e48a93d-9461-4a96-9838-f2c859e584a2" />
  
- Scan Screen: Requests camera permission using a custom dialog. If granted, it shows the camera preview with a frame for QR code detection. Once a QR code is recognized, it navigates to the next screen.

<img width="350" height="2270" alt="Screenshot_20250816_170143" src="https://github.com/user-attachments/assets/35524dcd-142e-43e6-a5c3-ad2d24b912a3" />

- Scan Result Screen: Displays the scanned QR code and its data in the appropriate format. The app supports links, contacts, phone numbers, geolocations, Wi-Fi credentials, and plain text. Depending on the type, additional features are available—for example, tapping a link opens it in the browser, or long text (more than 6 lines) can be expanded/collapsed with a “Show more/less” button. This screen also includes Share and Copy actions.

<img width="350" height="2270" alt="Screenshot_20250816_170209" src="https://github.com/user-attachments/assets/f57c24cd-5e4f-469a-98fb-1644848c16bb" />



Additionally, the application supports responsive layouts for two screen sizes:
- Up to 600dp width
- 600dp and above

## Built with

* **Kotlin**: programming language used to build the app.
* **Compose**: modern declarative Toolkit for creating the app's UI.
* **MVVM**: architecture pattern the app is built with.
* **Viewmodel**: to keep the data shown on the screen.
* **State**: used on the viewmodels to keep the data that is observed for the composables and it's changes.
* **Channel**: used on the viewmodels to send on time events to the UI.
* **JUnit**: library used for Unit Testing on the detection of the type of QR.
* **Truth**: library used for assertions on unit tests.
* **CompositionLocalProvider**: used to have available some values without need to pass them down through each Composable.
* **.kts**: Kotlin script, a file type for writing the build.gradle file.
* **SplashScreen**: used to display the app icon while the application is starting..
* **Navigation**: handles navigation between different screens in the app.
* **Koin**: provides dependency injection for class instantiation and lifecycle management.
* **CameraX**: displays the device’s camera feed within the app..
* **ML Kit Barcode Scanning**: detects and extracts content from QR codes.
* **ZXing**: generates QR codes from text and other data formats.

## Roadmap
I’m currently working on Milestone 2, which will add the ability to create your own QR codes. After that, there will be two more milestones.

## Feedback

If you have any feedback, please reach out to me at [LinkedIn](https://www.linkedin.com/in/rnicolasmarin/).
