package org.example

import nl.adaptivity.xmlutil.serialization.XML
import kotlin.test.Test
import kotlin.test.assertEquals

class CustomCollectionSerializationTest {
    @Test
    fun testCustomCollectionSerialization() {
        val data = User(
            "Alice",
            ShoppingCart(listOf(Item("T-Shirt", 20.0), Item("Boots", 50.0)))
        )

        val encodedXml = XML.encodeToString(data)
        val decodedXml = XML.decodeFromString<User>(encodedXml)

        assertEquals(
            """<User name="Alice"><Item name="T-Shirt" price="20.0"/><Item name="Boots" price="50.0"/></User>""",
            encodedXml
        )
        assertEquals(data, decodedXml)
    }
}