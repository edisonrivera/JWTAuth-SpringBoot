BEGIN;

CREATE TABLE IF NOT EXISTS public.role
(
    id integer NOT NULL DEFAULT nextval('role_id_seq'::regclass),
    name character varying(10) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT role_pk PRIMARY KEY (id),
    CONSTRAINT role_unique UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS public.role_usuario
(
    id integer NOT NULL DEFAULT nextval('role_usuario_id_seq'::regclass),
    user_id bigint,
    rol_id integer,
    CONSTRAINT role_usuario_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.usuario
(
    username character varying(25) COLLATE pg_catalog."default" NOT NULL,
    password text COLLATE pg_catalog."default" NOT NULL,
    id bigint NOT NULL DEFAULT nextval('user_id_seq'::regclass),
    CONSTRAINT user_pk PRIMARY KEY (id),
    CONSTRAINT user_unique UNIQUE (username)
);

INSERT INTO role(name) VALUES ('ROLE_USER'), ('ROLE_ADMIN');
-- Password: Test1231231$
INSERT INTO usuario(username, password) VALUES ('Admin', '$2b$12$yhK3Vs3dEQgRusJ87fVnV.UkA2X9.wtcEpe5XMdxU4SiitPqCxdVK');
INSERT INTO role_usuario(user_id, rol_id) VALUES (1, 1), (1, 2);

END;