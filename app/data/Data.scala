package data
case class ContentCriteria(uri: String, acceptType: String) {
  def discreteTypes: List[ContentCriteria] =
    acceptType.split(",").map(ContentCriteria(uri, _)).toList
}
case class Content(uri: String, dataType: String, content: String)

case class TypedContent(dataType: String, data: Option[String])
case class Resource(uri: String, types: List[String])