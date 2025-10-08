package com.example.edgeviewer

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import android.opengl.GLSurfaceView
import com.example.edgeviewer.gl.GLFrameRenderer
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {
    private lateinit var previewView: PreviewView
    private lateinit var glView: GLSurfaceView
    private lateinit var renderer: GLFrameRenderer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        glView = findViewById(R.id.glView)
        glView.setEGLContextClientVersion(2)
        renderer = GLFrameRenderer()
        glView.setRenderer(renderer)
        glView.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
        previewView = findViewById(R.id.previewView)
        val requestPerm = registerForActivityResult(ActivityResultContracts.RequestPermission()){ granted -> if (granted) startCamera() }
        requestPerm.launch(Manifest.permission.CAMERA)
    }
    private fun startCamera(){
        val providerFuture = ProcessCameraProvider.getInstance(this)
        providerFuture.addListener({
            val provider = providerFuture.get()
            val preview = Preview.Builder().build().also { it.setSurfaceProvider(previewView.surfaceProvider) }
            val analysis = ImageAnalysis.Builder().build().also {
                it.setAnalyzer(ContextCompat.getMainExecutor(this)) { image ->\n                val yPlane = image.planes[0].buffer\n                val yBytes = ByteArray(yPlane.remaining())\n                yPlane.get(yBytes)\n                val width = image.width\n                val height = image.height\n                val out = ByteArray(width*height*4)\n                NativeBridge.processGrayscale(width, height, yBytes, image.planes[0].rowStride, out)
                renderer.updateFrame(width, height, out)\n                image.close()\n            }
            }
            val selector = CameraSelector.DEFAULT_BACK_CAMERA
            provider.unbindAll()
            provider.bindToLifecycle(this, selector, preview, analysis)
        }, ContextCompat.getMainExecutor(this))
    }
}




