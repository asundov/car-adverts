/* Create Table Comments, Sequences for Autonumber Columns */


COMMENT ON TABLE conf."user"
	IS 'Korisnici sustava'
;

COMMENT ON COLUMN conf."user".id
	IS 'Jedinstveni identifikator.'
;

COMMENT ON COLUMN conf."user".first_name
	IS 'Ime'
;

COMMENT ON COLUMN conf."user".last_name
	IS 'Prezime'
;

COMMENT ON COLUMN conf."user".username
	IS 'korisničko ime'
;

COMMENT ON COLUMN conf."user".admin
	IS 'da li je korisnik admin sustava'
;

COMMENT ON COLUMN conf."user"."e-mail-verified"
	IS 'da li je email verificiran'
;

COMMENT ON COLUMN conf."user".description
	IS 'opis'
;

COMMENT ON COLUMN conf."user".status
	IS '0 - neaktivan, 1 - aktivan'
;

COMMENT ON COLUMN conf."user".active
	IS 'Aktivan zapis: 1, ostalo: 0'
;

COMMENT ON COLUMN conf."user".created_by
	IS 'Korisnik koji je kreirao zapis.'
;

COMMENT ON COLUMN conf."user".created_date
	IS 'Dan i vrijeme kad je kreiran zapis'
;

COMMENT ON COLUMN conf."user".modified_by
	IS 'Korisnik koji je ažurirao zapis'
;

COMMENT ON COLUMN conf."user".modified_date
	IS 'Dan i vrijeme kad je ažuriran zapis'
;

CREATE SEQUENCE conf.user_id_seq INCREMENT 1 START 100000
;

COMMENT ON TABLE conf.user_function
	IS 'Korisničke funkcije'
;

COMMENT ON COLUMN conf.user_function.id
	IS 'Jedinstveni identifikator.'
;

COMMENT ON COLUMN conf.user_function.ucode
	IS 'Šifra funkcije'
;

COMMENT ON COLUMN conf.user_function.uname
	IS 'Naziv'
;

COMMENT ON COLUMN conf.user_function.status
	IS 'Aktivan zapis: 1, ostalo: 0'
;

COMMENT ON COLUMN conf.user_function.active
	IS 'Aktivan zapis: 1, ostalo: 0'
;

COMMENT ON COLUMN conf.user_function.created_by
	IS 'Korisnik koji je kreirao zapis.'
;

COMMENT ON COLUMN conf.user_function.created_date
	IS 'Dan i vrijeme kad je kreiran zapis'
;

COMMENT ON COLUMN conf.user_function.modified_by
	IS 'Korisnik koji je ažurirao zapis'
;

COMMENT ON COLUMN conf.user_function.modified_date
	IS 'Dan i vrijeme kad je ažuriran zapis'
;

CREATE SEQUENCE conf.user_function_id_seq INCREMENT 1 START 10000
;

COMMENT ON TABLE conf.user_role
	IS 'Korisničke funkcije'
;

COMMENT ON COLUMN conf.user_role.id
	IS 'Jedinstveni identifikator.'
;

COMMENT ON COLUMN conf.user_role.ucode
	IS 'Šifra role'
;

COMMENT ON COLUMN conf.user_role.uname
	IS 'Naziv'
;

COMMENT ON COLUMN conf.user_role.active
	IS 'Aktivan zapis: 1, ostalo: 0'
;

COMMENT ON COLUMN conf.user_role.created_by
	IS 'Korisnik koji je kreirao zapis.'
;

COMMENT ON COLUMN conf.user_role.created_date
	IS 'Dan i vrijeme kad je kreiran zapis'
;

COMMENT ON COLUMN conf.user_role.modified_by
	IS 'Korisnik koji je ažurirao zapis'
;

COMMENT ON COLUMN conf.user_role.modified_date
	IS 'Dan i vrijeme kad je ažuriran zapis'
;

CREATE SEQUENCE conf.user_role_id_seq INCREMENT 1 START 10000
;

COMMENT ON TABLE conf.user_role_x_function
	IS 'Veza korisnika i funkcije'
;

COMMENT ON COLUMN conf.user_role_x_function.id
	IS 'Jedinstveni identifikator.'
;

COMMENT ON COLUMN conf.user_role_x_function.role_id
	IS 'Veza na conf.user_role'
;

COMMENT ON COLUMN conf.user_role_x_function.function_id
	IS 'Veza na core.user_function'
;

COMMENT ON COLUMN conf.user_role_x_function.active
	IS 'Aktivan zapis: 1, ostalo: 0'
;

COMMENT ON COLUMN conf.user_role_x_function.created_by
	IS 'Korisnik koji je kreirao zapis.'
;

COMMENT ON COLUMN conf.user_role_x_function.created_date
	IS 'Dan i vrijeme kad je kreiran zapis'
;

COMMENT ON COLUMN conf.user_role_x_function.modified_by
	IS 'Korisnik koji je ažurirao zapis'
;

COMMENT ON COLUMN conf.user_role_x_function.modified_date
	IS 'Dan i vrijeme kad je ažuriran zapis'
;

CREATE SEQUENCE conf.user_role_x_function_id_seq INCREMENT 1 START 10000
;

COMMENT ON COLUMN conf.user_session.id
	IS 'Jedinstveni identifikator'
;

