-- Example Agents
INSERT INTO `AGENT` (`ip_address`,`port`,`capabilities`,`status`,`LAST_CHECKED`) VALUES ("10.200.2.231", "8080",NULL,0, NULL);
INSERT INTO `AGENT` (`ip_address`,`port`,`capabilities`,`status`,`LAST_CHECKED`) VALUES ("10.200.2.231", "5555",NULL,0, NULL);
INSERT INTO `AGENT` (`ip_address`,`port`,`capabilities`,`status`,`LAST_CHECKED`) VALUES ("10.200.1.146", "8080",NULL,0, NULL);

INSERT INTO `AGENT` (`ip_address`,`port`,`capabilities`,`status`,`LAST_CHECKED`) VALUES ("127.0.0.1", "8082",NULL,0, NULL);
INSERT INTO `AGENT` (`ip_address`,`port`,`capabilities`,`status`,`LAST_CHECKED`) VALUES ("127.0.0.1", "8083",NULL,0, NULL);
INSERT INTO `AGENT` (`ip_address`,`port`,`capabilities`,`status`,`LAST_CHECKED`) VALUES ("127.0.0.1", "8084",NULL,0, NULL);

-- Example Users
INSERT INTO `USERDATA` (`email`,`password`,`name`,`registered_since`,`role`) VALUES ("john.doe@example.com", "john", "doe",CURDATE(), 1);
INSERT INTO `USERDATA` (`email`,`password`,`name`,`registered_since`,`role`) VALUES ("j.d@example.com", "doe", "john",CURDATE(), 1);
INSERT INTO `USERDATA` (`email`,`password`,`name`,`registered_since`,`role`) VALUES ("doe.john@example.com", "test", "tester",CURDATE(), 1);

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
INSERT INTO `PIPELINE` (`name`, `status`) VALUES ("Pipe_1", "failed");
INSERT INTO `PIPELINE` (`name`, `status`) VALUES ("Pipe_2", "finished");

-- Pipeline to projects
INSERT INTO `PIPELINE_PROJECT` (`pipeline_id`, `project_id`) VALUES (1, 1);
INSERT INTO `PIPELINE_PROJECT` (`pipeline_id`, `project_id`) VALUES (2, 3);


-- Example pipeline steps
INSERT INTO `PIPELINE_STEP` (`agent_id`, `step_number`, `pipeline_id`, `step_type`, `content`) VALUES (1, 0, 1, 0, "input.csv");
INSERT INTO `PIPELINE_STEP` (`agent_id`, `step_number`, `pipeline_id`, `step_type`, `content`) VALUES (1, 1, 1, 2, "avg");
INSERT INTO `PIPELINE_STEP` (`agent_id`, `step_number`, `pipeline_id`, `step_type`, `content`) VALUES (1, 2, 1, 1, "out.csv");



-- Example Pipeline Succession
INSERT INTO `PIPELINE_STEP_SUCCESSORS` (`successor_id`, `step_id`) VALUES (2, 1);
INSERT INTO `PIPELINE_STEP_SUCCESSORS` (`successor_id`, `step_id`) VALUES (3, 2);

-- Configuration
INSERT INTO `CONFIGURATION` (`description`, `config_key`, `config_value`) VALUES ("Maximum number of agents, a pipeline can be distributed on", "BITFLOW_MAX_AGENT_DIST", "1");
INSERT INTO `CONFIGURATION` (`description`, `config_key`, `config_value`) VALUES ("Interval for checking the agents availability (in seconds)", "BITFLOW_MONITOR_INTERVAL", "30");
