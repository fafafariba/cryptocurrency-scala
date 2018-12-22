package com.seefaribacode.test

import com.seefaribacode.{Balances, Block, SignedTransaction, Transaction}
import org.scalatest.{FlatSpec, Matchers}

class BalancesTest extends FlatSpec with Matchers {

  val originalBal = Balances()

  behavior of "tryAddingTransactionToBalances with VALID transactions"

  it should "update Balances" in {
    //setup
    val sender = TestAccount()
    val recipient = TestAccount()

    val senderBal = 100.0
    val bals = setupBal(sender.account -> senderBal)
    val amt = 15
    val tran = Transaction.createTransaction(sender.publickKey, sender.privateKey, recipient.account, amt)

    //when
    val result = bals.tryAddingBlock(Block(List(tran), "123"))
    val newSenderBal = result.balances.getBalance(sender.account)
    val newRecipBal = result.balances.getBalance(recipient.account)

    //then
    result.isValid should be (true)
    result.balances shouldNot be (originalBal)
    newSenderBal shouldBe (senderBal - amt)
    newRecipBal shouldBe amt
  }

  behavior of "tryAddingTransactionToBalances with INVALID transactions"


  it should "not update Balance when sender doesn't have enough in their account" in {
    //given
    val sender = TestAccount()
    val recipient = TestAccount()
    val amt = 15
    val tran = Transaction.createTransaction(sender.publickKey, sender.privateKey, recipient.account, amt)

    //when
    val result = originalBal.tryAddingBlock(Block(List(tran), "123"))
    val newSenderBal = result.balances.getBalance(sender.account)
    val newRecipBal = result.balances.getBalance(recipient.account)

    //then
    result.isValid should be (false)
    result.balances should be (originalBal)
    newSenderBal shouldBe 0.0
    newRecipBal shouldNot be (amt)
  }


  def setupBal(acctBals: (String, Double) *): Balances = {

    def addToAcctMap(acctMap: Map[String, Double], acctBals:(String, Double)): Map[String, Double] = {
      val (acct, bal) = acctBals
      acctMap + (acct -> bal)
    }
    val initMap: Map[String, Double] = Map()
    val finalAcctMap: Map[String,Double] = acctBals.foldLeft(initMap)(addToAcctMap)
    Balances(accountMap = finalAcctMap)
  }

}
