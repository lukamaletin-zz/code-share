package com.lukamaletin.codeshare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
public class Comment {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(length = 500)
    private String text;

    @NotNull
    private Date date;

    @ManyToOne
    private User user;

    @NotNull
    @JsonIgnore
    @ManyToOne
    private Snippet snippet;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<Score> scores;

    private int upVotes;

    private int downVotes;

    public Comment() {
    }

    public Comment(String text, Date date, User user, Snippet snippet, List<Score> scores, int upVotes, int downVotes) {
        this.text = text;
        this.date = date;
        this.user = user;
        this.snippet = snippet;
        this.scores = scores;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Snippet getSnippet() {
        return snippet;
    }

    public void setSnippet(Snippet snippet) {
        this.snippet = snippet;
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    public int getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(int upVotes) {
        this.upVotes = upVotes;
    }

    public int getDownVotes() {
        return downVotes;
    }

    public void setDownVotes(int downVotes) {
        this.downVotes = downVotes;
    }

    public void addScore(Score score) {
        if (score.isUpVote()) {
            upVotes++;
        } else {
            downVotes++;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;

        Comment comment = (Comment) o;

        if (id != comment.id) return false;
        if (upVotes != comment.upVotes) return false;
        if (downVotes != comment.downVotes) return false;
        if (text != null ? !text.equals(comment.text) : comment.text != null) return false;
        if (date != null ? !date.equals(comment.date) : comment.date != null) return false;
        if (user != null ? !user.equals(comment.user) : comment.user != null) return false;
        if (snippet != null ? !snippet.equals(comment.snippet) : comment.snippet != null) return false;
        return scores != null ? scores.equals(comment.scores) : comment.scores == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (snippet != null ? snippet.hashCode() : 0);
        result = 31 * result + (scores != null ? scores.hashCode() : 0);
        result = 31 * result + upVotes;
        result = 31 * result + downVotes;
        return result;
    }
}
