package domain

import play.api.data.Form
import play.api.data.Forms.mapping
import play.api.data.Forms._

case class AuthData(user: String, pass: String)

object AuthData {
  val authForm = Form(
    mapping(
      "user" -> text,
      "pass" -> text
    )(AuthData.apply)(AuthData.unapply)
  )
}