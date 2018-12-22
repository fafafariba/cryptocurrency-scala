package com.seefaribacode

case class SignedTransaction(transaction: Transaction, signature: Signature) {

  def validate: Boolean = transaction.validateTransaction(signature)

}
