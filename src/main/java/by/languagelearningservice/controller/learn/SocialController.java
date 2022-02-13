package by.languagelearningservice.controller.learn;

import by.languagelearningservice.entity.Invite;
import by.languagelearningservice.entity.InviteStatus;
import by.languagelearningservice.entity.User;
import by.languagelearningservice.service.InviteService;
import by.languagelearningservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/social")
public class SocialController {

    @Autowired
    private InviteService inviteService;
    @Autowired
    private UserService userService;

    @GetMapping
    public String index(Model model, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        List<User> userList = userService.getAllUsers(user);
        model.addAttribute("userList", userList);
        model.addAttribute("user", user);
        return "/social/discover";
    }

    @GetMapping("/addFriend{userId}")
    public String addFriend(@RequestParam("userId") User userTo, @RequestParam("requestInvite") InviteStatus requestInvite, Model model, HttpSession httpSession) {
        User userFrom = (User) httpSession.getAttribute("user");
        Invite invite = new Invite(userFrom, userTo, requestInvite);
        if (inviteService.save(invite, userFrom)) {
            userTo.getInvites().add(invite);
            userFrom.getInvites().add(invite);
            userService.update(userFrom);
            userService.update(userTo);
        }
        List<User> userList = userService.getAllUsers(userFrom);
        model.addAttribute("userList", userList);
        httpSession.setAttribute("user", userFrom);
        model.addAttribute("user", userFrom);
        return "social/discover";
    }
}
