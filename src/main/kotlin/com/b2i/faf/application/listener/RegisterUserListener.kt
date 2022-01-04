package com.b2i.faf.application.listener

import com.b2i.faf.application.event.RegisterUserEvent
import com.b2i.faf.infrastructure.local.mail.helper.EmailHelper
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class RegisterUserListener(private var emailHelper: EmailHelper):ApplicationListener<RegisterUserEvent> {

    override fun onApplicationEvent(event: RegisterUserEvent) {
        if(event!=null){
            val user = event.user
            if(user.contact.email.isNotEmpty()){
                //emailHelper.sendEmailCreatedAccount(user,event.pass)
            }
        }
    }


}

