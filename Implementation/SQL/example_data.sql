
INSERT INTO `AGENT` (`id`,`ip_address`,`port`,`capabilities`,`status`,`LAST_CHECKED`) VALUES (NULL, "10.200.2.231", "8080",NULL,0, NULL);
INSERT INTO `USERDATA` (`id`,`email`,`password`,`name`,`registered_since`) VALUES (NULL, "john.doe@example.com", "John Doe", "john",'2017-10-01 00:00:00');
INSERT INTO `PROJECT` (`id`,`name`, `created_at`,`create_user_id`) VALUES (NULL, "ExampleProject", '2017-10-01', 1);
INSERT INTO `USER_PROJECT` (`id`,`user_id`,`project_id`) VALUES (NULL, 1,1);
INSERT INTO `PIPELINE` (`id`,`agent_id`,`id_on_agent`,`project_id`, `status`, `script`, `last_changed`) VALUES (1, 1, 1, 1, "failed", NULL, '2017-11-10');

