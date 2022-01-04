package com.b2i.faf.domain.account.port

import com.b2i.faf.domain.account.entity.User
import com.b2i.faf.utils.OperationResult

/**
 * Handle User's access management
 *
 * @author Alexwilfriedo
 **/
interface IManageUserPassword {

    fun forgotPassword(user: User): OperationResult<Boolean>

    fun updatePassword(user: User, newPassword: String): OperationResult<Boolean>
}