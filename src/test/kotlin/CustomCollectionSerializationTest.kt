package org.example

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import nl.adaptivity.xmlutil.serialization.XML
import kotlin.test.Test
import kotlin.test.assertEquals

class CustomCollectionSerializationTest {
    val data = User(
        "Alice",
        ShoppingCart(listOf(Item("T-Shirt", 20.0), Item("Boots", 50.0)))
    )

    @Test
    fun testCustomCollectionXmlSerialization() {
        val encodedXml = XML.encodeToString(data)
        val decodedXml = XML.decodeFromString<User>(encodedXml)

        assertEquals(
            """<User name="Alice"><Item name="T-Shirt" price="20.0"/><Item name="Boots" price="50.0"/></User>""",
            encodedXml
        )
        assertEquals(data, decodedXml)
    }

    @Test
    fun testCustomCollectionJsonSerialization() {
        val encodedJson = Json.encodeToString(data)
        val decodedJson = Json.decodeFromString<User>(encodedJson)

        assertEquals(
            """{"name":"Alice","shoppingCart":[{"name":"T-Shirt","price":20.0},{"name":"Boots","price":50.0}]}""",
            encodedJson
        )
        assertEquals(data, decodedJson)
    }
}