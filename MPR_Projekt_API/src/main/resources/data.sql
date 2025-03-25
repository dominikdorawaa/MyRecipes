CREATE TABLE IF NOT EXISTS recipe
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    title            VARCHAR(255),
    description      VARCHAR(255),
    image            VARCHAR(255),
    ready_in_minutes INT,
    servings         VARCHAR(255),
    summary          TEXT
);

CREATE TABLE IF NOT EXISTS nutrition
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    recipe_id BIGINT,
    FOREIGN KEY (recipe_id) REFERENCES recipe (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS nutrient
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(255),
    amount       DECIMAL(10, 2),
    unit         VARCHAR(50),
    nutrition_id BIGINT,
    FOREIGN KEY (nutrition_id) REFERENCES nutrition (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS ingredient
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(255),
    amount    DECIMAL(10, 2),
    unit      VARCHAR(50),
    recipe_id BIGINT,
    FOREIGN KEY (recipe_id) REFERENCES recipe (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS analyzed_instruction
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    recipe_id BIGINT,
    FOREIGN KEY (recipe_id) REFERENCES recipe (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS step
(
    id                      BIGINT AUTO_INCREMENT PRIMARY KEY,
    number                  INT,
    step                    TEXT,
    title                   TEXT,
    analyzed_instruction_id BIGINT,
    FOREIGN KEY (analyzed_instruction_id) REFERENCES analyzed_instruction (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS unit
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);



INSERT INTO unit (name)
VALUES ('g'),
       ('kg'),
       ('ml'),
       ('l'),
       ('łyżeczka'),
       ('łyżka'),
       ('szklanka'),
       ('szczypta'),
       ('butelka'),
       ('butelka'),
       ('kostka');



INSERT INTO recipe (title, description, image, ready_in_minutes, servings, summary)
SELECT 'Spaghetti Bolognese',
       'Klasyczne włoskie spaghetti z sosem bolońskim.',
       'spaghetti.png',
       40,
       4,
       'Aromatyczne spaghetti z mięsem i pomidorami'
WHERE NOT EXISTS (SELECT 1 FROM recipe WHERE title = 'Spaghetti Bolognese');


INSERT INTO nutrition (recipe_id)
SELECT 1
WHERE NOT EXISTS (SELECT 1 FROM nutrition WHERE recipe_id = 1);

INSERT INTO nutrient (name, amount, unit, nutrition_id)
VALUES ('Kalorie', 600, 'kcal', 1),
       ('Tłuszcz', 22, 'g', 1),
       ('Węglowodany', 70, 'g', 1),
       ('Białko', 30, 'g', 1);



INSERT INTO ingredient (name, amount, unit, recipe_id)
VALUES ('Makaron spaghetti', 400, 'g', 1),
       ('Mięso mielone wołowe', 500, 'g', 1),
       ('Cebula', 1, 'szt.', 1),
       ('Pomidory z puszki', 800, 'g', 1),
       ('Czosnek', 2, 'ząbki', 1),
       ('Oliwa z oliwek', 2, 'łyżki', 1),
       ('Bazylia', 1, 'łyżeczka', 1),
       ('Sól', 1, 'łyżeczka', 1),
       ('Pieprz', 0.5, 'łyżeczki', 1);


INSERT INTO analyzed_instruction (recipe_id)
VALUES (1);


INSERT INTO step (number, step, title, analyzed_instruction_id)
VALUES (1, 'Ugotuj makaron według instrukcji na opakowaniu.', 'Przygotowujemy makaron', 1),
       (2, 'Podsmaż cebulę i czosnek na oliwie.', 'Smażenie', 1),
       (3, 'Dodaj mięso mielone i smaż, aż się zarumieni.', 'Łączenie składników', 1),
       (4, 'Wlej pomidory, dopraw solą, pieprzem i bazylią.', 'Przygotowanie sosu', 1),
       (5, 'Gotuj na małym ogniu przez 20 minut.', 'Gotujemy', 1),
       (6, 'Podawaj z ugotowanym makaronem.', 'Podanie', 1);
