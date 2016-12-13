package controllers

import javax.inject.Singleton

import play.api.data.Form
import play.api.libs.json.{JsObject, Json}
import play.api.mvc.{Action, Controller}
import services.{MockAuthService, TokenService}

import play.api.data.Forms._
import scala.concurrent.ExecutionContext.Implicits.global

case class AuthData(user: String, pass: String)

@Singleton
class AuthController extends Controller {

  val userForm = Form(
    mapping(
      "user" -> text,
      "pass" -> text
    )(AuthData.apply)(AuthData.unapply)
  )

  // TODO inject
  val tokenService = new TokenService("secret")
  val authService = new MockAuthService

  def authenticate() = Action.async { implicit request =>
    val form = userForm.bindFromRequest()
    val user = form.data("user")
    val password = form.data("pass")

    val header: JsObject = Json.obj(
      "alg" -> "HS256",
      "typ" -> "JWT"
    )
    authService.auth(user, password).map { payload =>
      val token = tokenService.encode(header, payload)
      Ok(token)
    } recover { case exception =>
      Unauthorized
    }
  }
}
