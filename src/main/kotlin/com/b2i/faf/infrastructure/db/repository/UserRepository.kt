package com.b2i.faf.infrastructure.db.repository

import com.b2i.faf.domain.account.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface UserRepository : JpaRepository<User, Long> {

    fun findByUsername(username: String): Optional<User>

    fun countAllByUsername(username: String): Long


    fun findByContactEmail(email: String): Optional<User>

    fun countByContact_Email( email: String ) : Long

    fun findByContactPhone(contact_phone: String): Optional<User>

    @Query("select * from user_account where user_type = :user_type",nativeQuery = true)
    fun findAllUserByUserType(@Param("user_type")user_type:String):MutableList<User>

    @Query("select count(*) from user_account where user_type = :user_type",nativeQuery = true)
    fun countAllUserByUserType(@Param("user_type")user_type:String):Long

    @Query(value = "SELECT U.user_type FROM user_account U WHERE U.id = ?1", nativeQuery = true)
    fun findTypeBy(id: Long): String

    @Query("select * from  user_account where user_type = :user_type and id = :id",nativeQuery = true)
    fun findUserByIdAndUserType(
            @Param("user_type")user_type:String,
            @Param("id")id:Long
    ):Optional<User>

    @Query("select * from user_account where email = :email and user_type = :user_type",nativeQuery = true)
    fun findUserByEmailAndUserType(
            @Param("user_type")user_type:String,
            @Param("email")email:String
    ):Optional<User>


    @Query("select * from user_account where username = :username and user_type = :user_type",nativeQuery = true)
    fun findUserByUsernameAndUserType(
            @Param("user_type")user_type:String,
            @Param("username")username:String
    ):Optional<User>

    fun findByContactEmailAndIdNot(contact_email: String, id: Long):Optional<User>

    fun findByContactPhoneAndId(contact_phone: String, id: Long):Optional<User>

    fun findByContactPhoneAndIdNot(contact_phone: String, id: Long):Optional<User>

    fun findByContactEmailAndId(contact_email: String, id: Long):Optional<User>

    fun findByUsernameAndIdNot(username: String, id: Long):Optional<User>

    fun findByUsernameAndId(username: String, id: Long):Optional<User>

}