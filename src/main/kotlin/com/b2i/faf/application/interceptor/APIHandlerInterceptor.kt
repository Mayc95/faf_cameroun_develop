package com.b2i.faf.application.interceptor

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author Alexwilfriedo
 **/
@Component
class APIHandlerInterceptor : HandlerInterceptor {

   // val availableTokens: Array<String> = arrayOf("Bearer e42071e7e5a9e400486374da31ab0cd240ccdbc437f6f77b0c4b475a2ef11764")
   val availableTokens: Array<String> = arrayOf("Bearer ac5c1c00b98406203e667f3fb27c05")

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        super.preHandle(request, response, handler)

        val token = request.getHeader("Authorization")
        if (token == null || !availableTokens.contains(token) && request.session.getAttribute(token) == null) {
            response.status = HttpStatus.FORBIDDEN.value()
            response.writer.write("Forbidden Access")
            return false
        }

        return true
    }
}