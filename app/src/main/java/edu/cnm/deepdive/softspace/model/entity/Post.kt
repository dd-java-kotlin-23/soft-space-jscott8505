package edu.cnm.deepdive.softspace.model.entity

import java.util.Date

data class Post(
    val id: String = "",
    val authorName: String = "",
    val content: String = "",
    val imageUrl: String? = null,
    val commentCount: Int = 0,
    val createdDate: Date = Date()
)
