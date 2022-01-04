package com.b2i.faf.domain.account.port

import com.b2i.faf.domain.account.entity.User
import com.b2i.faf.utils.OperationResult
import java.util.*

/**
 * Manage User registration
 *
 * @author Alexwilfriedo
 **/
interface IRegisterUser {

    fun saveUser(model: User): OperationResult<User>

    fun createOrUpdateUser(user: User): OperationResult<User>

    fun countAllUserByUserType(user_type:String):Long

     fun findUserTypeByUserId(id: Long): String

    fun findByContactEmailAndId(contact_email: String, id: Long):Optional<User>

    fun findByContactPhoneAndId(contact_phone: String, id: Long):Optional<User>

    fun findAllUserByUserType(user_type:String):MutableList<User>

    fun findUserByContactEmailAndIdNot(contact_email: String, id: Long):Optional<User>

    fun findUserByContactPhoneAndIdNot(contact_phone: String, id: Long):Optional<User>

    fun findUserByUsernameAndIdNot(username: String, id: Long):Optional<User>

    fun findUserByUsernameAndId(username: String, id: Long):Optional<User>


    fun saveUserMix(role:String,user: User):OperationResult<User>

}