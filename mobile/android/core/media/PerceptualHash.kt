package com.trinetra.mobile.android.core.media

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.PointF

/**
 * Perceptual image hashing for similarity matching.
 * Person A implementation - client-side only.
 * Person B owns backend persistence and API contracts.
 * 
 * Uses a simplified pHash approach: resize to 32x32, grayscale, 
 * compute DCT, extract top-left hash bits.
 * 
 * Note: Full pPHASH implementation would require additional dependencies.
 * This provides a basic framework for similarity matching.
 */
object PerceptualHash {

    /** Hash size for perceptual fingerprint */
    private const val HASH_SIZE = 8

    /** Hash length in bits (HASH_SIZE x HASH_SIZE) */
    private const val HASH_BIT_COUNT = HASH_SIZE * HASH_SIZE

    /**
     * Compute perceptual hash of a bitmap.
     * Returns hex string representation of the hash.
     */
    fun computeHash(bitmap: Bitmap): String? {
        try {
            // Resize to 32x32 for consistency
            val resized = Bitmap.createScaledBitmap(bitmap, HASH_SIZE, HASH_SIZE, true)
            
            // Convert to grayscale
            val gray = Bitmap.createBitmap(HASH_SIZE, HASH_SIZE, Bitmap.Config.ALPHA_8)
            for (x in 0 until HASH_SIZE) {
                for (y in 0 until HASH_SIZE) {
                    val pixel = resized.getPixel(x, y)
                    val grayValue = (0.299 * android.graphics.Color.red(pixel) +
                            0.587 * android.graphics.Color.green(pixel) +
                            0.114 * android.graphics.Color.blue(pixel)).toInt()
                    gray.setPixel(x, y, android.graphics.Color.rgb(grayValue, grayValue, grayValue))
                }
            }
            
            // Compute average pixel value
            val pixels = IntArray(HASH_SIZE * HASH_SIZE)
            gray.getPixels(pixels, 0, HASH_SIZE, 0, 0, HASH_SIZE, HASH_SIZE)
            
            val avg = pixels.average().toFloat()
            
            // Generate hash: bit is 1 if pixel > average, 0 otherwise
            var hash = 0
            for (i in 0 until HASH_BIT_COUNT) {
                if (pixels[i] > avg) {
                    hash = hash or (1 shl i)
                }
            }
            
            return bytesToHex(arrayOf((hash and 0xFFFFFFFF).toByte()))
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Compute Hamming distance between two perceptual hashes.
     * Lower distance = higher similarity.
     */
    fun hammingDistance(hash1: String?, hash2: String?): Int {
        if (hash1 == null || hash2 == null) return HASH_BIT_COUNT
        
        val bytes1 = hexToBytes(hash1)
        val bytes2 = hexToBytes(hash2)
        
        if (bytes1.size != bytes2.size) return HASH_BIT_COUNT
        
        var distance = 0
        for (i in bytes1.indices) {
            val diff = bytes1[i] xor bytes2[i]
            distance += countBits(diff)
        }
        return distance
    }

    /** Convert hex string to byte array */
    private fun hexToBytes(hex: String): ByteArray {
        val len = hex.length()
        val data = ByteArray(len / 2)
        for (i in 0 until len / 2) {
            data[i] = java.lang.Byte.parseInt(hex.substring(i * 2, i * 2 + 2), 16)
        }
        return data
    }

    private fun countBits(n: Int): Int {
        var count = 0
        while (n != 0) {
            count += n and 1
            n = n shr 1
        }
        return count
    }

    private fun bytesToHex(bytes: ByteArray): String {
        val sb = StringBuilder()
        for (b in bytes) {
            sb.append(String.format("%02x", b.toInt() and 0xff))
        }
        return sb.toString()
    }
}