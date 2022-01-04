package com.b2i.faf.application.controller.member

import com.b2i.faf.application.controller.BaseController
import com.b2i.faf.application.controller.ControllerEndpoint
import com.b2i.faf.application.facade.AuthenticationFacade
import com.b2i.faf.domain.account.port.ParamDomain
import com.b2i.faf.domain.account.port.UserDomain
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.servlet.http.HttpServletRequest

@RequestMapping(value = ["/${ControllerEndpoint.BACKEND_ADMIN}"])
class AdminController(
        private val authenticationFacade: AuthenticationFacade,
        private val userDomain: UserDomain,
        private val paramDomain: ParamDomain
):BaseController(ControllerEndpoint.BACKEND_ADMIN) {


    @GetMapping(value=["","/"])
    fun pageDash(
            redirectAttributes: RedirectAttributes,
            model: Model
    ):String{
        return "backend/dash"
    }


    @GetMapping(value = ["/profil"])
    fun pageProfil(
            model: Model,
            redirectAttributes: RedirectAttributes,
            request: HttpServletRequest
    ):String{
        val dataUser = authenticationFacade.getAuthenticatedUser().get()
        val allSex = paramDomain.findAllSex()
        val allCivility = paramDomain.findAllCivility()
        model.addAttribute("userData",dataUser)
        model.addAttribute("sex",allSex)
        model.addAttribute("civility",allCivility)
        return "backend/profil"
    }


}