package com.seefaribacode
package test

import org.scalatest.{FlatSpec, Matchers}

class SignatureTest extends FlatSpec with Matchers {

  val keyPair = generateKeyPair

  behavior of "validate"

  it should "return true when decrypted message matches original message" in {
    //given
    val msg = "hello"
    val privateKey = keyPair.getPrivate
    val publicKey = keyPair.getPublic
    val encryptedMessage = Crypto.encryptMessagetoBase64Str(msg, privateKey)

    //when
    val sig = Signature(
      publicKey = publicKey,
      encryptedMsg = encryptedMessage
    )
    val isValidSignature = sig.validateMessage(msg)


    //then
    isValidSignature shouldBe true
  }

  behavior of "sign"

  it should "return valid Signature" in {
    ???
  }

}
