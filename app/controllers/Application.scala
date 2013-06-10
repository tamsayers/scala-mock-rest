package controllers

import play.api._
import play.api.mvc._
import service.ContentServiceComponent
import repository.ContentRepositoryComponent

object Application extends ApplicationController

object Contents extends ContentController
  with ContentServiceComponent
  with ContentRepositoryComponent