import java.util.Date

import okhttp3.HttpUrl
import org.fastily.jwiki.core.Wiki

object PublishToWiki {

  // PRE-REQ: Setup Bot instance at Special:BotPasswords
  private val wiki = new Wiki.Builder()
    .withApiEndpoint(
      HttpUrl.parse("https://test.hinduismpedia.org/api.php")
    )
    //.withDebug(true)
    .withLogin("Admin@test123", "ma52illci70ei31mqimc808to24ud61n")
    .build()

  def publish(title: String, content: String, reason: String): Boolean =
    wiki.edit(title, content, reason)

}

object TestPubishToWiki extends App {
  PublishToWiki.publish("AdminTest1", s"=== ${new Date}===", "test")


  List("aDareRa", "aDAmArgava").map(w => PublishToWiki.publish(w, MyParseXml.getPage(w), w))
}