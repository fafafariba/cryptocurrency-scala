package com.seefaribacode

import java.security.{MessageDigest, PrivateKey, PublicKey}

import com.google.gson.Gson

case class Transaction(fromAccount: String, toAccount: String, amount: Double) {
  //consider changing amount type at some point


  def validateFromAccount(publicKey: PublicKey): Boolean = {
    fromAccount == Transaction.getAccountIdentifier(publicKey)
  }

  // checks signature and account number
  def validateTransaction(sig: Signature): Boolean = {
    validateFromAccount(sig.publicKey) && sig.isValidForMsg(this.serialize())
  }

  def serialize(): String = {
    val gson: Gson = new Gson()
    gson.toJson(this)
  }

}

object Transaction {

  val md: MessageDigest = MessageDigest.getInstance("SHA-256")
  // need private and public key, fromAccount
  // return transaction + sig
  def createTransaction(publicKey: PublicKey, privateKey: PrivateKey, toAccount: String, amount: Double): (Transaction, Signature) = {
    val tran = Transaction(fromAccount = getAccountIdentifier(publicKey), toAccount = toAccount, amount = amount)
    val sig = Signature.sign(privateKey, publicKey, tran.serialize())
    (tran, sig)
  }

  // generate account number from public key
  def getAccountIdentifier(publicKey: PublicKey): String = {
    Crypto.encoder.encodeToString(md.digest(publicKey.getEncoded))
  }
}
