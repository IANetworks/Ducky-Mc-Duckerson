--
-- File generated with SQLiteStudio v3.1.1 on Sat Aug 12 20:18:34 2017
--
-- Text encoding used: System
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


-- Table: theme
DROP TABLE IF EXISTS theme;

CREATE TABLE theme (
    id_key        BIGINT       NOT NULL
                               PRIMARY KEY,
    theme_id      BIGINT       NOT NULL,
    theme_name_id VARCHAR (22) NOT NULL,
    theme_text    TEXT         NOT NULL
);


-- Table: theme_defaults
DROP TABLE IF EXISTS theme_defaults;

CREATE TABLE theme_defaults (
    id_key            BIGINT        NOT NULL
                                    PRIMARY KEY,
    theme_name_id     VARCHAR (22)  NOT NULL,
    defaults          TEXT,
    cat_page          INTEGER       NOT NULL
                                    DEFAULT 0,
    theme_full_name   VARCHAR (100) NOT NULL,
    theme_description TEXT          NOT NULL
);


-- Table: theme_detail
DROP TABLE IF EXISTS theme_detail;

CREATE TABLE theme_detail (
    id_key        BIGINT       NOT NULL
                               PRIMARY KEY,
    theme_name    VARCHAR (30) NOT NULL,
    theme_disc    TEXT         NOT NULL,
    theme_author  VARCHAR (60) NOT NULL,
    theme_created TIMESTAMP    NOT NULL,
    theme_modifed TIMESTAMP    NOT NULL
                               DEFAULT CURRENT_TIMESTAMP,
    played_count  BIGINT       NOT NULL
                               DEFAULT 0,
    loaded        INTEGER      NOT NULL
                               DEFAULT 0
);


-- Table: user_profile
DROP TABLE IF EXISTS user_profile;

CREATE TABLE user_profile (
    guild_id     INTEGER NOT NULL,
    user_id      INTEGER NOT NULL,
    balance      INTEGER NOT NULL
                         DEFAULT (0),
    points       INTEGER NOT NULL
                         DEFAULT (0),
    rank         INTEGER NOT NULL
                         DEFAULT (0),
    flipped      INTEGER NOT NULL
                         DEFAULT (0),
    unflipped    INTEGER NOT NULL
                         DEFAULT (0),
    level        INTEGER NOT NULL
                         DEFAULT (0),
    werewolf_win INTEGER NOT NULL
                         DEFAULT (0),
    PRIMARY KEY (
        guild_id,
        user_id
    )
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
    greeting_channel STRING,
    game_channel     STRING,
    werewolf_on      BOOLEAN NOT NULL
                             DEFAULT (1) 
);


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
