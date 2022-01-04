package com.b2i.faf.application.listener

import com.b2i.faf.application.event.PasswordResetEvent
import com.b2i.faf.infrastructure.local.mail.helper.EmailHelper
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class PasswordResetListener(private  val emailHelper: EmailHelper):ApplicationListener<PasswordResetEvent> {

    override fun onApplicationEvent(event: PasswordResetEvent) {
        val reset = event.passwordReset
        if(reset.user!!.contact.email.isNotEmpty()){
            emailHelper.sendEmailResetPassword(reset.user!!.contact.email,reset.token)
        }
    }
}