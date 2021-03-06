package com.seefaribacode

import java.security.{PrivateKey, PublicKey}
import com.google.gson.Gson

case class Transaction(sender: String, recipient: String, amount: Double) {
  //consider changing amount type at some point

  def validateSender(publicKey: PublicKey): Boolean = {
    sender == Account.getAccountIdentifier(publicKey)
  }

  // checks signature and account number
  def validateTransaction(sig: Signature): Boolean = {
    validateSender(sig.publicKey) && sig.validateMessage(this.serialize())
  }

  def serialize(): String = {
    val gson: Gson = new Gson()
    gson.toJson(this)
  }

}

object Transaction {

  // return transaction + sig
  def createSignedTransaction(publicKey: PublicKey, privateKey: PrivateKey, recipient: String, amount: Double): SignedTransaction = {
    val tran = Transaction(sender = Account.getAccountIdentifier(publicKey), recipient = recipient, amount = amount)
    val sig = Signature.sign(privateKey, publicKey, tran.serialize())
    SignedTransaction(tran, sig)
  }

}
