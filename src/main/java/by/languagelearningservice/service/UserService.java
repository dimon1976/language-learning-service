package by.languagelearningservice.service;


import by.languagelearningservice.entity.Language;
import by.languagelearningservice.entity.User;
import by.languagelearningservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException(String.format("User %s already exist! ", user.getEmail()));
        } else {
            log.info(String.format("User {} save", user.getEmail()));
            userRepository.save(user);
            return user;
        }
    }

    public User findByEmail(String email) {
        log.info(String.format("Request email {} exist", email));
        return userRepository.findByEmail(email).orElse(null);
    }

    public void test(User user) {
        Language.EN.setLevel("Начальный");
        user.setLearning(Language.EN);
        System.out.println(user.getLearning().getTranslation() + " " + user.getLearning().getLevel());
    }

}
