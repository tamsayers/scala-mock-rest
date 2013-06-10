package data

import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.specs2.mutable.SpecificationWithJUnit
import org.specs2.mock.Mockito

@RunWith(classOf[JUnitRunner])
class DataSpec extends SpecificationWithJUnit
    with Mockito {

  val dataUrl = DataUrl("", "")

  "content criteria" should {
    "return a list of criteria for the discrete data types" in {
      val mixedTypeCriteria = ContentCriteria(dataUrl, "type1,type2,type3")

      mixedTypeCriteria.discreteTypes must_== List(ContentCriteria(dataUrl, "type1"), ContentCriteria(dataUrl, "type2"), ContentCriteria(dataUrl, "type3"))
    }

    "return a list of one criteria if there is a single type" in {
      val criteria = ContentCriteria(dataUrl, "type1")

      criteria.discreteTypes must_== List(criteria)
    }
  }

  "data url" should {
    "give a the string representation of the data" in {
      DataUrl("url", "queryString").toString mustEqual "url?queryString"
    }
  }
}