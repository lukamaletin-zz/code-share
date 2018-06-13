package com.lukamaletin.codeshare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "language")
public class Language {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(unique = true, nullable = false)
    private String languageName;

    @JsonIgnore
    @OneToMany(mappedBy = "language")
    private List<Snippet> snippets;

    public Language() {
    }

    public Language(String languageName, List<Snippet> snippets) {
        this.languageName = languageName;
        this.snippets = snippets;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public List<Snippet> getSnippets() {
        return snippets;
    }

    public void setSnippets(List<Snippet> snippets) {
        this.snippets = snippets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Language)) return false;

        Language language = (Language) o;

        if (id != language.id) return false;
        if (languageName != null ? !languageName.equals(language.languageName) : language.languageName != null)
            return false;
        return snippets != null ? snippets.equals(language.snippets) : language.snippets == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (languageName != null ? languageName.hashCode() : 0);
        result = 31 * result + (snippets != null ? snippets.hashCode() : 0);
        return result;
    }
}
