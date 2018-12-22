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

  // need private and public key, fromAccount
  // return transaction + sig
  def createTransaction(publicKey: PublicKey, privateKey: PrivateKey, recipient: String, amount: Double): SignedTransaction = {
    val tran = Transaction(sender = Account.getAccountIdentifier(publicKey), recipient = recipient, amount = amount)
    val sig = Signature.sign(privateKey, publicKey, tran.serialize())
    SignedTransaction(tran, sig)
  }


  // generate account number from public key

}
