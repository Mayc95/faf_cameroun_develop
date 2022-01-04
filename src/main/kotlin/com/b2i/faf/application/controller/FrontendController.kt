package com.b2i.faf.application.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class FrontendController {

    @GetMapping("/index")
    fun homePage(model : Model) : String{
        return "frontend/index"
    }

    @GetMapping("/about")
    fun aboutUs(model: Model) : String{
        return "frontend/about"
    }

    @GetMapping("/apply")
    fun apply(model: Model) : String{
        return "frontend/apply-now"
    }

    @GetMapping("/contact")
    fun contact(model: Model) : String{
        return "frontend/contact"
    }

    @GetMapping("/gallery")
    fun gallery(model: Model) : String{
        return "frontend/gallery"
    }

    @GetMapping("/signup")
    fun signUp(model: Model) : String{
        return "frontend/sign-up"
    }
}