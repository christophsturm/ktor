/*
 * Copyright 2014-2020 JetBrains s.r.o and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package io.ktor.http.parsing

public class ParseException(
    override val message: String,
    override val cause: Throwable? = null
) : Exception(message, cause)
