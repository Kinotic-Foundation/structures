package org.kinotic.structures.sql.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a migration with a version and a list of statements to execute.
 * Used to manage Elasticsearch index migrations in a SQL-like DSL.
 * Created by Navíd Mitchell 🤝 Grok on 3/31/25.
 */
@Getter
@RequiredArgsConstructor
public class Migration {
    private final String version;
    private final List<Statement> statements = new ArrayList<>();

    public void addStatement(Statement statement) {
        statements.add(statement);
    }
}