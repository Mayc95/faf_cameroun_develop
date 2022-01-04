package com.b2i.faf.domain.account.port

import com.b2i.faf.utils.OperationResult

/**
 * @author Alexwilfriedo
 **/
interface ISendOTP {

    fun sendOTP(receiver: String, otp : String): OperationResult<Boolean>

    fun sendReferenceRDV(receiver: String,reference:String): OperationResult<Boolean>

    fun sendOrange(to:String,from:String,content : String): OperationResult<Boolean>
}