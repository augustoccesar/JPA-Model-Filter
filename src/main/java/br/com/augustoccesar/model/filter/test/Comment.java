package br.com.augustoccesar.model.filter.test;

import javax.persistence.*;

/**
 * Model used for tests.
 *
 * Created by augustoccesar on 1/29/16.
 */
@Entity
@Table
public class Comment {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String text;

    @Column
    private Long createdAt;

    @Column
    private Long deletedAt;

    public Comment(Long id, String text, Long createdAt, Long deletedAt) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Long deletedAt) {
        this.deletedAt = deletedAt;
    }
}
