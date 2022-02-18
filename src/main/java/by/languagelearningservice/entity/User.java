package by.languagelearningservice.entity;

import by.languagelearningservice.entity.courses.Course;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    private String email;
    private String password;
    @Transient
    private String password2;
    private String firstname;
    private String lastname;
    private String filename;

    @Column(length = 2048)
    private String description;

    @Column(length = 1028)
    private String shortDescription;
    @JoinTable(name = "users_courses",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    @ManyToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    private Set<Course> courses;

    @JoinTable(name = "users_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<User> friends;


    @JoinTable(name = "users_invites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "invite_id"))
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Invite> invites;

    @Enumerated(EnumType.STRING)
    private Language learning;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Comment> comments;

    @Enumerated(EnumType.STRING)
    private Language nativeLang;

    @Column(updatable = false)
    private LocalDateTime dateCreating;

    private Boolean teacher;

    @PrePersist
    protected void onCreate() {
        this.dateCreating = LocalDateTime.now();
    }

    public Set<Invite> getInvites() {
        return invites;
    }

    public void setInvites(Set<Invite> invites) {
        this.invites = invites;
    }

    @Transactional
    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null) {
            this.email = email;
        }
    }

    public Language getNativeLang() {
        return nativeLang;
    }

    public void setNativeLang(Language nativeLang) {
        if (nativeLang != null) {
            this.nativeLang = nativeLang;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password != null) {
            this.password = password;
        }
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        if (firstname != null) {
            this.firstname = firstname;
        }
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        if (lastname != null) {
            this.lastname = lastname;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description != null) {
            this.description = description;
        }
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        if (shortDescription != null) {
            this.shortDescription = shortDescription;
        }
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Language getLearning() {
        return learning;
    }

    public void setLearning(Language learning) {
        if (learning != null) {
            this.learning = learning;
        }
    }


    public LocalDateTime getDateCreating() {
        return dateCreating;
    }

    public void setDateCreating(LocalDateTime dateCreating) {
        if (dateCreating != null) {
            this.dateCreating = dateCreating;
        }
    }

    public Boolean getTeacher() {
        return teacher;
    }

    public void setTeacher(Boolean teacher) {
        if (teacher != null) {
            this.teacher = teacher;
        }
    }
}
