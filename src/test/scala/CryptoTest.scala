
import org.scalatest.{FlatSpec, Matchers}
import com.seefaribacode.generateKeyPair

class CryptoTest extends FlatSpec with Matchers {

  val keyPair = generateKeyPair
  behavior of "encryptMessageToBytes"

  it should "encrypt a message to bytes" in {
    //given
    val msg = "hello"

  }


}
