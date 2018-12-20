package com.seefaribacode

import java.security.PublicKey


case class Account(publicKey: PublicKey) {}

object Account {
  def getAccountIdentifier(publicKey: PublicKey): String = {
    Crypto.encoder.encodeToString(Crypto.md.digest(publicKey.getEncoded))
  }
}
