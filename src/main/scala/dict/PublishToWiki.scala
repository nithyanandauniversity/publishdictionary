package dict

import okhttp3.HttpUrl
import org.fastily.jwiki.core.Wiki

object PublishToWiki /*extends App*/ {

  // PRE-REQ: Setup Bot instance at Special:BotPasswords
  private val wiki: Wiki = new Wiki.Builder()
    .withApiEndpoint(
      HttpUrl.parse("https://test.hinduismpedia.org/api.php")
    )
    //.withDebug(true)
    //.withLogin("Dashsant@swamiji2", "pr7oitun1ui25hdllr7e2drbegge5oql")
    //.withLogin("Admin@Dictionary", "rv504rcvde8tn15slnk9i6s0upqaau08")
    //.withLogin("Admin@test123","siuuqjsoql3a3km00m2rp692buc3g4rl")
    .withLogin("Admin@test123","siuuqjsoql3a3km00m2rp692buc3g4rl")

    //.withLogin("Admin@test123", "ma52illci70ei31mqimc808to24ud61n")
    .build()

  def edit(title: String, content: String, reason: String): Boolean =
    Metrics.withTimer[Boolean] { () =>
      wiki.edit(title, content, reason)
    }

  wiki.getPageText("aDAmArgava")
  // .withLogin("Dashsant@swamiji1", "qegj7n83pfg9k7tukjjjpjhc3iutspih")
  /*
  def publish(title: String, content: String, reason: String): Unit =
      if(edit(title, content, reason))
        Metrics.count()
      else {
        println(s"Error: ${title}")
        Metrics.error()
      }
  */
  //val p = Path.of(URI.create("file:/Users/akhil/OneDrive/Pictures/IMG_6977.jpg"))
  //wiki.upload(p, "IMG_6977", "IMG_6977", "testing file upload api")
  //wiki.page
  //wiki.uploadByUrl(HttpUrl.parse("https://farm9.staticflickr.com/8213/8300206113_374c017fc5.jpg"),
  //"$_52-1.JPG", "$_52-1.JPG", "$_57-2.JPG")
}
