package com.back.domain.member.memberLog.eventListener

import com.back.domain.member.memberLog.service.MemberLogService
import com.back.domain.post.comment.event.PostCommentWrittenEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class MemberLogEventListener(
    private val memberLogService: MemberLogService,
) {
    @EventListener
    fun handle(event: PostCommentWrittenEvent) {
//        println("${event.actorDto.id}번 회원이 ${event.postDto.id}번 글에 댓글을 작성해서 ${event.commentDto.id}번 댓글이 생성되었습니다.")
        memberLogService.save(event)
    }
}