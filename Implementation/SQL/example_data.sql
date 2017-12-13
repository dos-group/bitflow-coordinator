-- Example Agents
INSERT INTO `AGENT` (`ip_address`,`port`,`capabilities`,`status`,`LAST_CHECKED`) VALUES ("10.200.2.231", "8080",NULL,0, NULL);
INSERT INTO `AGENT` (`ip_address`,`port`,`capabilities`,`status`,`LAST_CHECKED`) VALUES ("10.200.2.231", "5555",NULL,0, NULL);

-- Example Users
INSERT INTO `USERDATA` (`email`,`password`,`name`,`registered_since`) VALUES ("john.doe@example.com", "john", "doe",CURDATE());
INSERT INTO `USERDATA` (`email`,`password`,`name`,`registered_since`) VALUES ("j.d@example.com", "doe", "john",CURDATE());
INSERT INTO `USERDATA` (`email`,`password`,`name`,`registered_since`) VALUES ("doe.john@example.com", "test", "tester",CURDATE());

-- Example Project
INSERT INTO `PROJECT` (`name`, `created_at`,`create_user_id`) VALUES ("ExampleProject1", CURDATE(), 1);
INSERT INTO `PROJECT` (`name`, `created_at`,`create_user_id`) VALUES ("ExampleProject2", CURDATE(), 2);

-- User Project Data 
INSERT INTO `USER_PROJECT` (`user_id`,`project_id`) VALUES (1,1);
INSERT INTO `USER_PROJECT` (`user_id`,`project_id`) VALUES (2,1);
INSERT INTO `USER_PROJECT` (`user_id`,`project_id`) VALUES (3,1);

INSERT INTO `USER_PROJECT` (`user_id`,`project_id`) VALUES (1,2);
INSERT INTO `USER_PROJECT` (`user_id`,`project_id`) VALUES (2,2);
INSERT INTO `USER_PROJECT` (`user_id`,`project_id`) VALUES (3,2);

-- Example Pipelines - do not exist
INSERT INTO `PIPELINE` (`project_id`, `status`, `script`) VALUES (1, "failed", "Script1");
INSERT INTO `PIPELINE` (`project_id`, `status`, `script`) VALUES (2, "running", "Script2");

-- Example pipeline steps
INSERT INTO `PIPELINE_STEP` (`agent_id`, `step_number`, `pipeline_id`, `script`) VALUES (1, 1, 2, "Script11");
INSERT INTO `PIPELINE_STEP` (`agent_id`, `step_number`, `pipeline_id`, `script`) VALUES (2, 2, 2, "Script12");

-- Example Pipeline Succession
INSERT INTO `PIPELINE_STEP_SUCCESSORS` (`successor_id`, `step_id`) VALUES (1, 2);

-- Configuration
INSERT INTO `CONFIGURATION` (`description`, `config_key`, `config_value`) VALUES ("Interval for checking the agents availability (in seconds)", "BITFLOW_MONITOR_INTERVAL", "30");