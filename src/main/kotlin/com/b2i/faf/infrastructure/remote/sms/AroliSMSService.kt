package com.b2i.faf.infrastructure.remote.sms

import com.b2i.faf.domain.account.entity.SmsData
import com.b2i.faf.domain.account.port.SmsDataDomain
import com.b2i.faf.infrastructure.remote.sms.dto.HttpSMS
import com.google.gson.Gson
import com.mashape.unirest.http.HttpResponse
import com.mashape.unirest.http.Unirest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate


@Service
class AroliSMSService(
        private val restTemplate: RestTemplate,
        private val smsDataDomain: SmsDataDomain
) {

    fun sendTo(message: HttpSMS): ResponseEntity<String> {
        val responseEntity = restTemplate.getForEntity(String.format(HTTP_SMS_API_BASE_URL, "senderv2.php", message.toGetParamString()), String::class.java)

        println(responseEntity.statusCode)
        println(responseEntity.body)

        return responseEntity
    }

    fun sendOrangeCITo(to:String,from:String,content : String):HttpResponse<String> {
        val req : HttpResponse<String> = Unirest.post(
                "https://campaigns.nerhysms.com/api/send"
        )
                .header("content-type","application/json")
                .header("Authorization","Bearer ac5c1c00b98406203e667f3fb27c05")

                .queryString("to",to)
                .queryString("from",from)
                .queryString("content",content)

                /*.body(" {\"to\":\"${"2250779179030"}\",\n" +
                        " \"from\":\"${"B2I GROUP"}\"" +
                        " \"content\":\"${"Test send email"}\"" +
                        "}"
                )*/
                .asString()

        if(req.status == HttpStatus.OK.value()){
            println("send send ok   ${req.body}")
            val gson = Gson().fromJson(req.body,SmsData::class.java)

            var smsData = SmsData()
            smsData.message_count=gson.message_count
            smsData.message_id=gson.message_count
            smsData.network=gson.network
            smsData.receiver=gson.receiver
            smsData.sender=gson.sender
            smsDataDomain.saveSmsData(smsData)

        }else{
            println("mauvais bad bad  ${req.body}")
        }

        return req
    }

    companion object {

        private val HTTP_SMS_API_BASE_URL = "http://gateway2.arolitec.com/interface/%s?%s"
    }
}
