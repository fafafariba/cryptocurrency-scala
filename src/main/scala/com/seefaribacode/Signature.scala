package com.seefaribacode

import java.security.{KeyPair, PrivateKey, PublicKey}

case class Signature(plainMsg: String, publicKey: PublicKey, encryptedMsg: String) {

  //TODO: Should this be in Crypto object?
  def validate(): Boolean = {
    Crypto.decryptMessageToString(encryptedMsg, publicKey) == plainMsg
  }
}

object Signature {

  def sign(keyPair: KeyPair, msg: String) : Signature = {
    val encryptedmsg = Crypto.encodeAndEncryptMessage(msg, keyPair.getPrivate)
    Signature(plainMsg = msg, publicKey = keyPair.getPublic, encryptedMsg = encryptedmsg)
  }
}
