<img width="481" height="963" alt="Screenshot 2025-10-08 234716" src="https://github.com/user-attachments/assets/5f65d089-dd07-43ae-85e5-c0e07d8e1afe" />
<img width="1909" height="1011" alt="Screenshot 2025-10-08 234126" src="https://github.com/user-attachments/assets/d6f75333-6539-4ade-a326-40b8f730a461" />
<img width="1916" height="968" alt="Screenshot 2025-10-08 232654" src="https://github.com/user-attachments/assets/a02bbdc2-bb4a-46ec-aed7-8ffe323e86fa" />
﻿# Android + OpenCV (C++) + OpenGL ES + Web (TypeScript)

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






