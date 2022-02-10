package by.languagelearningservice.controller;

import by.languagelearningservice.controller.teach.ExController;
import by.languagelearningservice.dto.UserDto;
import by.languagelearningservice.entity.User;
import by.languagelearningservice.service.CourseService;
import by.languagelearningservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ModelMapper mapper;


    @GetMapping("/registration")
    public String reg(Model model, String learning) {
        model.addAttribute("newUser", new User());
        model.addAttribute("language", learning);
        return "/user/registration";
    }

    @PostMapping("/registration")
    public String req(@ModelAttribute("newUser") @Valid UserDto userDto, BindingResult result, Model model) throws IOException {
        if (result.hasErrors()) {
            Map<String, String> errorsMap = ExController.getErrors(result);
            model.mergeAttributes(errorsMap);
            return "/user/registration";
        } else {
            userService.save(mapper.map(userDto, User.class));
            return "redirect:/user/authorization";
        }
    }

    @GetMapping("/authorization")
    public String auth(Model model) {
        model.addAttribute("user", new User());
        return "/user/authorization";
    }

    @PostMapping("/authorization")
    public String auth(@ModelAttribute("user") @Valid UserDto userDto, BindingResult result, Model model, HttpSession httpSession) {
        if (result.hasErrors()) {
            Map<String, String> errorsMap = ExController.getErrors(result);
            model.mergeAttributes(errorsMap);
            return "/user/authorization";
        } else {
            User user = userService.findByEmail(mapper.map(userDto, User.class).getEmail());
            if (user == null) {
                model.addAttribute("notFoundEmail", true);
                return "/user/authorization";
            }
            if (userDto.getPassword().equals(user.getPassword())) {
                httpSession.setAttribute("user", user);
                return "redirect:/";
            } else {
                model.addAttribute("notFoundPassword", true);
            }
            return "/user/authorization";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession, Model model) {
        httpSession.invalidate();
//        model.addAttribute("newUser", new User());
        return "redirect:/";
    }

    @GetMapping("/choose-your-language")
    public String chooseYourLanguage() {
        return "/user/choose-your-language";
    }

}
