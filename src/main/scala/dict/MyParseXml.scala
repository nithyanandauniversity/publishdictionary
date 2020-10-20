package dict

object MyParseXml {
  import scala.xml._
  def process(s: String): NodeSeq = {
    XML.loadString(s) \\ "body"
  }
  private val myDict = MyDict
  private val dictMap: Map[String, String] = myDict.dictionariesList.map(d => d.id -> d.name).toMap

  def getPage(word: String): String = {
    val w = myDict.getWords(word).groupBy(_.source).toList.sortBy(_._1)
    s"'''${word}'''\n\r" + w.map(r => MyParseXml.getHtml(r._2)).mkString
  }

  private def getHtml(words: List[Data]): String =
    s"=== ${dictMap(words.head.source)} ===" + words.map(w =>
    s"""${MyParseXml.process(w.meaning)}
       |<br/>
       |""".stripMargin
      .mkString
      .replace("<H1>", "=== ")
      .replace("</H1>", " ===")
      .replace("<s>", "'''")
      .replace("<i>", "''")
      .replace("</i>", "''")
      .replace("</s>", "'''")
      .replace("<lb/>", "\n\r")
      .replace("<br/>", "\n")
      .replace("<body>", "\n")
      .replace("</body>", "\n")
      .stripMargin
  ).mkString
}
