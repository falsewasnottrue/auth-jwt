package services

import org.scalatest.{FlatSpec, Matchers}
import play.api.libs.json.{JsObject, Json}

class TokenServiceSpec extends FlatSpec with Matchers {

  trait Env {
    val secret = "secret"
    val tokenService = new TokenService(secret)

    val header: JsObject = Json.obj(
      "alg" -> "HS256",
      "typ" -> "JWT"
    )
    val payload: JsObject = Json.obj(
      "sub" -> "1234567890",
      "name" -> "John Doe",
      "admin" -> true
    )

    val jwt: String =
      "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
        "eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9." +
        "TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ"
  }

  "Token Service" should "encode a message" in new Env {
    tokenService.encode(header, payload) shouldBe jwt
  }

}
