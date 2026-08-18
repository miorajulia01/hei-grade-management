CREATE TABLE "academic_year" (
                                 id VARCHAR PRIMARY KEY DEFAULT gen_random_uuid()::varchar,
                                 label VARCHAR NOT NULL,
                                 start_date DATE NOT NULL,
                                 end_date DATE NOT NULL
);

CREATE TABLE "promotion" (
                             id VARCHAR PRIMARY KEY DEFAULT gen_random_uuid()::varchar,
                             academic_year_id VARCHAR REFERENCES "academic_year"(id),
                             ref VARCHAR NOT NULL,
                             label VARCHAR NOT NULL
);

CREATE TABLE "semester" (
                            id VARCHAR PRIMARY KEY DEFAULT gen_random_uuid()::varchar,
                            academic_year_id VARCHAR REFERENCES "academic_year"(id),
                            code VARCHAR NOT NULL,
                            label VARCHAR NOT NULL,
                            "order" INT NOT NULL,
                            start_date DATE NOT NULL,
                            end_date DATE NOT NULL
);

CREATE TABLE "program" (
                           id VARCHAR PRIMARY KEY DEFAULT gen_random_uuid()::varchar,
                           code VARCHAR NOT NULL,
                           label VARCHAR NOT NULL,
                           description TEXT
);