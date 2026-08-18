CREATE TYPE user_role AS ENUM ('STUDENT', 'TEACHER', 'ADMIN');
CREATE TYPE status_enum AS ENUM ('ACTIVE', 'INACTIVE');
CREATE TYPE group_type AS ENUM ('EL', 'TN');

CREATE TABLE "app_user" (
                            id VARCHAR PRIMARY KEY DEFAULT gen_random_uuid()::varchar,
                            email VARCHAR UNIQUE NOT NULL,
                            password VARCHAR NOT NULL,
                            role user_role NOT NULL,
                            created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                            status status_enum DEFAULT 'ACTIVE'
);

CREATE TABLE "teacher" (
                           id VARCHAR PRIMARY KEY DEFAULT gen_random_uuid()::varchar,
                           user_id VARCHAR UNIQUE REFERENCES "app_user"(id),
                           first_name VARCHAR NOT NULL,
                           last_name VARCHAR NOT NULL,
                           specialty VARCHAR,
                           status status_enum DEFAULT 'ACTIVE'
);

CREATE TABLE "student" (
                           id VARCHAR PRIMARY KEY DEFAULT gen_random_uuid()::varchar,
                           promotion_id VARCHAR REFERENCES "promotion"(id),
                           user_id VARCHAR UNIQUE REFERENCES "app_user"(id),
                           student_number VARCHAR UNIQUE NOT NULL,
                           first_name VARCHAR NOT NULL,
                           last_name VARCHAR NOT NULL,
                           email VARCHAR UNIQUE NOT NULL,
                           status status_enum DEFAULT 'ACTIVE',
                           date_enroll DATE
);

CREATE TABLE "group" (
                         id VARCHAR PRIMARY KEY DEFAULT gen_random_uuid()::varchar,
                         ref VARCHAR NOT NULL,
                         type group_type,
                         capacity INT
);

CREATE TABLE "group_assignment" (
                                    id VARCHAR PRIMARY KEY DEFAULT gen_random_uuid()::varchar,
                                    student_id VARCHAR REFERENCES "student"(id),
                                    group_id VARCHAR REFERENCES "group"(id),
                                    semester_id VARCHAR REFERENCES "semester"(id),
                                    assigned_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                    is_active BOOLEAN DEFAULT TRUE
);