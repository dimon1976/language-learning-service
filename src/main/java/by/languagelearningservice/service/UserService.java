package by.languagelearningservice.service;


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
            log.info(String.format("User %s save", user.getEmail()));
            if (user.getTeacher() == null) {
                user.setTeacher(false);
                return userRepository.save(user);
            } else {
                return userRepository.save(user);
            }
        }
    }

    public User findByEmail(String email) {
        log.info(String.format("Request User findByEmail %s exist", email));
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException(String.format("User byEmail %s not found", email)));
    }

    public User getUserById(long id) {
        log.info(String.format("Request User findById %s exist", id));
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("User byId %s not found", id)));
    }

    public User update(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            log.info(String.format("User %s update", user.getEmail()));
            return userRepository.save(user);
        } else {
            throw new RuntimeException(String.format("User email %s not found! ", user.getEmail()));
        }
    }
}
