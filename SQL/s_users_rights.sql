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
-- Структура на таблица `s_users_rights`
--

CREATE TABLE IF NOT EXISTS `s_users_rights` (
  `right_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`right_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Схема на данните от таблица `s_users_rights`
--

INSERT INTO `s_users_rights` (`right_name`) VALUES
('create__assignments'),
('create__disciplines'),
('create__lecturers'),
('create__specialities'),
('create__students'),
('create__users'),
('create__admin_panel'),
('delete__assignments'),
('delete__disciplines'),
('delete__lecturers'),
('delete__specialities'),
('delete__students'),
('delete__users'),
('delete__admin_panel'),
('read__assignments'),
('read__disciplines'),
('read__lecturers'),
('read__specialities'),
('read__students'),
('read__users'),
('read__admin_panel'),
('update__assignments'),
('update__disciplines'),
('update__lecturers'),
('update__specialities'),
('update__students'),
('update__users'),
('update__admin_panel');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

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
('read__admin_panel', 'admin'),
('update__admin_panel', 'admin'),
('create__admin_panel', 'admin'),
('delete__admin_panel', 'admin'),
('read__users', 'admin'),
('update__users', 'admin'),
('create__users', 'admin'),
('delete__users', 'admin'),
('read__students', 'admin'),
('update__students', 'admin'),
('create__students', 'admin'),
('delete__students', 'admin'),
('read__lecturers', 'admin'),
('update__lecturers', 'admin'),
('create__lecturers', 'admin'),
('delete__lecturers', 'admin'),
('read__specialities', 'admin'),
('update__specialities', 'admin'),
('create__specialities', 'admin'),
('delete__specialities', 'admin'),
('read__disciplines', 'admin'),
('update__disciplines', 'admin'),
('create__disciplines', 'admin'),
('delete__disciplines', 'admin'),
('read__assignments', 'admin'),
('update__assignments', 'admin'),
('create__assignments', 'admin'),
('delete__assignments', 'admin');


--
-- Ограничения за дъмпнати таблици
--

--
-- Ограничения за таблица `s_users_rights_and_users_roles`
--
ALTER TABLE `s_users_rights_and_users_roles`
  ADD CONSTRAINT `FK_s_users_rights_and_users_roles_right_name` FOREIGN KEY (`right_name`) REFERENCES `s_users_rights` (`right_name`),
  ADD CONSTRAINT `FK_s_users_rights_and_users_roles_role_name` FOREIGN KEY (`role_name`) REFERENCES `s_users_roles_list` (`role_name`);

INSERT INTO `s_users_rights_and_users_roles` (`right_name`, `role_name`) VALUES
('create__admin_panel', 'admin'),
('create__assignments_solutions', 'admin'),
('create__assignments', 'admin'),
('create__disciplines', 'admin'),
('create__files', 'admin'),
('create__lecturers', 'admin'),
('create__specialities', 'admin'),
('create__students', 'admin'),
('create__users', 'admin'),
('delete__admin_panel', 'admin'),
('delete__assignments_solutions', 'admin'),
('delete__assignments', 'admin'),
('delete__disciplines', 'admin'),
('delete__files', 'admin'),
('delete__lecturers', 'admin'),
('delete__specialities', 'admin'),
('delete__students', 'admin'),
('delete__users', 'admin'),
('read__admin_panel', 'admin'),
('read__assignments_solutions', 'admin'),
('read__assignments', 'admin'),
('read__disciplines', 'admin'),
('read__files', 'admin'),
('read__lecturers', 'admin'),
('read__specialities', 'admin'),
('read__students', 'admin'),
('read__users', 'admin'),
('update__admin_panel', 'admin'),
('update__assignments_solutions', 'admin'),
('update__assignments', 'admin'),
('update__disciplines', 'admin'),
('update__files', 'admin'),
('update__lecturers', 'admin'),
('update__specialities', 'admin'),
('update__students', 'admin'),
('update__users', 'admin'),
('create__assignments_solutions', 'lecturer'),
('create__files', 'lecturer'),
('delete__assignments_solutions', 'lecturer'),
('read__admin_panel', 'lecturer'),
('read__assignments_solutions', 'lecturer'),
('read__assignments', 'lecturer'),
('read__disciplines', 'lecturer'),
('read__files', 'lecturer'),
('read__specialities', 'lecturer'),
('read__users', 'lecturer'),
('update__assignments_solutions', 'lecturer'),
('create__assignments_solutions', 'student'),
('create__files', 'student'),
('read__assignments', 'student'),
('read__disciplines', 'student'),
('read__specialities', 'student'),
('read__users', 'student');

INSERT INTO `s_users_rights` (`right_name`) VALUES
('create__admin_panel'),
('create__assignments_solutions'),
('create__assignments'),
('create__disciplines'),
('create__files'),
('create__lecturers'),
('create__specialities'),
('create__students'),
('create__users'),
('delete__admin_panel'),
('delete__assignments_solutions'),
('delete__assignments'),
('delete__disciplines'),
('delete__files'),
('delete__lecturers'),
('delete__specialities'),
('delete__students'),
('delete__users'),
('read__admin_panel'),
('read__assignments_solutions'),
('read__assignments'),
('read__disciplines'),
('read__files'),
('read__lecturers'),
('read__specialities'),
('read__students'),
('read__users'),
('update__admin_panel'),
('update__assignments_solutions'),
('update__assignments'),
('update__disciplines'),
('update__files'),
('update__lecturers'),
('update__specialities'),
('update__students'),
('update__users');
