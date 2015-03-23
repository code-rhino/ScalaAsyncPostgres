
import Config._
import com.github.mauricio.async.db.postgresql.PostgreSQLConnection
import com.github.mauricio.async.db.postgresql.pool.PostgreSQLConnectionFactory
import com.github.mauricio.async.db.postgresql.util.{ParserURL, URLParser}
import com.github.mauricio.async.db.util.ExecutorServiceUtils.CachedExecutionContext
import com.github.mauricio.async.db.{Configuration, RowData, QueryResult, Connection}
import io.netty.util.CharsetUtil
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.language.postfixOps

object Main extends App {


  val conf = new Configuration(
    username = Postgres.username,
    password = Some(Postgres.password),
    database = Some(Postgres.database),
    host = Postgres.host,
    port = Postgres.port,
    charset = CharsetUtil.UTF_8)

  val connectionFactory: PostgreSQLConnectionFactory = new PostgreSQLConnectionFactory(conf)
  val connection: PostgreSQLConnection = connectionFactory.create

  //Await.result(connection.connect, 20 seconds)
  val future: Future[QueryResult] = AsyncDbConnector.executeQuery("SELECT 0")




  val mapResult:Future[Any] = future map(queryResult => queryResult.rows match {
    case Some(resultSet) => {
      val row: RowData = resultSet.head
      row(0)
    }
    case None => -1
  })

  val result = Await.result(mapResult, 5 seconds)
  println(result)

  connection.disconnect

  val msg:String = "flees"
  chuck {msg}

  def chuck(msg:String) = {
    s"$msg"
  }
}
