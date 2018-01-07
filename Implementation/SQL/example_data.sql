-- Example Agents
INSERT INTO `AGENT` (`ip_address`,`port`,`capabilities`,`status`,`LAST_CHECKED`) VALUES ("10.200.2.231", "8080",NULL,0, NULL);
INSERT INTO `AGENT` (`ip_address`,`port`,`capabilities`,`status`,`LAST_CHECKED`) VALUES ("10.200.2.231", "5555",NULL,0, NULL);
INSERT INTO `AGENT` (`ip_address`,`port`,`capabilities`,`status`,`LAST_CHECKED`) VALUES ("10.200.1.146", "8080",NULL,0, NULL);

-- Example Users
INSERT INTO `USERDATA` (`email`,`password`,`name`,`registered_since`) VALUES ("john.doe@example.com", "john", "doe",CURDATE());
INSERT INTO `USERDATA` (`email`,`password`,`name`,`registered_since`) VALUES ("j.d@example.com", "doe", "john",CURDATE());
INSERT INTO `USERDATA` (`email`,`password`,`name`,`registered_since`) VALUES ("doe.john@example.com", "test", "tester",CURDATE());

-- Example Project
INSERT INTO `PROJECT` (`name`, `created_at`,`create_user_id`) VALUES ("ExampleProject1", CURDATE(), 1);
INSERT INTO `PROJECT` (`name`, `created_at`,`create_user_id`) VALUES ("ExampleProject2", CURDATE(), 2);
INSERT INTO `PROJECT` (`name`, `created_at`,`create_user_id`) VALUES ("ExampleProject3", CURDATE(), 3);

-- User Project Data 
INSERT INTO `USER_PROJECT` (`user_id`,`project_id`) VALUES (1,1);
INSERT INTO `USER_PROJECT` (`user_id`,`project_id`) VALUES (2,1);
INSERT INTO `USER_PROJECT` (`user_id`,`project_id`) VALUES (3,1);

INSERT INTO `USER_PROJECT` (`user_id`,`project_id`) VALUES (1,2);
INSERT INTO `USER_PROJECT` (`user_id`,`project_id`) VALUES (2,2);
INSERT INTO `USER_PROJECT` (`user_id`,`project_id`) VALUES (3,2);

INSERT INTO `USER_PROJECT` (`user_id`,`project_id`) VALUES (2,3);
INSERT INTO `USER_PROJECT` (`user_id`,`project_id`) VALUES (3,3);

-- Example Pipelines
INSERT INTO `PIPELINE` (`project_id`, `status`, `script`) VALUES (1, "failed", "Test0");
INSERT INTO `PIPELINE` (`project_id`, `status`, `script`) VALUES (3, "finished", "Test1");

-- Example pipeline steps
INSERT INTO `PIPELINE_STEP` (`agent_id`, `step_number`, `pipeline_id`, `script`) VALUES (1, 0, 1, "input.csv -> avg() -> out.csv\r");
INSERT INTO `PIPELINE_STEP` (`agent_id`, `step_number`, `pipeline_id`, `script`) VALUES (1, 1, 1, "input.csv -> avg() -> out.csv\r");
INSERT INTO `PIPELINE_STEP` (`agent_id`, `step_number`, `pipeline_id`, `script`) VALUES (1, 2, 1, "in.csv -> avg() -> out.csv\r");
INSERT INTO `PIPELINE_STEP` (`agent_id`, `step_number`, `pipeline_id`, `script`) VALUES (1, 3, 2, "in.csv -> avg() -> out.csv\r");

-- Example Pipeline Succession
INSERT INTO `PIPELINE_STEP_SUCCESSORS` (`successor_id`, `step_id`) VALUES (1, 2);

-- Configuration
INSERT INTO `CONFIGURATION` (`description`, `config_key`, `config_value`) VALUES ("Interval for checking the agents availability (in seconds)", "BITFLOW_MONITOR_INTERVAL", "30");
