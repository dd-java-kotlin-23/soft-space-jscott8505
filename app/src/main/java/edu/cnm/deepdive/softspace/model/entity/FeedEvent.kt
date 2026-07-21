package edu.cnm.deepdive.softspace.model.entity

data class FeedEvent(
    val posts: List<Post> = emptyList()
)
