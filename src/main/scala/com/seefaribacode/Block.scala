package com.seefaribacode

case class Block(signedTransactions: List[SignedTransaction], rewardAccount: String) {

  def validateTransactionsAreSigned(): Boolean = signedTransactions.forall(st => st.validate)

  def add(signedTransaction: SignedTransaction): Block = {
    this.copy(signedTransactions = signedTransactions :+ signedTransaction)
  }


}
