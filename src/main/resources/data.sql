--CREATE EXTENSION fuzzystrmatch;

CREATE SEQUENCE drug_id_seq START 1;

INSERT INTO drug (id, aurora_packing_id, aurora_description_id, firm, name, packaging, metaphone)
SELECT
  nextval('drug_id_seq') AS id, -- Generate a new id value
  packing_id AS aurora_packing_id,
  desc_id AS aurora_description_id,
  firms AS firm,
  prep_full AS name,
  packing_short AS packaging,
  dmetaphone(prep_full) AS metaphone -- Generate metaphone value
FROM aurora_drug;

COMMIT;