package com.seefaribacode
package test

import org.scalatest.{FlatSpec, Matchers}

class TransactionTest extends FlatSpec with Matchers {

  behavior of "validatePayerAccount"
  behavior of "serialize"

  it should "serialize transactions" in {
    //given
    val trans = Transaction(sender = "123", recipient = "456", amount = 10.00)

    //when
    val result = trans.serialize()

    //then
    result should be ("{\"fromAccount\":\"123\",\"toAccount\":\"456\",\"amount\":10.0}")
  }
}
