import java.util.concurrent.TimeoutException

import Config._
import com.github.mauricio.async.db.{QueryResult, Connection, Configuration}
import com.github.mauricio.async.db.pool.{SingleThreadedAsyncObjectPool, PoolConfiguration}
import com.github.mauricio.async.db.postgresql.PostgreSQLConnection
import com.github.mauricio.async.db.postgresql.pool.PostgreSQLConnectionFactory
import io.netty.util.CharsetUtil
import com.github.mauricio.async.db.util.ExecutorServiceUtils.CachedExecutionContext
import scala.concurrent.Future

object AsyncDbConnector {
  val poolConfiguration = new PoolConfiguration(
    maxIdle             = Async.maxIdle,
    maxObjects          = Async.maxObjects,
    maxQueueSize        = Async.maxQueueSize,
    validationInterval  = Async.validationInterval
  )

  val dbConfiguration = new Configuration(
    username = Postgres.username,
    password = Some(Postgres.password),
    database = Some(Postgres.database),
    host = Postgres.host,
    port = Postgres.port,
    charset = CharsetUtil.UTF_8)

  val factory = new PostgreSQLConnectionFactory(dbConfiguration)
  val pool = new SingleThreadedAsyncObjectPool[PostgreSQLConnection](factory, poolConfiguration)

  def executeQuery(query:String):Future[QueryResult] = {
    getConnection flatMap {
      handler => handler.sendQuery(query)
    }
  }


  private def getConnection:Future[PostgreSQLConnection] = {
    pool.take
  }
}
