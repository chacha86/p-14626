package com.back.domain.member.member.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import java.util.*

@Entity
class Member(
    id: Long,
    username: String,
    var password: String,
    var nickname: String,
    @Column(unique = true)
    var apiKey: String,
    profileImgUrl: String?
) : BaseMember(id, username, profileImgUrl) {
    constructor(id: Long) : this(id, "", "")

    constructor(username: String, password: String, nickname: String, profileImgUrl: String?) : this(
        0,
        username,
        password,
        nickname,
        UUID.randomUUID().toString(),
        profileImgUrl
    )

    constructor(id: Long, username: String, nickname: String) : this(
        id,
        username,
        "",
        nickname,
        "",
        null
    )

    val name: String
        get() = this.nickname

    fun updateApiKey(apiKey: String) {
        this.apiKey = apiKey
    }

    fun update(nickname: String, profileImgUrl: String?) {
        this.nickname = nickname
        this.profileImgUrl = profileImgUrl
    }
}

