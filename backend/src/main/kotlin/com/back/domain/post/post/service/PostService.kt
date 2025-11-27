package com.back.domain.post.post.service

import com.back.domain.post.comment.dto.CommentDto
import com.back.domain.post.comment.entity.Comment
import com.back.domain.post.comment.event.PostCommentWrittenEvent
import com.back.domain.post.post.dto.PostDto
import com.back.domain.post.post.entity.Post
import com.back.domain.post.post.repository.PostRepository
import com.back.domain.post.postUser.dto.PostUserDto
import com.back.domain.post.postUser.entity.PostUser
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PostService (
    private val postRepository: PostRepository,
    private val publisher: ApplicationEventPublisher,
) {

    fun write(author: PostUser, title: String, content: String): Post {
        val post = Post(author, title, content)
        author.incrementPostsCount()
        return postRepository.save(post)
    }

    fun count(): Long {
        return postRepository.count()
    }

    fun findById(id: Long): Post? {
        return postRepository.findByIdOrNull(id)
    }

    fun findAll(): List<Post> {
        return postRepository.findAll()
    }

    fun modify(post: Post, title: String, content: String) {
        post.update(title, content)
    }

    fun writeComment(author: PostUser, post: Post, content: String): Comment {
        val comment = post.addComment(author, content)

        postRepository.flush()

        publisher.publishEvent(
            PostCommentWrittenEvent(
                CommentDto(comment),
                PostDto(post),
                PostUserDto(author)
            )
        )

        return comment
    }

    fun deleteComment(post: Post, commentId: Long) {
        post.author.decrementPostsCount()
        post.deleteComment(commentId)
    }

    fun modifyComment(post: Post, commentId: Long, content: String) {
        post.updateComment(commentId, content)
    }

    fun delete(post: Post) {
        postRepository.delete(post)
    }

    fun flush() {
        postRepository.flush()
    }
}
