package data

case class DataUrl(path: String, queryString: String)
case class ContentCriteria(dataUrl: DataUrl, dataType: String)
case class Content(dataUrl: DataUrl, dataType: String, content: String)

case class TypedContent(dataType: String, content: Option[String])