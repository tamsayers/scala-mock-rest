package data
case class ContentCriteria(dataUrl: DataUrl, acceptType: String) {
  def discreteTypes: List[ContentCriteria] =
    acceptType.split(",").map(ContentCriteria(dataUrl, _)).toList
}
case class Content(dataUrl: DataUrl, dataType: String, content: String)

case class TypedContent(dataType: String, data: Option[String])
case class Resource(dataUrl: DataUrl, types: List[String])
case class DataUrl(resource: String, queryParams: String) {
  override def toString: String = {
    val queryString = if (queryParams.trim().length() > 0) "?" + queryParams else ""
    resource + queryString
  }
}