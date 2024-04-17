package cn.soybean.system.application.bootstrap

import io.quarkus.arc.profile.IfBuildProfile
import io.quarkus.logging.Log
import io.quarkus.runtime.StartupEvent
import io.vertx.mutiny.pgclient.PgPool
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Observes

@IfBuildProfile("dev")
@ApplicationScoped
class DevStartupService(private val pgPool: PgPool) {

    fun onStart(@Observes ev: StartupEvent) {
        initDevDbSql()
    }

    private fun initDevDbSql() {
        val sqlPath = "sql/init.sql"
        val sql = javaClass.getResourceAsStream("/$sqlPath")?.bufferedReader().use { it?.readText() }
            ?: error("SQL file '$sqlPath' not found in classpath")
        executeSql(sql)
    }

    private fun executeSql(sql: String) {
        pgPool.query(sql).execute()
            .onItem().invoke { _ -> Log.debug("SQL script executed successfully.") }
            .onItem().ignore().andContinueWithNull()
            .onFailure().invoke { ex -> Log.errorf(ex, "Error executing SQL script") }
            .await().indefinitely()
    }
}