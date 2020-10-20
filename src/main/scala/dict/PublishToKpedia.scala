package dict

import java.io.FileInputStream

import okhttp3.HttpUrl
import org.apache.poi.xssf.usermodel.{XSSFRow, XSSFWorkbook}
import org.fastily.jwiki.core.Wiki

import scala.util.Try

object PublishToKpedia extends App {

  private val wiki: Wiki = new Wiki.Builder()
    .withApiEndpoint(
      HttpUrl.parse("https://kailasapedia.org/api.php")
    )
    .withLogin("Admin@OrderOfSanyas", "6j2217hmilpf4a5aiju3cjaamqjhtr1e")
    .build()

  def getValue(row: XSSFRow, i: Int): String =
    Try(row.getCell(i).getStringCellValue).recover {
      case _ => row.getCell(i).getNumericCellValue.intValue().toString
    }.getOrElse("")


  def getPage(row: XSSFRow): Page = {
    def _value: Int => String = getValue(row, _)

    val p = Page(
      order = _value(1),
      title = _value(2),
      description = _value(3),
      date = _value(5),
      typeOfDate = _value(6),
      numberOfPeople = _value(7),
      shastraName = _value(9),
      chapter = _value(10),
      section = _value(11),
      verseNumber = _value(12),
      verseSansrit = _value(13),
      transliteration = _value(14),
      translation = _value(15)
    )
    p
  }

  def page2Wiki(page: Page): String = {
    println(page)
    s"""
       |==${page.order}==
       |
       |===Description===
       |
       |${page.description}
       |
       |===Shastra Pramana===
       |{| class="wikitable"
       ||-
       |! Name
       |! Details
       ||-
       || Date
       || ${page.date}
       ||-
       || No of People
       || ${page.numberOfPeople}
       ||-
       || Shastra Name
       || ${page.shastraName}
       ||-
       || Chapter
       || ${page.chapter}
       ||-
       || Section
       || ${page.section}
       ||-
       || Verse Number
       || ${page.verseNumber}
       ||-
       || Verse (Sanskrit)
       || ${page.verseSansrit}
       ||-
       || Transliteration (English)
       || ${page.transliteration}
       ||-
       || Translation (English)
       || ${page.translation}
       ||}
       |
       |[[Category:Sovereign Order of Kailasa]]
       |""".stripMargin
  }

  val fis = new FileInputStream("C:\\github\\publishdictionary\\src\\Sovereign Order of KAILASA _ Pedia .xlsx")
  val book = new XSSFWorkbook(fis)
  val sheet = book.getSheetAt(0)
  val row = sheet.getRow(2)

  val result = (2 to 88).map(sheet.getRow).map(getPage)
    //.map(p => (p.numberOfPeople, p.typeOfDate, p.date))
    .withFilter(p => p.title != null && p.title.trim.nonEmpty)
    .map(p => wiki.edit(p.title, page2Wiki(p), s"updated from bot ${p.title}"))
    .toList
  println("========")
  val r = result.groupBy(_ == true).map(p => p._1 -> p._2.size)
  println(r)

  /*
  val sok: String = "Category:Sovereign Order of Kailasa"
  val count = wiki.getCategorySize(sok)
  println(count)
  val s = wiki.getCategoryMembers(sok)
  println(s.size())
  import scala.jdk.CollectionConverters._
  println(s.asScala.toList)
  val deletes = s.asScala.map(wiki.delete(_, "cleanup"))
  println(deletes.count(_ == true))
  println(deletes.count(_ == false))
  */
}

case class Page(
                 order: String,
                 title: String,
                 description: String,
                 date: String,
                 typeOfDate: String,
                 numberOfPeople: String,
                 shastraName: String,
                 chapter: String,
                 section: String,
                 verseNumber: String,
                 verseSansrit: String,
                 transliteration: String,
                 translation: String
               )