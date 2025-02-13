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
package test.io.kvision.form.select

import kotlinx.browser.document
import io.kvision.form.select.SelectInput
import io.kvision.form.select.SelectWidthType
import io.kvision.panel.Root
import io.kvision.test.DomSpec
import io.kvision.test.removeAllAfter
import kotlin.test.Test

class SelectInputSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val selectInput = SelectInput(listOf("test1" to "Test 1", "test2" to "Test 2"), "test1", true).apply {
                liveSearch = true
                placeholder = "Choose ..."
                selectWidthType = SelectWidthType.FIT
                emptyOption = true
            }
            root.add(selectInput)
            removeAllAfter(requireNotNull(document.querySelector("select")))
            val element = document.getElementById("test")
            assertEqualsHtml(
                """<span style="display: contents;"><div class="dropdown bootstrap-select show-tick fit-width"><select class="selectpicker" multiple="multiple" data-live-search="true" title="Choose ..." data-style="btn-default" data-width="fit"><option value="#kvnull"></option><option value="test1">Test 1</option><option value="test2">Test 2</option></select></div></span>""",
                element?.innerHTML,
                "Should render correct select input"
            )
        }
    }
}
