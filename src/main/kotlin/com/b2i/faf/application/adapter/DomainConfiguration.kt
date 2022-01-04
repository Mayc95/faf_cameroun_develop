package com.b2i.faf.application.adapter

import com.b2i.faf.domain.account.port.ConnectionDomain
import com.b2i.faf.domain.account.port.IAuthenticateUser
import com.b2i.faf.domain.account.port.SmsDataDomain
import com.b2i.faf.domain.account.port.UserDomain
import com.b2i.faf.domain.account.worker.ConnectionWorker
import com.b2i.faf.domain.account.worker.SmsDataWorker
import com.b2i.faf.domain.account.worker.UserWorker
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class DomainConfiguration {


    @Bean
    fun userDomain(): UserDomain = UserWorker()


    @Bean
    fun connectionDomain(): ConnectionDomain = ConnectionWorker()


    @Bean
    fun authenticationManager(): IAuthenticateUser = UserWorker()

    @Bean
    fun smsDataDomain():SmsDataDomain = SmsDataWorker()

}