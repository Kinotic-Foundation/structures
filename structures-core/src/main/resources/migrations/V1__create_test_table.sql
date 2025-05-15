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
UPDATE test_table SET 
    name = 'Test User',
    age = 25,
    is_active = true,
    created_at = '2024-03-20'
WHERE id = 'test-1'; 