CREATE TABLE users (
   github_id VARCHAR(255) PRIMARY KEY,
   github_username VARCHAR(255) UNIQUE NOT NULL,
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
  test_id BIGINT NOT NULL,
  taken_by VARCHAR(255) NOT NULL,
  submit_date TIMESTAMP NOT NULL DEFAULT NOW(),
  grade DOUBLE PRECISION,
  passed BOOLEAN,
  graded BOOLEAN NOT NULL DEFAULT FALSE,
  corrected_by VARCHAR(255),

  CONSTRAINT fk_test FOREIGN KEY (test_id) REFERENCES tests(id) ON DELETE CASCADE,
  CONSTRAINT fk_user FOREIGN KEY (taken_by) REFERENCES users(github_id) ON DELETE CASCADE
);

CREATE TABLE answers (
     id SERIAL PRIMARY KEY,
     question_id BIGINT NOT NULL,
     answer_text TEXT NOT NULL,
     test_result_id BIGINT NOT NULL,

     CONSTRAINT fk_question FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE,
     CONSTRAINT fk_test_result FOREIGN KEY (test_result_id) REFERENCES test_results(id) ON DELETE CASCADE
);
