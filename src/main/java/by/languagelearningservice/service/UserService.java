package by.languagelearningservice.service;


import by.languagelearningservice.entity.User;
import by.languagelearningservice.entity.courses.Course;
import by.languagelearningservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
