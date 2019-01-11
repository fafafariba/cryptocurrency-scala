package com.seefaribacode.test

import com.seefaribacode._
import org.scalatest.{FlatSpec, Matchers}

class BalancesTest extends FlatSpec with Matchers {

  val originalBal = Balances()
  val sender = TestAccount()
  val recipient = TestAccount()



  behavior of "tryAddingTransactionToBalances with VALID transactions"

  it should "update Balances" in {
    //setup

    val senderBal = 100.0
    val bals = setupBal(sender.account -> senderBal)
    val amt = 15
    val tran = Transaction.createSignedTransaction(sender.publickKey, sender.privateKey, recipient.account, amt)

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
    val amt = 15
    val tran = Transaction.createSignedTransaction(sender.publickKey, sender.privateKey, recipient.account, amt)

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



  behavior of "tryAddingBlock with VALID transactions"
  //TODO



  behavior of "tryAddingBlock with INVALID transactions"
  //TODO



  behavior of "getBalance"

  it should "return balance if account exists" in {
    //given
    val senderBal = 10.0
    val balances = setupBal(sender.account -> senderBal)
    //when
    val resultBal = balances.getBalance(sender.account)
    //then
    resultBal should be (senderBal)
  }

  it should "return 0 if account does not exist" in {
    val result = originalBal.getBalance(sender.account)
    result should be (0)
  }


  behavior of "applyReward"

  it should "update Balances with reward amount" in {
    // reward currently set = 100
    val balances = originalBal.applyReward(sender.account)
    balances.getBalance(sender.account) should be (100)
  }

  behavior of "accountHasAmount"

  it should "return true if account has sufficient funds" in {
    val balances = setupBal(sender.account -> 100)
    val amt = 10
    val result = balances.accountHasAmount(sender.account, amt)
    result should be (true)
  }

  it should "return false if account does not have sufficient funds" in {
    val balances = setupBal(sender.account -> 100)
    val amt = 1000
    val result = balances.accountHasAmount(sender.account, amt)
    result should be (false)
  }

  behavior of "processSignedTransactions"

  it should "return update Balances in BalancesUpdateResult and set isValid to true" in {
    //given
    val initBalResult = BalancesUpdateResult(setupBal(sender.account -> 100))
    val signedTrans = Transaction.createSignedTransaction(
      sender.publickKey,
      sender.privateKey,
      recipient.account,
      30)
    val finalBalResult = originalBal.verifySignedTransaction(initBalResult, signedTrans)
    finalBalResult.balances should not be originalBal
    finalBalResult.isValid should be (true)
  }


  it should "return initial Balances in BalancesUpdateResult and set isValid to false" in {
    //given
    val initBalResult = BalancesUpdateResult(originalBal)
    val signedTrans = Transaction.createSignedTransaction(
      sender.publickKey,
      sender.privateKey,
      recipient.account,
      30)
    val finalBalResult = originalBal.verifySignedTransaction(initBalResult, signedTrans)
    finalBalResult.balances should be (originalBal)
    finalBalResult.isValid should be (false)
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
