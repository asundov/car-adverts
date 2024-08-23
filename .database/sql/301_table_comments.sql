/* Create Table Comments, Sequences for Autonumber Columns */

COMMENT ON TABLE core.accommodation_unit_category
	IS 'Šifrarnik kategorija smještajnih jedinica.'
;

COMMENT ON COLUMN core.accommodation_unit_category.id
	IS 'Jedinstveni identifikator.'
;

COMMENT ON COLUMN core.accommodation_unit_category.uname
	IS 'Naziv'
;

COMMENT ON COLUMN core.accommodation_unit_category.sequence
	IS 'Poredak'
;

COMMENT ON COLUMN core.accommodation_unit_category.status
	IS 'Aktivan zapis: 1, ostalo: 0'
;

COMMENT ON COLUMN core.accommodation_unit_category.active
	IS 'Aktivan zapis: 1, ostalo: 0'
;

COMMENT ON COLUMN core.accommodation_unit_category.translation
	IS 'Prijevodi spremljeni u JSON formatu'
;

COMMENT ON COLUMN core.accommodation_unit_category.created_by
	IS 'Korisnik koji je kreirao zapis.'
;

COMMENT ON COLUMN core.accommodation_unit_category.created_date
	IS 'Dan i vrijeme kad je kreiran zapis'
;

COMMENT ON COLUMN core.accommodation_unit_category.modified_by
	IS 'Korisnik koji je ažurirao zapis'
;

COMMENT ON COLUMN core.accommodation_unit_category.modified_date
	IS 'Dan i vrijeme kad je ažuriran zapis'