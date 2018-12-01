Create Table AGENT(
	ID INT UNSIGNED NOT NULL AUTO_INCREMENT,
	IP_ADDRESS VARCHAR(128) NOT NULL,
	PORT INT UNSIGNED NOT NULL,
	STATUS INT NULL,
	LAST_CHECKED DATETIME,
	PRIMARY KEY (ID),
	UNIQUE(IP_ADDRESS,PORT)
);

Create Table USERDATA(
	ID INT UNSIGNED NOT NULL AUTO_INCREMENT,
	EMAIL VARCHAR(128) NOT NULL,
	PASSWORD VARCHAR(128) NOT NULL,
	NAME VARCHAR(128) NOT NULL,
	REGISTERED_SINCE DATETIME NOT NULL,
	ROLE INT UNSIGNED NOT NULL,
	PRIMARY KEY (ID),
	UNIQUE (NAME)
);

-- Each project has one 'admin user', the one who created the project
Create Table PROJECT(
	ID INT UNSIGNED NOT NULL AUTO_INCREMENT,
	NAME VARCHAR(256) NOT NULL,
	CREATED_AT DATETIME NOT NULL,
	CREATE_USER_ID INT UNSIGNED NOT NULL,
	PRIMARY KEY (ID),
	FOREIGN KEY (CREATE_USER_ID) REFERENCES USERDATA(ID)
);

-- In addition the 'admin user' can add other users to this project, modeled by this JOIN table
-- ManyToMany
Create Table USER_PROJECT(
	ID INT UNSIGNED NOT NULL AUTO_INCREMENT,
	USER_ID INT UNSIGNED NOT NULL,
	PROJECT_ID INT UNSIGNED NOT NULL,
	PRIMARY KEY (ID),
	FOREIGN KEY (PROJECT_ID) REFERENCES PROJECT(ID),
	FOREIGN KEY (USER_ID) REFERENCES USERDATA(ID),
	UNIQUE(USER_ID, PROJECT_ID)
);

-- Status as described in bitflow-process-agent REST-API - GET /pipeline/:id
-- PROJECT_ID INT UNSIGNED NOT NULL, -- OneToMany
Create Table PIPELINE(
	ID INT UNSIGNED NOT NULL AUTO_INCREMENT,
	NAME VARCHAR(256),
	STATUS VARCHAR(32),
	LAST_CHANGED DATETIME,
	PRIMARY KEY (ID),
	UNIQUE (Name)
);

-- ManyToMany
Create Table PIPELINE_PROJECT(
	ID INT UNSIGNED NOT NULL AUTO_INCREMENT,
	PROJECT_ID INT UNSIGNED NOT NULL,
	PIPELINE_ID INT UNSIGNED NOT NULL,
	PRIMARY KEY (ID),
	FOREIGN KEY (PROJECT_ID) REFERENCES PROJECT(ID),
	FOREIGN KEY (PIPELINE_ID) REFERENCES PIPELINE(ID),
	UNIQUE(PIPELINE_ID, PROJECT_ID)
);

-- This table models the pipeline steps, containing one specified algorithm
Create Table PIPELINE_STEP(
	ID INT UNSIGNED NOT NULL AUTO_INCREMENT,
	AGENT_ID INT UNSIGNED,
	STEP_NUMBER INT UNSIGNED NOT NULL,
	PIPELINE_ID INT UNSIGNED,
	STATUS VARCHAR(32),
	STEP_TYPE INT UNSIGNED NOT NULL,
	CONTENT VARCHAR(256) NOT NULL,
	PRIMARY KEY (ID),
	FOREIGN KEY (PIPELINE_ID) REFERENCES PIPELINE(ID),
	FOREIGN KEY (AGENT_ID) REFERENCES AGENT(ID)
);

