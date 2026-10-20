# TCL AirPlay UI Preview

UI-only Android TV application for TCL C1, Android 14. **No real AirPlay streaming or Bonjour discovery.** No HomeKit.

## Get an installable APK using GitHub Actions
1. Create an empty GitHub repository and upload the project files, preserving the `.github/workflows` folder.
2. Open **Actions** > **Build TCL AirPlay UI APK** > **Run workflow**.
3. After the workflow completes, download the `TCL-AirPlay-UI-Preview-APK` artifact.
4. Unzip the artifact and sideload `app-debug.apk` to the TCL projector.

You can also open this project in Android Studio and use **Build > Build APK(s)**.

## Controls
- Arrow Up / Down: navigate
- OK / Enter: select
- Back: return
- `Preview Connection` in settings simulates a connected screen.

The device name is read from Android global settings if available, then Bluetooth name, then model. The device name is not editable in the app.

## Security
This preview uses a local WebView asset, no network permissions, and a JavaScript bridge solely for reading the system device name. It does not start in the background and does not advertise AirPlay.
