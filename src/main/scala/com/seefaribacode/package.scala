package com

import java.security.{KeyPairGenerator, KeyPair}
import java.nio.charset.StandardCharsets

package object seefaribacode {

  val utf8 = StandardCharsets.UTF_8

  def generateKeyPair(): KeyPair = {
    val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
    keyPairGenerator.initialize(2048)
    keyPairGenerator.genKeyPair()
  }

}
