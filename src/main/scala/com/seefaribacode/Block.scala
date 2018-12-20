package com.seefaribacode

case class Block(transactions: List[SignedTransaction], rewardAccount: String) {

  def add(signedTransaction: SignedTransaction): Block = {
    this.copy(transactions = transactions :+ signedTransaction)
  }

  def validate(): Boolean = transactions.forall( st => st.validate())

}
