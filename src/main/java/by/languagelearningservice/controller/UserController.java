package by.languagelearningservice.controller;

import by.languagelearningservice.dto.UserDto;
import by.languagelearningservice.entity.Language;
import by.languagelearningservice.entity.User;
import by.languagelearningservice.entity.courses.Level;
import by.languagelearningservice.service.CourseService;
import by.languagelearningservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/profile")
    public String profile(Model model, HttpSession httpSession) {
        if (httpSession.getAttribute("user") == null) {
            return "redirect:/user/authorization";
        }
        User user = (User) httpSession.getAttribute("user");
        Map<String, String> languages = Stream.of(Language.values()).collect(Collectors.toMap(Language::name, Language::getTranslation));
        model.addAttribute("languages", languages);
        model.addAttribute("userUpdate", user);
        return "user/profile/index";
    }

    @PostMapping("/profile/update")
    public String update(@RequestParam("file") MultipartFile file, @ModelAttribute("userUpdate") @Valid UserDto userDto, BindingResult result, Model model, HttpSession httpSession) throws IOException {
        User userAuth = (User) httpSession.getAttribute("user");
        if (userDto.getPassword() != null && userAuth.getPassword().equals(userDto.getPassword()) && !userDto.getPassword().equals(userDto.getPassword2())) {
            model.addAttribute("userUpdate", userDto);
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "/user/profile/index";
        } else if (result.hasErrors()) {
            Map<String, String> errorsMap = ExController.getErrors(result);
            model.mergeAttributes(errorsMap);
            model.addAttribute("userUpdate", userDto);
            return "/user/profile/index";
        }
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFileName));
            userDto.setFilename(resultFileName);
        }
        User user = userService.update(mapper.map(userDto, User.class));
        Map<String, String> languages = Stream.of(Language.values()).collect(Collectors.toMap(Language::name, Language::getTranslation));
        Map<String, String> level = Stream.of(Level.values()).collect(Collectors.toMap(Level::name, Level::getTranslation));
        model.addAttribute("languages", languages);
        model.addAttribute("level", level);
        httpSession.setAttribute("user", user);
        model.addAttribute("userUpdate", user);
        return "/user/profile/index";
    }

    @GetMapping("/info")
    public String info(){

        return "/user/profile/info";
    }

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
