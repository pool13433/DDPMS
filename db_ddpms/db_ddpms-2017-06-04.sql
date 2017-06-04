-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 03, 2017 at 07:04 PM
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

--
-- Dumping data for table `budget_plan`
--

INSERT INTO `budget_plan` (`budp_id`, `budp_name`, `budp_year_begin`, `budp_year_end`, `modified_date`, `modified_by`) VALUES
(1, 'เงินปีงบประมาณ 2558- 2560', 2558, 2560, '2017-05-31', 1);

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
(5, 'EMPLOYEE_STATUS', 'ADMIN', 'เจ้าหน้าที่ดูแลระบบ', '2017-04-24', 1),
(6, 'DATE', 'MONTHS', 'มกราคม,กุมภาพันธ์,มีนาคม,เมษายน,พฤษภาคม,มิถุนายน,กรกฎาคม,สิงหาคม,กันยายน,ตุลาคม,พฤศจิกายน,ธันวาคม', '2017-04-24', 1),
(7, 'TASK', 'MANDAYS', '1,2,3,4,5,6,7,8', '2017-04-24', 1),
(8, 'PROJECT_STATUS', 'COMPLETE', 'ดำเนินการเรียบร้อย', '2017-05-03', 1),
(9, 'PROJECT_STATUS', 'ADJUST', 'ปรับแผนดำเนินการ', '2017-05-03', 1),
(10, 'PROJECT_STATUS', 'CANCEL', 'ยกเลิกการดำเนินการ', '2017-05-03', 1),
(11, 'PROJECT_STATUS', 'SUPPORT', 'งานสนับสนุน', '2017-05-03', 1),
(12, 'PROJECT_STATUS', 'INPLAN', 'อยู่ในแผนดำเนินการ', '2017-05-03', 1),
(13, 'PROJECT_STATUS', 'PROCESSING', 'อยู่ระหว่างดำเนินการ', '2017-05-03', 1),
(14, 'PROJECT_GROUP', 'CAPEX', 'ค่าใช้จ่ายด้านงบลงทุน (CAPEX)', '2017-05-03', 1),
(15, 'PROJECT_GROUP', 'OPEX', 'ค่าใช้จ่ายด้านการดำเนินงาน (OPEX)', '2017-05-03', 1),
(16, 'PROJECT_STATUS', 'WAITING', 'รอตรวจสอบ', '2017-05-03', 1),
(17, 'PROJECT_STATUS', 'REJECT', 'ไม่ผ่านการตรวจสอบ', '2017-05-03', 1),
(18, 'EMPLOYEE_STATUS', 'APPROVER', 'เจ้าหน้าที่ตรวจสอบข้อมูล', '2017-04-24', 1);

-- --------------------------------------------------------

--
-- Table structure for table `department`
--

