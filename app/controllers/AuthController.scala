package controllers

import javax.inject.Singleton

import domain.AuthData
import play.api.libs.json.{JsObject, Json}
import play.api.mvc.{Action, Controller}
import services.{MockAuthService, TokenService}


import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class AuthController extends Controller {

  import AuthData.authForm

  // TODO inject
  val tokenService = new TokenService("secret")
  val authService = new MockAuthService

  def authenticate() = Action.async { implicit request =>
    val form = authForm.bindFromRequest()
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
