package com.github.mvysny.karibudsl.v10

import com.github.mvysny.dynatest.DynaNodeGroup
import com.github.mvysny.kaributesting.v10.*
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.html.Label
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.tabs.Tab
import com.vaadin.flow.component.tabs.Tabs
import kotlin.test.expect
import kotlin.test.fail

fun DynaNodeGroup.tabSheetTest() {
    beforeEach { MockVaadin.setup() }
    afterEach { MockVaadin.tearDown() }

    test("smoke") {
        UI.getCurrent().tabSheet()
        _expectOne<TabSheet>()
    }

    group("content population") {
        test("Initially empty") {
            val th = TabSheet()
            th._expectNone<Tab>()
        }
        test("Adding a tab to an empty TabSheet shows it immediately") {
            val th = UI.getCurrent().tabSheet {
                tab("foo") {
                    span("it works!")
                }
            }
            _expectOne<Span> { text = "it works!" }
        }
        test("Adding a tab to a non-empty TabSheet doesn't change the currently shown tab") {
            val th = UI.getCurrent().tabSheet {
                tab("foo") {
                    span("it works!")
                }
                tab("bar") {
                    span("it works 2!")
                }
            }
            _expectOne<Span> { text = "it works!" }
            _expectNone<Span>() { text = "it works 2!" }
        }
        test("Removing last tab clears selection") {
            lateinit var tab: Tab
            val th = UI.getCurrent().tabSheet {
                tab = tab("foo") { span("it works!") }
            }
            th.remove(tab)

            _expectNone<Span> { text = "it works!" }
        }
        test("Removing all tabs clears selection") {
            val th = UI.getCurrent().tabSheet {
                tab("foo") { span("it works!") }
            }
            th.removeAll()

            _expectNone<Span> { text = "it works!" }
        }
    }

    group("tabCount") {
        test("zero when empty") {
            expect(0) { TabSheet().tabCount }
        }
        test("adding 1 tab") {
            val ts = UI.getCurrent().tabSheet {
                tab("foo") {
                    span("it works!")
                }
            }
            expect(1) { ts.tabCount }
        }
        test("two tabs") {
            val ts = UI.getCurrent().tabSheet {
                tab("foo") {
                    span("it works!")
                }
                tab("bar") {
                    span("it works 2!")
                }
            }
            expect(2) { ts.tabCount }
        }
        test("10 tabs") {
            val ts = UI.getCurrent().tabSheet()
            (0..9).map { ts.addTab("tab $it") }
            expect(10) { ts.tabCount }
        }
        test("Removing last tab clears selection") {
            lateinit var tab: Tab
            val th = UI.getCurrent().tabSheet {
                tab = tab("foo") { span("it works!") }
            }
            th.remove(tab)

            expect(0) { th.tabCount }
        }
        test("Removing all tabs clears selection") {
            val th = UI.getCurrent().tabSheet {
                tab("foo") { span("it works!") }
            }
            th.removeAll()

            expect(0) { th.tabCount }
        }
        test("Adding a tab with null contents works") {
            val th = UI.getCurrent().tabSheet {
                addTab("foo")
            }
            expect(1) { th.tabCount }
        }
    }

    group("selectedIndex") {
        test("-1 when empty") {
            expect(-1) { TabSheet().selectedIndex }
        }
        test("Adding a tab to an empty TabSheet selects it immediately") {
            val th = UI.getCurrent().tabSheet {
                tab("foo") {
                    span("it works!")
                }
            }
            expect(0) { th.selectedIndex }
        }
        test("Adding a tab to a non-empty TabSheet doesn't change the selection") {
            val th = UI.getCurrent().tabSheet {
                tab("foo") {
                    span("it works!")
                }
                tab("bar") {
                    span("it works 2!")
                }
            }
            expect(0) { th.selectedIndex }
        }
        test("Removing last tab clears selection") {
            lateinit var tab: Tab
            val th = UI.getCurrent().tabSheet {
                tab = tab("foo") { span("it works!") }
            }
            th.remove(tab)

            expect(-1) { th.selectedIndex }
        }
        test("Removing all tabs clears selection") {
            val th = UI.getCurrent().tabSheet {
                tab("foo") { span("it works!") }
            }
            th.removeAll()

            expect(-1) { th.selectedIndex }
        }
        test("Adding a tab with null contents works") {
            val th = UI.getCurrent().tabSheet {
                addTab("foo")
            }
            expect(0) { th.selectedIndex }
        }
    }

    group("selectedTab") {
        test("null when empty") {
            expect(null) { TabSheet().selectedTab }
        }
        test("Adding a tab to an empty TabSheet selects it immediately") {
            lateinit var tab: Tab
            val th = UI.getCurrent().tabSheet {
                tab = tab("foo") {
                    span("it works!")
                }
            }
            expect(tab) { th.selectedTab }
        }
        test("Adding a tab to a non-empty TabSheet doesn't change the selection") {
            lateinit var tab: Tab
            lateinit var tab2: Tab
            val th = UI.getCurrent().tabSheet {
                tab = tab("foo") {
                    span("it works!")
                }
                tab2 = tab("bar") {
                    span("it works 2!")
                }
            }
            expect(tab) { th.selectedTab }
        }
        test("Removing last tab clears selection") {
            lateinit var tab: Tab
            val th = UI.getCurrent().tabSheet {
                tab = tab("foo") { span("it works!") }
            }
            th.remove(tab)

            expect(null) { th.selectedTab }
        }
        test("Removing all tabs clears selection") {
            val th = UI.getCurrent().tabSheet {
                tab("foo") { span("it works!") }
            }
            th.removeAll()

            expect(null) { th.selectedTab }
        }
        test("Adding a tab with null contents works") {
            lateinit var tab: Tab
            val th = UI.getCurrent().tabSheet {
                tab = addTab("foo")
            }
            expect(tab) { th.selectedTab }
        }
    }

    group("tabs") {
        test("empty when no tabs") {
            expectList() { TabSheet().tabs }
        }
        test("adding 1 tab") {
            lateinit var tab: Tab
            val th = UI.getCurrent().tabSheet {
                tab = tab("foo") {
                    span("it works!")
                }
            }
            expectList(tab) { th.tabs }
        }
        test("two tabs") {
            lateinit var tab: Tab
            lateinit var tab2: Tab
            val th = UI.getCurrent().tabSheet {
                tab = tab("foo") {
                    span("it works!")
                }
                tab2 = tab("bar") {
                    span("it works 2!")
                }
            }
            expectList(tab, tab2) { th.tabs }
        }
        test("10 tabs") {
            val th = UI.getCurrent().tabSheet()
            val tabs = (0..9).map { th.addTab("tab $it") }
            expect(tabs) { th.tabs }
        }
        test("Removing last tab clears selection") {
            lateinit var tab: Tab
            val th = UI.getCurrent().tabSheet {
                tab = tab("foo") { span("it works!") }
            }
            th.remove(tab)

            expectList() { th.tabs }
        }
        test("Removing all tabs clears selection") {
            val th = UI.getCurrent().tabSheet {
                tab("foo") { span("it works!") }
            }
            th.removeAll()

            expectList() { th.tabs }
        }
        test("Adding a tab with null contents adds the tab") {
            lateinit var tab: Tab
            val th = UI.getCurrent().tabSheet {
                tab = addTab("foo")
            }
            expectList(tab) { th.tabs }
        }
        test("Adding lazy tabs") {
            val ts = TabSheet()
            val tabs = (0..9).map { ts.addLazyTab("lazy $it") { Span("foo") } }
            expect(tabs) { ts.tabs }
        }
    }

    test("owner") {
        lateinit var tab: Tab
        val ts = UI.getCurrent().tabSheet {
            tab = tab("foo") { span("it works!") }
        }
        expect(ts._get<Tabs>()) { tab.owner }
    }

    test("ownerTabSheet") {
        lateinit var tab: Tab
        val ts = UI.getCurrent().tabSheet {
            tab = tab("foo") { span("it works!") }
        }
        expect(ts) { tab.ownerTabSheet }
    }

    group("tab contents") {
        test("non-empty contents") {
            lateinit var tab: Tab
            UI.getCurrent().tabSheet {
                tab = tab("foo") { span("it works!") }
            }
            expect<Class<*>>(Span::class.java) { tab.contents!!.javaClass }
        }

        test("clearing contents") {
            lateinit var tab: Tab
            UI.getCurrent().tabSheet {
                tab = tab("foo") { span("it works!") }
            }
            expect<Class<*>>(Span::class.java) { tab.contents!!.javaClass }
            tab.contents = null
            _expectNone<Span>()
            expect(null) { tab.contents }
        }
    }
    group("find contents") {
        test("empty tab") {
            lateinit var tab: Tab
            val th = UI.getCurrent().tabSheet {
                tab = tab("foo")
            }
            expect(null) { tab.contents }
            expect(null) { th.findTabWithContents(Span("bar")) }
        }
        
        test("simple test") {
            lateinit var tab: Tab
            val th = UI.getCurrent().tabSheet {
                tab = tab("foo") { span("it works!") }
            }
            expect(tab) { th.findTabWithContents(tab.contents!!) }
        }
    }
    group("findTabContaining") {
        test("empty tab") {
            val th = UI.getCurrent().tabSheet {
                tab("foo")
            }
            expect(null) { th.findTabContaining(Span("bar")) }
        }

        test("simple test") {
            lateinit var tab: Tab
            val th = UI.getCurrent().tabSheet {
                tab = tab("foo") { span("it works!") }
            }
            expect(tab) { th.findTabContaining(tab.contents!!) }
        }

        test("complex test") {
            lateinit var tab: Tab
            lateinit var nested: Button
            val th = UI.getCurrent().tabSheet {
                tab = tab("foo") {
                    div {
                        div {
                            nested = button()
                        }
                    }
                }
            }
            expect(tab) { th.findTabContaining(nested) }
        }
    }

    group("lazy tabs") {
        test("addFirstLazyTabImmediatelyInvokesClosure") {
            val ts = UI.getCurrent().tabSheet {}
            val producedLabel = Label("baz")
            ts.addLazyTab("foo") { producedLabel }
            expect(producedLabel) { ts.selectedTab!!.contents }
            expect(producedLabel) { ts._get<Label>() }
        }

        test("addSecondLazyTabDelaysClosure") {
            val ts = UI.getCurrent().tabSheet {}
            val producedLabel = Label("baz")
            var allowInvoking = false
            val tab1 = ts.addTab("bar")
            val tab2 = ts.addLazyTab("foo") {
                if (!allowInvoking) fail("Should not invoke")
                producedLabel
            }
            expect(tab1) { ts.selectedTab }
            ts._expectNone<Label>()

            allowInvoking = true
            ts.selectedTab = tab2
            expect(tab2) { ts.selectedTab!! }
            expect(producedLabel) { ts.selectedTab!!.contents }
            ts._expectOne<Label>()
        }
    }
}
