/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE conf."user" ADD CONSTRAINT "PK_user"
	PRIMARY KEY (id)
;

ALTER TABLE conf.user_function ADD CONSTRAINT "PK_user_function"
	PRIMARY KEY (id)
;

ALTER TABLE conf.user_role ADD CONSTRAINT "PK_user_role"
	PRIMARY KEY (id)
;

ALTER TABLE conf.user_role_x_function ADD CONSTRAINT "PK_user_role_x_function"
	PRIMARY KEY (id)
;

CREATE INDEX "IXFK_user_role_x_function_user_function" ON conf.user_role_x_function (function_id ASC)
;

CREATE INDEX "IXFK_user_role_x_function_user_role" ON conf.user_role_x_function (role_id ASC)
;

ALTER TABLE conf.user_session ADD CONSTRAINT "PK_user_session"
	PRIMARY KEY (id)
;

CREATE INDEX "IXFK_user_session_user" ON conf.user_session (user_id ASC)
;

ALTER TABLE conf.user_x_function ADD CONSTRAINT "PK_user_x_function"
	PRIMARY KEY (id)
;

CREATE INDEX "IXFK_user_x_function_user" ON conf.user_x_function (user_id ASC)
;

CREATE INDEX "IXFK_user_x_function_user_function" ON conf.user_x_function (function_id ASC)
;

ALTER TABLE conf.user_x_role ADD CONSTRAINT "PK_user_x_role"
	PRIMARY KEY (id)
;

CREATE INDEX "IXFK_user_x_role_user" ON conf.user_x_role (user_id ASC)
;

CREATE INDEX "IXFK_user_x_role_user_role" ON conf.user_x_role (role_id ASC)
;

ALTER TABLE core.car_advert ADD CONSTRAINT "PK_car_advert"
	PRIMARY KEY (id)
;















/* Create Foreign Key Constraints */


ALTER TABLE conf.user_role_x_function ADD CONSTRAINT "FK_user_role_x_function_user_function"
	FOREIGN KEY (function_id) REFERENCES conf.user_function (id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE conf.user_role_x_function ADD CONSTRAINT "FK_user_role_x_function_user_role"
	FOREIGN KEY (role_id) REFERENCES conf.user_role (id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE conf.user_session ADD CONSTRAINT "FK_user_session_user"
	FOREIGN KEY (user_id) REFERENCES conf."user" (id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE conf.user_x_function ADD CONSTRAINT "FK_user_x_function_user"
	FOREIGN KEY (user_id) REFERENCES conf."user" (id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE conf.user_x_function ADD CONSTRAINT "FK_user_x_function_user_function"
	FOREIGN KEY (function_id) REFERENCES conf.user_function (id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE conf.user_x_role ADD CONSTRAINT "FK_user_x_role_user"
	FOREIGN KEY (user_id) REFERENCES conf."user" (id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE conf.user_x_role ADD CONSTRAINT "FK_user_x_role_user_role"
	FOREIGN KEY (role_id) REFERENCES conf.user_role (id) ON DELETE No Action ON UPDATE No Action
;