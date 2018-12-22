package com.seefaribacode

case class Balances(private val accountMap: Map[String, Double] = Map(), private val reward: Double = 100) {

  def applyTransaction(trans: Transaction) : TransactionResult = {

    def executeTransaction(): Balances = {
        val updatedAccountMap = accountMap.updated(trans.fromAccount,
          getBalance(trans.fromAccount) - trans.amount).updated(trans.toAccount,
          getBalance(trans.toAccount) + trans.amount)

        this.copy(updatedAccountMap)
      //consider deleting accounts with zero balances
    }

    if (checkBalance(getBalance(trans.fromAccount), trans.amount)) {
      TransactionResult(executeTransaction(), isSuccessful = true)
    } else TransactionResult(this, isSuccessful = false)
  }

  def getBalance(acct: String): Double = {
    accountMap.get(acct) match {
      case Some(b) => b
      case _ => 0.0
    }
  }

  def addBlock(block: Block): BlockResult = {

    // checks TransactionResult
    def useSuccessfulTransactionResult(tResult: TransactionResult): TransactionResult = {
      if (tResult.isSuccessful) tResult
      else TransactionResult(this, isSuccessful = false)
    }

    def processBlockSigTrans(tResult: TransactionResult, sTran: SignedTransaction): TransactionResult = {
      if (!tResult.isSuccessful) TransactionResult(this, isSuccessful = false)
      else {
        val newResult = tResult.balances.applyTransaction(sTran.transaction)
        if (newResult.isSuccessful) newResult
        else TransactionResult(this, isSuccessful = false)
      }
    }

    if (block.validateTransactionsAreSigned()) {

      val balAfterReward = applyReward(block.rewardAccount)
      val initTranRes = TransactionResult(balAfterReward, isSuccessful = true)


      val finalTransactionResult = block.signedTransactions.foldLeft(initTranRes)(processBlockSigTrans)
      BlockResult(finalTransactionResult.balances, finalTransactionResult.isSuccessful)

    } else BlockResult(this, isSuccessful = false)
  }

  def addNewAccount(account: String): Balances = this.copy(accountMap + (account -> 0.0))

  def checkBalance(balance: Double, amt: Double): Boolean = balance - amt >= 0

  def applyReward(account: String): Balances = {
    this.copy(accountMap = accountMap.updated(account, getBalance(account) + reward))
  }

  // FOR NEXT TIME
  // write tests

}
