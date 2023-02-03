package com.example.xparty.data.models

data class AllEvents(
    val _embedded: Any,
    val events : List<Party>
) {
}