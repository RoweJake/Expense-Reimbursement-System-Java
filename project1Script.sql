/*Drop tables*/
DROP VIEW user_info;
DROP TABLE ers_reimbursement;
DROP TABLE ers_reimbursement_type;
DROP TABLE ers_reimbursement_status;
DROP TABLE ers_users;
DROP TABLE ers_user_roles;

/*Create tables*/
CREATE TABLE ers_user_roles (
	ers_user_role_id SERIAL PRIMARY KEY,
	user_role VARCHAR(10) NOT NULL
);

CREATE TABLE ers_users (
	ers_users_id SERIAL PRIMARY KEY,
	ers_username VARCHAR(50) UNIQUE NOT NULL,
	ers_password VARCHAR(50) NOT NULL,
	user_first_name VARCHAR(100) NOT NULL,
	user_last_name VARCHAR(100) NOT NULL,
	user_email VARCHAR(150) UNIQUE NOT NULL,
	user_role_id INTEGER REFERENCES ers_user_roles(ers_user_role_id) NOT NULL
);

CREATE TABLE ers_reimbursement_status (
	reimb_status_id SERIAL PRIMARY KEY,
	reimb_status VARCHAR(10) NOT NULL
);

CREATE TABLE ers_reimbursement_type (
	reimb_type_id SERIAL PRIMARY KEY,
	reimb_type VARCHAR(10) NOT NULL
);

CREATE TABLE ers_reimbursement (
	reimb_id SERIAL PRIMARY KEY,
	reimb_amount INTEGER NOT NULL,
	reimb_submitted TIMESTAMP NOT NULL,
	reimb_resolved TIMESTAMP,
	reimb_description VARCHAR(250),
	reimb_author INTEGER REFERENCES ers_users(ers_users_id) NOT NULL,
	reimb_resolver INTEGER REFERENCES ers_users(ers_users_id),
	reimb_status_id INTEGER REFERENCES ers_reimbursement_status(reimb_status_id) NOT NULL,
	reimb_type_id INTEGER REFERENCES ers_reimbursement_type(reimb_type_id) NOT NULL
);

/*Define user roles, reimbursement statuses, and reimbursement types.*/
INSERT INTO ers_user_roles VALUES (DEFAULT, 'Employee');
INSERT INTO ers_user_roles VALUES (DEFAULT, 'Manager');

INSERT INTO ers_reimbursement_status VALUES (DEFAULT, 'Pending');
INSERT INTO ers_reimbursement_status VALUES (DEFAULT, 'Approved');
INSERT INTO ers_reimbursement_status VALUES (DEFAULT, 'Denied');

INSERT INTO ers_reimbursement_type VALUES (DEFAULT, 'Equipment');
INSERT INTO ers_reimbursement_type VALUES (DEFAULT, 'Potions');
INSERT INTO ers_reimbursement_type VALUES (DEFAULT, 'Food');
INSERT INTO ers_reimbursement_type VALUES (DEFAULT, 'Other');

/*Insert starting users*/
INSERT INTO ers_users VALUES (DEFAULT, 'Alphinaud', 'AvantGarde', 'Alphinaud', 'Leveilleur', 'alphinaud.leveilleur@aether.net', 1);
INSERT INTO ers_users VALUES (DEFAULT, 'Dragoon', 'Azure', 'Estinien', 'Wyrmblood', 'estinien.wyrmblood@aether.net', 1);
INSERT INTO ers_users VALUES (DEFAULT, 'Treasurer', 'Scions', 'Tataru', 'Taru', 'tataru.taru@aether.net', 2);

/*Insert user 1's reimbursements*/
INSERT INTO ers_reimbursement
	VALUES (DEFAULT, 20000000, CURRENT_TIMESTAMP, (CURRENT_TIMESTAMP + INTERVAL '5 hours'), 'Doman Steel Tachi', 1, 3, 3, 1);
INSERT INTO ers_reimbursement (reimb_id, reimb_amount, reimb_submitted, reimb_description, reimb_author, reimb_status_id, reimb_type_id)
	VALUES (DEFAULT, 180000, CURRENT_TIMESTAMP, '60 Grade 6 Tinctures of Mind HQ', 1, 1, 2);
INSERT INTO ers_reimbursement (reimb_id, reimb_amount, reimb_submitted, reimb_description, reimb_author, reimb_status_id, reimb_type_id)
	VALUES (DEFAULT, 46000, CURRENT_TIMESTAMP, '20 Pumpkin Potage HQ', 1, 1, 3);
INSERT INTO ers_reimbursement
	VALUES (DEFAULT, 560000, CURRENT_TIMESTAMP, (CURRENT_TIMESTAMP + INTERVAL '5 hours'), 'Oil Paints', 1, 3, 2, 4);

/*Insert user 2's reimbursements*/
INSERT INTO ers_reimbursement
	VALUES (DEFAULT, 9000, CURRENT_TIMESTAMP, (CURRENT_TIMESTAMP + INTERVAL '5 hours'), 'Thavnairian Weave Hair Tie', 2, 3, 3, 1);
/*INSERT INTO ers_reimbursement (reimb_id, reimb_amount, reimb_submitted, reimb_description, reimb_author, reimb_status_id, reimb_type_id)
	VALUES (DEFAULT, 180000, CURRENT_TIMESTAMP, '60 Grade 6 Tinctures of Strength HQ', 2, 1, 2);*/
INSERT INTO ers_reimbursement (reimb_id, reimb_amount, reimb_submitted, reimb_description, reimb_author, reimb_status_id, reimb_type_id)
	VALUES (DEFAULT, 46000, CURRENT_TIMESTAMP, '20 Pumpkin Potage HQ', 2, 1, 3);
INSERT INTO ers_reimbursement
	VALUES (DEFAULT, 86000, CURRENT_TIMESTAMP, (CURRENT_TIMESTAMP + INTERVAL '5 hours'), 'Classical Ring of Slaying HQ', 2, 3, 2, 1);
	
CREATE VIEW user_info AS
SELECT * FROM ers_users
JOIN ers_user_roles
ON user_role_id = ers_user_role_id;