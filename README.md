# QR Craft
An Android application built with Jetpack Compose for scanning QR codes. Created as Milestones 1, 2, 3, and 4 of the August–September App Challenge on Philipp Lackner’s Mobile Dev Campus.

This app allows scanning QR codes of the following types: Text, Link, Contact, Phone Number, Geolocation, or Wi-Fi.

<img width="885" height="183" alt="Github Readme Logo QR Craft" src="https://github.com/user-attachments/assets/0b3e12a8-5f41-4bc5-a0a9-a7457f607010" />

## Features
The application includes the following features/screens:

### Splash Screen: 
Displays the app’s icon.

<div style="text-align: center;">
  <img src="https://github.com/user-attachments/assets/39f9655f-88f2-4ce5-8043-e8c55c8f0a4b" width="30%" style="margin-right: 5%;" />
  <img src="https://github.com/user-attachments/assets/22905bae-317f-4c17-ae0a-971a7c449ddf" width="30%" />
</div>
  
### Scan Screen:
When opening, it checks if the app has permission to use the Camera:
- If the permission wasn’t granted, it requests that permission using a custom dialog.
- If the permission is just granted, it shows a snackbar; if it’s not granted, it closes the app.

<div style="text-align: center;">
  <img src="https://github.com/user-attachments/assets/2688c746-7ad6-467a-ab38-94ac73ab22ed" width="30%" style="margin-right: 5%;" />
  <img src="https://github.com/user-attachments/assets/659debf8-d93f-4c6a-b9c0-6f49e9966d29" width="30%" />
</div>

Once the permission is granted, it shows:
- a full-screen camera preview.
- a frame and label indicating where it is constantly scanning for QR codes.
- a button to turn the device’s flashlight on and off.
- a button to pick an image from your gallery to scan for a QR code.
- a navigation component to open the History or Create screens.

<div style="text-align: center;">
  <img src="https://github.com/user-attachments/assets/ebcc94f0-cd66-4d0f-8f0c-ab22c8453d98" width="30%" style="margin-right: 5%;" />
  <img src="https://github.com/user-attachments/assets/dda90ac0-4d82-4f26-a7f8-bf76c514aac5" width="30%" />
</div>

When it has an image to scan (from the camera or from picking an image file from the device), it shows a loading effect while it tries to identify any QR code. Then:
- If it fails, it shows a "No QR codes found" message that disappears after a few seconds to try again.
- If it succeeds, the QR code info is saved internally and it navigates to the next screen.

<div style="text-align: center;">
  <img src="https://github.com/user-attachments/assets/36f40d97-cd10-4fb1-a66b-ae6ba74d7112" width="30%" style="margin-right: 5%;" />
  <img src="https://github.com/user-attachments/assets/3a1f7642-435e-4a90-b309-c82add84f7cc" width="30%" />
</div>

<div style="text-align: center;">
  <img src="https://github.com/user-attachments/assets/d224abc0-7da8-41ea-a5d7-bd7b09be908d" width="30%" style="margin-right: 5%;" />
  <img src="https://github.com/user-attachments/assets/13fe652c-6eec-4bc8-bf19-9bb7b5edabf6" width="30%" />
</div>

### Scan Result Screen:
This screen displays the info from the detected QR Code and consists of the following:
- a toolbar with a back button, the name of the screen, and a Favourite button that shows whether this QR code is a favourite or not, and allows changing it.
- an image with the QR displayed.
- the title of this QR Code if it has been written, or the type of QR code depending on the information scanned (when tapping it, you can write or edit the custom title).
- the content of the QR Code in the appropriate format depending on the type.
- a Share button that opens the system sharing sheet.
- a Copy button that copies the QR Code content to the clipboard.
- a Save button that downloads the QR Code as a PNG file and shows a message if it succeeds or fails.

