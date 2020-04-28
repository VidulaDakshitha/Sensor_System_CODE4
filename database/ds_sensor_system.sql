-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 26, 2020 at 07:04 PM
-- Server version: 10.1.38-MariaDB
-- PHP Version: 7.3.4

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
-- Table structure for table `admins`
--

CREATE TABLE `admins` (
  `id` int(11) NOT NULL,
  `name` varchar(150) NOT NULL,
  `email` varchar(150) NOT NUll,
  `username` varchar(150) NOT NULL,
  `password` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admins`
--

INSERT INTO `admins` (`id`, `name`, `email`, `username`, `password`) VALUES
(1, 'admin', 'codefoursliit@gmail.com', 'admin1', '123');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `clients` (
  `id` int(11) NOT NULL,
  `name` varchar(150) NOT NULL,
  `email` varchar(150) NOT NULL,
  `room` int(5) NOT NULL,
  `phone` varchar(15) NOT NUll
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `clients`
--

INSERT INTO `clients` (`id`, `name`, `email`, `room`, `phone`) VALUES
(1, 'vidula', 'viduladakshitha@gmail.com', 1, '+94779819207'),
(2, 'kevin gomez', 'kevingomez890@gmail.com', 2 ,'+94771986561'),
(3, 'Dilshan Harendra', 'dilshanharendraperera123@gmail.com', 3, '+94783253430'),
(4, 'Sathira Lamal', 'sdsat756@gmail.com', 4, '+94717732324');

-- --------------------------------------------------------

--
-- Table structure for table `sensors`
--

CREATE TABLE `sensors` (
  `id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `room` varchar(100) DEFAULT NULL,
  `floor` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `colevel` double DEFAULT NULL,
  `smokelevel` double DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sensors`
--

INSERT INTO `sensors` (`id`, `name`, `room`, `floor`, `status`, `colevel`, `smokelevel`) VALUES
(1, 'sensor 1', 'room 1', 'floor 1', 'Active', 10, 10),
(2, 'sensor 2', 'room 2', 'floor 1', 'Active', 10, 10),
(3, 'sensor 3', 'room 3', 'floor 2', 'Active', 10, 10),
(4, ' sensor 4 ', ' room 4  ', ' floor 2 ', 'Inactive', 0, 0),
(5, ' sensor 5 ', ' room 5  ', ' floor 3 ', 'Inactive', 0, 0),
(6, ' sensor 6 ', ' room 6  ', ' floor 3 ', 'Inactive', 0, 0),
(7, ' sensor 7 ', ' room 7 ', ' floor 4 ', 'Inactive', 0, 0),
(8, ' sensor 8 ', ' room 8 ', ' floor 5 ', 'Inactive', 0, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admins`
--
ALTER TABLE `admins`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `clients`
--
ALTER TABLE `clients`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sensors`
--
ALTER TABLE `sensors`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admins`
--
ALTER TABLE `admins`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `clients`
--
ALTER TABLE `clients`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `sensors`
--
ALTER TABLE `sensors`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
