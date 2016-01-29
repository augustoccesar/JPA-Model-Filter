package br.com.augustoccesar.model.filter.test;

import javax.persistence.*;

/**
 * Model used for tests.
 *
 * Created by augustoccesar on 1/29/16.
 */
@Entity
@Table
public class Profile {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
