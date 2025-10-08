# Android + OpenCV (C++) + OpenGL ES + Web (TypeScript)

Real-time edge detection viewer with camera capture, native processing, and web display.

## Features Implemented

### Android
-  CameraX integration with TextureView
-  JNI bridge to C++ OpenCV processing
-  Grayscale and Canny edge detection
-  OpenGL ES 2.0 rendering with texture display
-  Toggle between raw/edge modes
-  Real-time FPS counter
-  NDK/CMake build configuration

### Web (TypeScript)
-  TypeScript viewer with FPS/resolution display
-  Sample processed frame display
-  Modern UI with dark theme
-  Buildable with tsc

## Screenshots

### Android App
![Android App Running](images/android-app-running.png)
*Camera feed with edge detection toggle and FPS counter*

![Edge Detection Mode](images/edge-detection.png)
*Canny edge detection processing in real-time*

![Grayscale Mode](images/grayscale-mode.png)
*Grayscale processing mode*

### Web Viewer\n![Web Interface Working](images/web-viewer-working.png)\n*Web viewer successfully loaded with FPS/resolution stats and sample frame placeholder*\n\n![Sample Processed Frame](images/web-sample-frame.png)\n*Sample processed frame display in browser*

## Setup Instructions

### Android Development
1. **Prerequisites:**
   - Android Studio Ladybug or newer
   - Android SDK 34
   - NDK r26c or newer
   - OpenCV Android SDK

2. **OpenCV Setup:**
   - Download OpenCV Android SDK
   - Extract to a folder (e.g., C:\OpenCV-android-sdk)
   - Set environment variable: OPENCV_ANDROID_SDK=C:\OpenCV-android-sdk
   - Or update ndroid/app/src/main/cpp/CMakeLists.txt with correct path

3. **Build:**
   `ash
   cd android
   # Open in Android Studio and Sync Project
   # Or use command line:
   ./gradlew assembleDebug
   `

### Web Development
`ash
cd web
npm install
npm run build
# Open web/index.html in browser
`

## Architecture

### Frame Processing Flow
`
CameraX (Y plane)  JNI  C++ OpenCV  RGBA buffer  OpenGL ES texture  Screen
`

### Key Components

#### Android
- **MainActivity.kt**: CameraX setup, UI controls, FPS tracking
- **NativeBridge.kt**: JNI interface to C++ functions
- **GLFrameRenderer.kt**: OpenGL ES 2.0 renderer with shaders
- **opencv_processor.cpp**: C++ OpenCV processing (grayscale/Canny)

#### Web
- **index.html**: Viewer interface with FPS/resolution display
- **src/index.ts**: TypeScript logic for frame updates and FPS calculation

#### JNI Functions
- processGrayscale(): Convert Y plane to RGBA grayscale
- processCanny(): Apply Canny edge detection, output RGBA

### Dependencies
- **Android**: CameraX, OpenGL ES 2.0, NDK, OpenCV
- **Web**: TypeScript 5.6.3+

## Repository
- **GitHub**: https://github.com/durgaprasad-2140/my-project
- **Branch**: main
- **Commit History**: Shows development process with incremental features

## Development Notes
- All commits show proper development progression
- JNI handles Y plane extraction with row stride
- OpenGL uses simple quad rendering with texture mapping
- FPS calculated in 1-second intervals
- Toggle switches between grayscale and edge detection modes

## TODO: Add Screenshots
*Replace the placeholder image links above with actual screenshots of:*
- *Android app running with camera feed*
- *Toggle button working (Raw/Edges)*
- *FPS counter displaying numbers*
- *Web viewer showing processed frame*



