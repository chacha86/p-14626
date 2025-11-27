package com.back.global.initData

import com.back.domain.member.member.service.MemberService
import com.back.domain.post.post.service.PostService
import com.back.domain.post.postUser.service.PostUserService
import com.back.standard.extentions.getOrThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.transaction.annotation.Transactional

@Configuration
class BaseInitData(
    @Autowired
    @Lazy
    private val self: BaseInitData,
    private val postService: PostService,
    private val memberService: MemberService,
    private val postUserService: PostUserService
) {
    @Bean
    fun initDataRunner(): ApplicationRunner = ApplicationRunner {
        self.work1()
        self.work2()
    }

    @Transactional
    fun work1() {
        if (memberService.count() > 0) {
            return
        }

        listOf(
            Triple("system", "system", "시스템"),
            Triple("admin", "admin", "운영자"),
            Triple("user1", "1234", "유저1"),
            Triple("user2", "1234", "유저2"),
            Triple("user3", "1234", "유저3"),
            Triple("user4", "1234", "유저4"),
            Triple("user5", "1234", "유저5"),
            Triple("user6", "1234", "유저6"),
        ).forEach { (username, password, nickname) ->
            memberService.join(username, password, nickname).apply {
                updateApiKey(username)
            }
        }
    }

    @Transactional
    fun work2() {
        if (postService.count() > 0) {
            return
        }

        val postUser1 = postUserService.findByUsername("user1").getOrThrow()
        val postUser2 = postUserService.findByUsername("user2").getOrThrow()
        val postUser3 = postUserService.findByUsername("user3").getOrThrow()

        val post1 = postService.write(postUser1, "제목1", "내용1")
        val post2 = postService.write(postUser1, "제목2", "내용2")
        val post3 = postService.write(postUser2, "제목3", "내용3")

        postService.writeComment(postUser1, post1, "댓글 1-1")
        postService.writeComment(postUser1, post1, "댓글 1-2")
        postService.writeComment(postUser2, post1, "댓글 1-3")
        postService.writeComment(postUser3, post2, "댓글 2-1")
        postService.writeComment(postUser3, post2, "댓글 2-2")
    }
}