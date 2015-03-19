import _root_.Config.Async
import com.github.mauricio.async.db.pool.{SingleThreadedAsyncObjectPool, PoolConfiguration}
import com.github.mauricio.async.db.postgresql.PostgreSQLConnection
import com.github.mauricio.async.db.postgresql.pool.PostgreSQLConnectionFactory

object AsyncDbConnector {
  val poolConfiguration = new PoolConfiguration(
    maxIdle             = Async.idle,
    maxObjects          = Async.maxObjects,
    maxQueueSize        = Async.queueSize,
    validationInterval  = Async.validationInterval
  )

  val factory = new PostgreSQLConnectionFactory()
  val pool = new SingleThreadedAsyncObjectPool[PostgreSQLConnection](factory, poolConfiguration)

  
}
