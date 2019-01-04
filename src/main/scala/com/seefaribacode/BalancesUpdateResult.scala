package com.seefaribacode

case class BalancesUpdateResult(balances: Balances, isValid: Boolean = true)
//explore enum + extraction
  // enum is probably best for string values, not booleans
