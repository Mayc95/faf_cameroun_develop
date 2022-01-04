package com.b2i.faf.application.controller

import com.b2i.faf.application.facade.AuthenticationFacade
import com.b2i.faf.domain.account.entity.UserType
import com.b2i.faf.domain.account.port.UserDomain
import com.b2i.social.application.controlForm.ControlForm
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.servlet.http.HttpServletRequest

@Controller
@RequestMapping(value = ["/${ControllerEndpoint.BACKEND_DASHBOARD}"])
class MainController(
        private val authenticationFacade: AuthenticationFacade,
        private val userDomain: UserDomain
):BaseController(ControllerEndpoint.BACKEND_DASHBOARD){

    @GetMapping(value = ["", "/"])
    fun home(): String
    {
        val user = authenticationFacade.getAuthenticatedUser().get()

        val userType = userDomain.findUserTypeByUserId(user.id)
        //val link_password="redirect:/account/update-password"

        return when (userType)
        {

            UserType.ORGANIZATION -> {
                if(user.firstConnection){
                    "redirect:/account/update-password"
                }else{
                    redirectTo("member/organization")
                }

            }

            UserType.ADMIN -> {
                if(user.firstConnection){
                    "redirect:/account/update-password"
                }else{
                    redirectTo("member/admin")
                }
            }

            UserType.ACTUATOR -> {
                if(user.firstConnection){
                    "redirect:/account/update-password"
                }else{
                    redirectTo("member/admin")
                }
            }

            UserType.OPERATOR->{
                if(user.firstConnection){
                    "redirect:/account/update-password"
                }else{
                    redirectTo("member/operator")
                }
            }

            UserType.CHIEF->{
                if(user.firstConnection){
                    "redirect:/account/update-password"
                }else{
                    redirectTo("member/chief")
                }
            }


            UserType.CONTACT->{
                if(user.firstConnection){
                    "redirect:/account/update-password"
                }else{
                    redirectTo("member/contact")
                }
            }


            UserType.MANAGER->{
                if(user.firstConnection){
                    "redirect:/account/update-password"
                }else{
                    redirectTo("member/manager")
                }
            }


            else -> "redirect:/account/logout"
        }

    }


    @GetMapping("/update-password")
    fun pageUpdatePassword():String{
        return forwardTo("update-password")
    }

    @PostMapping("/update-password")
    fun updatePassword(
            @RequestParam("old_mdp")old_mdp:String,
            @RequestParam("new_mdp")new_mdp:String,
            @RequestParam("conf_mdp")conf_mdp:String,
            redirectAttributes: RedirectAttributes,
            model: Model,
            request: HttpServletRequest
    ):String{

        var pass = authenticationFacade.getAuthenticatedUser().get().password

        when{
            old_mdp.isEmpty()->{
                ControlForm.objetError(redirectAttributes, ControlForm.emptyField("Ancien Mot de Passe"))
                return "redirect:"+request.getHeader("Referer")
            }
            new_mdp.isEmpty()->{
                ControlForm.objetError(redirectAttributes, ControlForm.emptyField("Nouveau Mot de Passe"))
                return "redirect:"+request.getHeader("Referer")
            }
            conf_mdp.isEmpty()->{
                ControlForm.objetError(redirectAttributes, ControlForm.emptyField("Confirmer Mot de Passe"))
                return "redirect:"+request.getHeader("Referer")
            }
            !BCryptPasswordEncoder().matches(old_mdp,pass)->{
                ControlForm.objetError(redirectAttributes,"Ancien mot de passe incorrecte")
                return "redirect:"+request.getHeader("Referer")
            }
            new_mdp.equals(old_mdp)->{
                ControlForm.objetError(redirectAttributes,"Ancien mot de passe et nouveau mot de passe doivent être différent")
                return "redirect:"+request.getHeader("Referer")
            }
            else->{

                val user = authenticationFacade.getAuthenticatedUser().get()
                user.password= BCryptPasswordEncoder().encode(new_mdp)
                try {
                    userDomain.createOrUpdateUser(user)
                    ControlForm.objetSuccess(redirectAttributes, ControlForm.message.SUCCESS)
                }catch (e:Exception){
                    ControlForm.objetError(redirectAttributes, ControlForm.message.FAIL)
                    println("update password : ${e.message}")
                }
            }

        }
        return ControlForm.redirectPreviousPage(request)
    }


}