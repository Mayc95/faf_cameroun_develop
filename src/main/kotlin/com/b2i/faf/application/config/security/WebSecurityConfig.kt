package com.b2i.faf.application.config.security

import com.b2i.faf.application.controller.RestControllerEndpoint
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import kotlin.jvm.Throws

/**
 * Default Configuration for Web Security Module
 *
 * @author alexwilfriedo
 */
@Configuration
@EnableWebSecurity
class WebSecurityConfig @Autowired
constructor(
    private val userDetailsServiceImplementation: UserDetailsServiceImplementation,
    private val authenticationSuccessHandler: AuthenticationSuccessHandler,
    private val patientDetailsImplementation: PatientDetailsImplementation
) : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
                .cors()
               .and()
            .csrf()
            .ignoringAntMatchers(
                "/${RestControllerEndpoint.API_BASE_URL}/**",
                "/${RestControllerEndpoint.API_BASE_SECURED_URL}/**",
                    "/${RestControllerEndpoint.API_INTERNAL_BASE_URL}/**"
            )
            .and()
            .authorizeRequests()
            .antMatchers(
                "/${RestControllerEndpoint.API_BASE_URL}/**",
                "/${RestControllerEndpoint.API_BASE_SECURED_URL}/**",
                "/${RestControllerEndpoint.API_INTERNAL_BASE_URL}/**","/index","/","/about","/apply","/contact","/gallery","/signup",
                "/account/login","/account/forgot-password","/account/verifying-token",
                "/account/reset-password","/account/verifying-token-p"
            )
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .loginPage("/account/login")
            .failureUrl("/account/login?error=true")
            .usernameParameter("username")
            .passwordParameter("password")
            //.successHandler(authenticationSuccessHandler)
            .defaultSuccessUrl("/backend", true)
            .permitAll()
            .and()
            .logout()
            .logoutRequestMatcher(AntPathRequestMatcher("/account/logout"))
            .logoutSuccessUrl("/account/login").permitAll()
    }

    @Throws(Exception::class)
    override fun configure(web: WebSecurity?) {
        web?.ignoring()?.antMatchers(
            "/assets/**",
            "/login/**",
            "/css/**",
            "/demo/**",
            "/fonts/**",
            "/fullcalendar/**",
            "/lib/**",
            "/styles/**",
            "/img/**",
            "/js/**",
            "/scss/**"
        )
    }

    @Autowired
    @Throws(Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsServiceImplementation)
            .passwordEncoder(BCryptPasswordEncoder())
    }
}
