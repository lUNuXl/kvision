/*
 * Copyright (c) 2017-present Robert Jaros
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.kvision.utils

import io.kvision.form.Form
import io.kvision.form.FormPanel
import io.kvision.form.KFilesFormControl
import kotlinx.coroutines.suspendCancellableCoroutine
import org.w3c.files.File
import org.w3c.files.FileReader
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Suspending extension function to get file content.
 * @return file content
 */
suspend fun File.getContent(): String = suspendCancellableCoroutine { cont ->
    val reader = FileReader()
    reader.onload = {
        @Suppress("UnsafeCastFromDynamic")
        cont.resume(reader.result)
    }
    reader.onerror = { e ->
        cont.resumeWithException(Exception(e.type))
    }
    reader.readAsDataURL(this@getContent)
}

/**
 * Returns current data model with file content read for all KFiles controls.
 * @return data model
 */
suspend fun <K : Any> Form<K>.getDataWithFileContent(): K {
    val map = this.fields.entries.associateBy({ it.key }, {
        val value = it.value
        if (value is KFilesFormControl) {
            value.getValue()?.map {
                it.copy(content = value.getNativeFile(it)?.getContent())
            }
        } else {
            value.getValue()
        }
    })
    return this.modelFactory?.invoke(map.withDefault { null }) ?: throw IllegalStateException("Serializer not defined")
}

/**
 * Returns current data model with file content read for all KFiles controls.
 * @return data model
 */
suspend fun <K : Any> FormPanel<K>.getDataWithFileContent(): K {
    return this.form.getDataWithFileContent()
}
