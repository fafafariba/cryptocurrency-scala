package com.seefaribacode

/**
 * @author ${user.name}
 */
object App {




  def main(args : Array[String]) {

    //if public and private keys already exist, use those

    //TODO: extract pre-existing key pair

    // else create key pair
    val keyPair = generateKeyPair()
    val privateKey = keyPair.getPrivate
    val publicKey = keyPair.getPublic

    val keyPair2 = generateKeyPair()
    val privateKey2 = keyPair2.getPrivate
    val publicKey2 = keyPair2.getPublic

    val keyPair3 = generateKeyPair()
    val privateKey3 = keyPair3.getPrivate
    val publicKey3 = keyPair3.getPublic

    val balances = Balances()
    val ledger: List[Block] = List()

    val p1account = Account.getAccountIdentifier(publicKey)
    println(p1account)
    val p2account = Account.getAccountIdentifier(publicKey2)
    val p3account = Account.getAccountIdentifier(publicKey3)

    val p1paysp2: SignedTransaction = Transaction.createSignedTransaction(publicKey, privateKey, p2account, 100)

    val p2paysp3 = Transaction.createSignedTransaction(publicKey2, privateKey2, p3account, amount = 50)

    val newBlock = Block(List(), p1account).add(p1paysp2).add(p2paysp3)
    val blockResult = balances.tryAddingBlock(newBlock)

    println(blockResult.isValid)
    println(blockResult.balances)


  }

}
