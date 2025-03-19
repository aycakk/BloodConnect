package com.softwarengineering.bloodconnect

import android.content.Context
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import java.nio.ByteBuffer


class HealthModel(context: Context) {
    private val interpreter: Interpreter

    init {
        val model = FileUtil.loadMappedFile(context, "health_model.tflite")
        val options = Interpreter.Options()
        interpreter = Interpreter(model, options)
    }

    fun predict(inputData: FloatArray): Float {
        val inputBuffer = ByteBuffer.allocateDirect(inputData.size * 4).apply {
            order(java.nio.ByteOrder.nativeOrder())
            asFloatBuffer().put(inputData)
        }

        val outputData = Array(1) { FloatArray(1) }
        interpreter.run(inputBuffer, outputData)
        return outputData[0][0]
    }

    fun close() {
        interpreter.close()
    }

}