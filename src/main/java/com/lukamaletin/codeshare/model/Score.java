package com.lukamaletin.codeshare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Score {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private boolean upVote;

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    @JsonIgnore
    @ManyToOne
    private Comment comment;

    public Score() {
    }

    public Score(boolean upVote, User user, Comment comment) {
        this.upVote = upVote;
        this.user = user;
        this.comment = comment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isUpVote() {
        return upVote;
    }

    public void setUpVote(boolean upVote) {
        this.upVote = upVote;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Score)) return false;

        Score score = (Score) o;

        if (id != score.id) return false;
        if (upVote != score.upVote) return false;
        if (user != null ? !user.equals(score.user) : score.user != null) return false;
        return comment != null ? comment.equals(score.comment) : score.comment == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (upVote ? 1 : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
}
