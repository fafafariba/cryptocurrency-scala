package com.seefaribacode

import java.security.Key
import java.util.Base64
import javax.crypto.Cipher

object Crypto {

  val rsaCipher: Cipher = Cipher.getInstance("RSA")
  val encoder: Base64.Encoder = Base64.getEncoder
  val decoder: Base64.Decoder = Base64.getDecoder

  def encryptMessageToBytes(msg: String, key: Key): Array[Byte] = {
    rsaCipher.init(Cipher.ENCRYPT_MODE, key)

    val msgAsBytes = msg.getBytes(utf8)
    rsaCipher.doFinal(msgAsBytes)
    //TODO: refactor later
  }

  def encodeAndEncryptMessage(msg: String, key: Key): String = {
    val encryptedMsg = encryptMessageToBytes(msg, key)
    encoder.encodeToString(encryptedMsg)
  }

  def decryptMessageToString(encMsg: String, key: Key): String = {
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