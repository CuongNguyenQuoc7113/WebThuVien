-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.30 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.1.0.6537
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for qlmuonsach
CREATE DATABASE IF NOT EXISTS `qlmuonsach` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `qlmuonsach`;

-- Dumping structure for table qlmuonsach.author
CREATE TABLE IF NOT EXISTS `author` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table qlmuonsach.author: ~2 rows (approximately)
INSERT INTO `author` (`id`, `name`) VALUES
	(1, 'Nguyễn Nhật Ánh'),
	(2, 'Nam Cao');

-- Dumping structure for table qlmuonsach.bo
CREATE TABLE IF NOT EXISTS `bo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table qlmuonsach.bo: ~0 rows (approximately)
INSERT INTO `bo` (`id`, `name`) VALUES
	(1, 'GDGT');

-- Dumping structure for table qlmuonsach.book
CREATE TABLE IF NOT EXISTS `book` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `page` int NOT NULL,
  `price` double NOT NULL,
  `title` varchar(200) NOT NULL,
  `author_id` bigint DEFAULT NULL,
  `bo_id` bigint DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  `img` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKklnrv3weler2ftkweewlky958` (`author_id`),
  KEY `FKq0iyhbmq9oo4o7u79lkb3f6yk` (`bo_id`),
  KEY `FKam9riv8y6rjwkua1gapdfew4j` (`category_id`),
  CONSTRAINT `FKam9riv8y6rjwkua1gapdfew4j` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `FKklnrv3weler2ftkweewlky958` FOREIGN KEY (`author_id`) REFERENCES `author` (`id`),
  CONSTRAINT `FKq0iyhbmq9oo4o7u79lkb3f6yk` FOREIGN KEY (`bo_id`) REFERENCES `bo` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table qlmuonsach.book: ~34 rows (approximately)
INSERT INTO `book` (`id`, `page`, `price`, `title`, `author_id`, `bo_id`, `category_id`, `img`, `description`) VALUES
	(1, 300, 200000, 'Mắt biếc 123', 1, NULL, 2, '/image/mat-biec.jpg', '* Đối tượng:\r\n\r\nCuốn 40 ngày rèn tư duy dành cho đối tượng học sinh, sinh viên.\r\n'),
	(2, 500, 300000, 'Hạ đỏ', 1, NULL, 1, '/img/ha-do.jpg', NULL),
	(3, 300, 250000, 'Chí phèo', 2, NULL, 3, '/img/chi-pheo.jpg', NULL),
	(5, 500, 170000, 'Ca Cao Máu', 1, NULL, 2, '/img/ca-cao-mau.jpg', NULL),
	(6, 200, 175000, 'Layla-Linh hồn bị đánh tráo', 2, NULL, 2, '/img/layla-linh-hon.jpg', NULL),
	(7, 400, 88000, 'Ngôi Nhà Trên Phố Mango', 2, NULL, 2, '/img/ngoi-nha-tren-pho-mango.jpg', NULL),
	(8, 500, 190000, 'Bệnh Án của Thần Linh 3', 1, NULL, 2, '/img/benh-an-cua-than-linh.jpg', NULL),
	(9, 100, 168000, 'Những Ta', 1, NULL, 2, '/img/nhung-ta.jpg', NULL),
	(10, 600, 154000, 'Gió tự Thời Khuất Mặt', 2, NULL, 2, '/img/gio-tu-thoi.jpg', NULL),
	(11, 300, 80000, 'Hẹn Yêu', 1, NULL, 2, '/img/hen-yeu.jpg', NULL),
	(12, 100, 80000, 'Những Chàng Trai Xấu Tính', 1, NULL, 2, '/img/nhung-chang-trai.jpg', NULL),
	(13, 200, 10000, 'Chúc một ngày tốt lành', 1, NULL, 1, '/img/chuc-ngay-tot.jpg', NULL),
	(14, 900, 100000, 'Yagon-Những kẻ vô cảm', 2, NULL, 1, '/img/yagon.jpg', NULL),
	(15, 500, 40000, 'Trăng Trong Cõi', 2, NULL, 1, '/img/trang-trong-coi.jpg', NULL),
	(16, 200, 80000, 'Yêu anh - Em bất chấp', 1, NULL, 1, '/img/yeu-anh.jpg', NULL),
	(17, 400, 110000, 'Bí Mật Những Giấc Mơ', 2, NULL, 1, '/img/bimat-nhung-giac-mo.jpg', NULL),
	(18, 300, 74000, 'Sài Gòn Kì Án', 2, NULL, 1, '/img/saigon-ki-an.jpg', NULL),
	(19, 500, 81000, 'Thử Nghiệm Hoang Dã', 1, NULL, 1, '/img/thu-nghiem-hoang-da.jpg', NULL),
	(20, 600, 108000, 'Marcovaldo hay Các mùa trong Thành Phố', 2, NULL, 3, '/img/marcovaldo.jpg', NULL),
	(21, 300, 116000, 'Sức Hút của Sự Tập Trung', 1, NULL, 3, '/img/suc-hut-cua-tap-trung.jpg', NULL),
	(22, 200, 71000, 'Vì Sao Tôi Cứ Mãi FA', 2, NULL, 3, '/img/vi-sao-toi-fa.jpg', NULL),
	(23, 300, 112000, 'Gay Trong Loay Hoay', 1, 1, 3, '/img/gay-trong-loay-hoay.jpg', NULL),
	(24, 300, 106000, 'Les Từng Cen.Ti.Mét', 2, 1, 3, '/img/les-centimet.jpg', NULL),
	(25, 400, 162000, 'Kiên Cường trong Gian Khó', 1, NULL, 3, '/img/kien-cuong-gian-kho.jpg', NULL),
	(26, 100, 86000, 'Em Ngủ Chưa? Nay Ổn Không?', 1, NULL, 3, '/img/nay-on-khong.jpg', NULL);

