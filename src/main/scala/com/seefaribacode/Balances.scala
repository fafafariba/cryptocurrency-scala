package com.seefaribacode

case class Balances(private val accountMap: Map[String, Double] = Map(), private val reward: Double = 100) {

  case class TransactionResult(bal: Balances, isSuccessful: Boolean) //explore enum + extraction

  def applyTransaction(trans: Transaction) : TransactionResult = {

    def executeTransaction(): Balances = {
        val updatedAccountMap = accountMap.updated(trans.fromAccount,
          accountMap(trans.fromAccount) - trans.amount).updated(trans.toAccount,
          accountMap(trans.toAccount) + trans.amount)
        this.copy(updatedAccountMap)
      //consider deleting accounts with zero balances
    }

    if (checkBalance(getBalanceForAcct(trans.fromAccount), trans.amount)) {
      TransactionResult(executeTransaction(), isSuccessful = true)
    } else TransactionResult(this, isSuccessful = false)
  }

  def getBalanceForAcct(acct: String): Double = {
    accountMap.get(acct) match {
      case Some(b) => b
      case _ => 0.0
    }
  }

  def addNewAccount(account: String): Balances = this.copy(accountMap + (account -> 0.0))

  def checkBalance(balance: Double, amt: Double): Boolean = {
     if (balance - amt >= 0) true else false
  }

  def applyReward(acct: String): Balances = {
    this.copy(accountMap = accountMap.updated(acct, getBalanceForAcct(acct) + reward))
  }

  // FOR NEXT TIME
  // write tests

}
