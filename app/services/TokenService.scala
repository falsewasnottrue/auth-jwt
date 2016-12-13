package services

import play.api.libs.json.JsObject

class TokenService(secret: String) {

  def encode(header: JsObject, payload: JsObject): String =
    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ"


  def decode(encoded: String): (JsObject, JsObject) = ???

}
