package controllers

import javax.inject.Singleton

import play.api.libs.json.{JsObject, Json}
import play.api.mvc.{Action, Controller}
import services.{MockAuthService, TokenService}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class AuthController extends Controller {

  // TODO inject
  val tokenService = new TokenService("secret")
  val authService = new MockAuthService

  def login = Action.async(parse.json) { implicit request =>
    val user = (request.body \ "username").as[String]
    val password = (request.body \ "password").as[String]
    println(s"login: $user: $password")

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

  def logout = Action.async {
    Future.successful(Ok)
  }
}
