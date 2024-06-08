package org.example

import kotlinx.serialization.Serializable

@Serializable
data class Person(val name: String, val pets: BTreeSet<Pet>)

@Serializable
data class Pet(val name: String, val species: String)