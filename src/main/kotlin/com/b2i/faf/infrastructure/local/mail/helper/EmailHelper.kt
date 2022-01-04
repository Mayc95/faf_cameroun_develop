package com.b2i.faf.infrastructure.local.mail.helper

import com.b2i.faf.application.controlForm.Url
import com.b2i.faf.domain.account.entity.User
import com.b2i.social.domain.service.helper.Mail
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.PropertySource
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.SpringTemplateEngine


@Service
@PropertySource("classpath:application.properties")
class EmailHelper( private val javaMailSender: JavaMailSender,
                   @Autowired private val templateEngine : SpringTemplateEngine
)
{


    private val env: Environment?=null

    fun sendEmail( to : String, username : String, mdp : String )
    {
        val msg = SimpleMailMessage()
        msg.setTo(to)
        msg.setSubject("Bienvenue chez Youth-connect")
        msg.setText("Votre username est : $username \n" + "Votre mot de passe est : $mdp")
        javaMailSender.send(msg)
    }



    fun sendEmailContact(to:String,message:String,name:String){
        val msg =SimpleMailMessage()
        msg.setTo(to)
        msg.setSubject("Message Utilisateurs")
        msg.setText(message)
        javaMailSender.send(msg)
    }

    fun sendEmailPasswordReset( mail : Mail)
    {

        val context = Context()
        context.setVariables(mail.model)
        val html: String = templateEngine.process("email/email-template", context)

        var msg = SimpleMailMessage()
        msg.setFrom( mail.from.toString() )
        msg.setTo( mail.to )
        msg.setSubject( mail.subject.toString() )
        msg.setText( html )
        javaMailSender.send(msg)
    }

   /* fun sendEmailCreatedAccount(user:User,pass:String){
        val msg =SimpleMailMessage()
        msg.setTo(user.contact.email)
        msg.setSubject("Bienvenue sur ${user.laboAdmin?.smsHeader}  ${user.lastname }  ${user.firstname} \n")
        msg.setText("Votre compte a été crée avec succès votre mot de passe est : ${pass}")
        javaMailSender.send(msg)
    }*/



   /* fun sendRegeneratePassword(user:User,pass:String){
        val msg =SimpleMailMessage()
        msg.setTo(user.contact.email)
        msg.setSubject("Bienvenue sur ${user.laboAdmin?.smsHeader}  ${user.lastname }  ${user.firstname} \n")
        msg.setText("Votre mot de passe a été réinitialisé et le nouveau mot de passe est : ${pass}")
        javaMailSender.send(msg)
    }
*/


    fun sendEmailResetPassword(to: String,text:String){
        val msg =SimpleMailMessage()
        msg.setTo(to)
        msg.setSubject("Récuperer password  ")
        //msg.setSubject("Email : $email")
        msg.setText("Votre code de récupération est : $text \nDélai : 1 heure")
        javaMailSender.send(msg)
    }

    fun registerEmail(user: User) {

        val baseUrl : String = Url.BASE_URL  + "/email/"

//        val link : String = baseUrl + "confirm-account/" + user.username
        val link : String = Url.BASE_URL + "/account/login"
        val unsubscribeLink : String =  baseUrl + "unsubscribe/" + user.username

        val context = Context()
        context.setVariable("user", user)
        context.setVariable("link", link)
        context.setVariable("unsubscribe_link", unsubscribeLink)

        val process = templateEngine.process("service/email/register-template", context)
        val mimeMessage = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(mimeMessage)
        helper.setSubject("Bienvenue " + user.firstname + " " + user.lastname)
        helper.setText(process, true)
        helper.setTo(user.contact.email)
        javaMailSender.send(mimeMessage)
    }


   /* fun eventSendResponse(event: Event){

       /* env!!.getProperty("mail.smtp.auth")
        env.getProperty("mail.smtp.host")
        env.getProperty("mail.smtp.port")
        env.getProperty("spring.mail.username")
        env.getProperty("spring.mail.password")*/


        val prop = Properties()
        prop["mail.smtp.auth"] = true
        prop["mail.smtp.starttls.enable"] = "true"
        prop["mail.smtp.host"] = "smtp.gmail.com"
        prop["mail.smtp.port"] = "587"
        prop["spring.mail.username"] = "b2idevelop@gmail.com"
        prop["spring.mail.password"] = "5991bsi@"

        val session: Session = Session.getInstance(prop, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(
                        prop.getProperty("spring.mail.username"),
                        prop.getProperty("spring.mail.password")
                )
            }
        })

        val msg: Message = MimeMessage(session)
        msg.setFrom(InternetAddress(event.customer!!.contact.email, false))

        msg.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(event.customer!!.contact.email)
        )
        msg.subject = "Resultat d'analyse"
        msg.setContent("<p>${event.response}</p>", "text/html")
        msg.sentDate = Date()

        val messageBodyPart = MimeBodyPart()
        messageBodyPart.setContent("<p>${event.response}</p>", "text/html")

        val multipart: Multipart = MimeMultipart()
        multipart.addBodyPart(messageBodyPart)
        val attachPart = MimeBodyPart()

        attachPart.attachFile(
                File(
                        "${StorageService.uploadDirectory.toAbsolutePath()}/docs/${event.doc}")

        )
        multipart.addBodyPart(attachPart)
        msg.setContent(multipart)
        Transport.send(msg)
    }
        */


}