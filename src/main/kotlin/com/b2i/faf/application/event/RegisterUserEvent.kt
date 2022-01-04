package com.b2i.faf.application.event

import com.b2i.faf.domain.account.entity.User
import org.springframework.context.ApplicationEvent

data class RegisterUserEvent(val user:User,val pass:String):ApplicationEvent(user) {
}