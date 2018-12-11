package com.seefaribacode

/**
 * @author ${user.name}
 */
object App {


  def main(args : Array[String]) {

    //if public and private keys already exist, use those

    //TODO: extract pre-existing key pair

    // else create key pair
    val keyPair = generateKeyPair()
    val privateKey = keyPair.getPrivate
    val publicKey = keyPair.getPublic
  }

}
