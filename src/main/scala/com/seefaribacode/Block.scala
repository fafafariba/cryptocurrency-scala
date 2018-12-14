package com.seefaribacode

case class Block(transactions: List[(Transaction, Signature)], rewardAccount: String) {

  def add(tran: Transaction, sig: Signature): Block = {
    this.copy(transactions = transactions :+ (tran, sig))
  }

  def validate(): Boolean = {
    transactions.forall { case (t, s) => t.validateTransaction(s) }
  }

}
