
# QR Craft

An Android application built with Jetpack Compose for scanning QR codes. Created as Milestone 1, 2, 3 and 4 of the August–September App Challenge on Philipp Lackner’s Mobile Dev Campus.

<img width="885" height="183" alt="Github Readme Logo QR Craft" src="https://github.com/user-attachments/assets/0b3e12a8-5f41-4bc5-a0a9-a7457f607010" />

## Features

In Milestone 4, the application consists of the following features:
- Splash Screen: Displays the app icon.
  
<img width="350" height="2270" alt="Screenshot_20250816_165820" src="https://github.com/user-attachments/assets/5e48a93d-9461-4a96-9838-f2c859e584a2" />
  
- Scan Screen: Requests camera permission using a custom dialog. If granted, it shows the camera preview with a frame for QR code detection. Once a QR code is recognized, is saved internally and it navigates to the next screen. Alternatively you have a button tu turn on and off the device's flashlight and a button to pick an image from your gallery to scan for a QR Code.
 
<img width="350" height="2270" alt="Screenshot_20250919_115213" src="https://github.com/user-attachments/assets/d64d09db-2138-444e-90a2-9ed6f33e65f4" />

- Scan Result Screen: Displays the scanned QR code and its data in the appropriate format. The app supports links, contacts, phone numbers, geolocations, Wi-Fi credentials, and plain text. Depending on the type, additional features are available, for example, tapping a link opens it in the browser, or long text (more than 6 lines) can be expanded/collapsed with a “Show more/less” button. This screen also includes:
-- a Favourite button to saved this QR Code as Favourite or not
-- a Share and Copy actions
-- a Save button to download the QR Code as a PNG file.
-- and allows to edit a title or use the QR Code type as a default title (which would be saved when navigating to the previous screen).
  
<img width="350" height="2270" alt="Screenshot_20250919_115838" src="https://github.com/user-attachments/assets/d51bcd0d-c8b2-4c2c-8095-695124b684fc" />

- Create QR Screen: From the Scan Screen, you can tap the right button located at the center-bottom component to navigate here. In this screen, you can choose the type of QR Code you want to create.

<img width="350" height="2270" alt="Screenshot_20250828_200553" src="https://github.com/user-attachments/assets/30df55bf-8feb-4b49-a3b8-138cc0460a93" />

- Data Entry Screen: After selecting a QR Code type, this screen allows you to enter the required information specific to that type and then save it internally, navigating to the next screen.

<img width="350" height="2270" alt="Screenshot_20250828_200655" src="https://github.com/user-attachments/assets/511e7c46-3e77-42ac-96a8-211ac5335e44" />

- Preview Screen: This screen is similar to the Scan Result Screen, but with a different title. It provides the same functionalities.

<img width="350" height="2270" alt="Screenshot_20250828_201347" src="https://github.com/user-attachments/assets/59a16517-d13b-432b-998f-aeca086ebed9" />

- Scan History Screen: it shows a tab with all the QR Codes locally saved that were scanned and that were generated order with first the favourites and then the non favourties, from newest to oldest. For each QR Code shows an icon according to its type, its title (custom or default), a preview of its content, the time of creation and the favourite icon to check or uncheck as favourite.

<img width="350" height="2270" alt="Screenshot_20250919_122202" src="https://github.com/user-attachments/assets/3d120cda-7afd-447f-8a37-1c2e8368e811" />

This screen also allows the next actions:
- Tap: Open the Preview Screen
- Long Press: Opesn a bottom sheet with the options to Share (opens the system share sheet) and Delete (removes the item from history).

<img width="350" height="2270" alt="Screenshot_20250919_122551" src="https://github.com/user-attachments/assets/df79d567-3156-4790-aafd-ab5f2a5def29" />

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
* **Room**: library for local SQLite database.

## Roadmap
I will begin work on a final version with some small corrections and improvements.

## Feedback

If you have any feedback, please reach out to me at [LinkedIn](https://www.linkedin.com/in/rnicolasmarin/).
