package com.lukamaletin.codeshare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lukamaletin.codeshare.model.dto.RegisterDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    private static final String DEFAULT_PHOTO_URL = "/images/user_thumb.png";

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    @Column(unique = true, nullable = false)
    private String username;

    @NotNull
    @JsonIgnore
    private String password;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private String address;

    private String photoUrl;

    private boolean banned; // Adding snippets and comments disabled

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Snippet> snippets;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Score> scores;

    public User() {
    }

    public User(Role role, String username, String password, String firstName, String lastName, String phoneNumber, String email, String address, String photoUrl, boolean banned, List<Snippet> snippets, List<Comment> comments, List<Score> scores) {
        this.role = role;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.photoUrl = photoUrl;
        this.banned = banned;
        this.snippets = snippets;
        this.comments = comments;
        this.scores = scores;
    }

    public User(RegisterDto registerDto) {
        this.role = Role.REGULAR;
        this.username = registerDto.getUsername();
        this.password = registerDto.getPassword();
        this.firstName = registerDto.getFirstName();
        this.lastName = registerDto.getLastName();
        this.phoneNumber = registerDto.getPhoneNumber();
        this.email = registerDto.getEmail();
        this.address = registerDto.getAddress();
        this.photoUrl = DEFAULT_PHOTO_URL;
        this.banned = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public List<Snippet> getSnippets() {
        return snippets;
    }

    public void setSnippets(List<Snippet> snippets) {
        this.snippets = snippets;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    public boolean isAdmin() {
        return role == Role.ADMIN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (banned != user.banned) return false;
        if (role != user.role) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(user.phoneNumber) : user.phoneNumber != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (address != null ? !address.equals(user.address) : user.address != null) return false;
        if (photoUrl != null ? !photoUrl.equals(user.photoUrl) : user.photoUrl != null) return false;
        if (snippets != null ? !snippets.equals(user.snippets) : user.snippets != null) return false;
        if (comments != null ? !comments.equals(user.comments) : user.comments != null) return false;
        return scores != null ? scores.equals(user.scores) : user.scores == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (photoUrl != null ? photoUrl.hashCode() : 0);
        result = 31 * result + (banned ? 1 : 0);
        result = 31 * result + (snippets != null ? snippets.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        result = 31 * result + (scores != null ? scores.hashCode() : 0);
        return result;
    }
}
