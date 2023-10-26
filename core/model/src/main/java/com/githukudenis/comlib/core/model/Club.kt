package com.githukudenis.comlib.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Club(
    val _id: String,
    val creator: Creator,
    val id: String,
    val members: List<Member>,
    val name: String
)