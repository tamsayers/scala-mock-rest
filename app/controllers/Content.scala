package controllers

import play.api._
import play.api.mvc._

object Content extends Controller {

  def list = Action {
    Ok.withHeaders(
      CONTENT_TYPE -> "application/octet-stream")
  }
}