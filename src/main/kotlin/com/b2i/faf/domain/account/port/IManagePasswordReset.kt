package com.b2i.faf.domain.account.port

import com.b2i.faf.domain.account.entity.PasswordReset
import com.b2i.faf.utils.OperationResult
import java.util.*

interface IManagePasswordReset {

    fun savePasswordReset(passwordReset: PasswordReset): OperationResult<PasswordReset>

    fun findPasswordReset(id:Long): Optional<PasswordReset>

    fun findAllPasswordReset():MutableList<PasswordReset>

    fun findByEmailPasswordReset(email:String): Optional<PasswordReset>

    fun findByToken(token:String): Optional<PasswordReset>

    fun findPRByIdAndToken(id: Long, token: String):Optional<PasswordReset>



}