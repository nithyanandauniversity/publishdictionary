import com.ibm.icu.text.Transliterator
import okhttp3.HttpUrl
import org.fastily.jwiki.core.Wiki

object SanskritWikiSource extends App {

  /*

  en/sa wikisource https://en.wikisource.org/wiki/Special:BotPasswords/:
  Akhilkodali@test is addbubt3bjg7anl178b322shsbimsiir

   */

  private val wiki = new Wiki.Builder()
    .withApiEndpoint(
      HttpUrl.parse("https://sa.wikisource.org/w/api.php")
    )
    //.withDebug(true)
    .withLogin("Akhilkodali@test", "addbubt3bjg7anl178b322shsbimsiir")
    .build()

  val page = wiki.getRandomPages(1).get(0)

  val transliterators = Transliterator.getInstance("Any-Eng")
  val tp = transliterators.transform(page)
  println(page)
  println(tp)

  val myCategory = "आनुक्रमणिका"
  val excludeCategories = List("वर्गः:अनुक्रमणिका Not-Proofread", "वर्गः:अनुक्रमणिका पुष्टितम्")
  import scala.jdk.CollectionConverters._
  //val pages = wiki.allPages(null, false, false, -1, null).asScala.toList
  //println(pages.filter(_.contains(myCategory)))
  val a = wiki.getCategoryMembers(myCategory).asScala.toList
  val b = a.filterNot(excludeCategories.contains)
  println(b)
  println(b.size)
  println(transliterators.transform(myCategory))

  wiki.getRandomPages()
}
