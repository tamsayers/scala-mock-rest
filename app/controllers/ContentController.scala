package controllers

import service.ContentServiceComponent
import repository.ContentRepositoryComponent
import play.api.libs.json.Json
import data._
import play.api._
import play.api.mvc._
import play.api.libs.json.Writes
import play.api.libs.json.JsValue

trait ContentController extends Controller {
  self: ContentServiceComponent =>

  implicit val resourceWrites = new Writes[Resource] {
    def writes(resource: Resource): JsValue = {
      Json.obj("url" -> resource.dataUrl.toString,
        "types" -> resource.types)
    }
  }

  def list = Action {
    Ok(Json.toJson(contentService.getAll)).withHeaders(
      CONTENT_TYPE -> "application/json")
  }

  def add = Action { request =>
    val content = Content(DataUrl(request.uri, request.queryString.mkString("", "=", "&")), request.contentType.get, request.body.asText.get)

    contentService.add(content)

    Ok
  }
}