package com.lukamaletin.codeshare.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
public class Snippet {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private String description;

    @NotNull
    @Column(length = 1000)
    private String codeSnippet;

    @NotNull
    private Date date;

    private String repositoryUrl;

    private int duration; // In seconds

    private boolean banned; // Adding comments disabled

    @NotNull
    @ManyToOne
    private Language language;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "snippet", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    public Snippet() {
    }

    public Snippet(String description, String codeSnippet, Date date, String repositoryUrl, int duration, boolean banned, Language language, User user, List<Comment> comments) {
        this.description = description;
        this.codeSnippet = codeSnippet;
        this.date = date;
        this.repositoryUrl = repositoryUrl;
        this.duration = duration;
        this.banned = banned;
        this.language = language;
        this.user = user;
        this.comments = comments;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCodeSnippet() {
        return codeSnippet;
    }

    public void setCodeSnippet(String codeSnippet) {
        this.codeSnippet = codeSnippet;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    public void setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Date getExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, duration);
        return calendar.getTime();
    }

    public boolean isExpired() {
        final Date expirationDate = new Date(date.getTime() + duration * 1000);
        return expirationDate.before(new Date());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Snippet)) return false;

        Snippet snippet = (Snippet) o;

        if (id != snippet.id) return false;
        if (duration != snippet.duration) return false;
        if (banned != snippet.banned) return false;
        if (description != null ? !description.equals(snippet.description) : snippet.description != null) return false;
        if (codeSnippet != null ? !codeSnippet.equals(snippet.codeSnippet) : snippet.codeSnippet != null) return false;
        if (date != null ? !date.equals(snippet.date) : snippet.date != null) return false;
        if (repositoryUrl != null ? !repositoryUrl.equals(snippet.repositoryUrl) : snippet.repositoryUrl != null)
            return false;
        if (language != null ? !language.equals(snippet.language) : snippet.language != null) return false;
        if (user != null ? !user.equals(snippet.user) : snippet.user != null) return false;
        return comments != null ? comments.equals(snippet.comments) : snippet.comments == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (codeSnippet != null ? codeSnippet.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (repositoryUrl != null ? repositoryUrl.hashCode() : 0);
        result = 31 * result + duration;
        result = 31 * result + (banned ? 1 : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        return result;
    }
}
