package com.back.domain.post.comment.event

import com.back.domain.post.comment.dto.CommentDto
import com.back.domain.post.post.dto.PostDto
import com.back.domain.post.postUser.dto.PostUserDto
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

data class PostCommentWrittenEvent(
    @field:JsonIgnoreProperties("content") val commentDto: CommentDto,
    @field:JsonIgnoreProperties("title", "content") val postDto: PostDto,
    @field:JsonIgnore val actorDto: PostUserDto
) {

}