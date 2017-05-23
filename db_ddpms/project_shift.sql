DROP TABLE project_shift;

CREATE TABLE `project_shift` (
  `projs_id` int(11) NOT NULL,
  `proj_id` int(11) NOT NULL,
  `projs_reason` varchar(255) DEFAULT NULL,
  `projs_plan_date` date DEFAULT NULL COMMENT 'เลื่อนไปวันไหน',
  `modified_date` date NOT NULL,
  `modified_by` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `project_shift`
  ADD PRIMARY KEY (`projs_id`);

ALTER TABLE `project_shift`
  MODIFY `projs_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
