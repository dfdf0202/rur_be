package kr.co.rur.core.api.support.utils

import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object EncryptionUtils {

    private const val algorithm = "AES/CBC/PKCS5Padding"
    private const val key = "MySecretKey12345"
    private const val iv = "MySecretIV123456"

    private val secretKey = SecretKeySpec(key.toByteArray(), "AES")
    private val ivSpec = IvParameterSpec(iv.toByteArray())

    fun encrypt(plainText: String): String {
        val cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec)
        val encrypted = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))
        return Base64.getEncoder().encodeToString(encrypted)
    }

    fun decrypt(encryptedText: String): String {
        val cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec)
        val decodedBytes = Base64.getDecoder().decode(encryptedText)
        val decrypted = cipher.doFinal(decodedBytes)
        return String(decrypted, Charsets.UTF_8)
    }

}