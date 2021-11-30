INSERT INTO AUTHORITY (AUTHORITY_NAME) SELECT ('ROLE_ADMIN') WHERE NOT EXISTS (SELECT * FROM AUTHORITY WHERE AUTHORITY_NAME = 'ROLE_ADMIN');
INSERT INTO AUTHORITY (AUTHORITY_NAME) SELECT ('ROLE_USER') WHERE NOT EXISTS (SELECT * FROM AUTHORITY WHERE AUTHORITY_NAME = 'ROLE_USER');
INSERT INTO AUTHORITY (AUTHORITY_NAME) SELECT ('ROLE_GUEST') WHERE NOT EXISTS (SELECT * FROM AUTHORITY WHERE AUTHORITY_NAME = 'ROLE_GUEST');