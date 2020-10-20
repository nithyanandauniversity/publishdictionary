package wisdomlib

object WisdomData {

  case class WisdomWord(word: String, content: Option[String])

  import io.getquill._
  val ctx = new MysqlMonixJdbcContext(SnakeCase, "ctx")
  import ctx._
  import monix.execution.Scheduler.Implicits.global

  def insertWord(wordDefinition: WisdomWord) =
    run(quote(query[WisdomWord].insert(lift(wordDefinition))))

}

class WisdomData {

}