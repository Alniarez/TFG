DROP SCHEMA PUBLIC CASCADE;

--Usuarios
create table users (
	id 		INTEGER IDENTITY PRIMARY KEY,
	name 	VARCHAR(255) NOT NULL,
	email 	VARCHAR(255) NOT NULL,
	admin 	BOOLEAN NOT NULL,
	login 	VARCHAR(255) NOT NULL UNIQUE,
	psw 	VARCHAR(255) NOT NULL,
	active	BOOLEAN DEFAULT true
);

--Categorías
create table subjects(
	id 		INTEGER IDENTITY PRIMARY KEY,
	code 	VARCHAR(255) UNIQUE NOT NULL
);

--Temas
CREATE TABLE threads(
	id 		INTEGER IDENTITY PRIMARY KEY,
	name 	VARCHAR(255) NOT NULL,
	email 	VARCHAR(255) NOT NULL,
	subject_id INTEGER NOT NULL,
	date	TIMESTAMP NOT NULL,	
	open	BOOLEAN DEFAULT true,
	text 	VARCHAR(32768) NOT NULL,
	topic 	VARCHAR(255) NOT NULL,
	FOREIGN KEY (subject_id) REFERENCES subjects(id)
);

--Mensajes
CREATE TABLE messages(
	id 		INTEGER IDENTITY PRIMARY KEY,
	thread_id	INTEGER NOT NULL,
	text 	VARCHAR(32768) NOT NULL,
	date	TIMESTAMP NOT NULL,
	user_id INTEGER NOT NULL,
	FOREIGN KEY (user_id) REFERENCES users(id),
	FOREIGN KEY (thread_id) REFERENCES threads(id)
);

--Asignación categorías
CREATE TABLE assignments(
	user_id INTEGER,
	subject_id INTEGER,
	PRIMARY KEY(user_id, subject_id),
	FOREIGN KEY (user_id) REFERENCES users(id),
	FOREIGN KEY (subject_id) REFERENCES subjects(id)
);