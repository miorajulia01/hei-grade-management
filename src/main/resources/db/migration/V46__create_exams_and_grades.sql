CREATE TABLE "exam" (
                        id VARCHAR PRIMARY KEY DEFAULT gen_random_uuid()::varchar,
                        course_id VARCHAR REFERENCES "course"(id),
                        type VARCHAR NOT NULL,
                        title VARCHAR,
                        date_exam TIMESTAMP WITH TIME ZONE,
                        coefficient NUMERIC(5, 2) NOT NULL,
                        "order" INT NOT NULL,
                        is_published BOOLEAN DEFAULT FALSE
);

CREATE TABLE "grade" (
                         id VARCHAR PRIMARY KEY DEFAULT gen_random_uuid()::varchar,
                         student_id VARCHAR REFERENCES "student"(id),
                         exam_id VARCHAR REFERENCES "exam"(id),
                         score NUMERIC(4, 2),
                         weighted_score NUMERIC(5, 2),
                         is_validated BOOLEAN DEFAULT FALSE,
                         validated_at TIMESTAMP WITH TIME ZONE
);

CREATE TABLE "grade_history" (
                                 id VARCHAR PRIMARY KEY DEFAULT gen_random_uuid()::varchar,
                                 grade_id VARCHAR REFERENCES "grade"(id),
                                 teacher_id VARCHAR REFERENCES "teacher"(id),
                                 old_score NUMERIC(4, 2),
                                 new_score NUMERIC(4, 2),
                                 reason TEXT,
                                 modified_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);