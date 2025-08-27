package org.skypro.myskyshop.model.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.myskyshop.model.search.Searchable;

import java.util.Objects;
import java.util.UUID;

public final class Article implements Searchable {
    private final UUID id;
    private final String title;
    private final String text;

    public Article(UUID id, String title, String text) {
        if (id == null) {
            throw new IllegalArgumentException("Идентификатор статьи не может быть пустым!");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Название статьи не может быть пустым");
        }
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Текст статьи не может быть пустым");
        }
        this.id = id;
        this.title = title;
        this.text = text;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    @JsonIgnore
    public String getSearchTerm() {
        return toString();
    }

    @Override
    @JsonIgnore
    public String getContentType() {
        return "ARTICLE";
    }

    @Override
    public String getName() {
        return title + ": " + text;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return title + "\n" + text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Article article = (Article) o;
        return title.equals(article.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
