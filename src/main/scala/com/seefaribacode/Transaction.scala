package com.seefaribacode

import java.security.{MessageDigest, PrivateKey, PublicKey}

import com.google.gson.Gson

case class Transaction(fromAccount: String, toAccount: String, amount: Double) {
  //consider changing amount type at some point


  def validateFromAccount(publicKey: PublicKey): Boolean = {
    fromAccount == Account.getAccountIdentifier(publicKey)
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

case class SignedTransaction(transaction: Transaction, signature: Signature) {

  def validate(): Boolean = transaction.validateTransaction(signature)

}

object Transaction {

  // need private and public key, fromAccount
  // return transaction + sig
  def createTransaction(publicKey: PublicKey, privateKey: PrivateKey, toAccount: String, amount: Double): SignedTransaction = {
    val tran = Transaction(fromAccount = Account.getAccountIdentifier(publicKey), toAccount = toAccount, amount = amount)
    val sig = Signature.sign(privateKey, publicKey, tran.serialize())
    SignedTransaction(tran, sig)
  }


  // generate account number from public key

}
