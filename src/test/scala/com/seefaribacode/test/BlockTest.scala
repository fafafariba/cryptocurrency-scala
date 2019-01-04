package com.seefaribacode
package test

import org.scalatest.{FlatSpec, Matchers}

class BlockTest extends FlatSpec with Matchers {

  behavior of "validate"

  val user1 = TestAccount()
  val user2 = TestAccount()
  val user3 = TestAccount()

  it should "return true for a valid block" in {
    val signedTransaction1 = Transaction.createSignedTransaction(user1.publickKey, user1.privateKey, user2.account, 100.00)
    val signedTransaction2 = Transaction.createSignedTransaction(user2.publickKey, user2.privateKey, user3.account, 50.00)

    val signedTransactions = List(signedTransaction1, signedTransaction2)
    val block = Block(signedTransactions, "123")

    block.validateTransactionsAreSigned shouldBe true
  }

  behavior of "add"

  it should "return return" in {
    //given
    val signedTransaction1 = Transaction.createSignedTransaction(user1.publickKey, user1.privateKey, user2.account, 100.0)
    val signedTransaction2 = Transaction.createSignedTransaction(user3.publickKey, user3.privateKey, user2.account, 100.0)
    val block = Block(List(signedTransaction1), "123")

    //when
    val newBlock = block.add(signedTransaction2)

    //then
    newBlock shouldBe Block(List(signedTransaction1, signedTransaction2), "123")
  }
}
