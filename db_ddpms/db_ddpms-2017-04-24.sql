-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 24, 2017 at 03:52 PM
-- Server version: 10.1.16-MariaDB
-- PHP Version: 5.6.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_ddpms`
--

-- --------------------------------------------------------

--
-- Table structure for table `budget`
--

CREATE TABLE `budget` (
  `budg_id` int(11) NOT NULL,
  `budg_name` varchar(250) NOT NULL,
  `budg_type` varchar(50) NOT NULL,
  `modified_date` date NOT NULL,
  `modified_by` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `budget_plan`
--

CREATE TABLE `budget_plan` (
  `budp_id` int(11) NOT NULL,
  `budp_name` varchar(255) NOT NULL,
  `budp_year_begin` int(4) NOT NULL,
  `budp_year_end` int(4) NOT NULL,
  `modified_date` date NOT NULL,
  `modified_by` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `config`
--

CREATE TABLE `config` (
  `conf_id` int(11) NOT NULL,
  `conf_code` varchar(100) NOT NULL,
  `conf_name` varchar(100) NOT NULL,
  `conf_value` varchar(255) NOT NULL,
  `modified_date` date NOT NULL,
  `modified_by` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `config`
--

INSERT INTO `config` (`conf_id`, `conf_code`, `conf_name`, `conf_value`, `modified_date`, `modified_by`) VALUES
(1, 'GENDER', 'MALE', 'ชาย', '2017-04-24', 1),
(2, 'GENDER', 'FEMALE', 'หญิง', '2017-04-24', 1),
(3, 'EMPLOYEE_STATUS', 'ONWER', 'เจ้าของโครงการ', '2017-04-24', 1),
(4, 'EMPLOYEE_STATUS', 'STAFF', 'บุคลากร, ลูกน้อง', '2017-04-24', 1),
(5, 'EMPLOYEE_STATUS', 'ADMIN', 'เจ้าหน้าที่ดูแลระบบ', '2017-04-24', 1);

-- --------------------------------------------------------

--
-- Table structure for table `department`
--

CREATE TABLE `department` (
  `dep_id` int(11) NOT NULL,
  `dep_name` varchar(100) NOT NULL,
  `modified_date` date NOT NULL,
  `modified_by` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `department`
--

INSERT INTO `department` (`dep_id`, `dep_name`, `modified_date`, `modified_by`) VALUES
(1, 'IT', '2017-04-24', 1),
(2, 'HR', '2017-04-24', 1);

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `emp_id` int(11) NOT NULL,
  `emp_code` varchar(20) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `emp_fname` varchar(255) NOT NULL,
  `emp_lname` varchar(255) NOT NULL,
  `emp_email` varchar(50) NOT NULL,
  `emp_mobile` varchar(15) NOT NULL,
  `gender` varchar(20) NOT NULL,
  `title` varchar(20) NOT NULL,
  `dept_id` int(11) NOT NULL,
  `status` varchar(20) NOT NULL,
  `modified_date` date NOT NULL,
  `modified_by` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`emp_id`, `emp_code`, `username`, `password`, `emp_fname`, `emp_lname`, `emp_email`, `emp_mobile`, `gender`, `title`, `dept_id`, `status`, `modified_date`, `modified_by`) VALUES
(1, 'EMP001', 'admin', '81dc9bdb52d04dc20036dbd8313ed055', 'admin', 'admin', 'admin@gmail.com', '0800000000', 'MALE', 'admin', 1, 'ADMIN', '2017-04-24', 1);

-- --------------------------------------------------------

--
-- Table structure for table `plan`
--

CREATE TABLE `plan` (
  `plan_id` int(11) NOT NULL,
  `plan_name` varchar(250) NOT NULL,
  `modified_date` date NOT NULL,
  `modified_by` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `project`
--

CREATE TABLE `project` (
  `proj_id` int(11) NOT NULL,
  `proj_name` varchar(255) NOT NULL,
  `proj_details` varchar(255) NOT NULL,
  `proj_status` varchar(255) NOT NULL,
  `plan_id` int(11) NOT NULL,
  `budp_id` int(11) NOT NULL,
  `modified_date` date NOT NULL,
  `modified_by` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `project_expense`
--

CREATE TABLE `project_expense` (
  `exp_id` int(11) NOT NULL,
  `proj_id` int(11) NOT NULL,
  `exp_desc` text NOT NULL,
  `exp_amount` int(11) NOT NULL,
  `exp_voch` varchar(100) NOT NULL,
  `exp_pr` varchar(100) NOT NULL,
  `receipt` varchar(255) NOT NULL,
  `exp_date` date NOT NULL,
  `vender` varchar(255) NOT NULL,
  `modified_date` date NOT NULL,
  `modified_by` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `project_shift`
--

CREATE TABLE `project_shift` (
  `projs_id` int(11) NOT NULL,
  `proj_id` int(11) NOT NULL,
  `projs_reason` varchar(255) NOT NULL,
  `projs_plan` varchar(255) NOT NULL,
  `modified_date` date NOT NULL,
  `modified_by` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `project_strategic`
--

CREATE TABLE `project_strategic` (
  `projs_id` int(11) NOT NULL,
  `proj_id` int(11) NOT NULL,
  `stra_id` int(11) NOT NULL,
  `modified_date` date NOT NULL,
  `modified_by` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `project_working`
--

CREATE TABLE `project_working` (
  `projw_id` int(11) NOT NULL,
  `proj_id` int(11) NOT NULL,
  `budget_year` int(4) NOT NULL,
  `budget_request` varchar(50) NOT NULL,
  `budget_response` varchar(50) NOT NULL,
  `budget_usage` varchar(50) NOT NULL,
  `modified_date` date NOT NULL,
  `modified_by` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `strategic`
--

CREATE TABLE `strategic` (
  `stra_id` int(11) NOT NULL,
  `stra_name` varchar(100) NOT NULL,
  `modified_date` date NOT NULL,
  `modified_by` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `task`
--

CREATE TABLE `task` (
  `task_id` int(11) NOT NULL,
  `task_name` varchar(250) NOT NULL,
  `modified_date` date NOT NULL,
  `modified_by` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `task_assign`
--

CREATE TABLE `task_assign` (
  `taska_id` int(11) NOT NULL,
  `task_id` int(11) NOT NULL,
  `proj_id` int(11) NOT NULL,
  `task_userid` int(11) NOT NULL,
  `modified_date` date NOT NULL,
  `modified_by` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `task_work`
--

CREATE TABLE `task_work` (
  `taskw_id` int(11) NOT NULL,
  `taskw_detail` text NOT NULL,
  `taskw_manday` int(11) NOT NULL,
  `taska_id` int(11) NOT NULL,
  `taskw_date` date NOT NULL,
  `modified_date` date NOT NULL,
  `modified_by` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `budget`
--
ALTER TABLE `budget`
  ADD PRIMARY KEY (`budg_id`);

--
-- Indexes for table `budget_plan`
--
ALTER TABLE `budget_plan`
  ADD PRIMARY KEY (`budp_id`);

--
-- Indexes for table `config`
--
ALTER TABLE `config`
  ADD PRIMARY KEY (`conf_id`);

--
-- Indexes for table `department`
--
ALTER TABLE `department`
  ADD PRIMARY KEY (`dep_id`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`emp_id`);

--
-- Indexes for table `plan`
--
ALTER TABLE `plan`
  ADD PRIMARY KEY (`plan_id`);

--
-- Indexes for table `project`
--
ALTER TABLE `project`
  ADD PRIMARY KEY (`proj_id`);

--
-- Indexes for table `project_expense`
--
ALTER TABLE `project_expense`
  ADD PRIMARY KEY (`exp_id`);

--
-- Indexes for table `project_shift`
--
ALTER TABLE `project_shift`
  ADD PRIMARY KEY (`projs_id`);

--
-- Indexes for table `project_strategic`
--
ALTER TABLE `project_strategic`
  ADD PRIMARY KEY (`projs_id`);

--
-- Indexes for table `project_working`
--
ALTER TABLE `project_working`
  ADD PRIMARY KEY (`projw_id`);

--
-- Indexes for table `strategic`
--
ALTER TABLE `strategic`
  ADD PRIMARY KEY (`stra_id`);

--
-- Indexes for table `task`
--
ALTER TABLE `task`
  ADD PRIMARY KEY (`task_id`);

--
-- Indexes for table `task_assign`
--
ALTER TABLE `task_assign`
  ADD PRIMARY KEY (`taska_id`);

--
-- Indexes for table `task_work`
--
ALTER TABLE `task_work`
  ADD PRIMARY KEY (`taskw_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `budget`
--
ALTER TABLE `budget`
  MODIFY `budg_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `budget_plan`
--
ALTER TABLE `budget_plan`
  MODIFY `budp_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `config`
--
ALTER TABLE `config`
  MODIFY `conf_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `department`
--
ALTER TABLE `department`
  MODIFY `dep_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `emp_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `plan`
--
ALTER TABLE `plan`
  MODIFY `plan_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `project`
--
ALTER TABLE `project`
  MODIFY `proj_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `project_expense`
--
ALTER TABLE `project_expense`
  MODIFY `exp_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `project_shift`
--
ALTER TABLE `project_shift`
  MODIFY `projs_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `project_strategic`
--
ALTER TABLE `project_strategic`
  MODIFY `projs_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `project_working`
--
ALTER TABLE `project_working`
  MODIFY `projw_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `strategic`
--
ALTER TABLE `strategic`
  MODIFY `stra_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `task`
--
ALTER TABLE `task`
  MODIFY `task_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `task_assign`
--
ALTER TABLE `task_assign`
  MODIFY `taska_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `task_work`
--
ALTER TABLE `task_work`
  MODIFY `taskw_id` int(11) NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
