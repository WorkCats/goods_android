package com.agoines.goods.utils

import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

class BarcodeImageAnalysis : ImageAnalysis.Analyzer {
    @androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            val options = BarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                    Barcode.FORMAT_CODE_128,
                    Barcode.FORMAT_CODE_39
                )
                .build()
            val scanner = BarcodeScanning.getClient(options)

            val result = scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    Log.d("", "analyze: ")
                    for (barcode in barcodes) {
                        val bounds = barcode.boundingBox
                        val corners = barcode.cornerPoints

                        val rawValue = barcode.rawValue

                        when (barcode.valueType) {
                            Barcode.FORMAT_CODE_39 -> {
                                Log.d("重大消息", "analyze: 这是 39")
                            }

                            Barcode.FORMAT_CODE_128 -> {
                                Log.d("重大消息", "analyze: 这是 128")
                            }
                        }
                    }
                }
                .addOnFailureListener {

                }
            result.addOnCompleteListener{
                imageProxy.close()
            }
        }
    }
}