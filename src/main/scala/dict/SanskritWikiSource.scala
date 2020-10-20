package dict

import okhttp3.HttpUrl
import org.fastily.jwiki.core.Wiki

object SanskritWikiSource extends App {

  /*

  en/sa wikisource https://en.wikisource.org/wiki/Special:BotPasswords/:
  Akhilkodali@test is addbubt3bjg7anl178b322shsbimsiir

   */

  private val wiki = new Wiki.Builder()
    .withApiEndpoint(
      HttpUrl.parse("https://test.hinduismpedia.org/api.php")
    )
    //.withDebug(true)
    //.withLogin("Dashsant@swamiji1", "qegj7n83pfg9k7tukjjjpjhc3iutspih")
    .withLogin("Admin@test123", "ma52illci70ei31mqimc808to24ud61n")
    .build()
/*
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
*/

  val book = "संस्काररत्नमाला (भागः १).djvu"
  val bo = wiki.uploadByUrl(
    HttpUrl.parse(s"https://upload.wikimedia.org/wikipedia/commons/f/f8/%E0%A4%B8%E0%A4%82%E0%A4%B8%E0%A5%8D%E0%A4%95%E0%A4%BE%E0%A4%B0%E0%A4%B0%E0%A4%A4%E0%A5%8D%E0%A4%A8%E0%A4%AE%E0%A4%BE%E0%A4%B2%E0%A4%BE_%28%E0%A4%AD%E0%A4%BE%E0%A4%97%E0%A4%83_%E0%A5%A7%29.djvu"),
    "संस्काररत्नमाला (भागः १).djvu", book, "uploaded from sa.wikisource.org")
  println(bo)
}