COMMENT ON COLUMN conf.user_session.user_id
	IS 'Veza na core.user'
;

COMMENT ON COLUMN conf.user_session.login_date
	IS 'Datum prijave korisnika'
;

COMMENT ON COLUMN conf.user_session.logout_date
	IS 'Datum odjave korisnika'
;

COMMENT ON COLUMN conf.user_session.ip_address
	IS 'ip adresa prijave'
;

COMMENT ON COLUMN conf.user_session.refresh_token
	IS 'autentikacijski token'
;

COMMENT ON COLUMN conf.user_session.refresh_token_creation_date
	IS 'Datum kreiranja autentikacijskog tokena'
;

COMMENT ON COLUMN conf.user_session.refresh_token_expiration_date
	IS 'Datum isteka autentikacijskog tokena'
;

COMMENT ON COLUMN conf.user_session.status
	IS 'Aktivan zapis: 1, ostalo: 0'
;

COMMENT ON COLUMN conf.user_session.created_by
	IS 'Korisnik koji je kreirao zapis'
;

COMMENT ON COLUMN conf.user_session.created_date
	IS 'Dan i vrijeme kad je kreiran zapis'
;

COMMENT ON COLUMN conf.user_session.modified_by
	IS 'Korisnik koji je ažurirao zapis'
;

COMMENT ON COLUMN conf.user_session.modified_date
	IS 'Dan i vrijeme kad je ažuriran zapis'
;

CREATE SEQUENCE conf.user_session_id_seq INCREMENT 1 START 100000
;

COMMENT ON TABLE conf.user_x_function
	IS 'Veza korisnika i funkcije'
;

COMMENT ON COLUMN conf.user_x_function.id
	IS 'Jedinstveni identifikator.'
;

COMMENT ON COLUMN conf.user_x_function.user_id
	IS 'Veza na core.user'
;

COMMENT ON COLUMN conf.user_x_function.function_id
	IS 'Veza na core.user_function'
;

COMMENT ON COLUMN conf.user_x_function.active
	IS 'Aktivan zapis: 1, ostalo: 0'
;

COMMENT ON COLUMN conf.user_x_function.created_by
	IS 'Korisnik koji je kreirao zapis.'
;

COMMENT ON COLUMN conf.user_x_function.created_date
	IS 'Dan i vrijeme kad je kreiran zapis'
;

COMMENT ON COLUMN conf.user_x_function.modified_by
	IS 'Korisnik koji je ažurirao zapis'
;

COMMENT ON COLUMN conf.user_x_function.modified_date
	IS 'Dan i vrijeme kad je ažuriran zapis'
;

CREATE SEQUENCE conf.user_x_function_id_seq INCREMENT 1 START 10000
;

COMMENT ON TABLE conf.user_x_role
	IS 'Veza korisnika i funkcije'
;

COMMENT ON COLUMN conf.user_x_role.id
	IS 'Jedinstveni identifikator.'
;

COMMENT ON COLUMN conf.user_x_role.user_id
	IS 'Veza na core.user'
;

COMMENT ON COLUMN conf.user_x_role.role_id
	IS 'Veza na core.user_function'
;

COMMENT ON COLUMN conf.user_x_role.active
	IS 'Aktivan zapis: 1, ostalo: 0'
;

COMMENT ON COLUMN conf.user_x_role.created_by
	IS 'Korisnik koji je kreirao zapis.'
;

COMMENT ON COLUMN conf.user_x_role.created_date
	IS 'Dan i vrijeme kad je kreiran zapis'
;

COMMENT ON COLUMN conf.user_x_role.modified_by
	IS 'Korisnik koji je ažurirao zapis'
;

COMMENT ON COLUMN conf.user_x_role.modified_date
	IS 'Dan i vrijeme kad je ažuriran zapis'
;

CREATE SEQUENCE conf.user_x_role_id_seq INCREMENT 1 START 10000
;




CREATE SEQUENCE core.car_advert_id_seq INCREMENT 1 START 10000
;

COMMENT ON TABLE core.car_advert
	IS 'Šifrarnik kategorija smještajnih jedinica.'
;

COMMENT ON COLUMN core.car_advert.id
	IS 'Jedinstveni identifikator.'
;

COMMENT ON COLUMN core.car_advert.title
	IS 'Naziv'
;

COMMENT ON COLUMN core.car_advert.fuel_type
	IS 'Tip goriva'
;

COMMENT ON COLUMN core.car_advert.price
	IS 'Cijena'
;

COMMENT ON COLUMN core.car_advert.is_new
	IS 'Novi model'
;

COMMENT ON COLUMN core.car_advert.mileage
	IS 'Kilometraza'
;

COMMENT ON COLUMN core.car_advert.first_registration
	IS 'Prva registracija'
;


COMMENT ON COLUMN core.car_advert.active
	IS 'Aktivan zapis: 1, ostalo: 0'
;


COMMENT ON COLUMN core.car_advert.created_by
	IS 'Korisnik koji je kreirao zapis.'
;

COMMENT ON COLUMN core.car_advert.created_date
	IS 'Dan i vrijeme kad je kreiran zapis'
;

COMMENT ON COLUMN core.car_advert.modified_by
	IS 'Korisnik koji je ažurirao zapis'
;

COMMENT ON COLUMN core.car_advert.modified_date
	IS 'Dan i vrijeme kad je ažuriran zapis'