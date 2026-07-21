package edu.cnm.deepdive.softspace.model.entity

import java.util.Date

class Post {

    val date: CharSequence
    val imageUrl: Any
    val id: Long = 0
    val authorName: String = ""
    val content: String = ""
    val imageURL: String? = null
    val createdDate = Date()

}