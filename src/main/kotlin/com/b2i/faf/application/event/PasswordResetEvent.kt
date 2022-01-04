package com.b2i.faf.application.event
import com.b2i.faf.domain.account.entity.PasswordReset
import org.springframework.context.ApplicationEvent

class PasswordResetEvent(val passwordReset: PasswordReset):ApplicationEvent(passwordReset) {
}