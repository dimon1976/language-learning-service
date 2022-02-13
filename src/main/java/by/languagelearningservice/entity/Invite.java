package by.languagelearningservice.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Invite {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private User from;

    @OneToOne
    private User to;


    @Enumerated(EnumType.ORDINAL)
    private InviteStatus status;



    public Invite(User from, User to, InviteStatus status) {
        this.from = from;
        this.to = to;
        this.status = status;
    }

    public Invite() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invite invite = (Invite) o;
        return Objects.equals(from, invite.from) && Objects.equals(to, invite.to) && status == invite.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, status);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public InviteStatus getStatus() {
        return status;
    }

    public void setStatus(InviteStatus status) {
        this.status = status;
    }
}
