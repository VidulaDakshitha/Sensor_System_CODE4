-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Apr 23, 2020 at 04:22 PM
-- Server version: 5.7.21
-- PHP Version: 7.1.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ds_sensor_system`
--

-- --------------------------------------------------------

--
-- Table structure for table `sensors`
--

DROP TABLE IF EXISTS `sensors`;
CREATE TABLE IF NOT EXISTS `sensors` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `room` varchar(100) DEFAULT NULL,
  `floor` varchar(100) DEFAULT NULL,
  `colevel` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sensors`
--

INSERT INTO `sensors` (`id`, `name`, `room`, `floor`, `colevel`) VALUES
(1, 'sensor 1', 'room 1', 'floor 1', 50),
(2, 'sensor 2', 'room 2', 'floor 2', 70),
(3, 'sensor 3', 'room 3', 'fllo 3', 50),
(4, ' sensor 4 ', ' room 4  ', ' floor 4 ', 0),
(5, ' sensor 4 ', ' room 4  ', ' floor 4 ', 0),
(6, ' sensor 4 ', ' room 4  ', ' floor 4 ', 0),
(7, ' sensor 5 ', ' room 5 ', ' floor 5 ', 0),
(8, ' sensor6 ', ' room 6 ', ' floor 6 ', 0);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
