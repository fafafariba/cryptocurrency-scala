package com.seefaribacode

import java.security.Key
import java.util.Base64
import javax.crypto.Cipher

object Crypto {

  val rsaCipher: Cipher = Cipher.getInstance("RSA")
  val encoder: Base64.Encoder = Base64.getEncoder
  val decoder: Base64.Decoder = Base64.getDecoder

  def encryptMessageToBytes(msgAsBytes: Array[Byte], key: Key): Array[Byte] = {
    rsaCipher.init(Cipher.ENCRYPT_MODE, key)
    rsaCipher.doFinal(msgAsBytes)
    //TODO: should the message be encoded?
  }

  def encodeAndEncryptMessage(message: String, key: Key): String = {
    val encodedMsg = encoder.encode(message.getBytes(utf8))
    new String(encryptMessageToBytes(encodedMsg, key))
  }

  def decryptMessageToString(encryptedMessage: String, key: Key): String = {
    val decodedMsg = decoder.decode(encryptedMessage)
    rsaCipher.init(Cipher.DECRYPT_MODE, key)
    new String(rsaCipher.doFinal(decodedMsg), utf8)
  }

}
