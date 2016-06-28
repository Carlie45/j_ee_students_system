-- phpMyAdmin SQL Dump
-- version 4.0.4.1
-- http://www.phpmyadmin.net
--
-- Хост: 127.0.0.1
-- Време на генериране: 
-- Версия на сървъра: 5.6.11
-- Версия на PHP: 5.5.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- БД: `j_ee_students_system`
--

-- --------------------------------------------------------

--
-- Структура на таблица `s_users_rights_and_users_roles`
--

CREATE TABLE IF NOT EXISTS `s_users_rights_and_users_roles` (
  `right_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `role_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`right_name`,`role_name`),
  KEY `FK_s_users_rights_and_users_roles_role_name` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Схема на данните от таблица `s_users_rights_and_users_roles`
--

INSERT INTO `s_users_rights_and_users_roles` (`right_name`, `role_name`) VALUES
('create__admin_panel', 'admin'),
('create__assignments', 'admin'),
('create__disciplines', 'admin'),
('create__lecturers', 'admin'),
('create__specialities', 'admin'),
('create__students', 'admin'),
('create__users', 'admin'),
('delete__admin_panel', 'admin'),
('delete__assignments', 'admin'),
('delete__disciplines', 'admin'),
('delete__lecturers', 'admin'),
('delete__specialities', 'admin'),
('delete__students', 'admin'),
('delete__users', 'admin'),
('read__admin_panel', 'admin'),
('read__assignments', 'admin'),
('read__disciplines', 'admin'),
('read__lecturers', 'admin'),
('read__specialities', 'admin'),
('read__students', 'admin'),
('read__users', 'admin'),
('update__admin_panel', 'admin'),
('update__assignments', 'admin'),
('update__disciplines', 'admin'),
('update__lecturers', 'admin'),
('update__specialities', 'admin'),
('update__students', 'admin'),
('update__users', 'admin');

--
-- Ограничения за дъмпнати таблици
--

--
-- Ограничения за таблица `s_users_rights_and_users_roles`
--
ALTER TABLE `s_users_rights_and_users_roles`
  ADD CONSTRAINT `FK_s_users_rights_and_users_roles_right_name` FOREIGN KEY (`right_name`) REFERENCES `s_users_rights` (`right_name`),
  ADD CONSTRAINT `FK_s_users_rights_and_users_roles_role_name` FOREIGN KEY (`role_name`) REFERENCES `s_users_roles_list` (`role_name`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
