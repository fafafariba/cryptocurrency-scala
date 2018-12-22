package com.seefaribacode.test

import java.security.{PrivateKey, PublicKey}

import com.seefaribacode.{Account, generateKeyPair}

case class TestAccount(publickKey: PublicKey,privateKey: PrivateKey, account: String) {}

object TestAccount {
   def apply(): TestAccount = {
    val keypair = generateKeyPair()
    val pubKey = keypair.getPublic
    val privKey = keypair.getPrivate
    val acct = Account.getAccountIdentifier(pubKey)
    TestAccount(pubKey, privKey, acct)
  }
}
