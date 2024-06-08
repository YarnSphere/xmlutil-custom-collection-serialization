package org.example

import nl.adaptivity.xmlutil.serialization.XML
import kotlin.test.Test
import kotlin.test.assertEquals

class CustomCollectionSerializationTest {
    @Test
    fun testCustomCollectionSerialization() {
        val data = Person(
            "Alice",
            BTreeSet(Pet("Woofer", "dog"), Pet("Meowser", "cat"))
        )

        val encodedXml = XML.encodeToString(data)
        val decodedXml = XML.decodeFromString<Person>(encodedXml)

        assertEquals(
            """<Person name="Alice"><Pet name="Woofer" species="dog"/><Pet name="Meowser" species="cat"/></Person>""",
            encodedXml
        )
        assertEquals(data, decodedXml)
    }
}