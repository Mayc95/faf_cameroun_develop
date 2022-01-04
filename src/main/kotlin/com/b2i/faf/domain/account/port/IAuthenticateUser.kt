package com.b2i.faf.domain.account.port

import com.b2i.faf.domain.account.entity.User
import com.b2i.faf.utils.OperationResult
import java.util.*

/**
 * Handle User authentication
 *
 * @author Alexwilfriedo
 **/
interface IAuthenticateUser {

    fun findByUsername(username: String): Optional<User>

    fun findByEmail(email: String): Optional<User>

    fun findByPhone(phone:String):Optional<User>

    fun findUserById(id: Long): Optional<User>

    fun authenticateUser(username: String, password: String): OperationResult<User>

    fun findUserByIdAndUserType(user_type:String,id_user:Long):Optional<User>

    fun findUserByIdUserTypeAndEmail(user_type:String,email:String):Optional<User>

    fun findUserByUserTypeAndUsername(user_type:String,username:String):Optional<User>

    fun updateDataUser(mode:String,user:User): OperationResult<User>
}