-- Dumping structure for table qlmuonsach.borrow
CREATE TABLE IF NOT EXISTS `borrow` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `borrow_date` date NOT NULL,
  `user_id` bigint NOT NULL,
  `accept_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtlx8cbafjlyp2hgfog0bdmni3` (`user_id`),
  CONSTRAINT `FKtlx8cbafjlyp2hgfog0bdmni3` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table qlmuonsach.borrow: ~3 rows (approximately)
INSERT INTO `borrow` (`id`, `borrow_date`, `user_id`, `accept_date`) VALUES
	(13, '2024-07-03', 1, '2024-07-03'),
	(14, '2024-07-03', 1, '2024-07-03'),
	(15, '2024-07-03', 1, '2024-07-03'),
	(16, '2024-07-03', 1, '2024-07-03'),
	(17, '2024-07-03', 1, '2024-07-03'),
	(18, '2024-07-03', 1, NULL),
	(19, '2024-07-03', 1, '2024-07-03'),
	(20, '2024-07-03', 1, '2024-07-03'),
	(21, '2024-07-03', 1, NULL),
	(22, '2024-07-03', 1, NULL);

-- Dumping structure for table qlmuonsach.borrow_detail
CREATE TABLE IF NOT EXISTS `borrow_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `actual_return_date` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `fine_amount` double DEFAULT NULL,
  `is_fines` bit(1) DEFAULT NULL,
  `return_date` date DEFAULT NULL,
  `book_id` bigint NOT NULL,
  `borrow_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6cj4gltab63v2j24q0cbu3xof` (`book_id`),
  KEY `FKta7rp6naow2nfmmtndop3hl62` (`borrow_id`),
  CONSTRAINT `FK6cj4gltab63v2j24q0cbu3xof` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
  CONSTRAINT `FKta7rp6naow2nfmmtndop3hl62` FOREIGN KEY (`borrow_id`) REFERENCES `borrow` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table qlmuonsach.borrow_detail: ~6 rows (approximately)
INSERT INTO `borrow_detail` (`id`, `actual_return_date`, `description`, `fine_amount`, `is_fines`, `return_date`, `book_id`, `borrow_id`) VALUES
	(19, '2024-07-03', NULL, NULL, NULL, '2024-07-13', 1, 13),
	(20, '2024-07-03', NULL, NULL, NULL, '2024-07-18', 14, 14),
	(21, '2024-07-03', NULL, NULL, NULL, '2024-07-01', 1, 15),
	(22, '2024-07-03', NULL, NULL, NULL, '2024-06-30', 7, 16),
	(23, '2024-07-03', NULL, NULL, NULL, '2024-07-01', 18, 17),
	(24, '2024-07-03', 'Tra cham', 123000, b'1', '2024-06-29', 2, 17),
	(25, NULL, NULL, NULL, NULL, NULL, 14, 18),
	(26, '2024-07-03', NULL, NULL, NULL, '2024-07-18', 2, 19),
	(27, '2024-07-03', NULL, NULL, NULL, '2024-07-10', 13, 20),
	(29, NULL, NULL, NULL, NULL, NULL, 1, 21),
	(30, NULL, NULL, NULL, NULL, NULL, 1, 22);

-- Dumping structure for table qlmuonsach.card
CREATE TABLE IF NOT EXISTS `card` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expiry_date` date NOT NULL,
  `issue_date` date NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKl4gbym62l738id056y12rt6q6` (`user_id`),
  CONSTRAINT `FKl4gbym62l738id056y12rt6q6` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table qlmuonsach.card: ~0 rows (approximately)
INSERT INTO `card` (`id`, `expiry_date`, `issue_date`, `user_id`) VALUES
	(2, '2024-09-03', '2024-07-03', 1);

-- Dumping structure for table qlmuonsach.category
CREATE TABLE IF NOT EXISTS `category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table qlmuonsach.category: ~3 rows (approximately)
INSERT INTO `category` (`id`, `name`) VALUES
	(1, 'Truyện dài'),
	(2, 'Tiểu thuyết'),
	(3, 'Truyện ngắn');

