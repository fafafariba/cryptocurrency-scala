package com.seefaribacode

import java.security.{PrivateKey, PublicKey}

case class Signature(publicKey: PublicKey, encryptedMsg: String) {

  //add something to convert json to Signature
  def isValidForMsg(plainMsg: String): Boolean = {
    Crypto.decryptMessagetFromBase64Str(encryptedMsg, publicKey) == plainMsg
  }
}

object Signature {

  def sign(privateKey: PrivateKey, publicKey: PublicKey, msg: String) : Signature = {
    val encMsg = Crypto.encryptMessagetoBase64Str(msg, privateKey)
    Signature(publicKey = publicKey, encryptedMsg = encMsg)
  }
}
