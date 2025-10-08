package com.example.edgeviewer.gl

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class GLFrameRenderer : GLSurfaceView.Renderer {
    private var textureId: Int = 0
    private var program: Int = 0
    private var vertexBuffer: FloatBuffer
    private var texBuffer: FloatBuffer
    @Volatile private var pendingFrame: ByteArray? = null
    @Volatile private var frameWidth = 0
    @Volatile private var frameHeight = 0

    init {
        val vertices = floatArrayOf(-1f, -1f, 1f, -1f, -1f, 1f, 1f, 1f)
        val tex = floatArrayOf(0f, 1f, 1f, 1f, 0f, 0f, 1f, 0f)
        vertexBuffer = ByteBuffer.allocateDirect(vertices.size * 4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(vertices)
        vertexBuffer.position(0)
        texBuffer = ByteBuffer.allocateDirect(tex.size * 4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(tex)
        texBuffer.position(0)
    }

    fun updateFrame(width: Int, height: Int, rgba: ByteArray){
        frameWidth = width; frameHeight = height; pendingFrame = rgba
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        val vs = "attribute vec2 aPos; attribute vec2 aTex; varying vec2 vTex; void main(){ vTex=aTex; gl_Position=vec4(aPos,0.0,1.0); }"
        val fs = "precision mediump float; varying vec2 vTex; uniform sampler2D uTex; void main(){ gl_FragColor = texture2D(uTex, vTex); }"
        val vsh = compileShader(GLES20.GL_VERTEX_SHADER, vs)
        val fsh = compileShader(GLES20.GL_FRAGMENT_SHADER, fs)
        program = GLES20.glCreateProgram()
        GLES20.glAttachShader(program, vsh)
        GLES20.glAttachShader(program, fsh)
        GLES20.glLinkProgram(program)
        val texIds = IntArray(1)
        GLES20.glGenTextures(1, texIds, 0)
        textureId = texIds[0]
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        val frame = pendingFrame
        if (frame != null && frameWidth>0 && frameHeight>0){
            val buf = ByteBuffer.wrap(frame)
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId)
            GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, frameWidth, frameHeight, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, buf)
        }
        GLES20.glUseProgram(program)
        val aPos = GLES20.glGetAttribLocation(program, "aPos")
        val aTex = GLES20.glGetAttribLocation(program, "aTex")
        GLES20.glEnableVertexAttribArray(aPos)
        GLES20.glEnableVertexAttribArray(aTex)
        GLES20.glVertexAttribPointer(aPos, 2, GLES20.GL_FLOAT, false, 0, vertexBuffer)
        GLES20.glVertexAttribPointer(aTex, 2, GLES20.GL_FLOAT, false, 0, texBuffer)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4)
        GLES20.glDisableVertexAttribArray(aPos)
        GLES20.glDisableVertexAttribArray(aTex)
    }

    private fun compileShader(type: Int, src: String): Int {
        val shader = GLES20.glCreateShader(type)
        GLES20.glShaderSource(shader, src)
        GLES20.glCompileShader(shader)
        return shader
    }
}

