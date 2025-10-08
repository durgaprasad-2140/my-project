package com.example.edgeviewer\n\nobject NativeBridge {\n    init {\n        System.loadLibrary("edgeproc")\n    }\n    external fun stringFromJNI(): String\n}\n
