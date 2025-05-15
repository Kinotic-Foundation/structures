-- Test migration to create a sample table

-- Create a test table with various field types
CREATE TABLE test_table (
    id KEYWORD,
    name TEXT,
    age INTEGER,
    is_active BOOLEAN,
    created_at DATE
);

-- Add some test data
INSERT INTO test_table (id, name, age, is_active, created_at) 
VALUES ('test-1', 'Test User', 25, true, '2024-03-20'); 