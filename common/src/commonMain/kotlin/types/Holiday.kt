package types

import kotlinx.serialization.Serializable

@Serializable
data class Holiday(
    val date: String,
    val name: String,
    val localName: String,
    val countryCode: String,
    val fixed: Boolean,
    val global: Boolean,
    val counties: List<String>?,
    val launchYear: Int?,
    val types: List<String>,
)
