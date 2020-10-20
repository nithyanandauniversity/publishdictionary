package dict

import java.sql.DriverManager

object MysqlApp extends App {

  val connection = DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/dict?user=dict&password=dict" +
      "&rewriteBatchedStatements=true&serverTimezone=UTC"
  )
  val statement = connection.createStatement()

  {
    val rs = statement.executeQuery("select word, lnum, meaning, source from data")
    var count = 0
    while (rs.next) { // read the result set
      count += 1
      print("word = " + rs.getString("word"))
      println(" ,source = " + rs.getString("source"))
    }
    println(count)
  }

  /*
  val psmt = connection.prepareStatement("INSERT into data (word, lnum, meaning, source) VALUES (?,?,?,?)")

  val resultSet = new SqliteApp().getResultSet

  var countInsert = 0
  while(resultSet.next()) {
    countInsert += 1
    (1 to 4).foreach(i =>
      psmt.setString(i, resultSet.getString(i))
    )
    if(countInsert % 1000 == 0) {
      psmt.executeBatch()
      println(countInsert)
    }
    psmt.addBatch()
  }
  println(countInsert)
  psmt.executeBatch()
  println(countInsert)
  connection.close()
   */

}
