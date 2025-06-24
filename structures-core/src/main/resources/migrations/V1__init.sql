
-- Create the application table if it does not exist
CREATE TABLE IF NOT EXISTS struct_application (
    id KEYWORD,
    description TEXT,
    updated DATE,
    enableGraphQL BOOLEAN,
    enableOpenAPI BOOLEAN
);

-- Create the named_query_service_definition table if it does not exist
CREATE TABLE IF NOT EXISTS struct_named_query_service_definition (
    id KEYWORD,
    applicationId KEYWORD,
    projectId KEYWORD,
    structure KEYWORD,
    namedQueries JSON
); 

-- Create the project table if it does not exist
CREATE TABLE IF NOT EXISTS struct_project (
    id KEYWORD,
    applicationId KEYWORD,
    name KEYWORD,
    description TEXT,
    sourceOfTruth KEYWORD,
    updated DATE
);

-- Create the structure table if it does not exist
CREATE TABLE IF NOT EXISTS struct_structure (
    id KEYWORD,
    name KEYWORD,
    applicationId KEYWORD,
    projectId KEYWORD,
    description TEXT,
    multiTenancyType KEYWORD,
    entityType KEYWORD,
    entityDefinition JSON,
    created DATE,
    updated DATE,
    published BOOLEAN,
    publishedTimestamp DATE,
    itemIndex KEYWORD,
    decoratedProperties JSON,
    versionFieldName KEYWORD,
    tenantIdFieldName KEYWORD,
    timeReferenceFieldName KEYWORD
);




