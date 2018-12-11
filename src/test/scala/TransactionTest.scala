
import com.seefaribacode.{Crypto, Signature, generateKeyPair}
import org.scalatest.{FlatSpec, Matchers}

class SignatureTest extends FlatSpec with Matchers {

  val keyPair = generateKeyPair

  behavior of "validate"

  it should "return true when decrypted message matches original message" in {
    //given
    val msg = "hello"
    val privateKey = keyPair.getPrivate
    val publicKey = keyPair.getPublic
    val encryptedMessage = Crypto.encodeAndEncryptMessage(msg, privateKey)

    //when
    val sig = Signature(
      plainMsg = msg,
      publicKey = publicKey,
      encryptedMsg = encryptedMessage
    )
    val isValid = sig.isValid()


    //then
    isValid shouldBe true
  }

  behavior of "sign"

}
