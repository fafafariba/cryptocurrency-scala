package com.seefaribacode

import java.security.{MessageDigest, PublicKey}

import com.google.gson
import com.google.gson.Gson

case class Transaction(fromAccount: String, toAccount: String, amount: Double) {
  //consider changing amount type at some point

  val md: MessageDigest = MessageDigest.getInstance("SHA-256")

  def validateFromAccount(publicKey: PublicKey): Boolean = {
    fromAccount == Crypto.encoder.encode(md.digest(publicKey.getEncoded))
  }

  // checks signature and account number

  // generate account number from public key

  def serialize(): String = {
    val gson: Gson = new Gson()
    gson.toJson(this)
  }

}
