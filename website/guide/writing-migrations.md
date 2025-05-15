---
title: Writing Migrations
description: Learn how to write migrations in Structures to manage schema evolution and data transformations.
---

# Writing Migrations

Migrations in Structures allow you to define and apply changes to your Elasticsearch indices in a controlled, versioned manner. Each migration is a SQL-like script identified by a unique version in its filename, ensuring idempotency and traceability. This guide explains how to write migration scripts and details the supported statements. If you're new to Structures, start with the [What is Structures?](/guide/overview) guide.

## Migration Script Structure

Migration scripts are written in a SQL-like syntax and consist of one or more statements. The version is determined by the filename in the format `V<number>__<description>.sql`.

### Filename Format

```
V<number>__<description>.sql
```

- `<number>`: A sequential number (e.g., `1`, `2`, `3`) to track applied migrations.
- `<description>`: A brief description of the migration's purpose.

### Example

```sql
-- Initial schema setup
CREATE TABLE users (name TEXT, age INTEGER);

-- Add a field and populate data
ALTER TABLE users ADD COLUMN active BOOLEAN;
UPDATE users SET active = true WHERE age > 18;
```

The above content would be saved in a file named `V1__create_users_table.sql`.

## How Migrations Work

1. **Parsing**: The `MigrationParser` reads the script and extracts the version from the filename, creating a `Migration` object containing the version and a list of statements.
2. **Execution**: The `MigrationExecutor` processes each `Migration`:
   - If not applied, executes all statements and records the version.
   - If already applied, skips the migration.
3. **Idempotency**: Ensures changes are applied only once.

## Supported Statements

Below are the SQL-like statements supported in migration scripts, with examples.

### 1. CREATE TABLE
Creates a new Elasticsearch index with specified column mappings.

- **Syntax**: `CREATE TABLE <index_name> (<column_name> <type> [, <column_name> <type>]*) ;`
- **Types**: `TEXT`, `KEYWORD`, `INTEGER`, `BOOLEAN`, `DATE`
- **Example**:
```sql
CREATE TABLE users (name TEXT, age INTEGER, active BOOLEAN);
```

### 2. CREATE COMPONENT TEMPLATE
Defines a reusable component template with settings and mappings.

- **Syntax**: `CREATE COMPONENT TEMPLATE <template_name> (<definition> [, <definition>]*) ;`
- **Definitions**: `NUMBER_OF_SHARDS = <integer>`, `NUMBER_OF_REPLICAS = <integer>`, `<column_name> <type>`
- **Example**:
```sql
CREATE COMPONENT TEMPLATE user_settings (NUMBER_OF_SHARDS = 1, NUMBER_OF_REPLICAS = 1, status KEYWORD);
```

### 3. CREATE INDEX TEMPLATE
Creates an index template with a pattern, component templates, and optional settings/mappings.

- **Syntax**: `CREATE INDEX TEMPLATE <template_name> FOR '<pattern>' USING '<component_template>' [WITH (<definition> [, <definition>]*) ] ;`
- **Example**:
```sql
CREATE INDEX TEMPLATE user_idx FOR 'users-*' USING 'user_settings' WITH (age INTEGER, active BOOLEAN);
```

### 4. ALTER TABLE
Adds a new field to an existing Elasticsearch index.

- **Syntax**: `ALTER TABLE <index_name> ADD COLUMN <column_name> <type> ;`
- **Example**:
```sql
ALTER TABLE users ADD COLUMN active BOOLEAN;
```

### 5. REINDEX
Reindexes data from one index to another with optional settings.

- **Syntax**: `REINDEX <source_index> INTO <dest_index> [WITH (<option> [, <option>]*) ] ;`
- **Options**: `CONFLICTS = (ABORT | PROCEED)`, `MAX_DOCS = <integer>`, `SLICES = (AUTO | <integer>)`, `SIZE = <integer>`, `SOURCE_FIELDS = '<fields>'`, `QUERY = '<lucene_query>'`, `SCRIPT = '<script>'`
- **Example**:
```sql
REINDEX users INTO users_new WITH (CONFLICTS = PROCEED, MAX_DOCS = 1000, SLICES = AUTO, QUERY = 'active:true');
```

### 6. UPDATE
Updates documents in an index based on a WHERE clause and SET assignments.

- **Syntax**: `UPDATE <index_name> SET <field> = <expression> [, <field> = <expression>]* WHERE <where_clause> ;`
- **Expressions**: Literals (`'value'`, `123`, `true`), parameters (`?`), binary expressions (`field + 1`)
- **Where Clause**: Conditions (`field < value`), `AND`, `OR`, parentheses
- **Example**:
```sql
UPDATE users SET status = 'active', age = age + 1 WHERE name = 'John' AND age > 30;
```

### 7. DELETE
Deletes documents from an index based on a WHERE clause.

- **Syntax**: `DELETE FROM <index_name> WHERE <where_clause> ;`
- **Example**:
```sql
DELETE FROM users WHERE active = false;
```

### 8. Comments
Ignored by the parser, used for documentation in migration scripts.

- **Syntax**: `-- <comment_text>`
- **Example**:
```sql
-- Initialize user index
CREATE TABLE users (name TEXT);
```

## Next Steps

- **Getting Started**: Learn how to set up Structures and run migrations in [Getting Started](/guide/getting-started).
- **Reference**: Explore the [Structures Config](/reference/structures-config) for advanced configuration options.
- **API Details**: Check the [Javadoc](/reference/javadoc) for in-depth class documentation.
