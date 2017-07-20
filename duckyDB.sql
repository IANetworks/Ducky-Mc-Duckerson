--
-- File generated with SQLiteStudio v3.1.1 on Thu Jul 20 19:16:19 2017
--
-- Text encoding used: UTF-8
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: permission_commands
DROP TABLE IF EXISTS permission_commands;

CREATE TABLE permission_commands (
    command_id INTEGER PRIMARY KEY
                       NOT NULL,
    guild_id   INTEGER NOT NULL,
    level_id   INTEGER NOT NULL
);


-- Table: permission_group
DROP TABLE IF EXISTS permission_group;

CREATE TABLE permission_group (
    id           INTEGER PRIMARY KEY
                         NOT NULL,
    guild_id     INTEGER NOT NULL,
    level_id     INTEGER NOT NULL,
    user_role_id INTEGER NOT NULL,
    is_user      INTEGER NOT NULL
);


-- Table: permission_level
DROP TABLE IF EXISTS permission_level;

CREATE TABLE permission_level (
    level_id   INTEGER NOT NULL,
    guild_id           NOT NULL,
    level_name STRING  NOT NULL
);


-- Table: self_roles
DROP TABLE IF EXISTS self_roles;

CREATE TABLE self_roles (
    guild_id      INTEGER NOT NULL,
    role_id       INTEGER NOT NULL,
    role_group_id INTEGER NOT NULL,
    exclusive_on  BOOLEAN NOT NULL
                          DEFAULT (0) 
);


-- Table: user_profile
DROP TABLE IF EXISTS user_profile;

CREATE TABLE user_profile (
    user_db_id         PRIMARY KEY
                       NOT NULL,
    user_id    INTEGER NOT NULL,
    guild_id   INTEGER NOT NULL,
    balance    INTEGER NOT NULL
                       DEFAULT (0),
    points     INTEGER NOT NULL
                       DEFAULT (0),
    rank       INTEGER NOT NULL
                       DEFAULT (0),
    flipped    INTEGER NOT NULL
                       DEFAULT (0),
    unflipped  INTEGER NOT NULL
                       DEFAULT (0),
    level      INTEGER NOT NULL
                       DEFAULT (0) 
);


-- Table: variables
DROP TABLE IF EXISTS variables;

CREATE TABLE variables (
    guild_id         INTEGER NOT NULL
                             UNIQUE
                             PRIMARY KEY,
    logging_on       INTEGER NOT NULL
                             DEFAULT (0),
    logging_channel  STRING,
    prefix           STRING  DEFAULT ('!'),
    greet_on         INTEGER NOT NULL
                             DEFAULT (0),
    greeting_msg     STRING,
    greeting_channel STRING
);


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
