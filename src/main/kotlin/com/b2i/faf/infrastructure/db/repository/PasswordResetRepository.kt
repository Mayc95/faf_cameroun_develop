package com.b2i.faf.infrastructure.db.repository

import com.b2i.faf.domain.account.entity.PasswordReset
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PasswordResetRepository:JpaRepository<PasswordReset,Long> {

    fun findByUserContactEmail(email: String):Optional<PasswordReset>

    fun findByToken(token: String):Optional<PasswordReset>

    fun findByIdAndToken(id: Long, token: String):Optional<PasswordReset>
}