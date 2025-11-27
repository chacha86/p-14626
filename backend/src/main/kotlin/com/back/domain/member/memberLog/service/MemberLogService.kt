package com.back.domain.member.memberLog.service

import com.back.domain.member.member.entity.Member
import com.back.domain.member.memberLog.entity.MemberLog
import com.back.domain.member.memberLog.repository.MemberLogRepository
import com.back.domain.post.comment.entity.Comment
import com.back.domain.post.comment.event.PostCommentWrittenEvent
import com.back.domain.post.post.entity.Post
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service

@Service
class MemberLogService(
    private val memberLogRepository: MemberLogRepository,
    private val objectMapper: ObjectMapper
) {
    fun save(event: PostCommentWrittenEvent) {
        val log = MemberLog(
            PostCommentWrittenEvent::class.simpleName!!,
            Comment::class.simpleName!!,
            event.commentDto.id,
            Member(event.commentDto.authorId),
            Post::class.simpleName!!,
            event.postDto.id,
            Member(event.postDto.authorId),
            Member(event.actorDto.id),
            objectMapper.writeValueAsString(event)
        )

        memberLogRepository.save(log)
    }
}