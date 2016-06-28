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
CREATE DATABASE IF NOT EXISTS `j_ee_students_system` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `j_ee_students_system`;

--
-- Схема на данните от таблица `s_assignments`
--



    INSERT INTO `s_assignments` (`id`, `attached_file`, `description`, `end_time`, `start_time`, `title`, `discipline_id`) VALUES
    (1, 'Homework1_SI_all.pdf', 'Предаване на домашното в указания срок от всеки студент\nкато .zip архив:\n- Архивът да съдържа само изходен код (.cpp и .h файлове)\nс решение отговарящо на условията на задачите, като\nфайловете изходен код за всяка задача трябва да са\nразположени в папка с име (номер_на_задача), където\n(номер_на_задача) е номера на задачата към която се\nотнася решението;\n- Качване на архива на посоченото място;\n- Спазване на форматирането на данните посочено в примеред вход/изход;\n', '2016-06-25 00:00:00', '2016-06-20 00:00:00', 'УП - СИ - Домашна работа №1', 1),
    (2, 'Homework2_SI_all.pdf', 'Предаване на домашното в указания срок от всеки студент\r\nкато .zip архив:\r\n- Архивът да съдържа само изходен код (.cpp и .h файлове)\r\nс решение отговарящо на условията на задачите, като\r\nфайловете изходен код за всяка задача трябва да са\r\nразположени в папка с име (номер_на_задача), където\r\n(номер_на_задача) е номера на задачата към която се\r\nотнася решението;\r\n- Качване на архива на посоченото място;\r\n- Спазване на форматирането на данните посочено в примеред вход/изход;\r\n', '2016-06-26 00:00:00', '2016-06-21 00:00:00', 'УП - СИ - Домашна работа №2', 1),
    (3, 'Homework3_SI_all.pdf', 'Предаване на домашното в указания срок от всеки студент\r\nкато .zip архив:\r\n- Архивът да съдържа само изходен код (.cpp и .h файлове)\r\nс решение отговарящо на условията на задачите, като\r\nфайловете изходен код за всяка задача трябва да са\r\nразположени в папка с име (номер_на_задача), където\r\n(номер_на_задача) е номера на задачата към която се\r\nотнася решението;\r\n- Качване на архива на посоченото място;\r\n- Спазване на форматирането на данните посочено в примеред вход/изход;\r\n', '2016-06-27 00:00:00', '2016-06-22 00:00:00', 'УП - СИ - Домашна работа №3', 1);

--
-- Схема на данните от таблица `s_disciplines`
--

INSERT INTO `s_disciplines` (`id`, `discipline_name`, `speciality_id`) VALUES
(1, 'Увод в програмирането - СИ', 1),
(2, 'Обектно-ориентирано програмиране - СИ', 1),
(3, 'Структури от данни и алгоритми - СИ', 1),
(4, 'Структури от данни и програмиране - КН', 2),
(5, 'Увод в програмирането - КН', 2),
(6, 'Обектно ориентирано програмиране - КН', 2);

--
-- Схема на данните от таблица `s_specialities`
--

INSERT INTO `s_specialities` (`id`, `speciality_name`) VALUES
(2, 'Компютърни науки'),
(1, 'Софтуерно инженерство');

--
-- Схема на данните от таблица `s_users`
--

INSERT INTO `s_users` (`id`, `user_type`, `email`, `is_activated`, `password`, `username`, `first_name`, `last_name`, `surname`, `faculty_number`, `information`) VALUES
(1, 's_users', 'admin@example.bg', 1, 'x61Ey612Kl2gpFL56FT9weDnpSo4AV8j8+qx2AuTHdRyY036xxzTTrw10Wq3+4qQyB+XURPWx1ONxp3Y3pB37A==', 'admin', 'h5', 'h5', 'h5', NULL, NULL),
(2, 's_students', 'student@student.info', 1, 'Mq3l58NvoynqOdvDUnQ9tA2lqnRg7FX5W5mdY3GtIBcAlNiNkpZkPxkunVQzuNbYF9Z3djLlVuluWPdB3Fs1UA==', 'student', 'Студентско-име', 'Студентска-фамилия', 'Студентско-презиме', '123456', NULL),
(3, 's_students', 'sdfsdfsdfsdfds@fdfsdfsd.bg', 1, '79nXgNppI5aeTCFuC707lIryRzDXHTnC7gCKp+OYJHm/rhLHJfb3QQMcjxtbmhXmCt9xnoOWz+JfbRPhgu8Uqg==', 'dfgfd', 'dsfsd', 'sdfdsfsdfsd', 'dsfsdf', 'sdfsdf', NULL);

--
-- Схема на данните от таблица `s_users_and_users_roles_assigned`
--

INSERT INTO `s_users_and_users_roles_assigned` (`user_id`, `role_name`) VALUES
(1, 'admin'),
(2, 'student');

--
-- Схема на данните от таблица `s_users_rights`
--

INSERT INTO `s_users_rights` (`right_name`) VALUES
('create__admin_panel'),
('create__assignments'),
('create__disciplines'),
('create__files'),
('create__lecturers'),
('create__specialities'),
('create__students'),
('create__users'),
('delete__admin_panel'),
('delete__assignments'),
('delete__disciplines'),
('delete__files'),
('delete__lecturers'),
('delete__specialities'),
('delete__students'),
('delete__users'),
('read__admin_panel'),
('read__assignments'),
('read__disciplines'),
('read__files'),
('read__lecturers'),
('read__specialities'),
('read__students'),
('read__users'),
('update__admin_panel'),
('update__assignments'),
('update__disciplines'),
('update__files'),
('update__lecturers'),
('update__specialities'),
('update__students'),
('update__users');

--
-- Схема на данните от таблица `s_users_rights_and_users_roles`
--

INSERT INTO `s_users_rights_and_users_roles` (`right_name`, `role_name`) VALUES
('create__admin_panel', 'admin'),
('create__assignments', 'admin'),
('create__disciplines', 'admin'),
('create__files', 'admin'),
('create__lecturers', 'admin'),
('create__specialities', 'admin'),
('create__students', 'admin'),
('create__users', 'admin'),
('delete__admin_panel', 'admin'),
('delete__assignments', 'admin'),
('delete__disciplines', 'admin'),
('delete__files', 'admin'),
('delete__lecturers', 'admin'),
('delete__specialities', 'admin'),
('delete__students', 'admin'),
('delete__users', 'admin'),
('read__admin_panel', 'admin'),
('read__assignments', 'admin'),
('read__disciplines', 'admin'),
('read__files', 'admin'),
('read__lecturers', 'admin'),
('read__specialities', 'admin'),
('read__students', 'admin'),
('read__users', 'admin'),
('update__admin_panel', 'admin'),
('update__assignments', 'admin'),
('update__disciplines', 'admin'),
('update__files', 'admin'),
('update__lecturers', 'admin'),
('update__specialities', 'admin'),
('update__students', 'admin'),
('update__users', 'admin'),
('create__files', 'lecturer'),
('read__files', 'lecturer'),
('create__files', 'student'),
('read__files', 'student');

--
-- Схема на данните от таблица `s_users_roles_list`
--

INSERT INTO `s_users_roles_list` (`role_name`, `role_title`) VALUES
('admin', 'Администратор'),
('lecturer', 'Лектор'),
('student', 'Студент');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
