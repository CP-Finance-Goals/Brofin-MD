package com.example.brofin.utils

import java.util.UUID

fun generateUniqueId(): String {
    return UUID.randomUUID().toString()
}
