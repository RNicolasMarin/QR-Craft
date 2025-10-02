# QR Craft
An Android application built with Jetpack Compose for scanning QR Codes. Created as Milestones 1, 2, 3, and 4 of the August–September App Challenge on Philipp Lackner’s Mobile Dev Campus.

This app allows you to:
- Scan QR Codes with the device's camera.
- Scan QR Codes from the device's files.
- Generate your own QR Codes.
- Browse the history of scanned and generated QR Codes.

It supports the following QR Code types: Text, Link, Contact, Phone Number, Geolocation, and Wi-Fi.

<img width="885" height="183" alt="Github Readme Logo QR Craft" src="https://github.com/user-attachments/assets/0b3e12a8-5f41-4bc5-a0a9-a7457f607010" />

## Features

### Scan QR Codes with the camera:
- Open the app and instantly scan QR Codes through the camera.
- View the detected content on a result screen.
- Save as favourite, add a custom title, share, copy, or export as an image.

<div style="text-align: center;">
  <img src="https://github.com/user-attachments/assets/8ebe2a52-7c89-45d4-9835-f173ecd0cf77" width="30%" style="margin-right: 5%;" />
</div>

### Scan QR Codes from files:
- Pick an image from your device’s files.
- Detect QR Codes just like with the camera.
- Preview and manage results with the same screen.

<div style="text-align: center;">
  <img src="https://github.com/user-attachments/assets/d5807168-767d-4c84-99e5-67e397e94b5e" width="30%" style="margin-right: 5%;" />
</div>

### Generate your own QR Codes:
- Start from the main screen and select the type of QR Code.
- Enter the required information for that type.
- Preview the generated QR Code on a dedicated screen.

<div style="text-align: center;">
  <img src="https://github.com/user-attachments/assets/a94b2bcc-16a5-43ab-8bf8-5891aa55e750" width="30%" style="margin-right: 5%;" />
</div>

### History of QR Codes:
- Browse scanned and generated QR Codes in one place.
- Separate tabs for scanned and generated items.
- Favourites always appear first, sorted from newest to oldest.
- Tap to preview, long press to share or delete.

<div style="text-align: center;">
  <img src="https://github.com/user-attachments/assets/8173bb8c-3dd3-4da9-8b8e-4176743db418" width="30%" style="margin-right: 5%;" />
</div>

## Built with

* **Koin**: provides dependency injection for class instantiation and lifecycle management.
* **CameraX**: displays the device’s camera feed within the app..
* **ML Kit Barcode Scanning**: detects and extracts content from QR codes.
* **ZXing**: generates QR codes from text and other data formats.
* **Room**: library for local SQLite database.

## Feedback
If you have any feedback or like my work and need a freelance Android Developer, please feel free to reach out to me at [LinkedIn](https://www.linkedin.com/in/rnicolasmarin/).
