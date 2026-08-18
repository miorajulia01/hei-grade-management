CREATE TABLE "course" (
                          id VARCHAR PRIMARY KEY DEFAULT gen_random_uuid()::varchar,
                          semester_id VARCHAR REFERENCES "semester"(id),
                          program_id VARCHAR REFERENCES "program"(id),
                          ref VARCHAR NOT NULL,
                          title VARCHAR NOT NULL,
                          credit INT NOT NULL,
                          type VARCHAR,
                          is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE "course_teacher" (
                                  course_id VARCHAR REFERENCES "course"(id),
                                  teacher_id VARCHAR REFERENCES "teacher"(id),
                                  assigned_at DATE DEFAULT CURRENT_DATE,
                                  is_primary BOOLEAN DEFAULT FALSE,
                                  PRIMARY KEY (course_id, teacher_id)
);