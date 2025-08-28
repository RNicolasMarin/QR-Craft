
# QR Craft

An Android application built with Jetpack Compose for scanning QR codes. Created as Milestone 1 and 2 of the August–September App Challenge on Philipp Lackner’s Mobile Dev Campus.

<img width="885" height="183" alt="Github Readme Logo QR Craft" src="https://github.com/user-attachments/assets/0b3e12a8-5f41-4bc5-a0a9-a7457f607010" />

## Features

In Milestone 2, the application consists of the following features:
- Splash Screen: Displays the app icon.
  
<img width="350" height="2270" alt="Screenshot_20250816_165820" src="https://github.com/user-attachments/assets/5e48a93d-9461-4a96-9838-f2c859e584a2" />
  
- Scan Screen: Requests camera permission using a custom dialog. If granted, it shows the camera preview with a frame for QR code detection. Once a QR code is recognized, it navigates to the next screen.
<img width="350" height="2270" alt="Screenshot_20250828_200155" src="https://github.com/user-attachments/assets/fedadc4b-925c-428c-b447-78008d74727b" />

- Scan Result Screen: Displays the scanned QR code and its data in the appropriate format. The app supports links, contacts, phone numbers, geolocations, Wi-Fi credentials, and plain text. Depending on the type, additional features are available—for example, tapping a link opens it in the browser, or long text (more than 6 lines) can be expanded/collapsed with a “Show more/less” button. This screen also includes Share and Copy actions.

<img width="350" height="2270" alt="Screenshot_20250816_170209" src="https://github.com/user-attachments/assets/f57c24cd-5e4f-469a-98fb-1644848c16bb" />

- Create QR Screen: From the Scan Screen, you can tap the right button located at the center-bottom component to navigate here. In this screen, you can choose the type of QR Code you want to create.

<img width="350" height="2270" alt="Screenshot_20250828_200553" src="https://github.com/user-attachments/assets/30df55bf-8feb-4b49-a3b8-138cc0460a93" />

- Data Entry Screen: After selecting a QR Code type, this screen allows you to enter the required information specific to that type.

<img width="350" height="2270" alt="Screenshot_20250828_200655" src="https://github.com/user-attachments/assets/511e7c46-3e77-42ac-96a8-211ac5335e44" />

- QR Code Preview: This screen is similar to the Scan Result Screen, but with a different title. It provides the same functionalities.

<img width="350" height="2270" alt="Screenshot_20250828_201347" src="https://github.com/user-attachments/assets/59a16517-d13b-432b-998f-aeca086ebed9" />

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
I will begin working on Milestone 3 at the beginning of September. After that, there will be one final milestone.

## Feedback

If you have any feedback, please reach out to me at [LinkedIn](https://www.linkedin.com/in/rnicolasmarin/).
