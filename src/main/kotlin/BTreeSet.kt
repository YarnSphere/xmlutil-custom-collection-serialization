package org.example

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/** Example custom collection. The implementation isn't really relevant to the example. */
@Serializable(with = BTreeSetSerializer::class)
class BTreeSet<T> private constructor(private val set: MutableSet<T>) {
    constructor(from: Collection<T>) : this(from.toMutableSet())
    constructor(vararg from: T) : this(from.toMutableSet())

    val values get(): List<T> = set.toList()

    override fun toString(): String = set.toString()
    override fun equals(other: Any?): Boolean = other is BTreeSet<*> && set == other.set
    override fun hashCode(): Int = set.hashCode()
}

/** Serializes a [BTreeSet] as a [List]. */
class BTreeSetSerializer<T>(valueSerializer: KSerializer<T>) : KSerializer<BTreeSet<T>> {
    private val listSerializer = ListSerializer(valueSerializer)

    @OptIn(ExperimentalSerializationApi::class)
    override val descriptor: SerialDescriptor =
        SerialDescriptor("org.example.BTreeSet", listSerializer.descriptor)

    override fun serialize(encoder: Encoder, value: BTreeSet<T>): Unit =
        encoder.encodeSerializableValue(listSerializer, value.values)

    override fun deserialize(decoder: Decoder): BTreeSet<T> =
        BTreeSet(decoder.decodeSerializableValue(listSerializer))
}