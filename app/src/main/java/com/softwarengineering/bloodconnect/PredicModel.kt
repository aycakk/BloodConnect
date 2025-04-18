package com.softwarengineering.bloodconnect

import android.content.Context
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import java.nio.ByteBuffer

class PredicModel(context: Context) {
    private val interpreter: Interpreter

    init {
        val model = FileUtil.loadMappedFile(context, "predic_model.tflite")
        val options = Interpreter.Options()
        interpreter = Interpreter(model, options)
    }

    //input format [months since last, total num, total c.c., months since first]
    fun predict(inputData: FloatArray): Int {
        val inputBuffer = ByteBuffer.allocateDirect(inputData.size * 4).apply {
            order(java.nio.ByteOrder.nativeOrder())
            asFloatBuffer().put(inputData)
        }

        val outputData = Array(1) { FloatArray(2) }
        interpreter.run(inputBuffer, outputData)

        return outputData[0].indices.maxByOrNull { outputData[0][it] } ?: -1
    }

    fun close() {
        interpreter.close()
    }
}