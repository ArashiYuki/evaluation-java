-- USERS
INSERT INTO users (ID, FIRST_NAME, LAST_NAME) VALUES (1, 'Hermione','Granger');
INSERT INTO users (ID, FIRST_NAME, LAST_NAME) VALUES (2, 'Harry','Potter');
INSERT INTO users (ID, FIRST_NAME, LAST_NAME) VALUES (3, 'Ron','Weasley');

-- TASKS
INSERT INTO tasks (ID, OWNER_ID, TITLE, DESCRIPTION, COMPLETED) VALUES (1, 1, 'Rendre devoir', 'Potions', false);
INSERT INTO tasks (ID, OWNER_ID, TITLE, DESCRIPTION, COMPLETED) VALUES (2, 2, 'Descendre les poubelles', 'Salle de bain & cuisine', true);
INSERT INTO tasks (ID, OWNER_ID, TITLE, DESCRIPTION, COMPLETED) VALUES (3, 1, 'Rendre le roman à la bibliothèque', 'Créatures magiques', false);
INSERT INTO tasks (ID, OWNER_ID, TITLE, DESCRIPTION, COMPLETED) VALUES (4, 1, 'Aller chercher la commande', 'Nimbus 2000', true);
INSERT INTO tasks (ID, OWNER_ID, TITLE, DESCRIPTION, COMPLETED) VALUES (5, 2, 'Prendre ticket pour Poudlard', 'Voie 9 3/4', false);
INSERT INTO tasks (ID, OWNER_ID, TITLE, DESCRIPTION, COMPLETED) VALUES (6, 3, 'Préparer sac 3j', '', false);
INSERT INTO tasks (ID, OWNER_ID, TITLE, DESCRIPTION, COMPLETED) VALUES (7, 2, 'Faire les courses', 'Livres, baguette, chouette', false);
INSERT INTO tasks (ID, OWNER_ID, TITLE, DESCRIPTION, COMPLETED) VALUES (8, 3, 'Garder chat', '', true);
INSERT INTO tasks (ID, OWNER_ID, TITLE, DESCRIPTION, COMPLETED) VALUES (9, 3, 'Cuillette champignons', '', false);
INSERT INTO tasks (ID, OWNER_ID, TITLE, DESCRIPTION, COMPLETED) VALUES (10, 1, 'Anniversaire Harry', '', false);