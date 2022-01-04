package com.b2i.faf.infrastructure.local.mail.email

import com.b2i.faf.domain.account.entity.User


/**
 * Handle email management
 *
 * @author Alexwilfriedo
 **/
interface ISendEmail {

    fun sendUserRegistrationEmail(user: User): Boolean

    fun sendUserForgotPasswordEmail(user: User): Boolean

    fun sendUserUpdatePasswordEmail(user: User): Boolean
}