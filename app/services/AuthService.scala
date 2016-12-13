package services

import play.api.libs.json.{JsObject, Json}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait AuthService {
  def auth(user: String, password: String): Future[JsObject]
}

class MockAuthService extends AuthService {
  def auth(user: String, password: String): Future[JsObject] = Future {
    if (user == "test" && password == "test") {
      Json.obj(
        "sub" -> "1234567890",
        "name" -> "John Doe",
        "admin" -> true
      )
    } else {
      throw new IllegalStateException("not authenticated")
    }
  }
}
