CREATE SCHEMA board;

CREATE TABLE `board`.`tb_userinfo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(100) NOT NULL,
  `email` varchar(45) NOT NULL,
  `role` varchar(20) DEFAULT 'ROLE_USER',
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`,`username`)
) ENGINE=InnoDB;

CREATE TABLE `board`.`tb_board` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `writer_id` int NOT NULL,
  `writer` varchar(20) NOT NULL,
  `delete_yn` varchar(1) DEFAULT 'N',
  `create_date` datetime DEFAULT NULL,
  `views` int DEFAULT '0',
  `type` varchar(20) DEFAULT 'board',
  PRIMARY KEY (`id`),
  KEY `writer_id` (`writer_id`),
  CONSTRAINT `tb_board_ibfk_1` FOREIGN KEY (`writer_id`) REFERENCES `tb_userinfo` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE `board`.`tb_comment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `board_id` int NOT NULL,
  `content` text NOT NULL,
  `writer_id` int NOT NULL,
  `writer` varchar(20) NOT NULL,
  `create_date` datetime NOT NULL,
  `type` varchar(20) DEFAULT 'board',
  PRIMARY KEY (`id`),
  KEY `board_id` (`board_id`),
  KEY `writer_id` (`writer_id`),
  CONSTRAINT `tb_comment_ibfk_1` FOREIGN KEY (`board_id`) REFERENCES `tb_board` (`id`) ON DELETE CASCADE,
  CONSTRAINT `tb_comment_ibfk_2` FOREIGN KEY (`writer_id`) REFERENCES `tb_userinfo` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE `board`.`tb_file` (
  `id` int NOT NULL AUTO_INCREMENT,
  `board_id` int NOT NULL,
  `original_file_name` varchar(100) NOT NULL,
  `size` int NOT NULL,
  `path` varchar(100) NOT NULL,
  `stored_file_name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `board_id` (`board_id`),
  CONSTRAINT `tb_file_ibfk_1` FOREIGN KEY (`board_id`) REFERENCES `tb_board` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 관리자 계정 생성 (username: admin, password: 123456, email: tester159@gmail.com)
INSERT INTO board.tb_userinfo(id, username, password, email, role, create_date) VALUES (1, 'admin', '$2a$10$YfM/qUJh2Vatbav6XR4QYOucFQ1l/zY0KTm7jJLNZKpsrT0wTxDB2', 'tester159@gmail.com', 'ROLE_ADMIN', now());