Create Table PIPELINE_STEP_PARAM(
	ID INT UNSIGNED NOT NULL AUTO_INCREMENT,
	PIPELINE_STEP_ID INT UNSIGNED,
	PARAM_NAME VARCHAR(128) NOT NULL,
	PARAM_VALUE VARCHAR(128) NOT NULL,
	PRIMARY KEY (ID),
	FOREIGN KEY (PIPELINE_STEP_ID) REFERENCES PIPELINE_STEP(ID)
);

-- We model the pipeline by making each pipeline step aware of its successor steps, that's what this table is about
Create Table PIPELINE_STEP_SUCCESSORS(
	ID INT UNSIGNED NOT NULL AUTO_INCREMENT,
	SUCCESSOR_ID INT UNSIGNED NOT NULL,
	STEP_ID INT UNSIGNED NOT NULL,
	PRIMARY KEY (ID),
	FOREIGN KEY (SUCCESSOR_ID) REFERENCES PIPELINE_STEP(ID),
	FOREIGN KEY (STEP_ID) REFERENCES PIPELINE_STEP(ID),
	UNIQUE (STEP_ID, SUCCESSOR_ID)
);

Create Table CAPABILITY (
	ID INT UNSIGNED NOT NULL AUTO_INCREMENT,
	NAME VARCHAR(64) NOT NULL,
	IS_FORK BOOLEAN NOT NULL,
	DESCRIPTION VARCHAR(512),
	REQUIRED_PARAMS VARCHAR(128),
	OPTIONAL_PARAMS VARCHAR(128),
	PRIMARY KEY (ID)
);

CREATE Table AGENT_CAPABILITY (
	AGENT_ID INT UNSIGNED NOT NULL,
	CAPABILITY_ID INT UNSIGNED NOT NULL,
	PRIMARY KEY (AGENT_ID, CAPABILITY_ID),
	FOREIGN KEY (AGENT_ID) REFERENCES AGENT(ID),
	FOREIGN KEY (CAPABILITY_ID) REFERENCES CAPABILITY(ID)
);

CREATE Table PIPELINE_HISTORY (
	ID INT UNSIGNED NOT NULL AUTO_INCREMENT,
	PIPELINE_ID INT UNSIGNED NOT NULL,
	STATUS VARCHAR(32),
	STARTED_AT DATETIME,
	FINISHED_AT DATETIME,
	SCRIPT TEXT,
	PRIMARY KEY (ID),
	FOREIGN KEY (PIPELINE_ID) REFERENCES PIPELINE(ID)
);

Create Table CONFIGURATION(
	ID INT UNSIGNED NOT NULL AUTO_INCREMENT,
	DESCRIPTION VARCHAR(128),
	CONFIG_KEY VARCHAR(64) NOT NULL,
	CONFIG_VALUE VARCHAR(64) NOT NULL,
	PRIMARY KEY (ID),
	UNIQUE (CONFIG_KEY)
);

-- Configuration
INSERT INTO `CONFIGURATION` (`description`, `config_key`, `config_value`) VALUES ("Basically the port range for the proxy tcp sockets.", "BITFLOW_PROXY_PORT_RETRY", "100");
INSERT INTO `CONFIGURATION` (`description`, `config_key`, `config_value`) VALUES ("The port on wich to begin to deploy the proxy tcp sockets", "BITFLOW_PROXY_PORT", "60001");
INSERT INTO `CONFIGURATION` (`description`, `config_key`, `config_value`) VALUES ("Maximum number of agents, a pipeline can be distributed on", "BITFLOW_MAX_AGENT_DIST", "1");
INSERT INTO `CONFIGURATION` (`description`, `config_key`, `config_value`) VALUES ("Interval for checking the agents availability (in seconds)", "BITFLOW_MONITOR_INTERVAL", "30");
INSERT INTO `CONFIGURATION` (`description`, `config_key`, `config_value`) VALUES ("Interval for checking the status of a pipeline exectution", "BITFLOW_PIPELINE_MONITOR_INTERVAL", "30");