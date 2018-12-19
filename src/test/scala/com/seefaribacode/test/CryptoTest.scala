package com.seefaribacode
package test

import org.scalatest.{FlatSpec, Matchers}
import com.seefaribacode.{Crypto, generateKeyPair}

class CryptoTest extends FlatSpec with Matchers {

  val keyPair = generateKeyPair

  behavior of "encodeAndEncryptMessage and decryptMessageToString"

  it should "encrypt a message and decrypt back to original message" in {
    //given
    val msg = "hello"
    val privateKey = keyPair.getPrivate
    val publicKey = keyPair.getPublic

    //when
    val encryptedMessage = Crypto.encryptMessagetoBase64Str(msg, privateKey)

    //then
    msg shouldBe Crypto.decryptMessagetFromBase64Str(encryptedMessage, publicKey)
  }


}
