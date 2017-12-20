PRAGMA auto_vacuum = 2; -- INCREMENTAL
PRAGMA secure_delete = on;
PRAGMA foreign_keys = off;

-- Table: variables
DROP TABLE IF EXISTS variables;
CREATE TABLE `variables` (
	`guild_id`	INTEGER NOT NULL UNIQUE,
	`logging_on`	INTEGER NOT NULL DEFAULT 0,
	`logging_channel`	TEXT,
	`prefix`	TEXT DEFAULT '!',
	`greet_on`	INTEGER NOT NULL DEFAULT 0,
	`greeting_msg`	TEXT,
	`greeting_channel`	TEXT,
	PRIMARY KEY(`guild_id`)
);

-- Table: user_profile
DROP TABLE IF EXISTS user_profile;
CREATE TABLE `user_profile` (
	`user_id`	INTEGER NOT NULL,
	`guild_id`	INTEGER NOT NULL,
	`balance`	INTEGER NOT NULL DEFAULT 0,
	`points`	INTEGER NOT NULL DEFAULT 0,
	`rank`	INTEGER NOT NULL DEFAULT 0,
	`flipped`	INTEGER NOT NULL DEFAULT 0,
	`unflipped`	INTEGER NOT NULL DEFAULT 0,
	`level`	INTEGER NOT NULL DEFAULT 0,
	PRIMARY KEY(`user_id`,`guild_id`)
);

-- Table: self_roles
DROP TABLE IF EXISTS self_roles;
CREATE TABLE `self_roles` (
	`role_id`	INTEGER NOT NULL,
	`guild_id`	INTEGER NOT NULL,
	`role_group_id`	INTEGER NOT NULL,
	`exclusive_on`	INTEGER DEFAULT 0,
	PRIMARY KEY(`role_id`)
);

-- Table: permission_level
DROP TABLE IF EXISTS permission_level;
CREATE TABLE `permission_level` (
	`level_id`	INTEGER NOT NULL,
	`guild_id`	INTEGER NOT NULL,
	`level_name`	TEXT NOT NULL,
	PRIMARY KEY(`level_id`)
);

-- Table: permission_group
DROP TABLE IF EXISTS permission_group;
CREATE TABLE `permission_group` (
	`id`	INTEGER NOT NULL,
	`guild_id`	INTEGER NOT NULL,
	`level_id`	INTEGER NOT NULL,
	`user_role_id`	INTEGER NOT NULL,
	`is_user`	INTEGER NOT NULL,
	PRIMARY KEY(`id`)
);

-- Table: permission_commands
DROP TABLE IF EXISTS permission_commands;
CREATE TABLE `permission_commands` (
	`command_id`	INTEGER NOT NULL,
	`guild_id`	INTEGER NOT NULL,
	`level_id`	INTEGER NOT NULL,
	PRIMARY KEY(`command_id`)
);

PRAGMA foreign_keys = on;
