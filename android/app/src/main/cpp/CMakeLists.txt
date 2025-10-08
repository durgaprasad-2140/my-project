cmake_minimum_required(VERSION 3.22.1)
project(edgeproc)
set(CMAKE_CXX_STANDARD 17)

# Add OpenCV path (update this path to your OpenCV installation)
set(OpenCV_DIR "{OPENCV_ANDROID_SDK}/sdk/native/jni")
find_package(OpenCV REQUIRED)

add_library(edgeproc SHARED 
    native-lib.cpp 
    opencv_processor.cpp
)

target_include_directories(edgeproc PRIVATE )
target_link_libraries(edgeproc  log)
