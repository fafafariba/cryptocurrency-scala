package com.seefaribacode

import java.util

object Balances {

  // fixed reward per Block
  val reward: Double = 100

  val accountMap: Map[String, Double] = Map()

  def update(trans: Transaction): Map[String, Double] = {

    if (accountMap.exists{ case (k,v) => k == trans.fromAccount}) {
      // if not first transaction throw error
      ???
    } else {
      ???
    }

  }

  // FOR NEXT TIME
  // update transactions
  // update blocks
  // reward


  //first block is an empty block


}
