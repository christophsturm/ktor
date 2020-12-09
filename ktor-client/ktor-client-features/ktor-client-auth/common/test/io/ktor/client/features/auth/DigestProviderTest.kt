/*
 * Copyright 2014-2020 JetBrains s.r.o and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package io.ktor.client.features.auth

import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.test.dispatcher.*
import io.ktor.util.*
import kotlin.test.*

class DigestProviderTest {

    private val path = "path"

    private val paramName = "param"

    private val paramValue = "value"

    lateinit var requestBuilder: HttpRequestBuilder

    @BeforeTest
    fun setup() {
        if (!PlatformUtils.IS_JVM) return
        val params = ParametersBuilder(1)
        params.append(paramName, paramValue)

        val url = URLBuilder(encodedPath = path, parameters = params, trailingQuery = true)
        requestBuilder = HttpRequestBuilder {
            takeFrom(url)
        }
    }

    @Test
    fun addRequestHeadersSetsExpectedAuthHeaderFields() = testSuspend {
        if (!PlatformUtils.IS_JVM) return@testSuspend

        runIsApplicable(authAllFields)
        val authHeader = addRequestHeaders()

        assertTrue(authHeader.contains("qop=qop"))
        assertTrue(authHeader.contains("opaque=opaque"))
        checkStandardFields(authHeader)
    }

    @Test
    fun addRequestHeadersOmitsQopAndOpaqueWhenMissing() = testSuspend {
        if (!PlatformUtils.IS_JVM) return@testSuspend

        runIsApplicable(authMissingQopAndOpaque)
        val authHeader = addRequestHeaders()

        assertFalse(authHeader.contains("opaque="))
        assertFalse(authHeader.contains("qop="))
        checkStandardFields(authHeader)
    }

}
