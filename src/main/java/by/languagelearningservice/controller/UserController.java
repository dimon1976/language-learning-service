package by.languagelearningservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

@GetMapping("/registration")
    public String reg(Model model){
    return "/user/registration";
}

@PostMapping("/registration")
    public String req(){

    return "redirect:/";
}

@GetMapping("/authorization")
    public String auth(Model model){
    return "/user/authorization";
}

@PostMapping("/authorization")
    public String auth(){
    return "/";
}

}
