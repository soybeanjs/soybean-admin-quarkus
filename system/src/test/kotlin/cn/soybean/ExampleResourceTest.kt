/*
 * Copyright 2024 Soybean Admin Backend
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package cn.soybean

import cn.soybean.infrastructure.config.consts.AppConstants
import cn.soybean.system.infrastructure.util.SignUtil.createSign
import cn.soybean.system.infrastructure.util.SignUtil.getRandomString
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test
import java.time.Instant

@QuarkusTest
class ExampleResourceTest {
    val apiKey = "Soybean_key"
    val apiSecret = "Soybean_secret"

    @Test
    fun testSignEndpoint() {
        val toEpochMilli = Instant.now().toEpochMilli()
        val randomString = getRandomString(32)
        given()
            .header(AppConstants.API_KEY, apiKey)
            .header(AppConstants.API_TIMESTAMP, toEpochMilli)
            .header(AppConstants.API_NONCE, randomString)
            .header(
                AppConstants.API_SIGNATURE,
                createSign(
                    mutableMapOf(
                        AppConstants.API_KEY to apiKey,
                        AppConstants.API_TIMESTAMP to toEpochMilli,
                        AppConstants.API_NONCE to randomString,
                        AppConstants.API_SIGNATURE to randomString,
                    ),
                    "HmacSHA256",
                    apiSecret,
                ),
            ).log()
            .all()
            .`when`()
            .get("/hello/sign")
            .then()
            .statusCode(200)
            .body(`is`("sign request success"))
    }

    @Test
    fun testApiKeyEndpoint() {
        given()
            .header(AppConstants.API_KEY, apiKey)
            .log()
            .all()
            .`when`()
            .get("/hello/apiKey")
            .then()
            .statusCode(200)
            .body(`is`("apiKey request success"))
    }
}
