-- Step 1: Add the column
ALTER TABLE answers ADD COLUMN test_id BIGINT;

-- Step 2: Update existing rows with a default value or a valid test_id
-- For example, setting a default value of 1 (or another valid ID)
UPDATE answers
SET test_id = 1; -- Adjust according to your needs

-- Step 3: Alter the column to enforce NOT NULL
ALTER TABLE answers ALTER COLUMN test_id SET NOT NULL;
