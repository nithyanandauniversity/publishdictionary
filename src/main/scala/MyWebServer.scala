import monix.reactive.Observable

object MyWebServer extends cask.MainRoutes {

  @cask.get("/")
  def hello(): String =
    "Hello: use /word/:word"

  @cask.get("/word/:word")
  def getMeaning(word: String): cask.Response[String] = {
    cask.Response(
      headers = Seq(("Content-Type", "text/plain; charset=UTF-8")),
      data = MyParseXml.getPage(word)
    )
  }

  initialize()
}

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

case class Data(word: String, lnum: String, meaning: String, source: String)
case class Dictionaries(id: String, date: Int, name: String)

object MyDict {

  import io.getquill._
  val ctx = new MysqlMonixJdbcContext(SnakeCase, "ctx")
  import ctx._

  import monix.execution.Scheduler.Implicits.global

  private def words(word: String) = quote {
    query[Data].filter(_.word == lift(word))
  }

  val dictionariesList: List[Dictionaries] =
    run(quote {
      query[Dictionaries]
    }).runSyncUnsafe()

  def getWords(word: String): List[Data] = run(words(word)).runSyncUnsafe()

  def getAllWords: Observable[String] = stream(query[Data]
      .sortBy(_.word)(Ord.desc).map(_.word).distinct
  )

}


//TODO: Test with http://localhost:8080/word/aDAmArgava
// http://localhost:8080/word/aDareRa

// TODO: use {{#tip-text: text | tooltip-text}}