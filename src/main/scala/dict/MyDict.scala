package dict

import monix.reactive.Observable

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
