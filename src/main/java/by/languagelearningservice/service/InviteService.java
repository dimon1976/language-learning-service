package by.languagelearningservice.service;

import by.languagelearningservice.entity.Invite;
import by.languagelearningservice.entity.InviteStatus;
import by.languagelearningservice.entity.User;
import by.languagelearningservice.repository.InviteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class InviteService {

    @Autowired
    private UserService userService;
    @Autowired
    private InviteRepository inviteRepository;

    public void save(Invite invite) {
        List<Invite> invites = inviteRepository.getAllByToUserId(invite.getTo().getUserId());
        for (Invite i : invites) {
            if (i.getFrom().getUserId() == invite.getFrom().getUserId()) {
                i.setStatus(invite.getStatus());
                if (invite.getStatus().name().equals(InviteStatus.ACCEPTED.name())) {
                    userService.addFriend(invite);
                }
                inviteRepository.save(i);
                return;
            }
        }
        inviteRepository.save(invite);
    }


    public List<Invite> getAllInvitesByToUser(long id) {
        return inviteRepository.findInvitesByTo_UserId(id);
    }

    public Invite getById(long id) {
        return inviteRepository.getById(id);
    }

    public void remove(User to, User from) {
        List<Invite> invites = inviteRepository.getAllByToUserId(to.getUserId());
        for (Invite i : invites) {
            if (i.getFrom().getUserId() == from.getUserId()) {
                inviteRepository.delete(i);
                userService.removeFriends(i);
                return;
            }
        }
    }
}
