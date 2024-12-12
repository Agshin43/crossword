package az.idrak.convextquickstart

import kotlinx.serialization.Serializable

@Serializable
data class Word(val value: String, val starts: String)