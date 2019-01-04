package com.seefaribacode

case class Balances(private val accountMap: Map[String, Double] = Map(),
                    private val reward: Double = 100) {

  def rollbackResult: BalancesUpdateResult = BalancesUpdateResult(this, isValid = false)

  def tryAddingTransactionToBalances(tran: Transaction) : BalancesUpdateResult = {

    def applyTransaction(): Balances = {
      val updatedAccountMap = accountMap +
        (tran.sender -> (getBalance(tran.sender) - tran.amount)) +
        (tran.recipient -> (getBalance(tran.recipient) + tran.amount))

      this.copy(accountMap = updatedAccountMap)
      //TODO: consider deleting accounts with zero balances
    }

    if (accountHasAmount(tran.sender, tran.amount)) {
      BalancesUpdateResult(applyTransaction())
    } else BalancesUpdateResult(this, isValid = false)
  }

  def getBalance(acct: String): Double = {
    accountMap.get(acct) match {
      //if acct exists, return balance, otherwise return 0
      case Some(b) => b
      case _ => 0.0
    }
  }

  def applyReward(acct: String): Balances = {
    val newAccountMap = accountMap + (acct -> (getBalance(acct) + reward))
    this.copy(accountMap = newAccountMap)
  }

  //TODO can this be refactored to something better?
  def tryAddingBlock(block: Block): BalancesUpdateResult = {
    if (block.validateTransactionsAreSigned()) {

      val initResult = BalancesUpdateResult(applyReward(block.rewardAccount))
      val finalResult = block.signedTransactions.foldLeft(initResult)(verifySignedTransactions)

      if (finalResult.isValid) finalResult
      else rollbackResult

    } else rollbackResult

  }


  def accountHasAmount(account: String, amt: Double): Boolean = getBalance(account) - amt >= 0

  def verifySignedTransactions(result: BalancesUpdateResult,
                               tran: SignedTransaction): BalancesUpdateResult = {
    if (!result.isValid) rollbackResult
    else result.balances.tryAddingTransactionToBalances(tran.transaction)
  }
}
