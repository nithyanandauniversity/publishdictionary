
object MyWebServer extends cask.MainRoutes {

  val myDict = new MyDict

  @cask.get("/")
  def hello(): String =
    "Hello: use /word/:word"

  @cask.get("/word/:word")
  def getMeaning(word: String): cask.Response[String] =
    cask.Response(
      //headers = Seq(("Content-Type", "text/html; charset=UTF-8")),
      data = myDict.getWords(word).map(_.meaning).head
    )


  initialize()
}

case class Data(word: String, lnum: String, meaning: String, source: String)

class MyDict {

  import io.getquill._
  lazy val ctx = new MysqlJdbcContext(SnakeCase, "ctx")
  import ctx._

  private def words(word: String) = quote {
    query[Data].filter(_.word == lift(word))
  }

  def getWords(word: String): List[Data] = run(words(word))

}


