package com.back.domain.member.member.entity

import com.back.global.jpa.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

@MappedSuperclass
class BaseMember(
    id: Long,
    @field:Column(unique = true) val username: String,
    var profileImgUrl: String? = null,
) : BaseEntity(id) {
    val profileImgUrlOrDefault: String
        get() {
            profileImgUrl?.let { return it }

            return "https://placehold.co/600x600?text=U_U"
        }

    val isAdmin: Boolean
        get() = "admin" == this.username

    val authorities: Collection<GrantedAuthority>
        get() {
            val authorities: MutableList<GrantedAuthority> = ArrayList()

            if (isAdmin) {
                authorities.add(SimpleGrantedAuthority("ROLE_ADMIN"))
            }

            return authorities
        }
}