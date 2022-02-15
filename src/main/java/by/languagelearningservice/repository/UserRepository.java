package by.languagelearningservice.repository;

import by.languagelearningservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Override
    Optional<User> findById(Long id);

//    @Query(value = "select f from User f left join f.friends where f.userId=:id")
//    List<User> getFriendsByUser(@Param("id") long id);
}
