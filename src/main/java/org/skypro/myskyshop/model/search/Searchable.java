package org.skypro.myskyshop.model.search;

import java.util.UUID;

public interface Searchable {

    String getSearchTerm();

    String getContentType();

    String getName();

    default String getStringRepresentation() {
        return getName() + " - " + getContentType();
    }

    UUID getId();
}
