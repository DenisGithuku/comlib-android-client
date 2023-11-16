package com.githukudenis.comlib.core.common

fun String.capitalize(): String = this.lowercase().replaceFirstChar { it.uppercase() }

fun String.untangle(separator: String): String {
    return try {
        this.split(separator)
            .joinToString(" ") { it.capitalize() }
    } catch (e: Exception) {
        this
    }
}


