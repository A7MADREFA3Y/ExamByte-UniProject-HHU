CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    github_username VARCHAR(255) UNIQUE,
    github_id VARCHAR(255) UNIQUE,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE tests (
    id SERIAL PRIMARY KEY,
    test_name VARCHAR(255) UNIQUE NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    result_publication_time TIMESTAMP NOT NULL,
    created_by VARCHAR(255) NOT NULL
);

CREATE TABLE questions (
    id SERIAL PRIMARY KEY,
    question_text TEXT NOT NULL,
    question_type VARCHAR(50) NOT NULL,
    correct_answer TEXT,
    test_id BIGINT REFERENCES tests(id) ON DELETE CASCADE
);

CREATE TABLE question_options (
    question_id BIGINT REFERENCES questions(id) ON DELETE CASCADE,
    option_text TEXT NOT NULL
);

CREATE TABLE test_results (
    id SERIAL PRIMARY KEY,
    test_id BIGINT REFERENCES tests(id) ON DELETE CASCADE,
    taken_by BIGINT REFERENCES users(id) ON DELETE CASCADE,
    submit_date TIMESTAMP NOT NULL,
    grade DOUBLE PRECISION,
    passed BOOLEAN,
    corrected_by VARCHAR(255)
);

CREATE TABLE answers (
    id SERIAL PRIMARY KEY,
    question_id BIGINT REFERENCES questions(id) ON DELETE CASCADE,
    answer_text TEXT NOT NULL,
    test_result_id BIGINT REFERENCES test_results(id) ON DELETE CASCADE
);
