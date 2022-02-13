package by.languagelearningservice.service;

import by.languagelearningservice.entity.Invite;
import by.languagelearningservice.entity.User;
import by.languagelearningservice.repository.InviteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
public class InviteService {

    @Autowired
    private InviteRepository inviteRepository;

    public boolean save(Invite invite, User user) {
        Set<Invite> invites = user.getInvites();
        for (Invite i : invites) {
            if (i.getTo().getUserId()==invite.getTo().getUserId()) {
                if(i.getStatus()==invite.getStatus()){
                    return false;
                }
            }
        }
        inviteRepository.save(invite);
        return true;
    }
}
