ALTER TABLE test_results
    DROP CONSTRAINT fk_user;

ALTER TABLE test_results
    ADD CONSTRAINT fk_user FOREIGN KEY (taken_by)
        REFERENCES users(github_username);
