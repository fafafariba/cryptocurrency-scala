package com.seefaribacode

import java.security.{Key, MessageDigest}
import java.util.Base64

import javax.crypto.Cipher

object Crypto {

  val rsaCipher: Cipher = Cipher.getInstance("RSA")
  val encoder: Base64.Encoder = Base64.getEncoder
  val decoder: Base64.Decoder = Base64.getDecoder
  val md: MessageDigest = MessageDigest.getInstance("SHA-256")


  def encryptMessagetoBase64Str(msg: String, key: Key): String = {
    rsaCipher.init(Cipher.ENCRYPT_MODE, key)
    val encryptedMsg = rsaCipher.doFinal(msg.getBytes(utf8))
    encoder.encodeToString(encryptedMsg)
  }

  def decryptMessagetFromBase64Str(encMsg: String, key: Key): String = {
    rsaCipher.init(Cipher.DECRYPT_MODE, key)

    val decodedMsg = decoder.decode(encMsg.getBytes(utf8))
    val decryptedMsg = rsaCipher.doFinal(decodedMsg)
    new String(decryptedMsg, utf8)
  }

}

// ENCRYPTION
// plain msg -> bytes -> encrypt -> encode -> toString
// DECRYPTION
// encrypted msg -> bytes -> decode -> decrypt -> toString