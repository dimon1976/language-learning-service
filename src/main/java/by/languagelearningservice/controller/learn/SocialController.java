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
import java.util.Set;

@Controller
@RequestMapping("/social")
public class SocialController {

    @Autowired
    private InviteService inviteService;
    @Autowired
    private UserService userService;

    @GetMapping("/notifications")
    public String notifications(Model model, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        List<Invite> inviteList = inviteService.getAllInvitesByToUser(user.getUserId());
        model.addAttribute("user", user);
        model.addAttribute("inviteList", inviteList);
        return "/social/notifications";
    }

    @GetMapping
    public String index(Model model, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        List<User> userList = userService.getAllUsers(user);
        model.addAttribute("userList", userList);
        model.addAttribute("user", user);
        return "/social/discover";
    }

    @GetMapping("/invite{userId}{inviteId}")
    public String addFriend(@RequestParam(value = "userId", required = false) User userTo,
                            @RequestParam(value = "inviteId", required = false) Invite invite,
                            @RequestParam("requestInvite") InviteStatus requestInvite,
                            Model model, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if (invite == null) {
            Invite inviteNew = new Invite(user, userTo, requestInvite);
            inviteService.save(inviteNew);
        }else {
            invite.setStatus(requestInvite);
            inviteService.save(invite);
        }
        Set<User> friend = userService.getAllFriends(user.getUserId());
        List<Invite> inviteList = inviteService.getAllInvitesByToUser(user.getUserId());
        List<User> userList = userService.getAllUsers(user);
        model.addAttribute("userList", userList);
        model.addAttribute("user", user);
        model.addAttribute("inviteList", inviteList);
        return "social/discover";
    }
}