-- Dumping structure for table qlmuonsach.invoice
CREATE TABLE IF NOT EXISTS `invoice` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` bigint NOT NULL,
  `create_date` datetime(6) NOT NULL,
  `invoice_type` varchar(255) NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjunvl5maki3unqdvljk31kns3` (`user_id`),
  CONSTRAINT `FKjunvl5maki3unqdvljk31kns3` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table qlmuonsach.invoice: ~0 rows (approximately)

-- Dumping structure for table qlmuonsach.password_reset_token
CREATE TABLE IF NOT EXISTS `password_reset_token` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expiry_date` datetime(6) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKf90ivichjaokvmovxpnlm5nin` (`user_id`),
  CONSTRAINT `FK5lwtbncug84d4ero33v3cfxvl` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table qlmuonsach.password_reset_token: ~0 rows (approximately)
INSERT INTO `password_reset_token` (`id`, `expiry_date`, `token`, `user_id`) VALUES
	(1, '2024-07-04 23:53:13.524000', '226aae15-6d56-48e2-8d3e-93b339282ed5', 3);

-- Dumping structure for table qlmuonsach.role
CREATE TABLE IF NOT EXISTS `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(258) DEFAULT NULL,
  `name` varchar(58) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table qlmuonsach.role: ~2 rows (approximately)
INSERT INTO `role` (`id`, `description`, `name`) VALUES
	(1, NULL, 'ADMIN'),
	(2, NULL, 'USER');

-- Dumping structure for table qlmuonsach.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(50) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `password` varchar(250) NOT NULL,
  `username` varchar(50) NOT NULL,
  `email_verified` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKsb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table qlmuonsach.user: ~1 rows (approximately)
INSERT INTO `user` (`id`, `email`, `name`, `password`, `username`, `email_verified`) VALUES
	(1, 'hoainam1511ytn@gmail.com', 'Nam', '$2a$10$zexjBEwZYf5SSR2dnIBlduy24L9H2Iq9/aRjYUCl9C3eII/13oQ6u', 'nam', b'1'),
	(2, 'cuong@gmail.com', 'Cuong', '$2a$10$ORxn1oB.LYcgekdj1Hg7OeBWGPIOSM7FjygIQPtxuZ2X4RVDih.bW', 'QC', b'0'),
	(3, 'cuongnguyenquoc003@gmail.com', 'Cuong', '$2a$10$BjOxuJPD3XwV9r7bs7EGC.IObPvRW3dLtIHmc9HTfnOm0JUS4gTXS', 'Cuong123', b'1');

-- Dumping structure for table qlmuonsach.user_role
CREATE TABLE IF NOT EXISTS `user_role` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table qlmuonsach.user_role: ~2 rows (approximately)
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
	(1, 1),
	(1, 2),
	(2, 2),
	(3, 2);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
