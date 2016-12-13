package services

import org.scalatest.{FlatSpec, Matchers}
import play.api.libs.json.Json

class TokenServiceSpec extends FlatSpec with Matchers {

  trait Env {
    val secret = "secret"
    val tokenService = new TokenService(secret)
  }

  "Token Service" should "encode a message" in new Env {

    val header = Json.obj(
      "alg" -> "HS256",
      "typ" -> "JWT"
    )
    val payload = Json.obj(
      "sub" -> "1234567890",
      "name" -> "John Doe",
      "admin" ->  true
    )

    val expected = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ"

    tokenService.encode(header, payload) shouldBe expected
  }
}
