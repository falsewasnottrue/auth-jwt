package services

import java.nio.charset.StandardCharsets
import java.util.Base64
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

import play.api.libs.json.JsObject

class TokenService(secret: String) {

  val charset = StandardCharsets.UTF_8
  val algorithm = "HmacSHA256" // TODO read from header
  val mac: Mac = Mac.getInstance(algorithm)
  val secretKey: SecretKeySpec = new SecretKeySpec(secret.getBytes, algorithm)

  mac.init(secretKey)

  private def base64(in: Array[Byte]): String = Base64.getEncoder.encodeToString(in).replaceAll("=", "")

  def encode(header: JsObject, payload: JsObject): String = {

    val h = base64(header.toString.getBytes(charset))
    val p = base64(payload.toString.getBytes(charset))

    val s = base64(mac.doFinal(s"$h.$p".getBytes(StandardCharsets.UTF_8)))

    s"$h.$p.$s"
  }


  def decode(encoded: String): (JsObject, JsObject) = ???

}
