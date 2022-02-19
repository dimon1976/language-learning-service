package by.languagelearningservice.controller.learn;

import by.languagelearningservice.entity.Invite;
import by.languagelearningservice.entity.InviteStatus;
import by.languagelearningservice.entity.Language;
import by.languagelearningservice.entity.User;
import by.languagelearningservice.service.InviteService;
import by.languagelearningservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/social")
public class SocialController {

    @Autowired
    private InviteService inviteService;
    @Autowired
    private UserService userService;

    @GetMapping("/notifications{removeUser}")
    public String notifications(@RequestParam(value = "removeUser", required = false) User removeUser, Model model, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if (removeUser != null) {
            inviteService.remove(user, removeUser);
        }
        List<Invite> inviteList = inviteService.getAllInvitesByToUser(user.getUserId());
        model.addAttribute("user", user);
        model.addAttribute("inviteList", inviteList);
        return "/social/notifications";
    }

    @GetMapping
    public String index(@RequestParam(value = "filter", required = false) Language language, Model model, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if (language != null) {
            List<User> userList = userService.getUsersByNativeLanguage(user, language);
            model.addAttribute("userList", userList);
        } else {
            List<User> userList = userService.getAllUsers(user);
            model.addAttribute("userList", userList);
        }
        Set<User> friendList = userService.getAllFriends(user.getUserId());
        Map<String, String> languages = Stream.of(Language.values()).collect(Collectors.toMap(Language::name, Language::getTranslation));
        model.addAttribute("languages", languages);
        model.addAttribute("friendList", friendList);
        model.addAttribute("user", user);

        return "/social/discover";
    }

    @GetMapping("/invite{userId}{inviteId}")
    public String addFriend(@RequestParam(value = "userId", required = false) User userTo,
                            @RequestParam(value = "inviteId", required = false) Invite invite,
                            @RequestParam("requestInvite") InviteStatus requestInvite,
                            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                            Model model, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if (invite == null) {
            Invite inviteNew = new Invite(user, userTo, requestInvite);
            inviteService.save(inviteNew);
        } else {
            invite.setStatus(requestInvite);
            inviteService.save(invite);
        }
        List<Invite> inviteList = inviteService.getAllInvitesByToUser(user.getUserId());
        List<User> userList = userService.getAllUsers(user);
        model.addAttribute("userList", userList);
        model.addAttribute("user", user);
        model.addAttribute("inviteList", inviteList);
        return "social/discover";
    }
}
