-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Erstellungszeit: 23. Jun 2017 um 09:10
-- Server-Version: 10.1.13-MariaDB
-- PHP-Version: 5.6.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `xxs_datenbank`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `exercises`
--

CREATE TABLE `exercises` (
  `name` varchar(20) NOT NULL,
  `target_reps` int(11) NOT NULL,
  `target_sets` int(11) NOT NULL,
  `target_weight` int(11) NOT NULL,
  `fk_username` varchar(20) NOT NULL,
  `fk_workoutplan_name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `exercises`
--

INSERT INTO `exercises` (`name`, `target_reps`, `target_sets`, `target_weight`, `fk_username`, `fk_workoutplan_name`) VALUES
('Bankdrücken', 5, 3, 100, 'dennsi', 'Push-Plan'),
('Military Press', 6, 3, 50, 'dennsi', 'Push-Plan'),
('Benchen', 5, 3, 80, 'dennsi', 'Push'),
('Trizepsdrücken', 5, 3, 80, 'dennsi', 'Push'),
('Trizepsdrücken', 5, 3, 120, 'dennsi', 'Push-Plan');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `logs`
--

CREATE TABLE `logs` (
  `date` date NOT NULL,
  `reps` int(11) NOT NULL,
  `weight` int(11) NOT NULL,
  `sets` int(11) NOT NULL,
  `fk_username` varchar(20) NOT NULL,
  `fk_workoutplan` varchar(20) NOT NULL,
  `fk_exercisename` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `logs`
--

INSERT INTO `logs` (`date`, `reps`, `weight`, `sets`, `fk_username`, `fk_workoutplan`, `fk_exercisename`) VALUES
('2017-06-06', 5, 100, 3, 'dennsi', 'Push-Plan', 'Bankdrücken'),
('2017-06-07', 5, 100, 3, 'dennsi', 'Push-Plan', 'Bankdrücken'),
('2017-06-09', 5, 100, 3, 'dennsi', 'Push-Plan', 'Bankdrücken'),
('2017-06-14', 5, 100, 3, 'dennsi', 'Push-Plan', 'Bankdrücken'),
('2017-06-15', 5, 100, 3, 'dennsi', 'Push-Plan', 'Bankdrücken'),
('2017-06-15', 6, 50, 3, 'dennsi', 'Push-Plan', 'Military Press'),
('2017-06-19', 5, 100, 3, 'dennsi', 'Push-Plan', 'Bankdrücken'),
('2017-06-18', 5, 100, 3, 'dennsi', 'Push-Plan', 'Bankdrücken'),
('2017-06-21', 5, 100, 3, 'dennsi', 'Push-Plan', 'Bankdrücken'),
('2017-06-20', 5, 100, 3, 'dennsi', 'Push-Plan', 'Bankdrücken'),
('2017-06-16', 5, 100, 3, 'dennsi', 'Push-Plan', 'Bankdrücken');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `users`
--

CREATE TABLE `users` (
  `username` varchar(25) NOT NULL,
  `password` varchar(40) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `email_address` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `users`
--

INSERT INTO `users` (`username`, `password`, `first_name`, `last_name`, `email_address`) VALUES
('dennsi', '-1564338303', 'Dennis', 'Rathke', 'dennis.rathke@yahoo.de');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `workoutplans`
--

CREATE TABLE `workoutplans` (
  `name` varchar(20) NOT NULL,
  `description` varchar(400) NOT NULL,
  `fk_username` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `workoutplans`
--

INSERT INTO `workoutplans` (`name`, `description`, `fk_username`) VALUES
('Push-Plan', 'Einfacher Push-Plan', 'dennsi'),
('Push', '2er Push', 'dennsi');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
