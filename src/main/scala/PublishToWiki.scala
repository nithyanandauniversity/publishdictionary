import java.util.Date

import monix.eval.Task
import monix.execution.atomic.AtomicInt
import okhttp3.HttpUrl
import org.fastily.jwiki.core.Wiki

object PublishToWiki {

  val count = AtomicInt(0)
  // PRE-REQ: Setup Bot instance at Special:BotPasswords
  private val wiki = new Wiki.Builder()
    .withApiEndpoint(
      HttpUrl.parse("https://test.hinduismpedia.org/api.php")
    )
    //.withDebug(true)
    .withLogin("Admin@test123", "ma52illci70ei31mqimc808to24ud61n")
    .build()

  def publish(title: String, content: String, reason: String): Unit = {
    if(wiki.edit(title, content, reason))
        if(count.incrementAndGet(1) % 1000 == 0) println(count.get)
    else
        println("Error ---> False: " + count.get )
  }

}

object TestPubishToWiki extends App {
  //PublishToWiki.publish("AdminTest1", s"=== ${new Date}===", "test")

  def publish(word: String): Unit = PublishToWiki.publish(word, MyParseXml.getPage(word), word)
  val myDict = MyDict

  import monix.execution.Scheduler.Implicits.global
  val last = myDict.getAllWords.mapParallelUnordered(5)(d => Task(publish(d)))

  last.lastL.runSyncUnsafe()

  println(last)
  println("================")
//  List("aDareRa", "aDAmArgava").map(w => )
}