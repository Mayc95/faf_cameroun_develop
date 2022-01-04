package com.b2i.social.application.controlForm

import com.b2i.faf.application.controlForm.Color
import org.springframework.ui.Model
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.text.SimpleDateFormat
import java.util.*
import javax.servlet.http.HttpServletRequest

object ControlForm {

    fun model(model: Model, message: String, color: Color) {
        model.addAttribute("operationMessage", message)
        model.addAttribute("colorMessage", color)
    }

    fun modelPhoto(model: Model, clef: String, message: String, color: Color) {
        model.addAttribute(clef, message)
        model.addAttribute("colorMessage$clef", color)
    }

    fun redirectAttribute(redirectAttributes: RedirectAttributes, message: String, color: Color){
        ///Something is wrong
        redirectAttributes.addFlashAttribute("operationMessage", message)
        redirectAttributes.addFlashAttribute("colorMessage", color)
    }


    object message{
        const val SUCCESS="Operation effectuée avec succès"
        const val FAIL="Malheureusement nous avons pas pu traité votre demande réessayer plus tard"
        const val USER_NOT_FOUND="Utilisateur iintrouvable"
        const val emailExist ="L'email a déjà été utilisé"
        const val incorectEmailFormat="Format de l'email incorrecte "
        const val phoneNumberExist="Le numero de téléphone a déjà été utilisé"
        const val usernameExist="Le username a déjà été pris"
        const val nameExist="Le nom a déjà été pris"
        const val samePassword="Les deux passwords doivent être identiques"
    }

    fun emptyField(msg:String):String{
        return "Remplissez le champ ${msg} SVP"
    }

    fun existsField(champ:String):String{
        return "${champ} a déjà été utilisé"
    }

    fun objectNotFound(ob:String):String{
        return "${ob} introuvable"
    }

    fun objetEmpty(
            redirectAttributes: RedirectAttributes,
            data:String
    ) {
        redirectAttribute(redirectAttributes,emptyField(data),Color.red)
    }

    fun objetExist(
            redirectAttributes: RedirectAttributes,
            data:String
    ) {
        redirectAttribute(redirectAttributes, existsField(data),Color.red)
    }

    fun objetError(
            redirectAttributes: RedirectAttributes,
            data:String
    ) {
        redirectAttribute(redirectAttributes,data,Color.red)
    }


    fun objetSuccess(
            redirectAttributes: RedirectAttributes,
            data:String
    ) {
        redirectAttribute(redirectAttributes,data,Color.green)
    }


    fun redirectPreviousPage( request : HttpServletRequest) : String {
        return "redirect:" + request.getHeader("Referer")
    }


    fun redirectPhoto(redirectAttributes: RedirectAttributes, clef: String, message: String, color: Color) {
        redirectAttributes.addFlashAttribute(clef, message)
        redirectAttributes.addFlashAttribute("colorMessage$clef", color)
    }

    fun verifyHashMap(model: Model, errors : Map<String, String>):Boolean
    {
        var success=true

        if ( errors.isEmpty() )
        {
            model( model, "Operation réussie", Color.green )
        }
        else
        {
            val entry: Map.Entry<String, String> = errors.entries.iterator().next()
            val key = entry.key
            val value = entry.value

            success=false

            model(model, value, Color.red)
        }

        return success
    }


    fun verifyHashMapRedirect( redirectAttributes: RedirectAttributes, errors : Map<String, String>):Boolean
    {
        var success=true

        if ( errors.isEmpty() )
        {
            redirectAttribute( redirectAttributes, "Operation réussie", Color.green )
        }
        else
        {
            val entry: Map.Entry<String, String> = errors.entries.iterator().next()
            val key = entry.key
            val value = entry.value

            success=false

            redirectAttribute( redirectAttributes , value, Color.red)
        }

        return success
    }

    fun verifyApiHashMap( errors : Map<String, String>):Boolean
    {
        var success=true

        if ( errors.isNotEmpty() )
        {
            success=false
        }

        return success
    }

    fun extractFirstMessage( errors: Map<String, String> ) : String {

        val entry: Map.Entry<String, String> = errors.entries.iterator().next()
        return entry.value
    }


    fun formatDate( date : String ) : Date {
        val format = SimpleDateFormat( "yyyy-MM-dd" )

        return format.parse( date )
    }

}