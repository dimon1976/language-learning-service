package by.languagelearningservice.service;


import by.languagelearningservice.entity.Invite;
import by.languagelearningservice.entity.Language;
import by.languagelearningservice.entity.User;
import by.languagelearningservice.entity.courses.Course;
import by.languagelearningservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    public User update(User userUpdate) {
        if (userRepository.findByEmail(userUpdate.getEmail()).isPresent()) {
            log.info(String.format("User %s update", userUpdate.getEmail()));
            User user = userRepository.findByEmail(userUpdate.getEmail()).get();
            user.setNativeLang(userUpdate.getNativeLang());
            user.setTeacher(userUpdate.getTeacher());
            user.setFirstname(userUpdate.getFirstname());
            user.setLastname(userUpdate.getLastname());
            user.setDescription(userUpdate.getDescription());
            user.setShortDescription(userUpdate.getShortDescription());
            user.setLearning(userUpdate.getLearning());
            user.setFilename(userUpdate.getFilename());
            return userRepository.save(user);
        } else {
            throw new RuntimeException(String.format("User email %s not found! ", userUpdate.getEmail()));
        }
    }

    public void addLearnCourse(long userId, Course course) {
        log.info(String.format("Request User findById %s exist", userId));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException(String.format(String.format("User Id %s not found", userId))));
        log.info(String.format("Request Course findById %s exist", course.getCourseId()));
        user.getCourses().add(course);
        userRepository.save(user);
    }

    public void removeCourse(long userId, Course course) {
        log.info(String.format("Request User findById %s exist", userId));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException(String.format(String.format("User Id %s not found", userId))));
        user.getCourses().remove(course);
        userRepository.save(user);
    }

    public List<User> getAllUsers(User user) {
        log.info(String.format("Request list Users by user %s ", user.getUserId()));
        List<User> userList = userRepository.findAll();
        List<User> users = new ArrayList<>();
        for (User u : userList) {
            if (u.getUserId() != user.getUserId() && u.getTeacher().equals(user.getTeacher())) {
                users.add(u);
            }
        }
        return users;
    }

    public List<User> getUsersByNativeLanguage(User user, Language language) {
        log.info(String.format("Request list Users by nativeLang %s ", language));
        List<User> userList = userRepository.findAll();
        List<User> users = new ArrayList<>();
        for (User u : userList) {
            if (u.getUserId() != user.getUserId() && u.getTeacher().equals(user.getTeacher())) {
                if(u.getNativeLang().name().equals(language.name()))
                users.add(u);
            }
        }
        return users;
    }


    public User getUsersById(long id) {
        return userRepository.findById(id).get();
    }


    public Set<User> getAllFriends(long id) {
        log.info(String.format("Request list Friend by user id %s ", id));
        User user = getUserById(id);
        return user.getFriends();
    }

    public void addFriend(Invite invite){
        log.info(String.format("Request add friend id %s ", invite.getFrom().getUserId()));
        User user = userRepository.findById(invite.getTo().getUserId()).
                orElseThrow(() -> new RuntimeException(String.format("Request User findById %s", invite.getFrom().getUserId())));
        user.getFriends().add(invite.getFrom());
        userRepository.save(user);
    }

    public void removeFriends(Invite invite){
        log.info(String.format("Request remove friend id %s ", invite.getFrom().getUserId()));
        User user = userRepository.findById(invite.getTo().getUserId()).
                orElseThrow(() -> new RuntimeException(String.format("Request User findById %s", invite.getFrom().getUserId())));
        user.getFriends().remove(invite.getFrom());
        userRepository.save(user);
    }
}
