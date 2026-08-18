CREATE TYPE doc_export_type AS ENUM ('TRANSCRIPT', 'GRADUATE_LIST', 'GRADE_REPORT');

CREATE TABLE "doc_export" (
                              id VARCHAR PRIMARY KEY DEFAULT gen_random_uuid()::varchar,
                              user_id VARCHAR REFERENCES "app_user"(id),
                              export_type doc_export_type NOT NULL,
                              file_name VARCHAR NOT NULL,
                              file_path VARCHAR NOT NULL,
                              file_size BIGINT,
                              format VARCHAR,
                              filters JSONB,
                              created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);