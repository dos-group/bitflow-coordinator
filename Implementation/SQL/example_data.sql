-- Example Agents
INSERT INTO `AGENT` (`id`,`ip_address`,`port`,`capabilities`,`status`,`LAST_CHECKED`) VALUES (NULL, "10.200.2.231", "8080",NULL,0, NULL);
INSERT INTO `AGENT` (`id`,`ip_address`,`port`,`capabilities`,`status`,`LAST_CHECKED`) VALUES (NULL, "10.200.2.231", "5555",NULL,0, NULL);

-- Example Users
INSERT INTO `USERDATA` (`id`,`email`,`password`,`name`,`registered_since`) VALUES (NULL, "john.doe@example.com", "john", "doe",'2017-10-01');
INSERT INTO `USERDATA` (`id`,`email`,`password`,`name`,`registered_since`) VALUES (NULL, "j.d@example.com", "doe", "doe",'2017-10-01'); -- provoke error
INSERT INTO `USERDATA` (`id`,`email`,`password`,`name`,`registered_since`) VALUES (NULL, "doe.john@example.com", "doe", "john",'2017-10-01');

-- Example Project
INSERT INTO `PROJECT` (`id`,`name`, `created_at`,`create_user_id`) VALUES (NULL, "ExampleProject1", '2017-10-01', 1);
INSERT INTO `PROJECT` (`id`,`name`, `created_at`,`create_user_id`) VALUES (NULL, "ExampleProject2", '2017-10-01', 2);

-- User Project Data 
INSERT INTO `USER_PROJECT` (`id`,`user_id`,`project_id`) VALUES (NULL, 1,1);
INSERT INTO `USER_PROJECT` (`id`,`user_id`,`project_id`) VALUES (NULL, 1,1); -- provoke error
INSERT INTO `USER_PROJECT` (`id`,`user_id`,`project_id`) VALUES (NULL, 1,2);
INSERT INTO `USER_PROJECT` (`id`,`user_id`,`project_id`) VALUES (NULL, 2,2);

-- Example Pipelines - do not exist
INSERT INTO `PIPELINE` (`id`,`agent_id`,`id_on_agent`,`project_id`, `status`, `script`, `step_number`, `last_changed`) VALUES (null, 1, 1, 1, "failed", NULL, 0, '2017-11-10');
INSERT INTO `PIPELINE` (`id`,`agent_id`,`id_on_agent`,`project_id`, `status`, `script`, `step_number`, `last_changed`) VALUES (null, 1, 2, 1, "failed", NULL, 1,'2017-11-10');
INSERT INTO `PIPELINE` (`id`,`agent_id`,`id_on_agent`,`project_id`, `status`, `script`, `step_number`, `last_changed`) VALUES (null, 1, 3, 1, "failed", NULL, 1, '2017-11-10');

-- Example Pipeline Succession
INSERT INTO `PIPELINE_SUCCESSION` (`id`,`PIPELINE_ID`,`SUCCESSOR_ID`) VALUES (NULL, 1, 2);
INSERT INTO `PIPELINE_SUCCESSION` (`id`,`PIPELINE_ID`,`SUCCESSOR_ID`) VALUES (NULL, 1, 2); -- provoke error
INSERT INTO `PIPELINE_SUCCESSION` (`id`,`PIPELINE_ID`,`SUCCESSOR_ID`) VALUES (NULL, 1, 3);
