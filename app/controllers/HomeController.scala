package controllers

import domain.AuthData
import play.api.mvc.{Action, Controller}
import play.api.Play.current
import play.api.i18n.Messages.Implicits._

class HomeController extends Controller {

  import AuthData.authForm

  def index = Action {
    Ok(views.html.index(authForm))
  }
}
