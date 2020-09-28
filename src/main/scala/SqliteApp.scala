import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class SqliteApp {
  def getResultSet: ResultSet = {
    val connection = DriverManager.getConnection("jdbc:sqlite:sanskrit-dictionary-2020-09-24-0800.sqlite")
    val statement = connection.createStatement()
    statement.executeQuery("select word, lnum, meaning, source from data")
  }
}

// Only for testing 
object SqliteApp extends App {
  val rs = new SqliteApp().getResultSet
  var count = 0
  while (rs.next) { // read the result set
    count +=1
    print("word = " + rs.getString("word"))
    println(" ,source = " + rs.getString("source"))
  }

  println(count)
}