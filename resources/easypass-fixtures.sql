-- --------------------------------------------------------
-- Hôte :                        127.0.0.1
-- Version du serveur:           10.1.10-MariaDB - mariadb.org binary distribution
-- SE du serveur:                Win32
-- HeidiSQL Version:             9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Export de la structure de la base pour easypass
DROP DATABASE IF EXISTS `easypass`;
CREATE DATABASE IF NOT EXISTS `easypass` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `easypass`;


-- Export de la structure de table easypass. categories
DROP TABLE IF EXISTS `categories`;
CREATE TABLE IF NOT EXISTS `categories` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `logo` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  KEY `Index 1` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Export de données de la table easypass.categories : ~5 rows (environ)
DELETE FROM `categories`;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` (`id`, `name`, `logo`) VALUES
	(30, 'Forums', 'http://www.team-site.com/uploads/5/8/9/0/5890900/2365667.png'),
	(31, 'Réseaux sociaux', 'http://www.euskalnet.fr/agence-web-pays-basque/images/menu-reseaux-sociaux.png'),
	(32, 'Autre', 'http://moba-champion.com/wiki/images/f/f0/License-unknown.png'),
	(33, 'Boîte mail', 'https://upload.wikimedia.org/wikipedia/commons/thumb/d/df/Aiga_mail.svg/2000px-Aiga_mail.svg.png'),
	(34, 'Travail', 'http://icons.iconarchive.com/icons/dapino/office-men/256/Man-Black-icon.png');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;


-- Export de la structure de table easypass. groups
DROP TABLE IF EXISTS `groups`;
CREATE TABLE IF NOT EXISTS `groups` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `logo` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  KEY `Index 1` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Export de données de la table easypass.groups : ~2 rows (environ)
DELETE FROM `groups`;
/*!40000 ALTER TABLE `groups` DISABLE KEYS */;
INSERT INTO `groups` (`id`, `name`, `description`, `logo`) VALUES
	(20, 'Page Facebook ESGI', 'Administrateurs de la page Facebook ESGI.', 'https://pbs.twimg.com/profile_images/557829264737697792/8GevlAeT.jpeg'),
	(21, 'Entreprise Clevermind', 'Groupe avec les mots de passe de la boîte.', 'https://cdn4.iconfinder.com/data/icons/111-seo-services-pack/128/new_seo2-14-512.png');
/*!40000 ALTER TABLE `groups` ENABLE KEYS */;


-- Export de la structure de table easypass. owner_password
DROP TABLE IF EXISTS `owner_password`;
CREATE TABLE IF NOT EXISTS `owner_password` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned DEFAULT NULL,
  `group_id` int(10) unsigned DEFAULT NULL,
  `password_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `owner_password_user_id_foreign` (`user_id`),
  KEY `owner_password_group_id_foreign` (`group_id`),
  KEY `owner_password_password_foreign` (`password_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Export de données de la table easypass.owner_password : ~6 rows (environ)
DELETE FROM `owner_password`;
/*!40000 ALTER TABLE `owner_password` DISABLE KEYS */;
INSERT INTO `owner_password` (`id`, `user_id`, `group_id`, `password_id`) VALUES
	(8, NULL, 20, 4),
	(9, 40, NULL, 5),
	(10, 40, NULL, 6),
	(11, 40, NULL, 7),
	(12, NULL, 21, 8),
	(13, 41, NULL, 9);
/*!40000 ALTER TABLE `owner_password` ENABLE KEYS */;


-- Export de la structure de table easypass. passwords
DROP TABLE IF EXISTS `passwords`;
CREATE TABLE IF NOT EXISTS `passwords` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `category_id` int(10) NOT NULL,
  `title` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `informations` varchar(255) COLLATE utf8_unicode_ci DEFAULT '0',
  `urlSite` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `paswords_category_id_foreign` (`category_id`),
  CONSTRAINT `FK_passwords_categories` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Export de données de la table easypass.passwords : ~6 rows (environ)
DELETE FROM `passwords`;
/*!40000 ALTER TABLE `passwords` DISABLE KEYS */;
INSERT INTO `passwords` (`id`, `category_id`, `title`, `informations`, `urlSite`, `password`) VALUES
	(4, 31, 'Mot de passe du compte facebook', 'Adresse mail du compte : facebook@esgi.com', 'www.facebook.com/pages/esgi', 'esgi2016'),
	(5, 30, 'Mot de passe comment ça marche ?', '                    \r\n                ', 'www.commentcamarche.com', 'mdpccm'),
	(6, 34, 'Travail', '                    \r\n                ', 'Mot de passe de connexion Windows', 'blablbala'),
	(7, 33, 'Gmail', '                    \r\n                ', 'www.gmail.com', 'gmailmdp'),
	(8, 33, 'Gmail entreprise', '                    \r\n                ', 'www.gmail.com', 'clevergmail'),
	(9, 31, 'Facebook', '                    \r\n                ', 'www.facebook.com', 'fbcholet');
/*!40000 ALTER TABLE `passwords` ENABLE KEYS */;


-- Export de la structure de table easypass. users
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `lastname` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `username` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `admin` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Export de données de la table easypass.users : ~5 rows (environ)
DELETE FROM `users`;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`, `firstname`, `lastname`, `username`, `password`, `email`, `admin`) VALUES
	(40, 'Adrien', 'Turcey', 'aturcey', '7b902e6ff1db9f560443f2048974fd7d386975b0', 'adrienturcey@gmail.com', 1),
	(41, 'Jonathan', 'Cholet', 'jcholet', '7b902e6ff1db9f560443f2048974fd7d386975b0', 'jcholet@gmail.com', 0),
	(42, 'Jeff', 'Jacquelot', 'jjacquelot', '7b902e6ff1db9f560443f2048974fd7d386975b0', 'jjacquelot@gmail.com', 0),
  (43, 'Use', 'Less', 'useless', '7b902e6ff1db9f560443f2048974fd7d386975b0', 'useless@gmail.com', 0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

-- Export de la structure de table easypass. user_group
DROP TABLE IF EXISTS `user_group`;
CREATE TABLE IF NOT EXISTS `user_group` (
  `user_id` int(10) DEFAULT NULL,
  `admin` int(1) DEFAULT NULL,
  `group_id` int(10) DEFAULT NULL,
  KEY `group_owner` (`group_id`),
  KEY `user_owner` (`user_id`),
  CONSTRAINT `group_owner` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`) ON DELETE CASCADE,
  CONSTRAINT `user_owner` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Export de données de la table easypass.user_group : ~6 rows (environ)
DELETE FROM `user_group`;
/*!40000 ALTER TABLE `user_group` DISABLE KEYS */;
INSERT INTO `user_group` (`user_id`, `admin`, `group_id`) VALUES
	(41, 0, 20),
	(42, 0, 20),
	(40, 1, 20),
	(40, 0, 21),
	(42, 0, 21),
	(41, 1, 21);
/*!40000 ALTER TABLE `user_group` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
