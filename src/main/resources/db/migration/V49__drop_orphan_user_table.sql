DO $$
DECLARE
r RECORD;
BEGIN
FOR r IN
SELECT tc.table_name, tc.constraint_name
FROM information_schema.table_constraints tc
         JOIN information_schema.constraint_column_usage ccu
              ON tc.constraint_name = ccu.constraint_name
WHERE tc.constraint_type = 'FOREIGN KEY'
  AND ccu.table_name = 'user'
    LOOP
    EXECUTE format('ALTER TABLE %I DROP CONSTRAINT %I', r.table_name, r.constraint_name);
END LOOP;
END $$;

DROP TABLE IF EXISTS "user" CASCADE;