package com.trinetra.mobile.android.core.media

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * SHA-256 file fingerprinting for exact content matching.
 * Person A implementation - client-side only.
 * Person B owns backend persistence and API contracts.
 */
object FileFingerprint {

    /** Computes SHA-256 hash of a file */
    suspend fun computeFileHash(file: java.io.File): String? {
        return try {
            MessageDigest.getInstance("SHA-256").also { digest ->
                val input = java.io.FileInputStream(file)
                try {
                    val buffer = ByteArray(8192)
                    var bytesRead: Int
                    while (bytesRead = input.read(buffer).also { } != -1) {
                        digest.update(buffer)
                    }
                    bytesToHex(digest.digest())
                } finally {
                    input.close()
                }
            }
        } catch (e: NoSuchAlgorithmException) {
            null
        } catch (e: Exception) {
            null
        }
    }

    /** Computes SHA-256 hash of raw bytes */
    fun computeBytesHash(bytes: ByteArray): String {
        return bytesToHex(MessageDigest.getInstance("SHA-256").digest(bytes))
    }

    private fun bytesToHex(bytes: ByteArray): String {
        val sb = StringBuilder()
        for (b in bytes) {
            sb.append(String.format("%02x", b.toInt() and 0xff))
        }
        return sb.toString()
    }

    /** Equality comparison of two file hashes */
    fun matches(hash1: String?, hash2: String?): Boolean {
        if (hash1 == null || hash2 == null) return false
        return hash1.equals(hash2, ignoreCase = false)
    }
}