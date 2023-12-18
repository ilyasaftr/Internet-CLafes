-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 18, 2023 at 04:15 AM
-- Server version: 10.4.19-MariaDB
-- PHP Version: 8.0.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `internet-clafes`
--

-- --------------------------------------------------------

DROP TABLE IF EXISTS `pcbook`;
DROP TABLE IF EXISTS `transactiondetail`;
DROP TABLE IF EXISTS `transactionheader`;
DROP TABLE IF EXISTS `job`;
DROP TABLE IF EXISTS `report`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `pc`;

--
-- Table structure for table `job`
--

CREATE TABLE `job` (
  `Job_ID` int(11) NOT NULL,
  `UserID` int(11) NOT NULL,
  `PC_ID` int(11) NOT NULL,
  `JobStatus` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `job`
--

INSERT INTO `job` (`Job_ID`, `UserID`, `PC_ID`, `JobStatus`) VALUES
(1, 4, 2, 'UnComplete'),
(2, 4, 4, 'UnComplete'),
(3, 4, 1, 'Complete'),
(4, 4, 3, 'Complete');

-- --------------------------------------------------------

--
-- Table structure for table `pc`
--

CREATE TABLE `pc` (
  `PC_ID` int(11) NOT NULL,
  `PC_Condition` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pc`
--

INSERT INTO `pc` (`PC_ID`, `PC_Condition`) VALUES
(1, 'Usable'),
(2, 'Maintenance'),
(3, 'Usable'),
(4, 'Maintenance'),
(5, 'Broken');

-- --------------------------------------------------------

--
-- Table structure for table `pcbook`
--

CREATE TABLE `pcbook` (
  `Book_ID` int(11) NOT NULL,
  `PC_ID` int(11) NOT NULL,
  `UserID` int(11) NOT NULL,
  `BookedDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pcbook`
--

INSERT INTO `pcbook` (`Book_ID`, `PC_ID`, `UserID`, `BookedDate`) VALUES
(1, 1, 1, '2023-12-06'),
(2, 3, 1, '2023-12-27'),
(3, 3, 1, '2023-12-28');

-- --------------------------------------------------------

--
-- Table structure for table `report`
--


CREATE TABLE `report` (
  `Report_ID` int(11) NOT NULL,
  `UserRole` varchar(255) NOT NULL,
  `PC_ID` int(11) NOT NULL,
  `ReportNote` text NOT NULL,
  `ReportDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `report`
--

INSERT INTO `report` (`Report_ID`, `UserRole`, `PC_ID`, `ReportNote`, `ReportDate`) VALUES
(1, 'Customer', 2, 'Failure to Power On: The PC does not respond to the power button despite being connected to a functioning power source.', '2023-11-09'),
(2, 'Customer', 4, 'Error Messages: Upon attempting to power up the system, it displays error messages.', '2023-11-16'),
(3, 'Operator', 5, 'Blue Screen Error: The computer screen displayed an error message, saying \"Windows has been shut down to prevent damage to your computer\".', '2023-12-01');

-- --------------------------------------------------------

--
-- Table structure for table `transactiondetail`
--

CREATE TABLE `transactiondetail` (
  `TransactionDetailID` int(11) NOT NULL,
  `TransactionID` int(11) NOT NULL,
  `PC_ID` int(11) NOT NULL,
  `CustomerName` varchar(255) NOT NULL,
  `BookedTime` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transactiondetail`
--

INSERT INTO `transactiondetail` (`TransactionDetailID`, `TransactionID`, `PC_ID`, `CustomerName`, `BookedTime`) VALUES
(1, 1, 1, 'Christopher', '18:30:00'),
(2, 2, 3, 'Christopher', '17:00:00'),
(3, 3, 3, 'Christopher', '19:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `transactionheader`
--

CREATE TABLE `transactionheader` (
  `TransactionID` int(11) NOT NULL,
  `StaffID` int(11) NOT NULL,
  `StaffName` varchar(255) NOT NULL,
  `TransactionDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transactionheader`
--

INSERT INTO `transactionheader` (`TransactionID`, `StaffID`, `StaffName`, `TransactionDate`) VALUES
(1, 2, 'Anthony', '2023-11-29'),
(2, 3, 'Administrator', '2023-12-02'),
(3, 4, 'Blundetto', '2023-12-03');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `UserID` int(11) NOT NULL,
  `UserName` varchar(255) NOT NULL,
  `UserPassword` varchar(255) NOT NULL,
  `UserAge` int(11) NOT NULL,
  `UserRole` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`UserID`, `UserName`, `UserPassword`, `UserAge`, `UserRole`) VALUES
(1, 'Christopher', 'abc123', 15, 'Customer'),
(2, 'Anthony', 'abc123', 20, 'Operator'),
(3, 'Administrator', 'abc123', 18, 'Admin'),
(4, 'Blundetto', 'abc123', 20, 'Computer Technician');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `job`
--
ALTER TABLE `job`
  ADD PRIMARY KEY (`Job_ID`),
  ADD KEY `UserID` (`UserID`),
  ADD KEY `PC_ID` (`PC_ID`);

--
-- Indexes for table `pc`
--
ALTER TABLE `pc`
  ADD PRIMARY KEY (`PC_ID`);

--
-- Indexes for table `pcbook`
--
ALTER TABLE `pcbook`
  ADD PRIMARY KEY (`Book_ID`),
  ADD KEY `PC_ID` (`PC_ID`),
  ADD KEY `UserID` (`UserID`);

--
-- Indexes for table `report`
--
ALTER TABLE `report`
  ADD PRIMARY KEY (`Report_ID`),
  ADD KEY `PC_ID` (`PC_ID`);

--
-- Indexes for table `transactiondetail`
--
ALTER TABLE `transactiondetail`
  ADD PRIMARY KEY (`TransactionDetailID`),
  ADD KEY `TransactionID` (`TransactionID`),
  ADD KEY `PC_ID` (`PC_ID`);

--
-- Indexes for table `transactionheader`
--
ALTER TABLE `transactionheader`
  ADD PRIMARY KEY (`TransactionID`),
  ADD KEY `StaffID` (`StaffID`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`UserID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `job`
--
ALTER TABLE `job`
  MODIFY `Job_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `pc`
--
ALTER TABLE `pc`
  MODIFY `PC_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `pcbook`
--
ALTER TABLE `pcbook`
  MODIFY `Book_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `report`
--
ALTER TABLE `report`
  MODIFY `Report_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `transactiondetail`
--
ALTER TABLE `transactiondetail`
  MODIFY `TransactionDetailID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `transactionheader`
--
ALTER TABLE `transactionheader`
  MODIFY `TransactionID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `UserID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `job`
--
ALTER TABLE `job`
  ADD CONSTRAINT `job_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `job_ibfk_2` FOREIGN KEY (`PC_ID`) REFERENCES `pc` (`PC_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `pcbook`
--
ALTER TABLE `pcbook`
  ADD CONSTRAINT `pcbook_ibfk_1` FOREIGN KEY (`PC_ID`) REFERENCES `pc` (`PC_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `pcbook_ibfk_2` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `report`
--
ALTER TABLE `report`
  ADD CONSTRAINT `report_ibfk_1` FOREIGN KEY (`PC_ID`) REFERENCES `pc` (`PC_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `transactiondetail`
--
ALTER TABLE `transactiondetail`
  ADD CONSTRAINT `transactiondetail_ibfk_1` FOREIGN KEY (`TransactionID`) REFERENCES `transactionheader` (`TransactionID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `transactiondetail_ibfk_2` FOREIGN KEY (`PC_ID`) REFERENCES `pc` (`PC_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `transactionheader`
--
ALTER TABLE `transactionheader`
  ADD CONSTRAINT `transactionheader_ibfk_1` FOREIGN KEY (`StaffID`) REFERENCES `user` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
