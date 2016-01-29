package br.com.augustoccesar.model.filter.test;

import javax.persistence.*;
import java.util.List;

/**
 * Model used for tests.
 *
 * Created by augustoccesar on 1/29/16.
 */
@Entity
@Table
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String username;

    @Column
    private String email;

    @OneToOne
    private Profile profile;

    @OneToMany
    private List<Comment> comments;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
