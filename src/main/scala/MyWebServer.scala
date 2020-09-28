object MyWebServer extends cask.MainRoutes {

  import MyParseXml.myDict

  @cask.get("/")
  def hello(): String =
    "Hello: use /word/:word"

  @cask.get("/word/:word")
  def getMeaning(word: String): cask.Response[String] = {
    val w = myDict.getWords(word)
    cask.Response(
      headers = Seq(("Content-Type", "text/html; charset=UTF-8")),
      data = w.map(MyParseXml.getHtml).mkString("\n")

    )
  }

  initialize()
}

object MyParseXml {
  import scala.xml._
  def process(s: String): NodeSeq = {
    XML.loadString(s) \\ "body"
  }
  val myDict = new MyDict
  private val dictMap: Map[String, String] = myDict.dictionariesList.map(d => d.id -> d.name).toMap

  def getHtml(w: Data): String =
    <html>
      <body>{dictMap(w.source)}<br/></body>
      <body>{w.word}<br/></body>
      {MyParseXml.process(w.meaning)}
      <hr/>
    </html>
    .mkString
      .replace("<s>", "<strong>")
      .replace("</s>", "</strong>")
}

case class Data(word: String, lnum: String, meaning: String, source: String)
case class Dictionaries(id: String, date: Int, name: String)

class MyDict {

  import io.getquill._
  lazy val ctx = new MysqlJdbcContext(SnakeCase, "ctx")
  import ctx._

  private def words(word: String) = quote {
    query[Data].filter(_.word == lift(word))
  }

  val dictionariesList: List[Dictionaries] =
    run(quote {
      query[Dictionaries]
    })
  def getWords(word: String): List[Data] = run(words(word))

}


//TODO: Test with http://localhost:8080/word/aDAmArgava