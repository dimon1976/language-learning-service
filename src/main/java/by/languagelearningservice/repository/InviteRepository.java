package by.languagelearningservice.repository;

import by.languagelearningservice.entity.Invite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InviteRepository extends JpaRepository<Invite,Long> {


    List<Invite> findInvitesByTo_UserId(Long id);
}
