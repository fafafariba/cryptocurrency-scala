package com.seefaribacode

import java.security.{KeyPair, PublicKey}

case class Signature(plainMsg: String, publicKey: PublicKey, encryptedMsg: String) {

  //add something to convert json to Signature
  def isValid(): Boolean = {
    Crypto.decryptMessageToString(encryptedMsg, publicKey) == plainMsg
  }
}

object Signature {

  def sign(keyPair: KeyPair, msg: String) : Signature = {
    val encMsg = Crypto.encodeAndEncryptMessage(msg, keyPair.getPrivate)
    Signature(plainMsg = msg, publicKey = keyPair.getPublic, encryptedMsg = encMsg)
  }
}
