package data

case class DataUrl(path: String, queryString: String)
case class ContentCriteria(dataUrl: DataUrl, dataType: String) {
  def discreteTypes: List[ContentCriteria] =
    dataType.split(",").map(ContentCriteria(dataUrl, _)).toList
}
case class Content(dataUrl: DataUrl, dataType: String, content: String)

case class TypedContent(dataType: String, content: Option[String])
case class Resource(dataUrl: DataUrl, types: List[String])