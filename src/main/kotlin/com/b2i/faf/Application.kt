package com.b2i.faf

import com.b2i.faf.application.bootstrap.*
import com.b2i.faf.domain.account.port.*
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@SpringBootApplication
class Application {

    @Bean
    fun initDatabase(
            userDomain: UserDomain,
            roleDomain: RoleDomain,
            periodDomain: PeriodDomain,
    ): CommandLineRunner {

        return CommandLineRunner {
            //
            RoleBootstrap.seed(roleDomain)
            UserBootstrap.seed(userDomain, roleDomain)
        }
    }

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate = builder.build()

}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
