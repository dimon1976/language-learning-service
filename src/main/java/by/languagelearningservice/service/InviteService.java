package by.languagelearningservice.service;

import by.languagelearningservice.entity.Invite;
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
        List<Invite> invites = inviteRepository.findAll();
        for (Invite i : invites) {
            if (i.getTo().getUserId() == invite.getTo().getUserId()) {
//                if (!i.getStatus().name().equals(invite.getStatus().name())) {
                Invite toSave = getById(i.getId());
                toSave.setStatus(invite.getStatus());
                inviteRepository.save(toSave);
                return;
            }
        }
        inviteRepository.save(invite);
    }




    public List<Invite> getAllInvitesByToUser(long id) {
        return inviteRepository.findInvitesByTo_UserId(id);
    }

    public Invite getById(long id){
        return inviteRepository.getById(id);
    }
}
