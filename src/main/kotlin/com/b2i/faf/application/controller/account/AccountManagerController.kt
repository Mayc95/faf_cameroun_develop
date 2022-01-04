package com.b2i.faf.application.controller.account

import com.b2i.faf.application.controlForm.Color
import com.b2i.faf.application.controller.BaseController
import com.b2i.faf.application.controller.ControllerEndpoint
import com.b2i.faf.application.event.PasswordResetEvent
import com.b2i.faf.application.facade.AuthenticationFacade
import com.b2i.faf.domain.account.entity.PasswordReset
import com.b2i.faf.domain.account.entity.StateData
import com.b2i.faf.domain.account.entity.UserType
import com.b2i.faf.domain.account.port.UserDomain
import com.b2i.faf.utils.OperationResult
import com.b2i.social.application.controlForm.ControlForm
import org.springframework.context.ApplicationEventPublisher
import org.springframework.mobile.device.Device
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.time.LocalDateTime
import java.util.*
import javax.servlet.http.HttpServletRequest

@Controller
@RequestMapping(value = [ControllerEndpoint.BACKEND_ACCOUNT])
class AccountManagerController(
        private val authenticationFacade: AuthenticationFacade,
        private val userDomain: UserDomain,
        private val eventPublisher: ApplicationEventPublisher
) : BaseController(ControllerEndpoint.BACKEND_ACCOUNT) {

    @GetMapping("/login")
    internal fun login(
            model: Model,
            redirectAttributes: RedirectAttributes,
            device: Device,
            request: HttpServletRequest,
            @RequestParam(value = "error", required = false) error: String?,
            @RequestParam(value = "logout", required = false) logout: String?,
            @RequestParam(value = "username", required = false) username: String?): String {

        if (error != null) {
            ControlForm.redirectAttribute(redirectAttributes,"Données incorrectes ou compte désactivé", Color.red)
            return redirectTo("login")
        }

        if (logout != null) {
            return redirectTo("login")
        }

        return forwardTo("login")
    }



    @GetMapping("/forgot-password")
    fun pageForgotPassword(
            model: Model
    ):String {
        return forwardTo("forgot-password")
    }



    @PostMapping("/forgot-password")
    fun forgotPassword(
            model: Model,
            redirectAttributes: RedirectAttributes,
            @RequestParam("email")email:String,
            request: HttpServletRequest
    ):String {

        when{
          email.isEmpty()->{
              ControlForm.objetError(redirectAttributes,ControlForm.emptyField("Email"))
          }
          !userDomain.findByEmail(email).isPresent->{
              ControlForm.objetError(redirectAttributes,ControlForm.objectNotFound("Email"))
          }
          else -> {
              val optionalUser = userDomain.findByEmail(email)

              if(optionalUser.isPresent){

                  val data = optionalUser.get()
                  var reset = PasswordReset()

                  var data_reset:OperationResult<PasswordReset>? = null
                  reset.user=data
                  reset.token= UUID.randomUUID().toString()
                  reset.expiredDate= LocalDateTime.now().plusHours(1)
                  reset.state=StateData.REQUEST

                  try {

                      data_reset = userDomain.savePasswordReset(reset)
                      eventPublisher.publishEvent(PasswordResetEvent(data_reset.data!!))

                      redirectAttributes.addFlashAttribute("email_user",data_reset.data?.user?.contact?.email)
                      redirectAttributes.addFlashAttribute("id_user",data_reset.data?.user?.id)
                      redirectAttributes.addFlashAttribute("id_reset",data_reset.data?.id)
                      ControlForm.redirectAttribute(redirectAttributes,"Opération effectuée avec succès",Color.green)
                      return redirectTo("verifying-token")

                  }catch(e:Exception) {
                      ControlForm.objetError(redirectAttributes, ControlForm.message.FAIL)
                      println("message error : ${e.message}")
                  }

              }else{
                  ControlForm.objetError(redirectAttributes,"Utilisateur introuvable")
              }

          }
        }
        return ControlForm.redirectPreviousPage(request)
    }


    @GetMapping("/verifying-token")
    fun pageVerifyingToken(
            model: Model
    ):String {
        print("")
        return forwardTo("verifying-token")
    }



    @PostMapping("/verifying-token")
    fun verifyingToken(
            @RequestParam("id_reset",defaultValue = "-1")id_reset:String,
            @RequestParam("token",defaultValue = "-1")token:String,
            redirectAttributes:RedirectAttributes,
            request: HttpServletRequest,
            model: Model
    ):String {

        val optionalVerifToken = userDomain.findPRByIdAndToken(id_reset.toLong(),token)

        when {
            token.isEmpty()->{
                ControlForm.objetError(redirectAttributes,ControlForm.emptyField("Le code de recupération"))
            }
            !optionalVerifToken.isPresent->{
                ControlForm.objetError(redirectAttributes,"Le code de recupération invalide")
            }
            else -> {
                val data = optionalVerifToken.get()
                var expiredDateTime = data.expiredDate
                var dateTimeNow = LocalDateTime.now()

                return if(expiredDateTime!!.isBefore(dateTimeNow)){
                    ControlForm.objetError(redirectAttributes,"Le code récupération a expiré")
                    redirectTo("login")
                }else{
                    /*model.addAttribute("user_id",data.user?.id)
                    forwardTo("reset-password")*/
                    redirectAttributes.addFlashAttribute("user_id",data.user?.id)
                    redirectTo("reset-password")
                }
            }
        }
        return ControlForm.redirectPreviousPage(request)
    }


    @GetMapping("/reset-password")
    fun pageResetPassword(
            model: Model
    ):String {
        print("")
        return forwardTo("reset-password")
    }


    @PostMapping("/reset-password")
    fun resetPassword(
            @RequestParam("user_id",defaultValue = "-1")user_id:String,
            @RequestParam("new_mdp")new_mdp:String,
            @RequestParam("conf_mdp")conf_mdp:String,
            redirectAttributes: RedirectAttributes,
            model: Model,
            request: HttpServletRequest
    ):String{

        val optionalUser = userDomain.findUserById(user_id.toLong())
        when{
            !optionalUser.isPresent->{
                ControlForm.objetError(redirectAttributes,"Utilisateur introuvable")
            }
            new_mdp.isEmpty()->{
                ControlForm.objetError(redirectAttributes,ControlForm.emptyField("Nouveau Mot de Passe"))
            }
            conf_mdp.isEmpty()->{
                ControlForm.objetError(redirectAttributes,ControlForm.emptyField("Confirmer Mot de Passe"))
            }
            !new_mdp.equals(conf_mdp)->{
                ControlForm.objetError(redirectAttributes,"Confirmer mot de passe et nouveau mot de passe doivent être identiques")
            }
            else->{

                try {
                    val user = optionalUser.get()
                    user.password=BCryptPasswordEncoder().encode(new_mdp)
                    userDomain.createOrUpdateUser(user)
                    ControlForm.objetSuccess(redirectAttributes,ControlForm.message.SUCCESS)
                }catch (e:Exception){
                    ControlForm.objetError(redirectAttributes,ControlForm.message.FAIL)
                    println("update password : ${e.message}")
                }
            }

        }
        return redirectTo("login")
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
        var user_id = authenticationFacade.getAuthenticatedUser().get().id
        var userType = userDomain.findUserTypeByUserId(user_id)

        when{
            old_mdp.isEmpty()->{
                ControlForm.objetError(redirectAttributes,ControlForm.emptyField("Ancien Mot de Passe"))
                return "redirect:"+request.getHeader("Referer")
            }
            new_mdp.isEmpty()->{
                ControlForm.objetError(redirectAttributes,ControlForm.emptyField("Nouveau Mot de Passe"))
                return "redirect:"+request.getHeader("Referer")
            }
            conf_mdp.isEmpty()->{
                ControlForm.objetError(redirectAttributes,ControlForm.emptyField("Confirmer Mot de Passe"))
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
                user.firstConnection=false
                try {
                    userDomain.createOrUpdateUser(user)
                    ControlForm.objetSuccess(redirectAttributes,ControlForm.message.SUCCESS)
                }catch (e:Exception){
                    ControlForm.objetError(redirectAttributes,ControlForm.message.FAIL)
                    println("update password : ${e.message}")
                }
            }

        }
        return when(userType){
            UserType.ADMIN->{
                "redirect:/backend/member/admin"
            }
            UserType.ACTUATOR->{
                "redirect:/backend/member/admin"
            }
            else->{
                "redirect:/account/logout"
            }
        }
    }
}