
DO $$
DECLARE
col_type text;
BEGIN

SELECT data_type INTO col_type FROM information_schema.columns
WHERE table_name = 'app_user' AND column_name = 'role';
IF col_type <> 'character varying' THEN
ALTER TABLE "app_user" ALTER COLUMN role TYPE VARCHAR USING role::text;
END IF;

SELECT data_type INTO col_type FROM information_schema.columns
WHERE table_name = 'app_user' AND column_name = 'status';
IF col_type <> 'character varying' THEN
ALTER TABLE "app_user" ALTER COLUMN status TYPE VARCHAR USING status::text;
ALTER TABLE "app_user" ALTER COLUMN status SET DEFAULT 'ACTIVE';
END IF;

SELECT data_type INTO col_type FROM information_schema.columns
WHERE table_name = 'teacher' AND column_name = 'status';
IF col_type <> 'character varying' THEN
ALTER TABLE "teacher" ALTER COLUMN status TYPE VARCHAR USING status::text;
ALTER TABLE "teacher" ALTER COLUMN status SET DEFAULT 'ACTIVE';
END IF;

SELECT data_type INTO col_type FROM information_schema.columns
WHERE table_name = 'student' AND column_name = 'status';
IF col_type <> 'character varying' THEN
ALTER TABLE "student" ALTER COLUMN status TYPE VARCHAR USING status::text;
ALTER TABLE "student" ALTER COLUMN status SET DEFAULT 'ACTIVE';
END IF;

SELECT data_type INTO col_type FROM information_schema.columns
WHERE table_name = 'group' AND column_name = 'type';
IF col_type <> 'character varying' THEN
ALTER TABLE "group" ALTER COLUMN type TYPE VARCHAR USING type::text;
END IF;
END $$;

DO $$ BEGIN
  IF NOT EXISTS (
    SELECT 1 FROM pg_attribute a JOIN pg_type t ON a.atttypid = t.oid
    WHERE t.typname = 'user_role' AND a.attnum > 0 AND NOT a.attisdropped
  ) THEN
DROP TYPE IF EXISTS user_role;
END IF;
  IF NOT EXISTS (
    SELECT 1 FROM pg_attribute a JOIN pg_type t ON a.atttypid = t.oid
    WHERE t.typname = 'status_enum' AND a.attnum > 0 AND NOT a.attisdropped
  ) THEN
DROP TYPE IF EXISTS status_enum;
END IF;
  IF NOT EXISTS (
    SELECT 1 FROM pg_attribute a JOIN pg_type t ON a.atttypid = t.oid
    WHERE t.typname = 'group_type' AND a.attnum > 0 AND NOT a.attisdropped
  ) THEN
DROP TYPE IF EXISTS group_type;
END IF;
END $$;