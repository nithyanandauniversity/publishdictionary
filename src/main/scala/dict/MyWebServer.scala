package dict

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
