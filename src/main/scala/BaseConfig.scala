
import com.typesafe.config.ConfigFactory


object Config {
  private val config = ConfigFactory.load("application")

  object Async {
    private lazy val pool         = config.getConfig("async_pool")
    lazy val maxObjects           = pool.getInt("max_objects")
    lazy val maxQueueSize         = pool.getInt("max_queue_size")
    lazy val maxIdle              = pool.getLong("max_idle")
    lazy val validationInterval   = pool.getLong("validation_interval")
  }

  object Postgres {
    private lazy val postgres = config.getConfig("postgres")
    lazy val username         = postgres.getString("username")
    lazy val password         = postgres.getString("password")
    lazy val database         = postgres.getString("database")
    lazy val host             = postgres.getString("host")
    lazy val port             = postgres.getInt("port")
  }

}