CREATE TABLE `department` (
  `dep_id` int(11) NOT NULL,
  `dep_name` varchar(100) NOT NULL,
  `dep_account` varchar(100) DEFAULT NULL,
  `modified_date` date NOT NULL,
  `modified_by` int(11) NOT NULL,
  `dep_code` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `department`
--

INSERT INTO `department` (`dep_id`, `dep_name`, `dep_account`, `modified_date`, `modified_by`, `dep_code`) VALUES
(1, 'ผู้อำนวยการฝ่าย \n', '', '2017-04-24', 1, NULL),
(2, 'แผนกดูแลระบบงานสารสนเทศ\n', '', '2017-04-24', 1, NULL),
(3, 'แผนกบริหารระบบงานทรัพยากรองค์กร\r\n', '', '2017-04-24', 1, NULL),
(4, 'แผนกงานพัฒนาระบบงานสารสนเทศ\r\n\r\n', '', '2017-04-24', 1, NULL),
(5, 'แผนกสนับสนุนระบบงานสารสนเทศ', '', '2017-04-24', 1, NULL);

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
(1, 'EMP001', 'admin', '81dc9bdb52d04dc20036dbd8313ed055', 'admin', 'admin', 'admin@gmail.com', '0800000000', 'FEMALE', 'xxxx', 2, 'ADMIN', '2017-05-11', 1),
(2, 'STAFF002', 'staff02', '81dc9bdb52d04dc20036dbd8313ed055', 'staff02', 'staff02', 'staff02@gmail.com', '0800000000', 'MALE', 'emp02', 1, 'STAFF', '2017-04-24', 1),
(3, 'EMP003', 'emp03', '81dc9bdb52d04dc20036dbd8313ed055', 'emp03', 'emp03', 'emp03@gmail.com', '0800000000', 'MALE', 'emp03', 1, 'STAFF', '2017-04-24', 1),
(4, 'APP003', 'approver', '81dc9bdb52d04dc20036dbd8313ed055', 'approver', 'approver', 'approver@gmail.com', '0800000000', 'MALE', 'emp03', 1, 'APPROVER', '2017-04-24', 1),
(5, 'OWNER001', 'ownerp03', '81dc9bdb52d04dc20036dbd8313ed055', 'ownerp03', 'ownerp03', 'ownerp03@gmail.com', '0800000000', 'MALE', 'emp03', 1, 'OWNER', '2017-04-24', 1);

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

--
-- Dumping data for table `plan`
--

INSERT INTO `plan` (`plan_id`, `plan_name`, `modified_date`, `modified_by`) VALUES
(1, 'แผนการพัฒนาเทคโนโลยีสารสนเทศของบริษัท (Company Information Technology)', '2017-04-27', 1),
(2, 'แผนการพัฒนาระบบงานสารสนเทศและฐานข้อมูล (Application Development and Database Management)', '2017-04-27', 1),
(3, 'แผนการพัฒนาระบบคอมพิวเตอร์และงานสนับสนุนการบริการ (Infrastructure and Service Support)', '2017-04-27', 1),
(4, 'แผนการพัฒนาระบบเครือข่ายและการสื่อสารข้อมูล (Networking)', '2017-04-27', 1),
(5, 'แผนการพัฒนาระบบสนับสนุนระบบงานสารสนเทศ (Application Support)', '2017-04-27', 1),
(6, 'แผนการพัฒนาความมั่นคงปลอดภัยเทคโนโลยีสารสนเทศ (Information Technology Security)', '2017-04-27', 1),
(7, 'แผนการพัฒนาบุคลากรด้านเทคโนโลยีสารสนเทศ', '2017-04-27', 1),
(8, 'งบประมาณอื่นๆ', '2017-04-27', 1);

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
  `prot_id` int(11) NOT NULL,
  `budp_id` int(11) NOT NULL,
  `proj_budg_approve` int(11) DEFAULT NULL COMMENT 'เงินที่อนุมัติครั้งแรกสุด',
  `modified_date` date NOT NULL,
  `modified_by` int(11) NOT NULL,
  `proj_remark` varchar(250) DEFAULT NULL,
  `proj_verify_date` date DEFAULT NULL,
  `proj_verify_by` int(11) DEFAULT NULL,
  `account_code` varchar(10) DEFAULT NULL,
  `stra_id` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `project`
--

INSERT INTO `project` (`proj_id`, `proj_name`, `proj_details`, `proj_status`, `plan_id`, `prot_id`, `budp_id`, `proj_budg_approve`, `modified_date`, `modified_by`, `proj_remark`, `proj_verify_date`, `proj_verify_by`, `account_code`, `stra_id`) VALUES
(1, 'โครงการสร้างรูปแบบและมาตรฐาน ส่วนต่อประสานผู้ใช้งานให้มีเอกลักษณ์เป็นรูปแบบเดียวกัน', '', 'PROCESSING', 1, 1, 1, 1100000, '2017-06-03', 3, 'อนุมัติ', '2017-06-03', 4, '1234', '[5, 6]');

-- --------------------------------------------------------

--
-- Table structure for table `project_budget`
--

CREATE TABLE `project_budget` (
  `prob_id` int(11) NOT NULL,
  `proj_id` int(11) NOT NULL,
  `budp_id` int(11) NOT NULL,
  `prob_req_budget` int(11) NOT NULL COMMENT 'เงินที่ขอก้อนใหญ่รวมทุกปี',
  `prob_appr_budget` int(11) DEFAULT NULL COMMENT 'เงินที่อนุมัติก้อนใหญ่รวมทุกปี'
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
  `receipt_date` date NOT NULL,
  `exp_date` date NOT NULL,
  `vender` varchar(255) NOT NULL,
  `modified_date` date NOT NULL,
  `modified_by` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `project_expense`
--

INSERT INTO `project_expense` (`exp_id`, `proj_id`, `exp_desc`, `exp_amount`, `exp_voch`, `exp_pr`, `receipt_date`, `exp_date`, `vender`, `modified_date`, `modified_by`) VALUES
(6, 1, 'budgetLimit', 13000000, '14000000', 'budgetLimit', '2017-05-30', '2017-05-31', '14000000', '2017-05-31', 1),
(8, 1, '500000', 300000, '500000', '500000', '2017-05-31', '2017-05-31', '500000', '2017-05-31', 1),
(9, 1, '200000', 200000, '200000', '200000', '2017-05-31', '2017-05-31', '200000', '2017-05-31', 1);

-- --------------------------------------------------------

--
-- Table structure for table `project_history`
--

CREATE TABLE `project_history` (
  `his_id` int(11) NOT NULL,
  `proj_id` int(11) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  `modified_date` date DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `remarks` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `project_history`
--

INSERT INTO `project_history` (`his_id`, `proj_id`, `status`, `modified_date`, `modified_by`, `remarks`) VALUES
(67, 1, 'WAITING', '2017-06-03', 3, 'เริ่มต้นขอ'),
(68, 1, 'CANCEL', '2017-06-03', 3, 'หยุดไว้ชั่วคราว'),
(69, 1, 'WAITING', '2017-06-03', 3, 'กลับมาเริ่มได้แล้ว'),
(70, 1, 'INPLAN', '2017-06-03', 4, 'อนุมัติ'),
(71, 1, 'PROCESSING', '2017-06-03', 3, 'เลื่อน วันเริ่มโครงการ');

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
-- Table structure for table `project_type`
--

CREATE TABLE `project_type` (
  `prot_id` int(11) NOT NULL,
  `prot_code` varchar(50) DEFAULT NULL,
  `prot_name` varchar(250) NOT NULL,
  `prot_type` enum('CAPEX','OPEX') DEFAULT NULL,
  `modified_date` date NOT NULL,
  `modified_by` int(11) NOT NULL,
  `dep_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `project_type`
--

INSERT INTO `project_type` (`prot_id`, `prot_code`, `prot_name`, `prot_type`, `modified_date`, `modified_by`, `dep_id`) VALUES
(1, 'B2001', 'Application & Software', 'CAPEX', '2017-05-03', 1, NULL),
(2, 'B2002', 'Network', 'CAPEX', '2017-05-03', 1, NULL),
(3, 'B2003', 'Hardware', 'CAPEX', '2017-05-03', 1, NULL),
(4, 'B2004', 'Security', 'CAPEX', '2017-05-03', 1, NULL),
(5, 'B2005', 'IT Outsource', 'CAPEX', '2017-05-03', 1, NULL),
(6, 'B2006', 'Internet & Firewall', 'OPEX', '2017-05-03', 1, NULL),
(7, 'B2007', 'Maintenance', 'CAPEX', '2017-05-03', 1, NULL),
(8, 'B2008', 'Fare', 'OPEX', '2017-05-03', 1, NULL),
(9, 'B2008', 'PC & Notebook', 'OPEX', '2017-05-03', 1, NULL),
(10, 'B2009', 'Travelling -Domestic', 'OPEX', '2017-05-03', 1, NULL),
(11, 'B2010', 'SBM&BSS Integration', 'OPEX', '2017-05-03', 1, NULL),
(12, 'B2011', 'NO BUDGET', 'OPEX', '2017-05-03', 1, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `project_working`
--

CREATE TABLE `project_working` (
  `projw_id` int(11) NOT NULL,
  `proj_id` int(11) NOT NULL,
  `budget_year` int(4) NOT NULL,
  `budget_request_m1` varchar(50) DEFAULT NULL,
  `budget_request_m2` varchar(50) DEFAULT NULL,
  `budget_request_m3` varchar(50) DEFAULT NULL,
  `budget_request_m4` varchar(50) DEFAULT NULL,
  `budget_request_m5` varchar(50) DEFAULT NULL,
  `budget_request_m6` varchar(50) DEFAULT NULL,
  `budget_request_m7` varchar(50) DEFAULT NULL,
  `budget_request_m8` varchar(50) DEFAULT NULL,
  `budget_request_m9` varchar(50) DEFAULT NULL,
  `budget_request_m10` varchar(50) DEFAULT NULL,
  `budget_request_m11` varchar(50) DEFAULT NULL,
  `budget_request_m12` varchar(50) DEFAULT NULL,
  `budget_approve_m1` varchar(50) DEFAULT NULL,
  `budget_approve_m2` varchar(50) DEFAULT NULL,
  `budget_approve_m3` varchar(50) DEFAULT NULL,
  `budget_approve_m4` varchar(50) DEFAULT NULL,
  `budget_approve_m5` varchar(50) DEFAULT NULL,
  `budget_approve_m6` varchar(50) DEFAULT NULL,
  `budget_approve_m7` varchar(50) DEFAULT NULL,
  `budget_approve_m8` varchar(50) DEFAULT NULL,
  `budget_approve_m9` varchar(50) DEFAULT NULL,
  `budget_approve_m10` varchar(50) DEFAULT NULL,
  `budget_approve_m11` varchar(50) DEFAULT NULL,
  `budget_approve_m12` varchar(50) DEFAULT NULL,
  `budget_usage` varchar(50) DEFAULT NULL,
  `modified_date` date NOT NULL,
  `modified_by` int(11) NOT NULL,
  `isFirstApprove` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `project_working`
--

INSERT INTO `project_working` (`projw_id`, `proj_id`, `budget_year`, `budget_request_m1`, `budget_request_m2`, `budget_request_m3`, `budget_request_m4`, `budget_request_m5`, `budget_request_m6`, `budget_request_m7`, `budget_request_m8`, `budget_request_m9`, `budget_request_m10`, `budget_request_m11`, `budget_request_m12`, `budget_approve_m1`, `budget_approve_m2`, `budget_approve_m3`, `budget_approve_m4`, `budget_approve_m5`, `budget_approve_m6`, `budget_approve_m7`, `budget_approve_m8`, `budget_approve_m9`, `budget_approve_m10`, `budget_approve_m11`, `budget_approve_m12`, `budget_usage`, `modified_date`, `modified_by`, `isFirstApprove`) VALUES
(78, 1, 2558, '', '', '', '', '', '', '300000', '', '', '', '', '', '300000', '', '', '', '', '', '', '', '', '', '', '', NULL, '2017-06-03', 3, NULL),
(79, 1, 2559, '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', NULL, '2017-06-03', 3, NULL),
(80, 1, 2560, '', '', '', '', '', '', '', '', '', '', '', '800000', '', '', '', '', '', '', '', '', '', '', '', '800000', NULL, '2017-06-03', 3, NULL);

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

--
-- Dumping data for table `strategic`
--

INSERT INTO `strategic` (`stra_id`, `stra_name`, `modified_date`, `modified_by`) VALUES
(1, 'ด้านการพัฒนาเทคโนโลยีสารสนเทศของบริษัท', '2017-05-03', 1),
(2, 'ด้านการพัฒนาระบบงานสารสนเทศและฐานข้อมูล', '2017-05-03', 1),
(3, 'ด้านการพัฒนาระบบคอมพิวเตอร์และงานสนับสนุนการบริการ', '2017-05-03', 1),
(4, 'ด้านการพัฒนาระบบเครือข่ายและการสื่อสารข้อมูล', '2017-05-03', 1),
(5, 'ด้านการพัฒนาระบบสนับสนุนระบบงานสารสนเทศ', '2017-05-03', 1),
(6, 'ด้านการพัฒนาความมั่นคงปลอดภัยเทคโนโลยีสารสนเทศ', '2017-05-03', 1),
(7, 'ด้านการพัฒนาศักยภาพบุคลากรด้านเทคโนโลยีสารสนเทศ', '2017-05-03', 1);

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

--
-- Dumping data for table `task`
--

INSERT INTO `task` (`task_id`, `task_name`, `modified_date`, `modified_by`) VALUES
(1, 'จัดงานเลี้ยงอำลาผู้อำนวยการ', '2017-04-27', 1),
(2, 'เลี้ยงอาหารวันเด็ก', '2017-04-27', 1),
(3, 'ฝึกอบรมหนักงานใหม่', '2017-04-27', 1);

-- --------------------------------------------------------

--
-- Table structure for table `task_assign`
--

CREATE TABLE `task_assign` (
  `taska_id` int(11) NOT NULL,
  `task_id` int(11) NOT NULL,
  `proj_id` int(11) NOT NULL,
  `task_userid` int(11) NOT NULL,
  `taska_assign_date` date NOT NULL,
  `taska_target_date` date NOT NULL,
  `modified_date` date NOT NULL,
  `modified_by` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `task_assign`
--

INSERT INTO `task_assign` (`taska_id`, `task_id`, `proj_id`, `task_userid`, `taska_assign_date`, `taska_target_date`, `modified_date`, `modified_by`) VALUES
(10, 3, 3, 1, '2017-04-05', '2017-04-20', '2017-04-28', 1),
(12, 1, 2, 1, '2017-04-07', '2017-04-28', '2017-04-28', 1),
(13, 1, 1, 1, '2017-04-04', '2017-04-11', '2017-04-28', 1),
(14, 1, 4, 1, '2017-04-01', '2017-04-30', '2017-04-28', 1),
(15, 3, 5, 1, '2017-04-04', '2017-04-21', '2017-04-28', 1),
(16, 1, 1, 1, '2017-04-19', '2017-04-27', '2017-04-28', 1),
(17, 1, 6, 1, '2017-04-05', '2017-04-26', '2017-04-28', 1),
(18, 2, 1, 1, '2017-04-10', '2017-04-03', '2017-04-29', 1),
(19, 3, 5, 3, '2017-04-01', '2017-04-30', '2017-04-30', 1),
(20, 3, 6, 2, '2017-04-03', '2017-04-26', '2017-04-30', 1),
(21, 2, 6, 3, '2017-04-06', '2017-04-18', '2017-04-30', 1),
(22, 1, 1, 2, '2017-05-02', '2017-05-11', '2017-05-29', 1);

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
-- Dumping data for table `task_work`
--

INSERT INTO `task_work` (`taskw_id`, `taskw_detail`, `taskw_manday`, `taska_id`, `taskw_date`, `modified_date`, `modified_by`) VALUES
(4, 'xxxx', 1, 13, '2017-04-28', '2017-04-28', 1),
(11, 'xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx', 2, 14, '2017-04-30', '2017-05-03', 1),
(12, 'ติดต่อร้านโต๊ะจีน', 2, 12, '2017-04-05', '2017-05-03', 1),
(13, 'จ้างร้านดอกไม้สดเพื่อใช้ตกแต่งงาน', 6, 12, '2017-04-20', '2017-05-03', 1),
(14, '1', 1, 17, '2017-04-13', '2017-04-30', 1),
(15, 'ติดต่อร้านขายอาหาร', 3, 12, '2017-05-10', '2017-05-03', 1),
(17, 'ดำเนินการให้เรียบร้อยแล้วครับ', 8, 17, '2017-04-06', '2017-05-03', 1),
(18, 'zzzzz', 8, 17, '2017-05-09', '2017-05-03', 1);

--
-- Indexes for dumped tables
--

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
-- Indexes for table `project_budget`
--
ALTER TABLE `project_budget`
  ADD PRIMARY KEY (`prob_id`);

--
-- Indexes for table `project_expense`
--
ALTER TABLE `project_expense`
  ADD PRIMARY KEY (`exp_id`);

--
-- Indexes for table `project_history`
--
ALTER TABLE `project_history`
  ADD PRIMARY KEY (`his_id`);

--
-- Indexes for table `project_strategic`
--
ALTER TABLE `project_strategic`
  ADD PRIMARY KEY (`projs_id`);

--
-- Indexes for table `project_type`
--
ALTER TABLE `project_type`
  ADD PRIMARY KEY (`prot_id`);

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
-- AUTO_INCREMENT for table `budget_plan`
--
ALTER TABLE `budget_plan`
  MODIFY `budp_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `config`
--
ALTER TABLE `config`
  MODIFY `conf_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
--
-- AUTO_INCREMENT for table `department`
--
ALTER TABLE `department`
  MODIFY `dep_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `emp_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `plan`
--
ALTER TABLE `plan`
  MODIFY `plan_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `project`
--
ALTER TABLE `project`
  MODIFY `proj_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;
--
-- AUTO_INCREMENT for table `project_budget`
--
ALTER TABLE `project_budget`
  MODIFY `prob_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `project_expense`
--
ALTER TABLE `project_expense`
  MODIFY `exp_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `project_history`
--
ALTER TABLE `project_history`
  MODIFY `his_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=72;
--
-- AUTO_INCREMENT for table `project_strategic`
--
ALTER TABLE `project_strategic`
  MODIFY `projs_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `project_type`
--
ALTER TABLE `project_type`
  MODIFY `prot_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT for table `project_working`
--
ALTER TABLE `project_working`
  MODIFY `projw_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=81;
--
-- AUTO_INCREMENT for table `strategic`
--
ALTER TABLE `strategic`
  MODIFY `stra_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `task`
--
ALTER TABLE `task`
  MODIFY `task_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `task_assign`
--
ALTER TABLE `task_assign`
  MODIFY `taska_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;
--
-- AUTO_INCREMENT for table `task_work`
--
ALTER TABLE `task_work`
  MODIFY `taskw_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