Depending on the type, additional features are available. For example, tapping a link opens it in the browser, or long text (more than 6 lines) can be expanded/collapsed with a "Show more/less" button.
When leaving this screen, the QR Code is updated if the title or its favourite status has been changed.

<div style="text-align: center;">
  <img src="https://github.com/user-attachments/assets/948ca64e-18f2-4ec2-aac5-2fcc72693e4c" width="30%" style="margin-right: 5%;" />
  <img src="https://github.com/user-attachments/assets/6622c5a4-f187-4999-9b2d-900b0549dc82" width="30%" />
</div>

<div style="text-align: center;">
  <img src="https://github.com/user-attachments/assets/967408d8-bcfc-42a4-9c03-3cc9cea5d3d4" width="30%" style="margin-right: 5%;" />
  <img src="https://github.com/user-attachments/assets/bcbb091c-2efc-407c-a99a-2dfecf3b2823" width="30%" />
</div>

### Create QR Screen:
This screen consists of:
- its name.
- a list with the 6 QR Code types; when you tap any of these, it navigates to the Data Entry Screen to create a QR Code of the chosen type.
- a navigation component to open the Scan or History screens.

<div style="text-align: center;">
  <img src="https://github.com/user-attachments/assets/e467dd26-50bd-4c9a-b138-e9a5cd7c3078" width="30%" style="margin-right: 5%;" />
  <img src="https://github.com/user-attachments/assets/e36a6178-cae5-4800-a50d-863ebce9fae6" width="30%" />
</div>

### Data Entry Screen:
After selecting a QR Code type, this screen allows you to enter the required information for that type and then save it internally, navigating to the next screen.

<div style="text-align: center;">
  <img src="https://github.com/user-attachments/assets/382b9450-1a5e-4d2e-9db3-4f091a4c5f96" width="30%" style="margin-right: 5%;" />
  <img src="https://github.com/user-attachments/assets/891ef30b-a600-42f8-87e3-6eb9d318b3d8" width="30%" />
</div>

### Preview Screen:
This screen is similar to the Scan Result Screen but has a different title. It provides the same functionalities.

<div style="text-align: center;">
  <img src="https://github.com/user-attachments/assets/4e13cfaf-b18e-42ba-900c-6c4ddecc2f2c" width="30%" style="margin-right: 5%;" />
  <img src="https://github.com/user-attachments/assets/645fb779-c6be-4e4c-bdc3-6811cb2113a7" width="30%" />
</div>

### Scan History Screen:
This screen shows the history of the QR Codes managed and consists of the following:
- a toolbar with the screen name.
- a tab listing all the scanned QR Codes and another tab for the generated ones. Each list shows the favourites first, then the non-favourite QR Codes, and all are sorted from newest to oldest.
- a navigation component to open the Scan or Create screens.

For each QR Code, it shows an icon according to its type, its title (custom or default), a preview of its content, the time of creation, and a favourite icon to mark or unmark as favourite.

<div style="text-align: center;">
  <img src="https://github.com/user-attachments/assets/c17b7653-f01c-4399-b242-391b58647ad9" width="30%" style="margin-right: 5%;" />
  <img src="https://github.com/user-attachments/assets/aabbe9db-5e47-47ef-8764-96651d832d88" width="30%" />
</div>

This screen also allows the following actions on a QR Code in any list:
- Tap: opens the Preview Screen.
- Long press: opens a bottom sheet with options to Share (opens the system share sheet) and Delete (removes the item from history).

<div style="text-align: center;">
  <img src="https://github.com/user-attachments/assets/f6d4b8f0-159b-4cac-850c-e5fe84d67773" width="30%" style="margin-right: 5%;" />
  <img src="https://github.com/user-attachments/assets/965bd66f-91d3-4b3f-a29a-826b5f2d7aa0" width="30%" />
</div>

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

## Feedback
If you have any feedback or like my work and need a freelance Android Developer, please feel free to reach out to me at [LinkedIn](https://www.linkedin.com/in/rnicolasmarin/).
