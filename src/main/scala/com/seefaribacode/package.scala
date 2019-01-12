package com

import java.security.{KeyPair, KeyPairGenerator}
import java.nio.charset.{Charset, StandardCharsets}

package object seefaribacode {

  val utf8: Charset = StandardCharsets.UTF_8

  def generateKeyPair(): KeyPair = {
    val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
    keyPairGenerator.initialize(2048)
    keyPairGenerator.genKeyPair()
  }

}
