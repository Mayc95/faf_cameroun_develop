package com.b2i.faf.application.config.security

import com.b2i.faf.application.controller.ControllerEndpoint
import com.b2i.faf.application.facade.AuthenticationFacade
import com.b2i.faf.domain.account.port.ConnectionDomain
import com.b2i.faf.domain.entity.common.ConnectionEvent
import com.b2i.faf.domain.entity.embeddable.Device
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession
import kotlin.jvm.Throws

/**
 * @author alexwilfriedo
 */
@Component
class AuthenticationSuccessHandler @Autowired
constructor(
    private val authenticationFacade: AuthenticationFacade,
    private val connectionDomain: ConnectionDomain
) : SimpleUrlAuthenticationSuccessHandler() {

    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationSuccess(
        request: HttpServletRequest, response: HttpServletResponse,
        authentication: Authentication
    ) {

        authenticationFacade.getAuthenticatedUser()
            .ifPresent { user ->

                val userAgent = request.getHeader("User-Agent")
                connectionDomain.save(
                    ConnectionEvent(
                        user.username, userAgent, user,
                        getDeviceInfoFrom(request.session)
                    )
                )
            }

        if (response.isCommitted) return

        logger.info(String.format("Authentication Success for user %s ", authentication.name))

        response.sendRedirect(ControllerEndpoint.BACKEND_DASHBOARD)

        super.onAuthenticationSuccess(request, response, authentication)
    }

    private fun getDeviceInfoFrom(session: HttpSession?): Device {

        val device = session?.getAttribute("device") as org.springframework.mobile.device.Device
        val type: String

        type = when {
            device.isNormal -> "Browser"
            device.isMobile -> "Mobile Phone"
            else -> "Tablet"
        }

        val sdk = session.getAttribute(Device.DEVICE_SDK) as String?
        val version = session.getAttribute(Device.DEVICE_VERSION) as String?
        val brand = session.getAttribute(Device.DEVICE_BRAND) as String?
        val manufacturer = session.getAttribute(Device.DEVICE_MANUFACTURED) as String?
        val model = session.getAttribute(Device.DEVICE_MODEL) as String?

        return Device(type, sdk ?: "", version ?: "", brand ?: "", manufacturer ?: "", model ?: "")
    }
}
