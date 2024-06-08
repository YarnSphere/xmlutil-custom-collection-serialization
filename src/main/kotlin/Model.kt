package org.example

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
data class User(val name: String, val shoppingCart: ShoppingCart)

@Serializable(with = ShoppingCartSerializer::class)
data class ShoppingCart(val items: List<Item>) {
    val total: Double get() = items.sumOf { it.price }
}

@Serializable
data class Item(val name: String, val price: Double)

/** Serializes the [ShoppingCart] as a [List]. */
class ShoppingCartSerializer : KSerializer<ShoppingCart> {
    private val listSerializer = ListSerializer(Item.serializer())

    @OptIn(ExperimentalSerializationApi::class)
    override val descriptor: SerialDescriptor =
        SerialDescriptor("org.example.ShoppingCart", listSerializer.descriptor)

    override fun serialize(encoder: Encoder, value: ShoppingCart): Unit =
        encoder.encodeSerializableValue(listSerializer, value.items)

    override fun deserialize(decoder: Decoder): ShoppingCart =
        ShoppingCart(decoder.decodeSerializableValue(listSerializer))
}