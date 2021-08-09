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
package test.io.kvision.panel

import kotlinx.browser.document
import io.kvision.core.FlexDirection
import io.kvision.core.JustifyContent
import io.kvision.html.Span
import io.kvision.html.span
import io.kvision.panel.FlexPanel
import io.kvision.panel.Root
import io.kvision.test.DomSpec
import kotlin.test.Test

class FlexPanelSpec : DomSpec {

    @Test
    fun render() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val flexPanel = FlexPanel(FlexDirection.ROWREV, justify = JustifyContent.SPACEEVENLY)
            root.add(flexPanel)
            flexPanel.add(Span("abc"), 1)
            flexPanel.add(Span("def"), 2)
            flexPanel.add(Span("ghi"), 3)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div style=\"display: flex; flex-direction: row-reverse; justify-content: space-evenly;\"><span style=\"order: 1;\">abc</span><span style=\"order: 2;\">def</span><span style=\"order: 3;\">ghi</span></div>",
                element?.innerHTML,
                "Should render correct flex panel"
            )
        }
    }

    @Test
    fun renderWithDSL() {
        run {
            val root = Root("test", containerType = io.kvision.panel.ContainerType.FIXED)
            val flexPanel = FlexPanel(FlexDirection.ROWREV, justify = JustifyContent.SPACEEVENLY, useWrappers = true) {
                options(order = 1) {
                    span("abc")
                }
                options(order = 2) {
                    span("def")
                }
                options(order = 3) {
                    span("ghi")
                }
            }
            root.add(flexPanel)
            val element = document.getElementById("test")
            assertEqualsHtml(
                "<div style=\"display: flex; flex-direction: row-reverse; justify-content: space-evenly;\"><div style=\"order: 1;\"><span>abc</span></div><div style=\"order: 2;\"><span>def</span></div><div style=\"order: 3;\"><span>ghi</span></div></div>",
                element?.innerHTML,
                "Should render correct flex panel with DSL"
            )
        }
    }
}