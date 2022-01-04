package com.b2i.faf.infrastructure.remote.service.sms

import com.b2i.faf.domain.account.port.ISendOTP
import com.b2i.faf.infrastructure.remote.sms.AroliSMSService
import com.b2i.faf.infrastructure.remote.sms.dto.HttpSMS
import com.b2i.faf.utils.OperationResult
import org.springframework.stereotype.Service

/**
 * @author Alexwilfriedo
 **/
@Service
class SMSService(private val smsService: AroliSMSService) : ISendOTP {

    override fun sendOTP(receiver: String, otp: String): OperationResult<Boolean> {
        val errors: MutableMap<String, String> = mutableMapOf()
        val sms = HttpSMS.Builder(receiver, "Votre code OTP est : $otp").setCharset("UTF-8").build()
        try {
            smsService.sendTo(sms)
        } catch (e: Exception) {
            errors["operation"] = e.localizedMessage
        }
        println(receiver)
        return OperationResult(errors.isEmpty(), errors)
    }

    override fun sendReferenceRDV(receiver: String, reference: String): OperationResult<Boolean> {
        val errors: MutableMap<String, String> = mutableMapOf()
        val sms = HttpSMS.Builder(receiver, "Le rendez-vous a ete pris avec succes \n la reference est : $reference").setCharset("UTF-8").build()
        try {
            smsService.sendTo(sms)
        } catch (e: Exception) {
            errors["operation"] = e.localizedMessage
            println("message error : ${e.message}")
        }
        println("message receiver : ${receiver}")
        return OperationResult(errors.isEmpty(), errors)
    }

    override fun sendOrange(to: String, from: String,content : String): OperationResult<Boolean> {
        val errors: MutableMap<String, String> = mutableMapOf()

        try {
            val sms = smsService.sendOrangeCITo(to,from,content)
        } catch (e: Exception) {
            errors["operation"] = e.localizedMessage
            println("message error : ${e.message}")
        }
        println("message receiver : ")
        return OperationResult(errors.isEmpty(), errors)
    }
}