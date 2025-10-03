CREATE TABLE `USERS` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `username` VARCHAR(40),
  `admin` BOOLEAN NOT NULL DEFAULT false,
  `email` VARCHAR(255),
  `password` VARCHAR(255),
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `TOPIC` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `label` VARCHAR(255),
  `description` VARCHAR(420)
);

CREATE TABLE `POST` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `topic_id` INT,
  `author_id` INT,
  `title` VARCHAR(255),
  `content` VARCHAR(2000),
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `COMMENT` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `post_id` INT,
  `commenter_id` INT,
  `content` VARCHAR(255),
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `SUBSCRIPTION` (
  `user_id` INT,
  `topic_id` INT
);

ALTER TABLE `POST` ADD FOREIGN KEY (`topic_id`) REFERENCES `TOPIC` (`id`);
ALTER TABLE `POST` ADD FOREIGN KEY (`author_id`) REFERENCES `USERS` (`id`);
ALTER TABLE `COMMENT` ADD FOREIGN KEY (`post_id`) REFERENCES `POST` (`id`);
ALTER TABLE `COMMENT` ADD FOREIGN KEY (`commenter_id`) REFERENCES `USERS` (`id`);
ALTER TABLE `SUBSCRIPTION` ADD FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`);
ALTER TABLE `SUBSCRIPTION` ADD FOREIGN KEY (`topic_id`) REFERENCES `TOPIC` (`id`);

/* default pwd: test!1234 */
INSERT INTO USERS (username, email, admin, password)
VALUES ('Admin', 'admin@mddapi.com', true, '$2a$10$.Hsa/ZjUVaHqi0tp9xieMeewrnZxrZ5pQRzddUXE/WjDu2ZThe6Iq'); 

INSERT INTO TOPIC (label, description)
VALUES ('Physique', 'Tous les articles de physique.'),
       ('Mathematiques', 'Tous les articles de mathematiques.'),
       ('Sciences', 'Tous les articles scientifiques.'),
       ('Informatique', 'Tous les articles sur l''informatique.');
