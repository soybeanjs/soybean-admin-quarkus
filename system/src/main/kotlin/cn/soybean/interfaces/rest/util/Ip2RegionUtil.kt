/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean.interfaces.rest.util

import io.quarkus.logging.Log
import jakarta.annotation.PreDestroy
import jakarta.enterprise.context.ApplicationScoped
import org.lionsoul.ip2region.xdb.Searcher

@ApplicationScoped
object Ip2RegionUtil {
    private lateinit var searcher: Searcher

    fun initialize() {
        val dbPath = "ip2region/ip2region.xdb"
        try {
            val inputStream =
                Thread.currentThread().contextClassLoader.getResourceAsStream(dbPath)
                    ?: throw IllegalStateException("Cannot find $dbPath in classpath")
            val cBuff = inputStream.readBytes()
            searcher = Searcher.newWithBuffer(cBuff)
            Log.info("Ip2Region searcher initialized successfully.")
        } catch (e: Exception) {
            Log.errorf(e, "Failed to initialize Ip2Region searcher")
        }
    }

    fun search(ip: String): String {
        when {
            !this::searcher.isInitialized -> {
                Log.warn("Searcher has not been initialized")
                return "Unknown"
            }

            else ->
                try {
                    return searcher.search(ip)
                } catch (e: Exception) {
                    Log.errorf(e, "Error searching IP $ip")
                    return "Unknown"
                }
        }
    }

    @PreDestroy
    fun cleanup() {
        if (this::searcher.isInitialized) {
            searcher.close()
            Log.info("Ip2Region searcher closed successfully.")
        }
    }
}
