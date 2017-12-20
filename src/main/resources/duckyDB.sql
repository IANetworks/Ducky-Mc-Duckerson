--
-- File generated with SQLiteStudio v3.1.1 on Tue Dec 19 21:14:20 2017
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

INSERT INTO permission_level (
                                 level_id,
                                 guild_id,
                                 level_name
                             )
                             VALUES (
                                 0,
                                 99903812700999680,
                                 'Bot Adminstrator'
                             );

INSERT INTO permission_level (
                                 level_id,
                                 guild_id,
                                 level_name
                             )
                             VALUES (
                                 1,
                                 99903812700999680,
                                 'Server Owner'
                             );

INSERT INTO permission_level (
                                 level_id,
                                 guild_id,
                                 level_name
                             )
                             VALUES (
                                 2,
                                 99903812700999680,
                                 'Assigned Adminstrator'
                             );

INSERT INTO permission_level (
                                 level_id,
                                 guild_id,
                                 level_name
                             )
                             VALUES (
                                 3,
                                 99903812700999680,
                                 'Assigned Moderator'
                             );

INSERT INTO permission_level (
                                 level_id,
                                 guild_id,
                                 level_name
                             )
                             VALUES (
                                 4,
                                 99903812700999680,
                                 'Assigned Operator'
                             );

INSERT INTO permission_level (
                                 level_id,
                                 guild_id,
                                 level_name
                             )
                             VALUES (
                                 999,
                                 99903812700999680,
                                 'User'
                             );

INSERT INTO permission_level (
                                 level_id,
                                 guild_id,
                                 level_name
                             )
                             VALUES (
                                 9999,
                                 99903812700999680,
                                 'Banned'
                             );


-- Table: rank_titles
DROP TABLE IF EXISTS rank_titles;

CREATE TABLE rank_titles (
    rank      INTEGER PRIMARY KEY
                      NOT NULL,
    rank_name STRING  NOT NULL,
    rank_exp  INTEGER
);

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            0,
                            'Landlubber',
                            20
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            1,
                            'Stowaway',
                            20
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            2,
                            'Cabin Person',
                            20
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            3,
                            'Scrub Lord',
                            20
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            4,
                            'Scrub',
                            20
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            5,
                            'Mascot',
                            20
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            6,
                            'Junior Apprentice',
                            40
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            7,
                            'Senior Apprentice',
                            40
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            8,
                            'Swabbie',
                            40
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            9,
                            'Crewman',
                            40
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            10,
                            'Able Crewman',
                            44
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            11,
                            'Senior Crewman',
                            49
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            12,
                            'Gunner',
                            54
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            13,
                            'Engineer',
                            60
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            14,
                            'Helmsman',
                            65
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            15,
                            'Pilot',
                            71
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            16,
                            'Privateer Officer',
                            77
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            17,
                            'Privateer Captain',
                            83
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            18,
                            'Privateer Admiral',
                            89
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            19,
                            'Recruit',
                            95
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            20,
                            'Grease-Monkey',
                            101
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            21,
                            'Red Shirt',
                            107
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            22,
                            'Oil-Gorilla',
                            114
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            23,
                            'Private 3rd Class',
                            120
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            24,
                            'Private 2nd Class',
                            126
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            25,
                            'Private 1st Class',
                            133
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            26,
                            'Merch--Harambe',
                            140
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            27,
                            'Petty Officer',
                            146
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            28,
                            'Chief Petty Officer',
                            153
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            29,
                            'Warrant Officer',
                            160
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            30,
                            'Midshipman',
                            167
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            31,
                            'Sub-Lieutenant',
                            173
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            32,
                            'Lieutenant',
                            180
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            33,
                            'Lieutenant-Commander',
                            187
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            34,
                            'Commander',
                            194
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            35,
                            'Captain',
                            202
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            36,
                            'Community Ambassador',
                            209
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            37,
                            'Commodore',
                            216
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            38,
                            'Commodore 64',
                            223
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            39,
                            'Vice-Rear Admiral',
                            231
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            40,
                            'Rear Admiral',
                            238
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            41,
                            'Vice-Admiral',
                            245
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            42,
                            'Admiral',
                            253
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            43,
                            'Community Ambassador Moderator',
                            260
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            44,
                            'Retired Admiral',
                            268
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            45,
                            'Force Adept',
                            275
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            46,
                            'Muse',
                            283
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            47,
                            'Local Voice',
                            291
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            48,
                            'Local Campaigner',
                            298
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            49,
                            'Local Council Candidate',
                            306
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            50,
                            'Local Councillor',
                            314
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            51,
                            'National Council Candidate',
                            322
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            52,
                            'Member of the Lower House',
                            330
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            53,
                            'Junior Shadow Minister',
                            338
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            54,
                            'Shadow Minister',
                            346
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            55,
                            'Junior Minister',
                            354
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            56,
                            'Minister',
                            362
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            57,
                            'Prime Minister',
                            370
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            58,
                            'Member of the Upper House',
                            378
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            59,
                            'After-Dinner Speaker',
                            386
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            60,
                            'Presidential Candidate',
                            394
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            61,
                            'President-Elect',
                            402
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            62,
                            'President',
                            411
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            63,
                            'Jedi/Sith',
                            419
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            64,
                            'Regional Conqueror',
                            427
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            65,
                            'Continental Conqueror',
                            436
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            66,
                            'World Conqueror',
                            444
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            67,
                            'World Leader',
                            452
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            68,
                            'Moon Lord',
                            461
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            69,
                            'System Lord',
                            469
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            70,
                            'Supreme System Lord',
                            478
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            71,
                            'Federation President',
                            486
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            72,
                            'Supreme Chancellor and the Senate',
                            495
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            73,
                            'Galactic Lord',
                            504
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            74,
                            'Master of the Universe',
                            512
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            75,
                            'Time Travelling Companion',
                            521
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            76,
                            'Time Travelling Conqueror',
                            530
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            77,
                            'Time Lord',
                            538
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            78,
                            'Grand Master of Time and Space',
                            547
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            79,
                            'Interdenominational Traveller',
                            556
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            80,
                            'Interdenominational Leader',
                            565
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            81,
                            'Demi-Angel/Demi-Demon',
                            573
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            82,
                            'Undisputed Ruler of All Time and Space',
                            582
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            83,
                            'Angel/Demon',
                            591
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            84,
                            'Rebel Against Heaven',
                            600
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            85,
                            'Demi-God/Goddess',
                            609
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            86,
                            'Minor Deity',
                            618
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            87,
                            'Arch-Angel/Demon-Knight',
                            627
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            88,
                            'Mid-Tier Deity',
                            636
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            89,
                            'Major Deity',
                            645
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            90,
                            'Q',
                            654
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            91,
                            'Royal Deity',
                            663
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            92,
                            'Crown Royal Deity',
                            673
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            93,
                            'Monarch of the Gods',
                            682
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            94,
                            'God/Goddess of all Gods and Goddess',
                            691
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            95,
                            'Programmer Deity',
                            700
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            96,
                            'Choppy Lover',
                            709
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            97,
                            'Skycalf',
                            719
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            98,
                            'Choppy',
                            728
                        );

INSERT INTO rank_titles (
                            rank,
                            rank_name,
                            rank_exp
                        )
                        VALUES (
                            99,
                            'Skywhale',
                            737
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

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      10,
                      1,
                      'ONEWOLF',
                      'Werewolf'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      11,
                      1,
                      'MANY_WOLVES',
                      'Werewolves'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      12,
                      1,
                      'ROLE_WOLF',
                      'Werewolf'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      13,
                      1,
                      'ROLE_SEER',
                      'Seer'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      14,
                      1,
                      'ROLE_VILL',
                      'Villager'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      15,
                      1,
                      'WOLF_INSTRUCTIONS',
                      'WOLF, you have NUMBER seconds to decide who to attack. To make your final decision type ''!kill <player>'''
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      16,
                      1,
                      'SEER_INSTRUCTIONS',
                      'Seer, you have NUMBER seconds to PM one name to BOTNAME and discover their true intentions. To enquire with the spirits type Direct Message BOTNAME "See <playername>"'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      17,
                      1,
                      'VILL_DESCRIPTION',
                      'You are a peaceful peasant turned vigilante, a Villager! You must root out the WOLFPURAL by casting accusations or protesting innocence at the daily village meeting, and voting who you believe to be untrustworthy during the daily Lynch Vote. Good luck!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      18,
                      1,
                      'WOLF_DESCRIPTION',
                      'You are a prowler of the night, a Werewolf! You must decide your nightly victims. By day you must deceive the villager and attempt to blend in. Keep this information to yourself! Good luck!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      19,
                      1,
                      'SEER_DESCRIPTION',
                      'You are one granted the gift of second sight, a Seer! Each night you may enquire as to the nature of one of your fellow village dwellers, and BOTNAME will tell you whether or not that person is a Werewolf - a powerful gift indeed! But beware revealing this information to the WOLF, or face swift retribution!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      20,
                      1,
                      'WOLVES_DESCRIPTION',
                      'You are a prowler of the night, a Werewolf! You must confer with your other kin via PM to decide your nightly victims. By day you must deceive the villager and attempt to blend in. Keep this information to yourself! Good luck!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      21,
                      1,
                      'OTHER_WOLF',
                      'Your other kin is PLAYER1'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      22,
                      1,
                      'NO_LYNCH',
                      'As the sun gets low in the sky, the villagers are unable to reach a decision on who to lynch, and without the momentum of a united mob, the crowd slowly dissipates to take shelter in their homes...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      23,
                      1,
                      'WOLF_LYNCH',
                      'After coming to a decision, PLAYER1 is quickly dragged from the crowd, and dragged to the hanging tree. PLAYER1 is strung up, and the block kicked from beneath their feet. There is a yelp of pain, but PLAYER1''s neck doesn''t snap, and fur begins to sprout from their body. A gunshot rings out, as a villager puts a silver bullet in the beast''s head...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      24,
                      1,
                      'SEER_LYNCH',
                      'PLAYER1 runs before the mob is organised, dashing away from the village. Tackled to the ground near to the lake, PLAYER1 is tied to a log, screaming, and thrown into the water. With no means of escape, PLAYER1 drowns, but as the villagers watch, tarot cards float to the surface and their mistake is all too apparent...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      25,
                      1,
                      'VILL_LYNCH',
                      'The air thick with adrenaline, the villagers grab PLAYER1 who struggles furiously, pleading innocence, but the screams fall on deaf ears. PLAYER1 is dragged to the stake at the edge of the village, and burned alive. But the villagers shouts and cheers fade as they realise the moon is already up - PLAYER1 was not a io.github.mannjamin.ducky.werewolf after all...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      26,
                      1,
                      'START_GAME',
                      'PLAYER1 has started a game. Everyone else has NUMBER seconds to join in the mob. "!join" to join the game.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      27,
                      1,
                      'START_GAME_NOTICE',
                      'PLAYER1 has started a game!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      28,
                      1,
                      'ADDED',
                      'MENTION You''ve been added to the game. Your role will be given once registration elapses.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      29,
                      1,
                      'GAME_STARTED',
                      'A game has been started! ''/msg BOTNAME join'' to join!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      30,
                      1,
                      'GAME_PLAYING',
                      'A game is currently underway. Please wait for it to finish before attempting to join.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      31,
                      1,
                      'NOT_ENOUGH',
                      'Sorry, not enough people to make a valid mob.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      32,
                      1,
                      'TWO_WOLVES',
                      'THERE ARE NUMBER WOLVES IN THIS GAME.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      33,
                      1,
                      'JOIN',
                      'PLAYER1 has joined the hunt. There are now NUMBER players'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      34,
                      1,
                      'FLEE',
                      'PLAYER1 has fled.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      35,
                      1,
                      'FLEE_ROLE',
                      'PLAYER1 has fled. They were a ROLE'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      36,
                      1,
                      'FIRST_NIGHT',
                      'Night descends on the sleepy village, and a full moon rises. Unknown to the villagers, tucked up in their warm beds, the early demise of one of their number is being plotted.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      37,
                      1,
                      'NIGHT_TIME',
                      'As the moon rises, the lynching mob dissipates, return to their homes and settle into an uneasy sleep. But in the pale moonlight, something stirs...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      38,
                      1,
                      'DAY_TIME',
                      'Villagers, you have NUMBER seconds to discuss suspicions, or cast accusations, after which time a lynch vote will be called.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      39,
                      1,
                      'VOTE_TIME',
                      'Villagers, you now have NUMBER seconds to vote for the person you would like to see lynched! Type ''/msg BOTNAME vote <player>''  or ''!vote <player>'' to cast your vote. Votes can be changed!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      40,
                      1,
                      'WOLF_CHOICE',
                      'You have chosen PLAYER1 to feast on tonight.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      41,
                      1,
                      'WOLVES_OTHER_CHOICE',
                      'PLAYER1 has chosen to kill PLAYER2.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      42,
                      1,
                      'WOLF_TARGET_DEAD',
                      'PLAYER1 is already dead, choose someone else.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      43,
                      1,
                      'ROLE_IS_KILLED',
                      'PLAYER1, the ROLE, has been killed!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      44,
                      1,
                      'SEER_KILL',
                      'The first villager to arrive at the center shrieks in horror - lying on the cobbles is a blood stained Ouija Board, and atop it sits PLAYER1''s head. It appears PLAYER1 had been seeking the guidance of the spirits to root out the WOLFPURAL, but apparently the magic eight ball didn''t see THIS one coming...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      45,
                      1,
                      'VILL_KILL',
                      'The villagers gather the next morning in the village center, but PLAYER1 does not appear. The villagers converge on PLAYER1''s home and find them decapitated in their bed. After carrying the body to the church, the villagers, now hysterical, return to the village center to decide how to retaliate...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      46,
                      1,
                      'NO_KILL',
                      'The villagers gather the next morning in the village center, to sighs of relief - it appears there was no attack the previous night.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      47,
                      1,
                      'NOT_VOTED',
                      'Having defied the powers of Good and Justice for long enough, PLAYER1 suddenly clutches their chest, before falling to the floor as blood pours from their ears. May that be a lesson to all who attempt to defend the WOLFPURAL.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      48,
                      1,
                      'NOT_VOTED_NOTICE',
                      'You have been removed from the game for not voting for two Lynch Votes in a row.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      49,
                      1,
                      'VOTED_FOR',
                      'PLAYER1 has voted to lynch PLAYER2!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      50,
                      1,
                      'CHANGE_VOTE',
                      'PLAYER1 has changed their vote to lynch PLAYER2'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      51,
                      1,
                      'VOTE_TARGET_DEAD',
                      'PLAYER1 is already dead. Vote someone else'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      52,
                      1,
                      'WOLF_WIN',
                      'Having successfully deceived the rest of the village''s population, PLAYER1 the Werewolf, breaks into the final villager''s home and rips out their jugular. PLAYER1 bays at the moon, before setting off to the next village...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      53,
                      1,
                      'WOLVES_WIN',
                      'That night, their plan of deception finally bearing it''s fruit, the Werewolves finish off the rest of the human population, and feast, before bounding off together, towards the next village...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      54,
                      1,
                      'VILL_WIN',
                      'With the beasts slain, the villagers cheer! Their peaceful village is once again free from the scourge of the WOLFPURAL'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      55,
                      1,
                      'CONGR_VILL',
                      'Congratulations, Villagers! You win!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      56,
                      1,
                      'CONGR_WOLF',
                      'Congratulations, PLAYER1!  You win!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      57,
                      1,
                      'CONGR_WOLVES',
                      'Congratulations, Werewolves! You win!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      58,
                      1,
                      'SEER_DEAD',
                      'Your link to the living in death is not as great as your link to the dead in life.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      59,
                      1,
                      'WOLF_DEAD',
                      'You find yourself unable to move a musle. Your wolf powers can''t help you in death.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      60,
                      1,
                      'NOT_WOLF',
                      'You are not a Werewolf!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      61,
                      1,
                      'NOT_SEER',
                      'You are not the Seer!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      62,
                      1,
                      'WILL_SEE',
                      'You will see the identity of PLAYER1 upon the dawning of tomorrow.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      63,
                      1,
                      'SEER_SEE',
                      'You find the identity of PLAYER1 to be a ROLE'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      64,
                      1,
                      'SEER_GOT_KILLED',
                      'It appears the WOLFPURAL got to you before your vision did...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      65,
                      1,
                      'SEER_TARGET_KILLED',
                      'The spirits needn''t have guided your sight tonight; Your target was also that of the WOLFPURAL'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      66,
                      1,
                      'SEER_TARGET_DEAD',
                      'PLAYER1 is dead, their spirit already gone. Choose another player to see.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      67,
                      1,
                      'TALLY',
                      'Tallying Votes...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      68,
                      1,
                      'TIE',
                      'A NUMBER way tie. Randomly choosing one...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      69,
                      1,
                      'DYING_BREATH',
                      'PLAYER1 is allowed a single line as your dying breath.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      70,
                      1,
                      'TIE_GAME',
                      'Rocks fell, everybody died! Have a nice night!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      71,
                      1,
                      'FELLOW_WOLF',
                      'PLAYER1, you can''t kill your fellow Wolf, PLAYER2. Your brethren frown upon you'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      72,
                      1,
                      'KILL_SELF',
                      'You cannot wolf on yourself'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      73,
                      1,
                      'SEE_SELF',
                      'You already know yourself, look in the mirror'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      74,
                      1,
                      'YOURE_DEAD',
                      'You are dead, so you can''t vote'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      75,
                      1,
                      'YOUVE_FLED',
                      'You have fled the game, so you can''t vote'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      76,
                      1,
                      'YOUR_ROLE',
                      'your role is ROLE'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      77,
                      1,
                      'TEN_WARNING_VOTE',
                      'Ten second Warning'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      78,
                      1,
                      'STOP_GAME',
                      'Game has stopped'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      79,
                      1,
                      'VILL_KILL',
                      'As some villagers begin to gather in the village center, a scream is heard from the direction of PLAYER1''s house. The elderly villager who had screamed points to the fence, on top of which, the remains of PLAYER1 are impaled, with their intestines spilling onto the cobbles. Apparently PLAYER1 was trying to flee their attacker...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      80,
                      1,
                      'VILL_KILL',
                      'When the villagers gather at the village center, one comes running from the hanging tree, screaming at others to follow. When they arrive at the hanging ree, a gentle creaking echoes through the air as the body of PLAYER1 swings gently in the breeze, it''s arms ripped off at the shoulders. It appears the attacker was not without a sense of irony...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      81,
                      1,
                      'VILL_KILL',
                      'As the village priest gathers the prayer books for the mornings sermon, he notices a trickle of blood snaking down the aisle.. He looks upward to see PLAYER1 impaled on the crucifix - the corpse has been gutted. He shouts for help, and the other villagers pile into the church, and start arguing furiously...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      82,
                      1,
                      'VILL_LYNCH',
                      'Realising the angry mob is turning, PLAYER1 tries to run, but is quickly seized upon. PLAYER1 is strung up to the hanging tree, and a hunter readies his rifle with a silver slug, as the block is kicked from beneath them. But there is a dull snap, and PLAYER1 hangs, silent, motionless. The silent villagers quickly realise their grave mistake...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      83,
                      1,
                      'WOLVES_OTHER_CHOICE',
                      'PLAYER2 is PLAYER1''s choice of victim this night.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      84,
                      1,
                      'WOLVES_CHOICE',
                      'You have chosen PLAYER1 to feast on tonight. We shall see who your brethren selects...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      85,
                      1,
                      'TEN_WARNING_JOIN',
                      '10 Seconds until end of sign up'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      86,
                      1,
                      'WOLVES_INSTRUCTIONS',
                      'PLAYER1, you have NUMBER seconds to confer via PM with PLAYER2 and decide who to attack. To make your final decision type ''/msg BOTNAME kill <player>'''
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      87,
                      1,
                      'TEN_WARNING_WOLF',
                      'PLAYER1, you have about 10 seconds left. (This is not accuate)'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      88,
                      1,
                      'VOTED_FOR_NOLYNCH',
                      'PLAYER1 wants a No Lynch and voted as such'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      89,
                      1,
                      'VOTED_NO_LYNCH',
                      'As the sun gets low in the sky, the villagers have decided to lynch no-one, and without the momentum of a united mob, the crowd slowly dissipates to take shelter in their homes...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      90,
                      1,
                      'CHANGE_VOTE_NOLYNCH',
                      'PLAYER1 has changed their vote for No Lynch'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      91,
                      1,
                      'ROLE_IS_LYNCHED',
                      'PLAYER1, the ROLE, has been killed!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      92,
                      2,
                      'ONEWOLF',
                      'Snitch'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      93,
                      2,
                      'MANY_WOLVES',
                      'Snitches'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      94,
                      2,
                      'ROLE_WOLF',
                      'Snitch'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      95,
                      2,
                      'ROLE_SEER',
                      'Godfather'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      96,
                      2,
                      'ROLE_VILL',
                      'Mafioso'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      97,
                      2,
                      'WOLF_INSTRUCTIONS',
                      'WOLFPURAL, you have NUMBER seconds to decide who to rat out. To make your final decision type ''! kill <player>'''
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      98,
                      2,
                      'WOLVES_INSTRUCTIONS',
                      'PLAYER1, you have NUMBER seconds to confer via PM with PLAYER2 and decide who to rat out. To make your final decision type ''/msg BOTNAME kill <player>'''
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      99,
                      2,
                      'SEER_INSTRUCTIONS',
                      'Godfather, you have NUMBER seconds to PM one name to BOTNAME and personally interview them. To organise the meeting DM BOTNAME and use "see <player name>"'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      100,
                      2,
                      'VILL_DESCRIPTION',
                      'You are a Mafioso, organising hits, and trying to search out the WOLFPURAL by casting accusations or protesting innocence at the daily mafia meeting, and voting who you believe to be untrustworthy during the daily mafia Vote. Good luck!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      101,
                      2,
                      'WOLF_DESCRIPTION',
                      'You are a Snitch, attempting to maintain your cover as you rat out the members of the mafia.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      102,
                      2,
                      'SEER_DESCRIPTION',
                      'You are the Godfather!  You are gifted with the ability to find out if somebody is lying, and can set up a meeting with a mafia member during the day.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      103,
                      2,
                      'WOLVES_DESCRIPTION',
                      'You are a Snitch, attempting to maintain your cover as you rat out the members of the mafia.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      104,
                      2,
                      'OTHER_WOLF',
                      'The Cops have told you that your fellow Snitch is PLAYER1'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      105,
                      2,
                      'NO_LYNCH',
                      'Nobody voted! The Mafia is not happy with this decision...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      106,
                      2,
                      'WOLF_LYNCH',
                      'After the meeting, PLAYER1 was quickly met by several figures in black.  PLAYER1 knew what was happening and pleaded with the mafia for mercy, but the damage was done and PLAYER1 was quickly shot.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      107,
                      2,
                      'SEER_LYNCH',
                      'As PLAYER1 walked into his office the next day, two members of the mafia quickly shot PLAYER1 before realising that it was their boss.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      108,
                      2,
                      'SEER_LYNCH',
                      'Having earned the distrust of the Mafia, during the day PLAYER1 was pulled into and elevator and quickly stabbed to death by Butter Knives.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      109,
                      2,
                      'VILL_LYNCH',
                      'As PLAYER1 was walking home, they heard a car drive up behind him.  Recognising the driver as one of the mafia, PLAYER1 instantly knew what was happening.  PLAYER1 quickly ducked into the alley and fired off shots around the corner.  Just as soon as he had done a second mafia member had snuck up behind tham, quickly executing PLAYER1'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      110,
                      2,
                      'VILL_LYNCH',
                      'After the meeting, PLAYER1 was quickly pulled into the janitors closet where PLAYER1 was prompty beaten to death by Inflatable Hammers.  Only after the ordeal was it realised that he was in fact, not a snitch.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      111,
                      2,
                      'START_GAME',
                      'PLAYER1 has started a Snitch game. Everyone else has NUMBER seconds to join in the mob. "!join! to join the mob.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      112,
                      2,
                      'START_GAME_NOTICE',
                      'PLAYER1 has started a game!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      113,
                      2,
                      'ADDED',
                      'MENTION You''ve been added to the game. Your role will be given once registration elapses.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      114,
                      2,
                      'GAME_STARTED',
                      'A game has been started! ''/msg BOTNAME join'' to join!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      115,
                      2,
                      'GAME_PLAYING',
                      'A game is currently underway. Please wait for it to finish before attempting to join.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      116,
                      2,
                      'NOT_ENOUGH',
                      'Sorry, not enough people to make a valid mafia.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      117,
                      2,
                      'TWO_WOLVES',
                      'THERE ARE NUMBER SNITCHES IN THIS GAME.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      118,
                      2,
                      'JOIN',
                      'PLAYER1 has joined the mafia. There are now NUMBER players'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      119,
                      2,
                      'FLEE',
                      'PLAYER1 has fled. Sadly, there''s only NUMBER players'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      120,
                      2,
                      'FLEE_ROLE',
                      'PLAYER1 has fled. They were a ROLE'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      121,
                      2,
                      'FIRST_NIGHT',
                      'The Godfather has discovered there are Snitches in his ranks, and has called a Meeting tonight.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      122,
                      2,
                      'NIGHT_TIME',
                      'After the meeting, the various members head back to their hideouts, knowing the imminent danger.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      123,
                      2,
                      'DAY_TIME',
                      'The Mafia Meeting has begun, it ends in NUMBER seconds.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      124,
                      2,
                      'VOTE_TIME',
                      'A vote to find out who to whack today has begun, to vote use "/msg BOTNAME vote <player>" the vote ends in NUMBER seconds.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      125,
                      2,
                      'WOLF_CHOICE',
                      'You have chosen PLAYER1 to rat out today.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      126,
                      2,
                      'WOLVES_CHOICE',
                      'You have chosen PLAYER1 to rat out today. We shall see who your partner selects...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      127,
                      2,
                      'WOLVES_OTHER_CHOICE',
                      'PLAYER1 has chosen to rat out PLAYER2.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      128,
                      2,
                      'WOLVES_OTHER_CHOICE',
                      'PLAYER2 is PLAYER1''s choice to rat out today.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      129,
                      2,
                      'WOLF_TARGET_DEAD',
                      'PLAYER1 is already dead, choose someone else.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      130,
                      2,
                      'ROLE_IS_KILLED',
                      'PLAYER1, the ROLE, has been killed!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      131,
                      2,
                      'ROLE_IS_LYNCHED',
                      'PLAYER1, the ROLE, has been whacked!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      132,
                      2,
                      'SEER_KILL',
                      'a mid-day raid on the mafia safehouse was organised by the police force, and executed perfectly as they are able to arrest the Godfather, PLAYER1'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      133,
                      2,
                      'VILL_KILL',
                      'After walking to his hideout, PLAYER1 did not realise he was being trailed by a cop.  Shortly after getting settled in, his hideout is raided by FBI agents and PLAYER1 is quickly arrested.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      134,
                      2,
                      'NO_KILL',
                      'The mafia ends the day with no members being arrested.  The Snitch must not have acted today.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      135,
                      2,
                      'NOT_VOTED',
                      'Having been useless in the current problem, PLAYER1 is shot at the meeting.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      136,
                      2,
                      'NOT_VOTED_NOTICE',
                      'You have been removed from the game for not voting for two mafia Votes in a row.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      137,
                      2,
                      'VOTED_FOR',
                      'PLAYER1 has voted to whack PLAYER2!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      138,
                      2,
                      'CHANGE_VOTE',
                      'PLAYER1 has changed their vote to whack PLAYER2!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      139,
                      2,
                      'VOTE_TARGET_DEAD',
                      'PLAYER1 is already dead. Vote someone else'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      140,
                      2,
                      'WOLF_WIN',
                      'After ratting out the entire mafia, PLAYER1 is put into witness protection as the last mafia member is arrested.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      141,
                      2,
                      'WOLVES_WIN',
                      'After ratting out the entire mafia, The Snitches are put into witness protection as the last few mafia members are arrested.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      142,
                      2,
                      'VILL_WIN',
                      'With the WOLFPURAL killed, the mafia is able to continue with their regular mafia duties.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      143,
                      2,
                      'CONGR_VILL',
                      'Congratulations, Mafia! You win!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      144,
                      2,
                      'CONGR_WOLF',
                      'Congratulations, PLAYER1!  You win!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      145,
                      2,
                      'CONGR_WOLVES',
                      'Congratulations, Snitches You win!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      146,
                      2,
                      'SEER_DEAD',
                      'You have been arrested before your meeting took place.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      147,
                      2,
                      'WOLF_DEAD',
                      'You find yourself unable to move a musle. Your wolf powers can''t help you in death.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      148,
                      2,
                      'NOT_WOLF',
                      'You are not a Snitch, just a loyal Mafia member!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      149,
                      2,
                      'NOT_SEER',
                      'You are not the Godfather!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      150,
                      2,
                      'WILL_SEE',
                      'You will interview PLAYER1 during the day to find out if they are loyal to the mafia.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      151,
                      2,
                      'SEER_SEE',
                      'You find the identity of PLAYER1 to be a ROLE'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      152,
                      2,
                      'SEER_GOT_KILLED',
                      'It appears the WOLFPURAL ratted out you out before the meeting.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      153,
                      2,
                      'SEER_TARGET_KILLED',
                      'There was no reason for the meeting today as the WOLFPURAL has ratted them out.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      154,
                      2,
                      'SEER_TARGET_DEAD',
                      'PLAYER1 is already arrested. Choose another player to interview.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      155,
                      2,
                      'TALLY',
                      'Tallying Votes...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      156,
                      2,
                      'TIE',
                      'A NUMBER way tie. Randomly choosing one...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      157,
                      2,
                      'DYING_BREATH',
                      'PLAYER1 is allowed to write a sentence in their own blood. (i.e. say one last line)'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      158,
                      2,
                      'TIE_GAME',
                      'Someone glances up at the ceiling of the mafia hideout. The roof isn''t remarkably secure, considering that the last time it was repaired was in 1928. Loud creaking sounds from above are suddenly followed by the complete collapse of the building. The police find no survivors.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      159,
                      2,
                      'FELLOW_WOLF',
                      'What''s that, PLAYER1? You want to snitch on PLAYER2, your partner? What makes you think that the mafia won''t just kill YOU right after they''ve shot the snitch you snitched on?'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      160,
                      2,
                      'KILL_SELF',
                      'You cannot get yourself arrested'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      161,
                      2,
                      'SEE_SELF',
                      'You already know yourself, look in the mirror'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      162,
                      2,
                      'YOURE_DEAD',
                      'You are dead, so you can''t vote'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      163,
                      2,
                      'YOUVE_FLED',
                      'You have fled the game, so you can''t vote'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      164,
                      2,
                      'YOUR_ROLE',
                      'your role is ROLE'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      165,
                      2,
                      'TEN_WARNING_VOTE',
                      'Ten second Warning'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      166,
                      2,
                      'STOP_GAME',
                      'Game has stopped'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      167,
                      2,
                      'TEN_WARNING_JOIN',
                      '10 Seconds until end of sign up'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      168,
                      2,
                      'TEN_WARNING_WOLF',
                      'PLAYER1, you have about 10 seconds left. (This maybe out by a few seconds)'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      169,
                      2,
                      'VOTED_FOR_NOLYNCH',
                      'PLAYER1 voted to hit no-one'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      170,
                      2,
                      'VOTED_NO_LYNCH',
                      'Everybody voted not to lynch! The Mafia is not happy with this decision......'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      171,
                      2,
                      'CHANGE_VOTE_NOLYNCH',
                      'PLAYER1 has changed their vote to hit no-one'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      172,
                      3,
                      'ONEWOLF',
                      'Enemy'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      173,
                      3,
                      'ONEWOLF',
                      'Enemy Soldier'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      174,
                      3,
                      'MANY_WOLVES',
                      'Enemies'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      175,
                      3,
                      'MANY_WOLVES',
                      'Enemy Soldiers'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      176,
                      3,
                      'ROLE_WOLF',
                      'Enemy'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      177,
                      3,
                      'ROLE_WOLF',
                      'Enemy Soldier'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      178,
                      3,
                      'ROLE_SEER',
                      'Special Op'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      179,
                      3,
                      'ROLE_VILL',
                      'Soldier'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      180,
                      3,
                      'ROLE_VILL',
                      'Ally'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      181,
                      3,
                      'ROLE_VILL',
                      'Allied Soldier'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      182,
                      3,
                      'WOLF_INSTRUCTIONS',
                      'Enemy, You have NUMBER seconds to ambush the allies. This is the perfect opportunity... Type ''!kill <player>'' to choose a victim.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      183,
                      3,
                      'WOLF_INSTRUCTIONS',
                      'Enemy, the NUMBER is right. you have gained a window of opportunity with NUMBER seconds to trap the allies. Type ''!kill <player>'' to choose a victim.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      184,
                      3,
                      'WOLVES_INSTRUCTIONS',
                      'Enemies, you have NUMBER seconds to work together and kill one of the soldiers. Type ''/msg BOTNAME kill <player>'' to choose a victim.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      185,
                      3,
                      'SEER_INSTRUCTIONS',
                      'Special Op, you have NUMBER seconds to use your training and find where your fellow soldier''s true allegiance lies. Send a direct message to BOTNAME with  ''see <player>'' to stalk them and discover their mission.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      186,
                      3,
                      'SEER_INSTRUCTIONS',
                      'A Window of NUMBER seconds has opened given you a chance to search a fellow solider true allegiance. Direct Message BOTNAME with  ''see <player>'' to stalk them and discover their mission.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      187,
                      3,
                      'VILL_DESCRIPTION',
                      'You are an ally solider, drafted to defend your country. You only have your training, your wits and your luck. May God be with you.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      188,
                      3,
                      'VILL_DESCRIPTION',
                      'For God and Country, you are an ally solider. The last line of defence. Only your wits, training and luck may save you. '
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      189,
                      3,
                      'WOLF_DESCRIPTION',
                      'You are a secret infiltrator, and your loyalies lie with the enemy. Wearing your Allied uniform, pried from a soldier''s cold, dead body, you join the newly formed unit to prevent them from completing their mission.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      190,
                      3,
                      'WOLF_DESCRIPTION',
                      'As you gently sink a knife into the exposed neck of an Ally solider, you smile as your mind forms a sneaky plan. Wearing this uniform, you can infiltrate an Allied regiment, learn of the enemy''s plans, and report back to base with useful intelligence.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      191,
                      3,
                      'SEER_DESCRIPTION',
                      'Unlike many of the other soliders in the unit, you have some extra training as a Special Op. This gives you the knowledge to help our your fellow soldiers. During each mission, you can use your extra knowledge to find out if someone is truly a friend or an enemy soldier. Be careful, should the WOLFPLURAL know, they will likely plot your demise with an ambush during the next mission.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      192,
                      3,
                      'WOLVES_DESCRIPTION',
                      'You and PLAYER2 drag some muffled Allied soliders into the shadow of a building, ending their lives with a swift crack of the neck. You formulate your next plan: to infiltrate an Allied unit, obtain intel, kill all soldiers without being discovered, and head back to base.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      193,
                      3,
                      'OTHER_WOLF',
                      'Your partner in crime is PLAYER1.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      194,
                      3,
                      'NO_LYNCH',
                      'As the unit looks at the votes it was clear they couldn''t leave the vote to chance.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      195,
                      3,
                      'WOLF_LYNCH',
                      'PLAYER1 is frog marched to the firing line, and tied up to the wall and blindfolded. The soldier begins to confess, crying "Mercy!" and offering to tell enemy secrets. But nothing can prevent these soldiers from administering justice to those who have killed their friends. One quick nod and the unit fires. PLAYER1''s body falls to the ground, silently.
Knowing the unit plans his execution, PLAYER1 bolts. However, the'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      196,
                      3,
                      'WOLF_LYNCH',
                      'PLAYER1 quickly ran from the group, dropping all of his possessions while running. As the soldiers examine the belongings, they discover letters in the enemy''s language, as well as an enemy ID. PLAYER1 seems to have escaped, but suddenly the soldier stops dead and falls over, blood pouring from the nose and mouth. An Ally sniper has ended the enemy''s life.
When the last count of votes is called out the group quickly'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      197,
                      3,
                      'SEER_LYNCH',
                      'The majority of the soldiers voted for PLAYER1. Bravely, PLAYER1 walks to the firing post, refusing a blindfold. Shots ring out, ending PLAYER1''s life. Later in the war, the soldiers learn that PLAYER1 was a Special Op.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      198,
                      3,
                      'SEER_LYNCH',
                      'When the final votes is counted, the unit turns onto the PLAYER1, all plunging the bayonets into the body, over and over until PLAYER1 corpse drops to the floor. Silently the group wipe clean their bayonets and gives PLAYER1 a crudish burial. Another solider recognizes the corpes as a member of his unit, a Special Op.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      199,
                      3,
                      'SEER_LYNCH',
                      'In fear for their life, PLAYER1 lash out against the group trying to defending themselves. In the ensuing fight the company manage to grab hold of the PLAYER1''s throat and in a iron grip the PLAYER1 struggles to breath. Slowly PLAYER1 struggles less and less becoming twitches until finally their body goes limp. A quick inspection of the corpse the company discovered that PLAYER1 is a Special OP.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      200,
                      3,
                      'VILL_LYNCH',
                      'Swiftly and confidently, one of the soldiers holds a gun up to PLAYER1 and shoots them. As the light fades away from PLAYER1''s eyes, the lifeless body drops to the floor into a pool of blood. The remaining soldiers search the body. As they remove the ammo from the gun and other supplies from PLAYER1''s clothing, a blood stained ID is discovered, which clearly shows PLAYER1''s loyalty.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      201,
                      3,
                      'VILL_LYNCH',
                      'PLAYER1 looks to their comrades with a sad and sorry face. There is no escaping death now. Nodding gently, the soldiers give PLAYER1 10 seconds to run before shooting. Closing their eyes, PLAYER1 runs from the group and stumbles into a battlefield as an enemy sniper shoots them down. The soliders realise their grave mistake...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      202,
                      3,
                      'VILL_LYNCH',
                      'The votes on the wall are counted -- PLAYER1 is to be executed. Quietly PLAYER1 says a small prayer and hopes for salvation. Not wanting to be killed by their own friends, PLAYER1 turns his gun inward and fires... Such sacrifice and loyalty, along with a discovered ID card, shows that PLAYER1 was not an enemy.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      203,
                      3,
                      'VILL_LYNCH',
                      'PLAYER1 is bounded in ropes and made prisoner by the other soldiers. Rather than kill PLAYER1, the soldiers hand him over to the higher-ups. Later, word arrives that PLAYER1 was actually an Ally.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      204,
                      3,
                      'VILL_LYNCH',
                      'The unit gathers around PLAYER1, and one of them seizes PLAYER1''s arm. The PLAYER1 tries to remove their arm from their grasp, while another soldier injects them.  PLAYER1 suddenly drops to the ground, twisting and writhing as the poison courses through their veins, their face curls into grotesque shapes as it attacks PLAYER1''s nervous system. PLAYER1 finally heaves a sigh, and stops moving as they die. PLAYER1''s old'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      205,
                      3,
                      'START_GAME',
                      'A World War has just been started by PLAYER1! Be warned, this theme is graphic.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      206,
                      3,
                      'START_GAME',
                      'All across the battle torn lands, PLAYER1 has begun to rally the troops. Be warned, this is a bloody theme.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      207,
                      3,
                      'START_GAME_NOTICE',
                      'In this God forsaken country, a war has begun. PLAYER1 has started this theme -- warning: graphic descriptions ahead.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      208,
                      3,
                      'START_GAME_NOTICE',
                      'Solider PLAYER1 is bringing together the scattered troops. Come and pledge your allegiance to your country! Warning: this is a bloody theme'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      209,
                      3,
                      'ADDED',
                      'MENTION you''re lost in the war torn lands, detached from your regiment, you finally stumble upon some of your fellow soliders and join their little group.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      210,
                      3,
                      'ADDED',
                      'After trekking alone for some time, MENTION find some fellow soliders and decide to join them.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      211,
                      3,
                      'GAME_STARTED',
                      'Shells whistle past, and their cacophanous explosions seem to rock the very core of the world. The air is filled with the dying screams of soldiers. Come! Help defend your country. Type ''/msg BOTNAME join'''
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      212,
                      3,
                      'GAME_STARTED',
                      'A grey mist settles upon the land and an uneasy silence fills the air. You are needed. Type ''/msg BOTNAME join'' to defend your rights.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      213,
                      3,
                      'GAME_PLAYING',
                      'War! You glance across the field of battle to see soldiers fighting for their lives. Please wait until this game is over before attempting to join.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      214,
                      3,
                      'GAME_PLAYING',
                      'You stumble across a battle between two armies. As you watch from afar, you find yourself wanting a piece of the action. Please wait until this game is over before attempting to join.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      215,
                      3,
                      'NOT_ENOUGH',
                      'Unable to form a unit of 5 soliders or more, the soldiers part ways to search for their original units.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      216,
                      3,
                      'TWO_WOLVES',
                      'During the evening encampment, a soldier stumbles upon NUMBER discarded enemy uniforms! Faces are suddenly filled with the grim realization that NUMBER enemy soldiers are among them.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      217,
                      3,
                      'TWO_WOLVES',
                      'In the remains of the evening fire, a soldier finds NUMBER charred enemy uniforms. Looks like the previous owners recently tried to burn them...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      218,
                      3,
                      'JOIN',
                      'With a respectful salute, PLAYER1 joins this group. There''s now NUMBER in the unit'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      219,
                      3,
                      'JOIN',
                      'Falling in, PLAYER1 joins this unit. There''s now NUMBER in the unit'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      220,
                      3,
                      'JOIN',
                      'PLAYER1 is assigned to this makeshift unit. There''s now NUMBER in the unit'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      221,
                      3,
                      'JOIN',
                      'PLAYER1 is sent to help this group. There''s now NUMBER in the unit'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      222,
                      3,
                      'FLEE',
                      'PLAYER1 spots their original regiment in the distance, and runs off to rejoin them.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      223,
                      3,
                      'FLEE',
                      'PLAYER1 has disobeyed orders and is sent away.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      224,
                      3,
                      'FLEE_ROLE',
                      'PLAYER1''s role was ROLE.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      225,
                      3,
                      'FIRST_NIGHT',
                      'The newly formed unit sets out in the black of night, separating so as to be able to sneak across enemy lines... However, there is already an enemy in their very midst.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      226,
                      3,
                      'FIRST_NIGHT',
                      'The new unit spilts up, following a set task for the assult against the enemy. Every one''s loyal, right?'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      227,
                      3,
                      'NIGHT_TIME',
                      'The start of a new mission. The screams of dying soldiers grow ever louder as the men push on... A soldier checks over his back and grips his rifle tighter. '
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      228,
                      3,
                      'NIGHT_TIME',
                      'Heading into the next town, the unit separates to infiltrate the area. They plan to later regroup after suppies have been found, but will everyone remain alive until then?'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      229,
                      3,
                      'DAY_TIME',
                      'The soliders find a moment''s peace in the battle. Knowing that a secret-stealing WOLFPLURAL lies among them, they fall to accusing each other. Voting starts in NUMBER seconds.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      230,
                      3,
                      'DAY_TIME',
                      'Pinned down during an enemy bombardment, the soliders are given a moment''s chance to accuse their comrades of who might be the camouflaged WOLFPLURAL. The unit will commence a vote in NUMBER seconds...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      231,
                      3,
                      'VOTE_TIME',
                      'The unit goes silent. The time for talking and accusing others is over. Someone turns over the hourglass. You have NUMBER seconds to vote for who you think is the WOLFPLURAL. Type ''/msg BOTNAME vote <player>'' to add your vote.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      232,
                      3,
                      'VOTE_TIME',
                      'The group lines up against a wall, the hourglass turned given the group just NUMBER seconds to vote. ''/msg BOTNAME vote <player>'' to add your vote to who do you think is WOLFPLURAL.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      233,
                      3,
                      'WOLF_CHOICE',
                      'PLAYER1 is your target... You carefully stalk them, and lay the perfect trap right in front of their path.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      234,
                      3,
                      'WOLVES_CHOICE',
                      'You have chosen PLAYER1 to ambush. We shall see who your fellow enemy selects...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      235,
                      3,
                      'WOLVES_OTHER_CHOICE',
                      'PLAYER1 has chosen to kill PLAYER2.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      236,
                      3,
                      'WOLVES_OTHER_CHOICE',
                      'PLAYER2 is PLAYER1''s choice of victim this time.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      237,
                      3,
                      'WOLF_TARGET_DEAD',
                      'PLAYER1 is already dead, choose someone else.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      238,
                      3,
                      'ROLE_IS_KILLED',
                      'PLAYER1, the ROLE, has been killed!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      239,
                      3,
                      'ROLE_IS_LYNCHED',
                      'PLAYER1, the ROLE, has been lynched!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      240,
                      3,
                      'SEER_KILL',
                      'The unit regroups at the remains of one corpse. A knife has been buried into PLAYER1''s back. Various equipment scattered about indicates that PLAYER1 had extra training as a Special Op.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      241,
                      3,
                      'SEER_KILL',
                      'PLAYER1 fought in vain against the enemy, but even with the extra training as a Special Op, they weren''t able to outmaneuver the enemy.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      242,
                      3,
                      'VILL_KILL',
                      'When searching in one of the town''s buildings, one of the soliders hears a thud, thud, thud coming from the staircase. The solider glances into the next room. With blood streaked across the stairway steps, the head of PLAYER1 rolls to a stop at the feet of the poor soldier. PLAYER1''s head slowly stains the man''s boots with blood.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      243,
                      3,
                      'VILL_KILL',
                      'Suddenly, the silence is shattered by blood-chilling screams emanating from behind a public building. The unit regroups at the area, only to find PLAYER1 tied to a chair, blood streaking down from the empty eyeballs. Skin and flesh have been removed from the hands of PLAYER1 leaving the bare bone exposed. Apparently, PLAYER1 was tortured by the enemy for information for their last few moments of their life.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      244,
                      3,
                      'VILL_KILL',
                      'Some of the solders meet up with another unit in the street, patrolling the streets until suddenly PLAYER1 stops walking. From PLAYER1''s forehead, dark streaks of blood pour. PLAYER1''s eyes roll as the lifeless body falls into the mudded street. The soliders of the other units scatter, hiding for cover from the sniper.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      245,
                      3,
                      'VILL_KILL',
                      'As the unit regathers at meeting point they discovered PLAYER1 kneeling on the floor clutching their throat in a vain attempt to stop the blood leaking out. PLAYER1''s eyes rolls as the last breath of life leaves them, the arms dropping to their sides.
A loud deafening explosion rends the air leaving the units'' ears ringing. As the ringing subsided and the dust settled, the unit regroups to find corpse with guts, mus'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      246,
                      3,
                      'NO_KILL',
                      'The soldiers fall in. Everyone is still here! God was with them and has spared their lives.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      247,
                      3,
                      'NOT_VOTED',
                      'PLAYER1''s nerves suddenly freeze, and PLAYER1 finds that they cannot move, psychologically glued to the floor. In every war, this nervousness can be a soldier''s demise. This is especially true for PLAYER1, as they were gunned down by an embedded Machine gun encampment.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      248,
                      3,
                      'NOT_VOTED',
                      'The makeshift group met up with another friendly unit of soldiers. PLAYER1 realized that this unit was the original one they were in, and so has left this group to rejoin them.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      249,
                      3,
                      'NOT_VOTED_NOTICE',
                      'You have been removed from the game for not voting for two Lynch Votes in a row.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      250,
                      3,
                      'VOTED_FOR',
                      'Someone writes a name up on the wall. PLAYER1 has voted to kill PLAYER2!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      251,
                      3,
                      'VOTED_FOR',
                      'Their eyes full of anger and rage, PLAYER1 votes for PLAYER2!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      252,
                      3,
                      'VOTED_FOR',
                      'Quickly PLAYER1 scribbles a mark on the wall. PLAYER1 votes for PLAYER2!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      253,
                      3,
                      'VOTED_FOR',
                      'With a strong sense of duty to their Country, PLAYER1 votes for PLAYER2!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      254,
                      3,
                      'CHANGE_VOTE',
                      'PLAYER1 changes their vote to PLAYER2.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      255,
                      3,
                      'VOTE_TARGET_DEAD',
                      'That person is already dead.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      256,
                      3,
                      'WOLF_WIN',
                      'PLAYER1 fires a bullet through the sorry heart of the last soldier, robbing another family of a son/daughter. For the Allies, this mission has failed. The WOLFPLURAL returns back to base with juicy intelligence that may turn the war in the Axis'' favor... '
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      257,
                      3,
                      'WOLVES_WIN',
                      'With their luck and skill, the Enemies were able to finish off the provisional unit, ending the Allies'' last hope of freedom in this part of the country. May God save us.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      258,
                      3,
                      'VILL_WIN',
                      'With the successful dispatch of the enemy from their unit, the soldiers were free to form a true band of brothers and sisters. The unit became such a successful coalition, helping to turned the tide of the war by fighting and winning many battles.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      259,
                      3,
                      'CONGR_VILL',
                      'Congratulations, Allies! You win!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      260,
                      3,
                      'CONGR_WOLF',
                      'Congratulations, PLAYER1! You win!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      261,
                      3,
                      'CONGR_WOLVES',
                      'Congratulations, Enemies! You win!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      262,
                      3,
                      'SEER_DEAD',
                      'As a Special Op, you should know that because your heart isn''t beating, it means you''re a lifeless corpse and you can''t do anything now.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      263,
                      3,
                      'WOLF_DEAD',
                      'You''re dead! You can''t do that!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      264,
                      3,
                      'NOT_WOLF',
                      'Your loyalty is to your country, not to the enemy.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      265,
                      3,
                      'NOT_SEER',
                      'You do not have enough special training for this mission.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      266,
                      3,
                      'WILL_SEE',
                      'After gathering information on PLAYER1, all you can do now is wait until your other sources report back to you.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      267,
                      3,
                      'SEER_SEE',
                      'Your sources have come back to you to show that PLAYER1 is a(n) ROLE!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      268,
                      3,
                      'SEER_GOT_KILLED',
                      'Your sources returned to you, unable to find you at your prior location. That might have something to do with you being dead.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      269,
                      3,
                      'SEER_TARGET_KILLED',
                      'Your sources didn''t need to report back to you -- looks like your target was also that of the WOLFPLURAL.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      270,
                      3,
                      'SEER_TARGET_DEAD',
                      'That person is already dead. No need to stalk them.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      271,
                      3,
                      'TALLY',
                      'The unit gathered together to count the final votes.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      272,
                      3,
                      'TALLY',
                      'A Soldier stands up and counts the votes for each person...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      273,
                      3,
                      'TIE',
                      'Much to their dismay, the soldiers discover there was a tie in votes. So, the unit left the decision to the flip of a coin...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      274,
                      3,
                      'DYING_BREATH',
                      'Your last letter was found. It read as thus:'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      275,
                      3,
                      'TIE_GAME',
                      'In the end the unit gathers seeking shelter from the planes. However, a bomb drops directly on the building, ending the lives of everyone.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      276,
                      3,
                      'FELLOW_WOLF',
                      'You cannot kill your fellow Enemy.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      277,
                      3,
                      'KILL_SELF',
                      'Suicide isn''t the way out right now.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      278,
                      3,
                      'SEE_SELF',
                      'You''re pretty sure that you''re a Special Op already. Try stalking someone else.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      279,
                      3,
                      'YOURE_DEAD',
                      'Your current level of ghostiness prohibits you from writing a vote on the wall.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      280,
                      3,
                      'YOUVE_FLED',
                      'You left already, it''s too late to come back and try to change anything.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      281,
                      3,
                      'YOUR_ROLE',
                      'Glancing at your ID card, you see that your role is ROLE. Now get a move on!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      282,
                      3,
                      'TEN_WARNING_VOTE',
                      'If you''re gonna vote, do it now! Only 10 seconds remain.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      283,
                      3,
                      'TEN_WARNING_WOLF',
                      'Dawn is about to break. If you haven''t already made your move, you''d better do it fast! 10 seconds remain.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      284,
                      3,
                      'TEN_WARNING_JOIN',
                      'This makeshift unit is about to get moving. Join in 10 seconds or get left behind!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      285,
                      3,
                      'STOP_GAME',
                      'Everything goes dark, and you wake up again... huh? Oh, the game stopped.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      286,
                      3,
                      'VOTED_FOR_NOLYNCH',
                      'PLAYER1 has voted for no lynching today.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      287,
                      3,
                      'VOTED_FOR_NOLYNCH',
                      'PLAYER1 walks up the wall and writes ''NO LYNCH!'' on it.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      288,
                      3,
                      'VOTED_NO_LYNCH',
                      'Apparently the soldiers don''t feel like killing someone off today. No lynch!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      289,
                      3,
                      'VOTED_NO_LYNCH',
                      'Maybe they''re just too weary and tired. The unit comes to the decision not to kill anyone.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      290,
                      3,
                      'CHANGE_VOTE_NOLYNCH',
                      'PLAYER1 changes their vote to a ''NO LYNCH'' vote!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      291,
                      4,
                      'ONEWOLF',
                      'Mafioso'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      292,
                      4,
                      'MANY_WOLVES',
                      'Mafiosi'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      293,
                      4,
                      'ROLE_WOLF',
                      'Mafioso'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      294,
                      4,
                      'ROLE_SEER',
                      'Inspector'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      295,
                      4,
                      'ROLE_VILL',
                      'Citizen'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      296,
                      4,
                      'WOLF_INSTRUCTIONS',
                      'WOLFPLURAL, you have NUMBER seconds to decide who to murder. To make your final decision type ''!kill <player>'''
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      297,
                      4,
                      'WOLVES_INSTRUCTIONS',
                      'WOLFPLURAL, you have NUMBER seconds to confer via PM and unanimously decide who to murder. To make your final decision type ''/msg BOTNAME kill <player>'''
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      298,
                      4,
                      'SEER_INSTRUCTIONS',
                      'Inspector, you have NUMBER seconds to PM one name to BOTNAME and investigate them. To intrude into their privacy direct message BOTNAME ''see <player>'''
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      299,
                      4,
                      'VILL_DESCRIPTION',
                      'You are a law-abiding citizen, mystified by the series of murders occuring all around. You must try and catch the WOLFPLURAL in your daily lynch vote, ending the menace once and for all.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      300,
                      4,
                      'WOLF_DESCRIPTION',
                      'You are a criminal and a murderer, an elite member of the local Mafia. Your goal is to kill off all of the law-abiding citizens by killing one each night and remaining undetected during the daily lynch vote.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      301,
                      4,
                      'SEER_DESCRIPTION',
                      'You are a representative of the LAW, an Inspector. Each night you may investigate one of the townspeople and BOTNAME will tell you if they are Mafia or Innocent. Be careful of the Mafia discovering and assasinating you!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      302,
                      4,
                      'WOLVES_DESCRIPTION',
                      'You are a criminal and a murderer, an elite member of the local Mafia. Your goal is to kill off all of the law-abiding citizens by killing one each night, conferring with your fellow Mafiosi, and remaining undetected during the daily lynch vote.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      303,
                      4,
                      'OTHER_WOLF',
                      'You are a Mafioso. Your counterpart is Grandino PLAYER1.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      304,
                      4,
                      'NO_LYNCH',
                      'Nobody voted! The Electoral Commission will investigate this...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      305,
                      4,
                      'WOLF_LYNCH',
                      'After coming to a decision, PLAYER1 is quickly dragged from the crowd, and dragged to the hanging tree. PLAYER1 is strung up, and the block kicked from beneath their feet. There is a jingle as the rope pulls accross what is shown to be a solid gold necklace... PLAYER1 was clearly a Mafioso!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      306,
                      4,
                      'SEER_LYNCH',
                      'PLAYER1 runs before the mob is organised, dashing away from the square. Tackled to the ground in a dark alley, PLAYER1 is tied to a nearby fire escape, screaming, and thrown into the street. Dangling, PLAYER1 suffocates, but as the villagers watch, PLAYER1''s FBI ID badge tumbles out of the deep pocket PLAYER1 lost it in and the townspeople''s mistake is all too apparent...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      307,
                      4,
                      'VILL_LYNCH',
                      'The air thick with adrenaline, the townspeople grab PLAYER1 who struggles furiously, pleading innocence, but the screams fall on deaf ears. PLAYER1 is dragged to the gallows and hung. But the villagers shouts and cheers fade as they realise PLAYER1 was not a Mafioso after all...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      308,
                      4,
                      'VILL_LYNCH',
                      'Realising the angry mob is turning, PLAYER1 tries to run, but is quickly seized upon. PLAYER1 is strung up on the gallows, and a hunter readies his rifle as the block is kicked from beneath them. But the lack of bling clearly shows PLAYER1 was not a Mafioso...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      309,
                      4,
                      'START_GAME',
                      'PLAYER1 has started a game. Everyone else has NUMBER seconds to join in the mystery. !join to join the game'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      310,
                      4,
                      'START_GAME_NOTICE',
                      'PLAYER1 has started a game!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      311,
                      4,
                      'ADDED',
                      'MENTION you''ve added to the game. Your role will be given once registration elapses.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      312,
                      4,
                      'GAME_STARTED',
                      'A game has been started! ''/msg BOTNAME join'' to join!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      313,
                      4,
                      'GAME_PLAYING',
                      'A game is currently underway. Please wait for it to finish before attempting to join.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      314,
                      4,
                      'NOT_ENOUGH',
                      'Sorry, not enough people to make a valid mystery.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      315,
                      4,
                      'TWO_WOLVES',
                      'THERE ARE NUMBER MAFIA IN THIS GAME.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      316,
                      4,
                      'JOIN',
                      'PLAYER1 has joined the town.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      317,
                      4,
                      'FLEE',
                      'PLAYER1 has fled.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      318,
                      4,
                      'FLEE_ROLE',
                      'PLAYER1 has fled, they were ROLE'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      319,
                      4,
                      'FIRST_NIGHT',
                      'Night descends on the sleepy town, and the shadows move. Unknown to the townspeople, sleeping in their springy beds, the early demise of one of their number is being plotted.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      320,
                      4,
                      'NIGHT_TIME',
                      'As the moon rises, the lynch mob dissipates, return to their homes and settle into an uneasy sleep. But in the deep shadows, something moves...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      321,
                      4,
                      'DAY_TIME',
                      'Citizens, you have NUMBER seconds to discuss suspicions, or cast accusations, after which time a lynch vote will be called.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      322,
                      4,
                      'VOTE_TIME',
                      'Citizens, you now have NUMBER seconds to vote for the person you would like to see lynched! Type ''/msg BOTNAME vote <player>'' to cast your vote.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      323,
                      4,
                      'WOLF_CHOICE',
                      'You have chosen PLAYER1 to brutally murder.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      324,
                      4,
                      'WOLVES_CHOICE',
                      'You have chosen PLAYER1 to brutally murder. We shall see who your buddy selects...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      325,
                      4,
                      'WOLVES_OTHER_CHOICE',
                      'PLAYER1 has chosen to kill PLAYER2'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      326,
                      4,
                      'WOLVES_OTHER_CHOICE',
                      'PLAYER2 is PLAYER1''s choice of victim this night.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      327,
                      4,
                      'WOLF_TARGET_DEAD',
                      'PLAYER1 is already dead, choose someone else.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      328,
                      4,
                      'ROLE_IS_KILLED',
                      'PLAYER1, the ROLE, has been killed!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      329,
                      4,
                      'ROLE_IS_LYNCHED',
                      'PLAYER1, the ROLE, is lynched!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      330,
                      4,
                      'SEER_KILL',
                      'The first townsperson to arrive at the square shrieks in horror - lying on the cobbles is a blood stained box of donuts, and atop it sits PLAYER1''s head. It appears PLAYER1 had been staking out down-town to root out the WOLFPURAL, but apparently PLAYER1 didn''t see THIS one coming...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      331,
                      4,
                      'VILL_KILL',
                      'The townspeople gather the next morning in the town square, but PLAYER1 doesn''t answer the roll call. The townspeople come to  PLAYER1''s apartment and find them decapitated in their bed. After carrying the body to the morgue, the villagers, now hysterical, return to the square to decide how to retaliate..'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      332,
                      4,
                      'VILL_KILL',
                      'As some townspeople begin to gather in the town square, a scream is heard from the direction of PLAYER1''s apartment building. The senior citizen who had screamed points to the TV aerial, on top of which the remains of PLAYER1 are impaled, with their intestines spilling out as a gruesome flag. Apparently PLAYER1 was trying to hide from their attacker...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      333,
                      4,
                      'VILL_KILL',
                      'When the townspeople gather at the town square, one comes running from the gallows, screaming at others to follow. When they arrive, a gentle creaking echoes through the air as the body of PLAYER1 swings gently in the breeze, it''s arms ripped off at the shoulders. It appears the attacker was not without a sense of irony...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      334,
                      4,
                      'VILL_KILL',
                      'As the parish priest gathers the prayer books for the mornings sermon, he notices a trickle of blood snaking down the aisle.. He looks upward to see PLAYER1 impaled on the crucifix - the corpse has been gutted. He shouts for help, and the other villagers pile into the church, and start arguing furiously...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      335,
                      4,
                      'NO_KILL',
                      'The villagers gather the next morning in the village center, to sighs of relief - it appears there was no attack the previous night.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      336,
                      4,
                      'NOT_VOTED',
                      'Having defied the powers of Justice for long enough, PLAYER1 suddenly clutches their chest, before falling to the floor as blood pours from their ears. May that be a lesson to all who attempt to defend the WOLFPLURAL.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      337,
                      4,
                      'NOT_VOTED_NOTICE',
                      'You have been removed from the game for not voting for two Lynch Votes in a row.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      338,
                      4,
                      'VOTED_FOR',
                      'PLAYER1 has voted for PLAYER2!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      339,
                      4,
                      'CHANGE_VOTE',
                      'PLAYER1 has changed their vote to lynch PLAYER2!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      340,
                      4,
                      'VOTE_TARGET_DEAD',
                      'PLAYER1 is dead, silly.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      341,
                      4,
                      'WOLF_WIN',
                      'The Mafia has taken over this town! So much for the LAW...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      342,
                      4,
                      'WOLVES_WIN',
                      'That night, their plan of deception finally bearing it''s fruit, the Mafia finish off the rest of the human population, and rob the town blind before leaving together, towards the next town...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      343,
                      4,
                      'VILL_WIN',
                      'With the criminals executed, the townspeople celebrate for they are free from the terror of organized crime!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      344,
                      4,
                      'CONGR_VILL',
                      'Congratulations, Citizens! You win!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      345,
                      4,
                      'CONGR_WOLF',
                      'Congratulations, PLAYER1! You win!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      346,
                      4,
                      'CONGR_WOLVES',
                      'Congratulations, Mafia! You win!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      347,
                      4,
                      'SEER_DEAD',
                      'You should have had someone watching your back.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      348,
                      4,
                      'WOLF_DEAD',
                      'You''re dead you can''t do anything'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      349,
                      4,
                      'NOT_WOLF',
                      'You''re not a Mafia'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      350,
                      4,
                      'NOT_SEER',
                      'You
e not the Inspector'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      351,
                      4,
                      'WILL_SEE',
                      'You will discover the nature of PLAYER1 upon the dawning of tomorrow.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      352,
                      4,
                      'SEER_SEE',
                      'You find the identity of PLAYER1 to be ROLE?!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      353,
                      4,
                      'SEER_GOT_KILLED',
                      'It appears the Mafia got to you as you were investigating...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      354,
                      4,
                      'SEER_TARGET_KILLED',
                      'As you came to rummage through PLAYER1''s possessions, you found him already dead. What a waste of time.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      355,
                      4,
                      'SEER_TARGET_DEAD',
                      'As you try to investigate PLAYER1, you suddenly realised that PLAYER1 couldn`t be the mafia as they are already dead'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      356,
                      4,
                      'TALLY',
                      'Tallying votes...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      357,
                      4,
                      'TIE',
                      'A NUMBER way tie. Randomly choosing one...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      358,
                      4,
                      'DYING_BREATH',
                      'You are allowed a single line as your dying breath.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      359,
                      4,
                      'TIE_GAME',
                      'Aliens decide to test out their latest laser weapon on the third planet from Sol. Fortunately for the aliens, the LM9000 has an excellent vaporization-to-mass ratio. Unfortunately for you guys, you''re the target of their little test. Considering the fact that everyone''s plasma now, neither side wins this game. Better luck next time!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      360,
                      4,
                      'FELLOW_WOLF',
                      'Come on, PLAYER1! You''re trying to kill the townies, not your own partner in crime.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      361,
                      4,
                      'KILL_SELF',
                      'Why do you want to kill yourself?'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      362,
                      4,
                      'SEE_SELF',
                      'You already know what your role is.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      363,
                      4,
                      'YOURE_DEAD',
                      'DEAD, DEAD, DEAD. Get it now? You''re dead!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      364,
                      4,
                      'YOUVE_FLED',
                      'You take no part in this game, as you''ve left'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      365,
                      4,
                      'YOUR_ROLE',
                      'Your role is ROLE'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      366,
                      4,
                      'TEN_WARNING_VOTE',
                      '10 Seconds to get your votes in'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      367,
                      4,
                      'TEN_WARNING_WOLF',
                      '10 Seconds before the suns comes up. (Give or take a second)'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      368,
                      4,
                      'TEN_WARNING_JOIN',
                      '10 Seconds to the end of sign up'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      369,
                      4,
                      'STOP_GAME',
                      'Game Stopped'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      370,
                      4,
                      'VOTED_FOR_NOLYNCH',
                      'PLAYER1 has voted not to lynch anyone'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      371,
                      4,
                      'VOTED_NO_LYNCH',
                      'As the sun gets low in the sky, the townspeople are unable to reach a decision on who to lynch, and without the momentum of a united mob, the crowd slowly dissipates to take shelter in their apartments...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      372,
                      4,
                      'CHANGE_VOTE_NOLYNCH',
                      'PLAYER1 has changed their vote to No Lynch'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      373,
                      5,
                      'ONEWOLF',
                      'Tribble'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      374,
                      5,
                      'MANY_WOLVES',
                      'Tribbles'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      375,
                      5,
                      'ROLE_WOLF',
                      'Tribble'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      376,
                      5,
                      'ROLE_SEER',
                      'Doctor'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      377,
                      5,
                      'ROLE_VILL',
                      'Redshirt'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      378,
                      5,
                      'WOLF_INSTRUCTIONS',
                      'Tribble, you have NUMBER seconds to select someone to ambush and devour.  Type ''!kill <PLAYER1>'' choose a victim.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      379,
                      5,
                      'WOLVES_INSTRUCTIONS',
                      'Tribbles, you have NUMBER seconds to agree on someone to ambush and devour. Type ''msg BOTNAME kill <PLAYER1>'' choose a victim.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      380,
                      5,
                      'SEER_INSTRUCTIONS',
                      'Doctor, you have NUMBER seconds to choose who you wish to scan at the end of the search period. Direct Message BOTNAME with ''see <PLAYER1>'' to discover their species.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      381,
                      5,
                      'VILL_DESCRIPTION',
                      'You are a regular Federation Away Team member, AKA a Redshirt. You must search the abandoned vessel during the search period, and discuss with the others you think the Tribble(s) may be after each search.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      382,
                      5,
                      'WOLF_DESCRIPTION',
                      'You are the infiltrator within the crew, you are a Tribble! Using your latest technology RealRedshirt(tm) suit, you must attempt to exterminate the humans one by one before they discover your plans for this sector.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      383,
                      5,
                      'SEER_DESCRIPTION',
                      'You are the Away Team''s doctor. Your medical tricorder has enough energy to scan a single person after every search period, revealing their true biological chemistry.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      384,
                      5,
                      'WOLVES_DESCRIPTION',
                      'You are an infiltrator within the crew, you are a Tribble! Using your latest technology RealRedshirt(tm) suit, you must work with PLAYER1, your companion, in order to exterminate the humans one by one before they discover your plans for this sector.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      385,
                      5,
                      'OTHER_WOLF',
                      'You are a Tribble. Your counterpart is PLAYER1.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      386,
                      5,
                      'NO_LYNCH',
                      'Overcome with team spirit, the crew decide no-one should be killed without a conclusive majority of votes..'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      387,
                      5,
                      'WOLF_LYNCH',
                      'Certain they''ve found the invader and forgetting all human progress in the last 6000 years, the crew capture PLAYER1 and attempt to rip him limb from limb. The first limb ripped, an arm, produces a spark, and a bunch of cables dangles from it''s severed end. After further disassembly, the crew take the now defenceless PLAYER1 and take turns stepping on him.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      388,
                      5,
                      'SEER_LYNCH',
                      'The crew group together around PLAYER1 and take a single ''sticky'' low yeald explosive from their belts. Simultaniously, they set them, then lob them onto PLAYER1. 3 seconds after sticking all over him, they explode, leaving little left but a few circuit boards and wires. Everyone celebrates the destuction of an evil Tribble until someone points out one of the parts look like a medical tricorder display...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      389,
                      5,
                      'SEER_LYNCH',
                      'The crew group together around PLAYER1 and take a single ''sticky'' low yeald explosive from their belts. Simultaniously, they set them, then lob them onto PLAYER1. 3 seconds after sticking all over him, they explode, leaving little left but a few circuit boards and wires. Everyone celebrates the destuction of an evil Tribble until someone points out one of the parts look like a medical tricorder display...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      390,
                      5,
                      'VILL_LYNCH',
                      'Sure they''ve discovered a killer Tribble in their midst, the rest of the crew turn towards PLAYER1 and draw their phasers. As PLAYER1 backs into a corner of the room, the phasers bleep as they are set to ''kill''. Within seconds, all that is left is a puddle of organic matter. Rather than the hunk of burnt metal they expected from a robot. "Oops." they exclaim in unison.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      391,
                      5,
                      'VILL_LYNCH',
                      'Having decided PLAYER1 should die, the crew grab him and frog-march him to the weapons bay of the ship. After stuffing him into a torpedo tube, they move to the bridge to watch. One of the crew presses the fire button and everyone watches the screen as PLAYER1 flies out into space and promptly goes *POP*. Robots don''t go *POP*...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      392,
                      5,
                      'START_GAME',
                      'PLAYER1 has activated the Secret Star Trek Mode! It''s Redshirts vs. Tribbles aboard an abandoned vessel.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      393,
                      5,
                      'START_GAME_NOTICE',
                      'Ensign PLAYER1 is organizing an away team.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      394,
                      5,
                      'ADDED',
                      'You have volunteered for the mission, and your role will be revealed shortly! MENTION'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      395,
                      5,
                      'GAME_STARTED',
                      'Welcome, cadet. ''/msg BOTNAME join'' to enlist in the Federation'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      396,
                      5,
                      'GAME_PLAYING',
                      'An away team is currently on a mission. Please wait for them to return before starting a new mission.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      397,
                      5,
                      'NOT_ENOUGH',
                      '5 members is the Federation minimum for an away team; request denied.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      398,
                      5,
                      'TWO_WOLVES',
                      'THERE ARE NUMBER TRIBBLES IN THIS GAME'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      399,
                      5,
                      'JOIN',
                      'PLAYER1 has put on a standard issue Federation uniform.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      400,
                      5,
                      'FLEE',
                      'PLAYER1 escaped on a functioning escape pod.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      401,
                      5,
                      'FLEE_ROLE',
                      'PLAYER1 escaped on a functioning escape pod. They were a ROLE.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      402,
                      5,
                      'FIRST_NIGHT',
                      'The crew set out on a search of the abandoned vessel, not knowing that they have been infiltrated by an imposter...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      403,
                      5,
                      'NIGHT_TIME',
                      'While everyone is uneasy about searching the vessel further, their ship requires more supplies, so everyone parts ways again in search of anything salvagable...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      404,
                      5,
                      'DAY_TIME',
                      'Team, you now have NUMBER seconds to discuss with your crewmates who you believe the WOLFPLURAL to be. After the discussion period, you may vote for who you think is the guilty party.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      405,
                      5,
                      'VOTE_TIME',
                      'Team, you have NUMBER seconds to select who you believe to be a Tribble. ''/msg BOTNAME vote <PLAYER1>'' with your vote, the PLAYER1 with the majority of the votes will be disposed of.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      406,
                      5,
                      'WOLF_CHOICE',
                      'You have chosen an unsuspecting PLAYER1 to ambush.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      407,
                      5,
                      'WOLVES_CHOICE',
                      'You have chosen PLAYER1 to ambush. We shall see who your fellow Tribble selects...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      408,
                      5,
                      'WOLVES_OTHER_CHOICE',
                      'PLAYER1 has chosen to kill PLAYER2.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      409,
                      5,
                      'WOLVES_OTHER_CHOICE',
                      'PLAYER2 is PLAYER1''s choice of victim this time.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      410,
                      5,
                      'WOLF_TARGET_DEAD',
                      'PLAYER1 is already dead, choose someone else.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      411,
                      5,
                      'ROLE_IS_KILLED',
                      'PLAYER1, the ROLE, has been killed!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      412,
                      5,
                      'ROLE_IS_LYNCHED',
                      'PLAYER1, the ROLE is lynched!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      413,
                      5,
                      'SEER_KILL',
                      'As the crew gather at the designated return point after searching the ship, a head-count reveals a missing member. A quick search of the nearby rooms reveal PLAYER1, their chest cavity empty, and tufts of fur surrounding the fatal wound. Next to the body is a medical tricorder, reading PLAYER1 as dead.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      414,
                      5,
                      'VILL_KILL',
                      'As the time nears for the team to regroup, an alarm sounds throughout the ship: "Bulkhead breached". Everyone rushes to the engineering room to erect a forcefield around the affected area. Once verified as safe again, the group go to see what had happened. They find PLAYER1 there, or about half of them, hanging from the wall. It appears the WOLFPLURAL had pierced the ship and attempted to feed PLAYER1 through it...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      415,
                      5,
                      'VILL_KILL',
                      'A shrill scream is heard throughout the deck. The source is revealed to be a team member who went to the turbolifts to access another deck. Looking inside the lift, the remains of PLAYER1 are visble, all over the interior. After some investigation, it is apparent the WOLFPLURAL had tampered with the turbolift so that it would travel at 10 times the regular speed, severely jolting anyone who would travel in it.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      416,
                      5,
                      'VILL_KILL',
                      'On their way back to the rendezvous point, the crew spot two legs sticking out of a bulkhead. They find the other side, and see PLAYER1, with a terrified look on their face, also sticking out of the bulkhead. Further examination reveals that the WOLFPLURAL had initiated a site-to-site transport and beamed PLAYER1 directly into the wall. It also reveals that human organs don''t function when mixed with wall.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      417,
                      5,
                      'NO_KILL',
                      'Upon returning to the rendezvous point, the standard head count is performed. Thankfully, everyone is present and correct. The WOLFPLURAL didn''t strike this time...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      418,
                      5,
                      'NOT_VOTED',
                      'As PLAYER1 opens their mouth to speak their mind about who is to blame, a Cardassian Puffer-Fly buzzes in to it. As PLAYER1 discovers, this is not good. As soon as they close their mouth, the fly expands to ten times it''s normal size in defense, painting the bulkheads a nice colour of brain.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      419,
                      5,
                      'NOT_VOTED_NOTICE',
                      'You have been removed from the game for not voting for two Lynch Votes in a row.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      420,
                      5,
                      'VOTED_FOR',
                      'PLAYER1 has voted to lynch PLAYER2!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      421,
                      5,
                      'CHANGE_VOTE',
                      'PLAYER1 has changed their vote to lynch PLAYER2!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      422,
                      5,
                      'VOTE_TARGET_DEAD',
                      'PLAYER1 tried to vote for the deceased PLAYER2.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      423,
                      5,
                      'WOLF_WIN',
                      'As PLAYER1 vaporizes the last member of the away team with their robot''s eye-lasers, they cackle evilly, the way Tribbles do, and return to the Tribble world to inform the Administration that another small part of the Galaxy belongs to the Tribble Empire.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      424,
                      5,
                      'WOLVES_WIN',
                      'With their robot''s retractable claws, the Tribbles disembowel the last surviving member of the away team, then return to their home planet to get their next assignment...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      425,
                      5,
                      'VILL_WIN',
                      'Having successfully eradicated the Tribble threat from the abandoned vessel, the away team collects all the supplies they need for the trip back to Federation Space, and home.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      426,
                      5,
                      'CONGR_VILL',
                      'Congratulations, Redshirts! You win!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      427,
                      5,
                      'CONGR_WOLF',
                      'Congratulations, PLAYER1! You win!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      428,
                      5,
                      'CONGR_WOLVES',
                      'Congratulations, Tribbles! You win!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      429,
                      5,
                      'SEER_DEAD',
                      'Being a doctor, you should know that being dead impedes your ability to operate your tricorder.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      430,
                      5,
                      'WOLF_DEAD',
                      'You''re dead, and no longer able to trouble the away team.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      431,
                      5,
                      'NOT_WOLF',
                      'You are not a Tribble!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      432,
                      5,
                      'NOT_SEER',
                      'You are not the Doctor!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      433,
                      5,
                      'WILL_SEE',
                      'Your tricorder will reveal the truth about PLAYER1 when the team gather again...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      434,
                      5,
                      'SEER_SEE',
                      'The tricorder displays PLAYER1''s DNA structure to be consistent with that of a ROLE!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      435,
                      5,
                      'SEER_GOT_KILLED',
                      'Your sudden lack of life seems to have stopped you being able to scan people...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      436,
                      5,
                      'SEER_TARGET_KILLED',
                      'A quick scan of PLAYER1''s gutted corpse shows that they are both dead and human.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      437,
                      5,
                      'SEER_TARGET_DEAD',
                      'You scan the corpse of PLAYER1 but are unable to ascertain the cause of death.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      438,
                      5,
                      'TALLY',
                      'Tallying Votes...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      439,
                      5,
                      'TIE',
                      'A tie. Randomly choosing one...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      440,
                      5,
                      'DYING_BREATH',
                      'PLAYER1 the ROLE is allowed a single line as their dying breath.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      441,
                      5,
                      'TIE_GAME',
                      'Suddenly, the vessel''s matter and antimatter fuels react uncontrollably, resulting in a warp core breach. It is only months after the ship has exploded that Starfleet inspectors are able to trace the problem back to a neglected replacement of the ship''s dilithium crystals.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      442,
                      5,
                      'FELLOW_WOLF',
                      'As you plan the demise of your partner, PLAYER2, you realize that even if you were to suceed in killing your fellow tribble as well as everyone else on the ship, your home planet would execute you anyway for the murder of an associate. This sobering thought prevents you from deciding to kill PLAYER2.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      443,
                      5,
                      'KILL_SELF',
                      'You appear to be developing suicidal tendancies.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      444,
                      5,
                      'SEE_SELF',
                      'You scan yourself, confirming your suspiscions that you are, in fact, the seer.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      445,
                      5,
                      'YOURE_DEAD',
                      'Unfortunately even Starfleet''s best medical facilities cannot allow you to vote from beyond the grave.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      446,
                      5,
                      'YOUVE_FLED',
                      'You can''t, you''ve already fled.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      447,
                      5,
                      'YOUR_ROLE',
                      'You''re a ROLE.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      448,
                      5,
                      'TEN_WARNING_VOTE',
                      'Ten seconds remaining before votes will be tailled.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      449,
                      5,
                      'TEN_WARNING_WOLF',
                      'You have ten seconds left.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      450,
                      5,
                      'TEN_WARNING_JOIN',
                      'Ten seconds before initializing transporter...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      451,
                      5,
                      'STOP_GAME',
                      'The holodeck exit appears, and everyone leaves.  The game is over.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      452,
                      5,
                      'VOTED_FOR_NOLYNCH',
                      'PLAYER1 voted to not lynch anyone.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      453,
                      5,
                      'VOTED_NO_LYNCH',
                      'Overcome with team spirit, the crew decides no-one should be killed.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      454,
                      5,
                      'CHANGE_VOTE_NOLYNCH',
                      'PLAYER1 changed their vote and instead decides nobody should be lynched.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      814,
                      6,
                      'OTHER_MASONS',
                      'You worked alongside PLAYER1 at the Company Office before it was closed due to the current crisis.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      812,
                      6,
                      'ROLE_MASON',
                      'West India Company Member'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      813,
                      6,
                      'MASON_DESCRIPTION',
                      'You are a member of the Geoctroyeerde Westindische Compagnie, the West India Company, looking out for the economic interests of the Kingdom of the Netherlands in the West Indies'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      808,
                      4,
                      'OTHER_MASONS',
                      'Your siblings are PLAYER1'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      809,
                      5,
                      'ROLE_MASON',
                      '3D Chress Club Member'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      810,
                      5,
                      'MASON_DESCRIPTION',
                      'You and your fellow red shirts play 3D Chress. You all know each other well enought to that they can''t be tribbles. They are PLAYER2'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      811,
                      5,
                      'OTHER_MASONS',
                      'Your 3D Chress Members are PLAYER1'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      804,
                      3,
                      'MASON_DESCRIPTION',
                      'You are part of the already bonded unit. The other members of the bonded units are PLAYER2'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      805,
                      3,
                      'OTHER_MASONS',
                      'Your fellow units are PLAYER1'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      806,
                      4,
                      'ROLE_MASON',
                      'Siblings'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      807,
                      4,
                      'MASON_DESCRIPTION',
                      'You are part of a family. The other siblings are PLAYER2'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      801,
                      2,
                      'MASON_DESCRIPTION',
                      'You are part of the Degu family. Your fellow siblings are PLAYER2'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      802,
                      2,
                      'OTHER_MASONS',
                      'Your siblings are PLAYER1'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      803,
                      3,
                      'ROLE_MASON',
                      'United Units'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      796,
                      1,
                      'ROLE_MASON',
                      'Siblings'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      798,
                      1,
                      'MASON_DESCRIPTION',
                      'Your mother loved you both very much, x, y, but she could never remember your father''s names.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      799,
                      1,
                      'OTHER_MASONS',
                      'Your siblings are PLAYER1'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      800,
                      2,
                      'ROLE_MASON',
                      'Siblings'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      795,
                      1,
                      'TALLY',
                      'Please wait while the Degus tally your votes. It will take between 2 to 8 seconds. '
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      794,
                      6,
                      'CHANGE_VOTE_NOLYNCH',
                      'PLAYER1 changed their vote and instead decides nobody should be executed.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      793,
                      6,
                      'VOTED_NO_LYNCH',
                      'The villagers decide together that nobody should be executed.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      792,
                      6,
                      'VOTED_FOR_NOLYNCH',
                      'PLAYER1 votes that they shouldn''t execute anyone.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      791,
                      6,
                      'STOP_GAME',
                      'A ship arrives, and everyone leaves town.  The game is over.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      788,
                      6,
                      'TEN_WARNING_WOLF',
                      'Ye have but ten seconds left!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      789,
                      6,
                      'TEN_WARNING_WOLF',
                      'Ar! Ye have ten seconds left!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      790,
                      6,
                      'TEN_WARNING_JOIN',
                      'Ten seconds before landing at port...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      786,
                      6,
                      'TEN_WARNING_VOTE',
                      'Ten seconds remaining before votes will be tallied.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      787,
                      6,
                      'TEN_WARNING_VOTE',
                      'Ye have ten seconds to get yer votes in.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      784,
                      6,
                      'YOUR_ROLE',
                      'ARR! Ye be a ROLE!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      785,
                      6,
                      'YOUR_ROLE',
                      'Yo Ho! Yo Ho! A ROLE''s life for ye!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      783,
                      6,
                      'YOUR_ROLE',
                      'You''re a ROLE.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      782,
                      6,
                      'YOUVE_FLED',
                      'You can''t, you''ve already fled.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      781,
                      6,
                      'YOURE_DEAD',
                      'Unfortunately you are not in posession of any cursed voodoo artifacts, and therefore can no longer walk among the living.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      780,
                      6,
                      'SEE_SELF',
                      'You look yourself up in the towns archives, confirming your suspiscions that you are, in fact, the Magistrate.  You can''t help wondering whether your time would''ve been better spent looking up someone else.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      779,
                      6,
                      'KILL_SELF',
                      'Ye appear to be a bit suicidal.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      778,
                      6,
                      'FELLOW_WOLF',
                      'Ye be tryin'' to kill a fellow pirate.  PLAYER1 reminds ye of the pirate code, and ye come to yer senses.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      777,
                      6,
                      'TIE_GAME',
                      'Suddenly, a volcano erupts, and lava flows down onto the village, killing everyone.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      776,
                      6,
                      'DYING_BREATH',
                      'PLAYER1 the ROLE be allowed to say but one thing before they die.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      775,
                      6,
                      'DYING_BREATH',
                      'PLAYER1 the ROLE is allowed a single line as their dying breath.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      774,
                      6,
                      'TIE',
                      'Avast! The villagers flip a doubloon to decide...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      773,
                      6,
                      'TALLY',
                      'Arr.. countin'' yer votes...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      772,
                      6,
                      'SEER_TARGET_DEAD',
                      'The death certificate confirms that PLAYER1 was a humble villager.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      771,
                      6,
                      'SEER_TARGET_KILLED',
                      'The records confirm that PLAYER1 was a humble villager.  You add the death certificate to the file.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      770,
                      6,
                      'SEER_GOT_KILLED',
                      'You''d almost found out the information you needed when your life ended.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      769,
                      6,
                      'SEER_SEE',
                      'You find PLAYER1''s name on an offical list of ROLEs.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      768,
                      6,
                      'SEER_SEE',
                      'The records confirm it, PLAYER1 has spent their whole life as a ROLE!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      767,
                      6,
                      'WILL_SEE',
                      'You will look through the books for any mention of PLAYER1''s past when the villagers gather again...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      766,
                      6,
                      'WILL_SEE',
                      'You will carefully peruse the records, and discover the truth about PLAYER1 when the villagers gather again...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      765,
                      6,
                      'NOT_SEER',
                      'You are not the Magistrate!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      764,
                      6,
                      'NOT_WOLF',
                      'You are not a Pirate!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      763,
                      6,
                      'WOLF_DEAD',
                      'Ye be dead, and are no longer able to trouble the island of Aruba.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      761,
                      6,
                      'CONGR_WOLVES',
                      'Congratulations, Pirates! You win!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      762,
                      6,
                      'SEER_DEAD',
                      'Even a magistrate under employ of the King cannot investigate while dead.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      759,
                      6,
                      'CONGR_VILL',
                      'Congratulations, Villagers! You win!  HUZZAH!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      760,
                      6,
                      'CONGR_WOLF',
                      'Congratulations, PLAYER1! You win!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      758,
                      6,
                      'VILL_WIN',
                      'The pirate menace has been erradicated from Aruba! The villagers relax and hold a festival in the tavern to celebrate.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      757,
                      6,
                      'VILL_WIN',
                      'Having successfully erradicated all pirates from the island of Aruba, the villagers relax and return to work.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      756,
                      6,
                      'WOLVES_WIN',
                      'The pirates shoot the final villager and empty the town coffers into their treasure chests, before stealing a ship and setting sail for their hideout.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      755,
                      6,
                      'WOLF_WIN',
                      'PLAYER1 kills the last villager and buries their ill-gotten booty in the now deserted village square, marking the place with an X on their map.  They walk off towards the docks whistling, a bottle of rum in their hand.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      754,
                      6,
                      'WOLF_WIN',
                      'PLAYER1 runs the last villager through, the corpse falling into a milk churn.  "How appropriate, you fought like a cow," the pirate says with a grin, before stealing a ship and sailing off into the sunset.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      752,
                      6,
                      'VOTE_TARGET_DEAD',
                      'PLAYER1 tried to vote for the deceased PLAYER2.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      753,
                      6,
                      'WOLF_WIN',
                      'As PLAYER1 slits the throat of the last villager, they look across the town with a grin.  "Let this be known as the day you *almost* caught Captain PLAYER1", they say, before steal.. er commandeering a vessel and sailing to the next port.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      749,
                      6,
                      'VOTED_FOR',
                      'PLAYER1 has voted to send PLAYER2 off to meet Davey Jones!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      750,
                      6,
                      'VOTED_FOR',
                      'PLAYER1 thinks PLAYER2 smells suspisciously of rum and votes that they''re are the pirate!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      751,
                      6,
                      'CHANGE_VOTE',
                      'PLAYER1 has changed their vote to execute PLAYER2!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      747,
                      6,
                      'NOT_VOTED_NOTICE',
                      'You have been removed from the game for not voting for two Lynch Votes in a row.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      748,
                      6,
                      'VOTED_FOR',
                      'PLAYER1 has voted to execute PLAYER2!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      746,
                      6,
                      'NOT_VOTED',
                      'PLAYER1 notices spots forming on their thighs and legs, and their gums feel spongy.  They begin bleeding from all mucous membranes and soon fall to the fround with open suppurating wounds, spitting teeth onto the floor.  PLAYER1 then collapses, dead, another victim of skurvy.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      745,
                      6,
                      'NO_KILL',
                      'When they reach the tavern the villagers realise they''re all still alive.  The WOLFPLURAL didn''t steal any of their doubloons tonight.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      744,
                      6,
                      'NO_KILL',
                      'Upon returning to the tavern, the villagers count their blessings as everyone is still alive. The WOLFPLURAL didn''t strike this time...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      743,
                      6,
                      'VILL_KILL',
                      'As it approaches the time to rendezvous in the tavern, a villager notices something unusual in the bay.  They run out along the dock and see a dropped lantern.  Looking around further they soon find the corpse of PLAYER1 floating face down in the water, with a musket-ball wound in the back.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      742,
                      6,
                      'VILL_KILL',
                      'A shout calls the villagers out of the tavern and towards the plantation.  A villager meets them there and shows them to an area of freshly dug earth.  After a few moments the villagers see a hand sticking out of the ground, wearing PLAYER1''s distinctive ring.  It appears the WOLFPLURAL had buried them alive.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      741,
                      6,
                      'VILL_KILL',
                      'One of the villagers who was out fishing was returning to the docks with their day''s catch when their oars caught something in the water.  They threw their nets into the water and with a struggle brought in the corpse of PLAYER1 - throat slit ear to ear..'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      740,
                      6,
                      'VILL_KILL',
                      'It nears time to return to the tavern, and the villagers return to find the corpse of PLAYER1, cutlass in hand with slashes across the chest. Obviously there had been quite a fight, as the tavern is in great disarray, but in the end the WOLFPLURAL clearly came better off.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      739,
                      6,
                      'VILL_KILL',
                      'A shrill scream is heard from the village shop. The source is revealed to be a villager who went to the shop to check on PLAYER1''s whereabouts.  On the floor is the body of PLAYER1, with a neat musket-ball hole in their forehead.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      738,
                      6,
                      'VILL_KILL',
                      'As the villagers gather in the tavern, it soon becomes clear that PLAYER1 is missing..  A quick search of the town reveals nothing, so the villagers decide to drink to PLAYER1''s health, but the beer comes out a deep red colour.  Further inspection reveals the corpse of PLAYER1, cut open from stomach to sternum...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      737,
                      6,
                      'SEER_KILL',
                      'The villagers gather together in the tavern, but PLAYER1 is not among them.  A search of the town is conducted, and soon a shout comes from the boarded up magistrate''s office.  PLAYER1''s head, wearing a long powdered wig, is nailed to the door.  It appears they had tried to contront the WOLFPLURAL on their own.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      736,
                      6,
                      'SEER_KILL',
                      'On their way back to the tavern, a villager notices an unusual scarecrow on the plantation.  Closer inspection reveals something more grizzly than expected - the scarecrow is the corpse of PLAYER1!  On their forehead the WOLFPLURAL had pressed the official seal of Aruba!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      734,
                      6,
                      'ROLE_IS_LYNCHED',
                      'PLAYER1, the ROLE is executed!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      735,
                      6,
                      'SEER_KILL',
                      'It soon becomes clear that PLAYER1 is missing from the meeting..  A quick search reveals nothing, so the villagers decide to drink to PLAYER1''s health, but the beer comes out red; Further inspection reveals the bloody corpse of PLAYER1, with an official powdered wig nailed to its head.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      731,
                      6,
                      'WOLVES_OTHER_CHOICE',
                      'PLAYER2 is PLAYER1''s choice of booty this time.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      732,
                      6,
                      'WOLF_TARGET_DEAD',
                      'PLAYER1''s already with Old Hob.. ye be needin'' to choose somebody else.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      733,
                      6,
                      'ROLE_IS_KILLED',
                      'Avast! PLAYER1, the ROLE, has been killed!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      728,
                      6,
                      'WOLF_CHOICE',
                      'Arr! Ye be choosin'' PLAYER1 to kill take their booty.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      729,
                      6,
                      'WOLVES_CHOICE',
                      'Ye''ve chosen PLAYER1 to ambush. Ye shall be seein'' who yer fellow pirate selects...'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      730,
                      6,
                      'WOLVES_OTHER_CHOICE',
                      'PLAYER1 declares no quarter should be given to PLAYER2.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      727,
                      6,
                      'WOLF_CHOICE',
                      'Arr! Ye''ve chosen PLAYER1 to ambush and steal their doubloons.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      726,
                      6,
                      'VOTE_TIME',
                      'Villagers, ye have NUMBER seconds to select who ye think the pirate be! ''/msg BOTNAME vote <player>'' with your vote, the player with most of the votes will be sent to Davey Jones.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      725,
                      6,
                      'VOTE_TIME',
                      'Villagers, you have NUMBER seconds to select who you think the pirate is. ''/msg BOTNAME vote <player>'' with your vote, the player with the majority of the votes will be executed for piracy.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      724,
                      6,
                      'DAY_TIME',
                      'Villagers, ye be havin'' NUMBER seconds to discuss who ye feel the WOLFPLURAL to be. After this meetin'', ye may vote for who ye think the skallywag be.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      723,
                      6,
                      'DAY_TIME',
                      'Villagers, you now have NUMBER seconds to discuss who you feel WOLFPLURAL to be. After the discussion period, you may vote for who you think the skurvy dog is.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      722,
                      6,
                      'NIGHT_TIME',
                      'The villagers all head back to their normal duties, knowing all the time that those working alongside them may still be a skurvy pirate..'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      721,
                      6,
                      'NIGHT_TIME',
                      'The villagers nervously head off to their normal duties on the plantation, the village and around the docks.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      720,
                      6,
                      'FIRST_NIGHT',
                      'The villagers of the Caribbean island of Aruba live out their day peacefully, unaware that a Pirate is among them, searching for doubloons.. and blood.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      718,
                      6,
                      'FLEE_ROLE',
                      'PLAYER1 set sail for home. They were a ROLE.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      719,
                      6,
                      'FIRST_NIGHT',
                      'The villagers of the Caribbean island live out their lives as normal, unaware that a skurvy Pirate has come ashore in search of gold, jewels and blood.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      716,
                      6,
                      'JOIN',
                      'PLAYER1 be headed to this isle of Aruba. NUMBER are now on board.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      717,
                      6,
                      'FLEE',
                      'PLAYER1 set sail for home.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      714,
                      6,
                      'TWO_WOLVES',
                      'EGADS! THERE ARE NUMBER LOATHSOME PIRATES IN THIS GAME!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      715,
                      6,
                      'JOIN',
                      'PLAYER1 has joined the voyage to Aruba.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      712,
                      6,
                      'NOT_ENOUGH',
                      'Ye be needin at least 5 skurvy swabs fer a voyage to Aruba.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      713,
                      6,
                      'TWO_WOLVES',
                      'ARR! THAR BE NUMBER SKURVY PIRATES IN THIS GAME!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      711,
                      6,
                      'NOT_ENOUGH',
                      'Need at least 5 people for a journey to the Caribbean.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      709,
                      6,
                      'GAME_PLAYING',
                      'People are currently hunting pirates on Aruba; wait for their return.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      710,
                      6,
                      'GAME_PLAYING',
                      'Arr! Folks be huntin'' pirates, ye must wait.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      707,
                      6,
                      'GAME_STARTED',
                      'Welcome! A voyage to the Caribbean is underway. ''/msg BOTNAME join'' to set sail.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      708,
                      6,
                      'GAME_STARTED',
                      'Avast! Folks be headin'' to hunt pirates on Aruba! ''/msg BOTNAME join'' to set sail.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      706,
                      6,
                      'ADDED',
                      'Ye be on yer way to Aruba!  Yer role will be assigned shortly MENTION'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      704,
                      6,
                      'START_GAME_NOTICE',
                      'PLAYER1 sets sail for Aruba.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      705,
                      6,
                      'ADDED',
                      'You have signed up for the voyage to Aruba.  Roles will be assigned shortly MENTION'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      703,
                      6,
                      'START_GAME_NOTICE',
                      'PLAYER1 is off to the Caribbean.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      702,
                      6,
                      'START_GAME',
                      'PLAYER1 has raised their colours and set sail for the Caribbean!  The villagers have to discover the pirate in their midst..'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      701,
                      6,
                      'START_GAME',
                      'PLAYER1 has grabbed the wheel and set sail for the island of Aruba!  The villagers have to discover the pirate in their midst..'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      700,
                      6,
                      'VILL_LYNCH',
                      'The villagers catch up with PLAYER1 at the end of the docks, where a single plank stretches out over the shark-infested water.. PLAYER1 has no choice but to back along this plank, and soon they fall to their death. "A pirate would surely have done something to escape," is the general concensus.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      699,
                      6,
                      'VILL_LYNCH',
                      'PLAYER1 searches for an escape, and runs towards the door and out into the street.  They make it as far as the village fountain before they are caught from behind and held face down in the water till they drown.  The villagers cheer, but a post-mortem check reveals nothing incriminating..'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      698,
                      6,
                      'VILL_LYNCH',
                      'PLAYER1 struggles madly, somehow escapes the villagers'' clutches, and runs towards the docks. They are half-way to a ship when they hear the gunshots; blood pours from PLAYER1''s wound.  A post-mortem check reveals nothing incriminating, and that PLAYER1 was running towards their *own* ship..'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      697,
                      6,
                      'VILL_LYNCH',
                      'PLAYER1 is grabbed, and they protest loudly as their hands are tied and they''re dragged to the gallows in the island''s fort.  The stool is kicked away from under PLAYER1''s feet, and they hang for a few seconds, before falling still.  The corpse is inspected, but nothing incriminating is found..'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      696,
                      6,
                      'SEER_LYNCH',
                      'PLAYER1 searches for an escape, and runs towards the door and out into the street.  They make it as far as the village fountain before they are caught from behind and held face down in the water till they drown.  The villagers cheer is cut short when they see PLAYER1''s official powdered wig..'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      695,
                      6,
                      'SEER_LYNCH',
                      'PLAYER1 struggles and somehow escapes the clutches of the villagers and runs towards the docks. They are half-way to a ship when they hear the gunshots; blood pours from PLAYER1''s wound.  With their last strength, PLAYER1 pulls a scroll bearing the King''s seal from their jacket..'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      694,
                      6,
                      'SEER_LYNCH',
                      'PLAYER1''s hands are tied, and they''re dragged to the gallows at the island''s fort.  A drum roll sounds and the stool is kicked away from under PLAYER1''s feet.  Their feet jerk for a few seconds, then fall still.  An inspection reveals official documents bearing the King''s seal..'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      693,
                      6,
                      'WOLF_LYNCH',
                      'The villagers advance on PLAYER1, who draws their sword.  PLAYER1 spins their sword effortlessly, throwing it from one hand to the other, and grins, showing a gold tooth. "Arr.. D''ye be feelin'' lucky?", they ask, seconds before a shot is fired, hitting PLAYER1 square in the forehead.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      692,
                      6,
                      'WOLF_LYNCH',
                      'PLAYER1 struggles and somehow escapes the clutches of the villagers and runs towards the docks. They are half-way to a ship when they hear the gunshots; blood pours from PLAYER1''s wound.  With their last strength, PLAYER1 pulls a flintlock pistol from their jacket, and collapses, dead.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      691,
                      6,
                      'WOLF_LYNCH',
                      'PLAYER1''s hands are tied, and they''re dragged to the gallows at the island''s fort.  A drum roll sounds and the stool is kicked away from under PLAYER1''s feet.  Their feet jerk for a few seconds, then fall still.  The corpse is inspected, and a ''P'' is clearly branded on their forearm.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      690,
                      6,
                      'WOLF_LYNCH',
                      'As the villagers advance, PLAYER1 draws his sword.  They swing from the docks onto the deck of a ship.  Their ship seem almost certain to escape, but cannon fire from the island''s fort reduces it to splinters.  "Let this be known as the day Captain PLAYER1 *almost* escaped," a villager says.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      689,
                      6,
                      'NO_LYNCH',
                      'The villagers cannot determine who amongst them might be a pirate and decide that nobody should be held accountable at this time.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      686,
                      6,
                      'OTHER_WOLF',
                      'Ye''re a pirate, along with fellow cur PLAYER1.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      687,
                      6,
                      'OTHER_WOLF',
                      'Ye be a pirate, along with fellow skurvy cur PLAYER1.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      688,
                      6,
                      'NO_LYNCH',
                      'The villagers are unable to reach a decision about who is responsible, and decide that nobody should be executed.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      685,
                      6,
                      'WOLVES_DESCRIPTION',
                      'Ye and PLAYER2 both be part of a pirate crew searching fer victims and doubloons on the island of Aruba!  Ye must keep yer piratey identities a secret and work together to avoid the gallows!'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      684,
                      6,
                      'WOLVES_DESCRIPTION',
                      'Ye be a pirate, a skallywag.  Ye sail the seven seas lookin'' fer yer fortune, and here on Aruba ye be thinkin'' ye''ve found it.  Ye must work with yer fellow pirate, PLAYER2, but keep yer piratey pasts a secret to avoid a certain trip to the gallows.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      683,
                      6,
                      'SEER_DESCRIPTION',
                      'The current dangerous climate on Aruba has caused you to desert your official office and hide amongst the common villagers, but you are not one of them.  You are a Magistrate with access to records on all villagers and pirates in the Caribbean.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      682,
                      6,
                      'SEER_DESCRIPTION',
                      'You are the village magistrate, taken to hiding among the villagers in an attempt to avoid assasination.  You have no redcoats to protect you, but you do have access to information and records on all the villagers and suspected pirates in the Caribbean.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      681,
                      6,
                      'WOLF_DESCRIPTION',
                      'ARR! Ye be one of the scourge of the seven seas, raidin'' and lootin'' from port to port, and here on Aruba is where ye find yerself right now.  Ye must keep yer piratey nature secret to avoid a trip to the gallows.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      680,
                      6,
                      'WOLF_DESCRIPTION',
                      'Ye be a pirate, a skallywag.  Ye sail the seven seas lookin'' fer yer fortune, and here on Aruba ye be thinkin'' ye''ve found it.  Ye must however keep yer piratey past secret to avoid a short drop and a sudden stop.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      679,
                      6,
                      'VILL_DESCRIPTION',
                      'You''re a regular villager turned vigilante, looking amongst the village for signs of piracy.  Using nothing but your wits, you must discover who amongst you is really a pirate..'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      678,
                      6,
                      'VILL_DESCRIPTION',
                      'You are a regular villager working in the plantation on the island of Aruba.  Using nothing but your wits, you must seek out who amongst you is a scurvy pirate in disguise..'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      677,
                      6,
                      'SEER_INSTRUCTIONS',
                      'As the Magistrate, you have NUMBER seconds to decide who you wish to check for any past records of piracy. Direct Message BOTNAME with ''see <player>'' to look them up in the official records.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      676,
                      6,
                      'SEER_INSTRUCTIONS',
                      'Magistrate, you have but NUMBER seconds to choose who you wish to look up in the records to check for a past record of piracy.Send a DM to BOTNAME with ''see <player>'' to look them up in the logs.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      675,
                      6,
                      'WOLVES_INSTRUCTIONS',
                      'Pirates, ye have NUMBER seconds to agree on who ye want to split from stem to stern. Type ''/msg BOTNAME kill <player>'' to choose yer victim.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      674,
                      6,
                      'WOLF_INSTRUCTIONS',
                      'Avast! Ye have NUMBER seconds to choose who it is ye want to be sendin'' to meet Old Hob.  Type ''!kill <player>'' to choose yer victim.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      672,
                      6,
                      'ROLE_VILL',
                      'Villager'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      673,
                      6,
                      'WOLF_INSTRUCTIONS',
                      'Arr, ye be havin'' NUMBER seconds to decide who ye want to send to Davey Jones'' locker.  Type ''!kill <player>'' to choose yer victim.'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      670,
                      6,
                      'ROLE_WOLF',
                      'Pirate'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      671,
                      6,
                      'ROLE_SEER',
                      'Magistrate'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      668,
                      6,
                      'ONEWOLF',
                      'Pirate'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      669,
                      6,
                      'MANY_WOLVES',
                      'Pirates'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      975,
                      1,
                      'STATE_KILLED',
                      'has been killed'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      976,
                      1,
                      'STATE_ALIVE',
                      'Still Alive'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      978,
                      1,
                      'STATE_FLED',
                      'Has Ran Away'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      979,
                      1,
                      'AVATAR',
                      'https://avatarfiles.alphacoders.com/471/47141.jpg'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      980,
                      1,
                      'AVATAR',
                      'https://vignette1.wikia.nocookie.net/free-realms-roleplay/images/d/d9/Darkpaw.jpg/revision/latest?cb=20131203001703'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      981,
                      1,
                      'AVATAR',
                      'https://avatarfiles.alphacoders.com/100/100826.png'
                  );

INSERT INTO theme (
                      id_key,
                      theme_id,
                      theme_name_id,
                      theme_text
                  )
                  VALUES (
                      982,
                      1,
                      'AVATAR',
                      'https://i.pinimg.com/736x/18/4c/9b/184c9bea9fee748aeb85d6e8d16ffdb6--io.github.mannjamin.ducky.werewolf-art-werewolves.jpg'
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

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               1,
                               'ONEWOLF',
                               NULL,
                               0,
                               'One Wolf',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               2,
                               'MANY_WOLVES',
                               NULL,
                               0,
                               'Many Wolves',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               3,
                               'ROLE_WOLF',
                               NULL,
                               0,
                               'Role Wolf',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               4,
                               'ROLE_SEER',
                               NULL,
                               0,
                               'Role Seer',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               5,
                               'ROLE_VILL',
                               NULL,
                               0,
                               'Role Villager',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               6,
                               'ROLE_MASON',
                               NULL,
                               0,
                               'Role Mason',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               7,
                               'WOLF_INSTRUCTIONS',
                               NULL,
                               0,
                               'Wolf Instructions',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               8,
                               'SEER_INSTRUCTIONS',
                               NULL,
                               0,
                               'Seer Instructions',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               9,
                               'WOLVES_INSTRUCTIONS',
                               NULL,
                               0,
                               'Wolves Instructions',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               10,
                               'VILL_DESCRIPTION',
                               NULL,
                               0,
                               'Villager Descriptions',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               11,
                               'WOLF_DESCRIPTION',
                               NULL,
                               0,
                               'Wolf Description',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               12,
                               'SEER_DESCRIPTION',
                               NULL,
                               0,
                               'Seer Description',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               13,
                               'MASON_DESCRIPTION',
                               NULL,
                               0,
                               'Mason Description',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               14,
                               'WOLVES_DESCRIPTION',
                               NULL,
                               0,
                               'Wolves Description',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               15,
                               'OTHER_WOLF',
                               NULL,
                               0,
                               'Other Wolf',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               16,
                               'OTHER_MASONS',
                               NULL,
                               0,
                               'Other Masons',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               17,
                               'NO_LYNCH',
                               NULL,
                               0,
                               'No Lynch',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               18,
                               'WOLF_LYNCH',
                               NULL,
                               0,
                               'Wolf Lynch',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               19,
                               'SEER_LYNCH',
                               NULL,
                               0,
                               'Seer Lynch',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               20,
                               'VILL_LYNCH',
                               NULL,
                               0,
                               'Villager Lynch',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               21,
                               'START_GAME',
                               NULL,
                               0,
                               'Start Game',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               22,
                               'START_GAME_NOTICE',
                               NULL,
                               0,
                               'Start Game Notice',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               23,
                               'ADDED',
                               NULL,
                               0,
                               'Added',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               24,
                               'GAME_STARTED',
                               NULL,
                               0,
                               'Game Started',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               25,
                               'GAME_PLAYING',
                               NULL,
                               0,
                               'Game Playing',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               26,
                               'NOT_ENOUGH',
                               NULL,
                               0,
                               'Not Enough',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               27,
                               'TWO_WOLVES',
                               NULL,
                               0,
                               'Two Wolves',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               28,
                               'JOIN',
                               NULL,
                               0,
                               'Join',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               29,
                               'FLEE',
                               NULL,
                               0,
                               'Flee',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               30,
                               'FLEE_ROLE',
                               NULL,
                               0,
                               'Flee Role',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               31,
                               'FIRST_NIGHT',
                               NULL,
                               0,
                               'First Night',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               32,
                               'NIGHT_TIME',
                               NULL,
                               0,
                               'Night Time',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               33,
                               'DAY_TIME',
                               NULL,
                               0,
                               'Daytime',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               34,
                               'VOTE_TIME',
                               NULL,
                               0,
                               'Vote Time',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               35,
                               'WOLF_CHOICE',
                               NULL,
                               0,
                               'Wolf Choice',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               36,
                               'WOLVES_CHOICE',
                               NULL,
                               0,
                               'Wolves Choice',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               37,
                               'WOLVES_OTHER_CHOICE',
                               NULL,
                               0,
                               'Wolves'' Other Choice',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               38,
                               'WOLF_TARGET_DEAD',
                               NULL,
                               0,
                               'Wolf Target Dead',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               39,
                               'ROLE_IS_KILLED',
                               NULL,
                               0,
                               'Role is Killed',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               40,
                               'SEER_KILL',
                               NULL,
                               0,
                               'Seer Killed',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               41,
                               'VILL_KILL',
                               NULL,
                               0,
                               'Villager Killed',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               42,
                               'NO_KILL',
                               NULL,
                               0,
                               'No Kill',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               43,
                               'NOT_VOTED',
                               NULL,
                               0,
                               'Not Voted',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               44,
                               'NOT_VOTED_NOTICE',
                               NULL,
                               0,
                               'Not Voted Notice',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               45,
                               'VOTED_FOR',
                               NULL,
                               0,
                               'Voted For',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               46,
                               'CHANGE_VOTE',
                               NULL,
                               0,
                               'Change Vote',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               47,
                               'VOTE_TARGET_DEAD',
                               NULL,
                               0,
                               'Vote Target Dead',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               48,
                               'WOLF_WIN',
                               NULL,
                               0,
                               'Wolf Win',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               49,
                               'WOLVES_WIN',
                               NULL,
                               0,
                               'Wolves Win',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               50,
                               'VILL_WIN',
                               NULL,
                               0,
                               'Villagers Win',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               51,
                               'CONGR_VILL',
                               NULL,
                               0,
                               'Congratulations Villagers',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               52,
                               'CONGR_WOLVES',
                               NULL,
                               0,
                               'Congratulations Wolves',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               53,
                               'WOLF_DEAD',
                               NULL,
                               0,
                               'Wolf Dead',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               54,
                               'SEER_DEAD',
                               NULL,
                               0,
                               'Seer Dead',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               55,
                               'NOT_WOLF',
                               NULL,
                               0,
                               'Not Wolf',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               56,
                               'NOT_SEER',
                               NULL,
                               0,
                               'Not Seer',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               57,
                               'SEER_SEE',
                               NULL,
                               0,
                               'Seer See',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               58,
                               'SEER_GOT_KILLED',
                               NULL,
                               0,
                               'Seer Got Killed',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               59,
                               'SEER_TARGET_KILLED',
                               NULL,
                               0,
                               'Seer Target Killed',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               60,
                               'SEER_TARGET_DEAD',
                               NULL,
                               0,
                               'Seer Target Dead',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               61,
                               'TALLY',
                               NULL,
                               0,
                               'Tally',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               62,
                               'TIE',
                               NULL,
                               0,
                               'Tie',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               63,
                               'DYING_BREATH',
                               NULL,
                               0,
                               'Dying Breath',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               64,
                               'FELLOW_WOLF',
                               NULL,
                               0,
                               'Fellow Wolf',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               65,
                               'KILL_SELF',
                               NULL,
                               0,
                               'Kill Self',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               66,
                               'SEE_SELF',
                               NULL,
                               0,
                               'See Self',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               67,
                               'YOURE_DEAD',
                               NULL,
                               0,
                               'You''re Dead',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               68,
                               'YOUVE_FLED',
                               NULL,
                               0,
                               'You''ve Fled',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               69,
                               'YOUR_ROLE',
                               NULL,
                               0,
                               'Your Role',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               70,
                               'TEN_WARNNG_JOIN',
                               NULL,
                               0,
                               'Ten Second Warning Join',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               71,
                               'TEN_WARNING_VOTE',
                               NULL,
                               0,
                               'Ten Second Warning Vote',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               72,
                               'TEN_WARNING_WOLF',
                               NULL,
                               0,
                               'Ten Second Warning Role',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               73,
                               'STOP_GAME',
                               NULL,
                               0,
                               'Stop Game',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               74,
                               'VOTED_FOR_NOLYNCH',
                               NULL,
                               0,
                               'Voted For No Lynch',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               75,
                               'CHANGE_VOTE_NOLYNCH',
                               NULL,
                               0,
                               'Change Vote to No Lynch',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               76,
                               'VOTED_NO_LYNCH',
                               NULL,
                               0,
                               'Voted No Lynch',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               77,
                               'ROLE_IS_LYNCHED',
                               NULL,
                               0,
                               'Role is Lynched',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               78,
                               'CONGR_WOLF',
                               NULL,
                               0,
                               'Congratulations Wolf',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               79,
                               'WILL_SEE',
                               NULL,
                               0,
                               'Will See',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               80,
                               'TIE_GAME',
                               NULL,
                               0,
                               'Tie Game',
                               ''
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               81,
                               'STATE_ALIVE',
                               NULL,
                               0,
                               'Player State is Alive',
                               ' '
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               82,
                               'STATE_KILLED',
                               NULL,
                               0,
                               'Player State is Killed',
                               ' '
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               83,
                               'STATE_FLED',
                               NULL,
                               0,
                               'Player has fled the game',
                               ' '
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               84,
                               'AVATAR_GAME',
                               NULL,
                               0,
                               'URL for Avatar game',
                               ' '
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               85,
                               'AVATAR_NOTICE',
                               NULL,
                               0,
                               'URL for Avatar notice',
                               ' '
                           );

INSERT INTO theme_defaults (
                               id_key,
                               theme_name_id,
                               defaults,
                               cat_page,
                               theme_full_name,
                               theme_description
                           )
                           VALUES (
                               86,
                               'AVATAR_NARR',
                               NULL,
                               0,
                               'URL for Avatar for Narration',
                               ' '
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
                               DEFAULT 0,
    avatar        STRING
);

INSERT INTO theme_detail (
                             id_key,
                             theme_name,
                             theme_disc,
                             theme_author,
                             theme_created,
                             theme_modifed,
                             played_count,
                             loaded,
                             avatar
                         )
                         VALUES (
                             1,
                             'Werewolf',
                             'Werewolf',
                             'Unknown',
                             '2007-11-18 22:53:47',
                             '2007-11-18 22:53:47',
                             0,
                             1,
                             'https://avatarfiles.alphacoders.com/471/47141.jpg'
                         );

INSERT INTO theme_detail (
                             id_key,
                             theme_name,
                             theme_disc,
                             theme_author,
                             theme_created,
                             theme_modifed,
                             played_count,
                             loaded,
                             avatar
                         )
                         VALUES (
                             3,
                             'World War',
                             'Cross war torn lands, you vs the enemy',
                             'AbbyTheRat',
                             '2007-12-01 20:23:00',
                             '2007-12-01 20:23:00',
                             0,
                             1,
                             NULL
                         );

INSERT INTO theme_detail (
                             id_key,
                             theme_name,
                             theme_disc,
                             theme_author,
                             theme_created,
                             theme_modifed,
                             played_count,
                             loaded,
                             avatar
                         )
                         VALUES (
                             2,
                             'Snitch',
                             'There''s a Snitch infiltrating the Mafia',
                             'WPPWAH',
                             '2007-12-01 16:40:00',
                             '2007-12-01 16:40:00',
                             0,
                             1,
                             NULL
                         );

INSERT INTO theme_detail (
                             id_key,
                             theme_name,
                             theme_disc,
                             theme_author,
                             theme_created,
                             theme_modifed,
                             played_count,
                             loaded,
                             avatar
                         )
                         VALUES (
                             4,
                             'Mafia',
                             'With the Mafia claiming a stake in town, there''s no end to the citizens'' woe.',
                             'WPPWAH',
                             '2007-12-01 21:30:00',
                             '2007-12-01 21:30:00',
                             0,
                             1,
                             NULL
                         );

INSERT INTO theme_detail (
                             id_key,
                             theme_name,
                             theme_disc,
                             theme_author,
                             theme_created,
                             theme_modifed,
                             played_count,
                             loaded,
                             avatar
                         )
                         VALUES (
                             5,
                             'Star Trek',
                             'Ouuu~',
                             'Unknown',
                             '2007-12-01 23:00:00',
                             '2007-12-01 23:00:00',
                             0,
                             1,
                             NULL
                         );

INSERT INTO theme_detail (
                             id_key,
                             theme_name,
                             theme_disc,
                             theme_author,
                             theme_created,
                             theme_modifed,
                             played_count,
                             loaded,
                             avatar
                         )
                         VALUES (
                             6,
                             'Pirates',
                             'Yarrrr, I be a pirate. Catch me booty, if ye can.',
                             'Xaphod',
                             '2007-12-02 00:50:00',
                             '2007-12-02 20:00:00',
                             0,
                             1,
                             NULL
                         );


-- Table: user_inv
DROP TABLE IF EXISTS user_inv;

CREATE TABLE user_inv (
    item_id  INTEGER PRIMARY KEY AUTOINCREMENT
                     UNIQUE
                     NOT NULL,
    user_id  INTEGER NOT NULL,
    guild_id INTEGER NOT NULL,
    inv_id   INTEGER NOT NULL
);


-- Table: user_profile
DROP TABLE IF EXISTS user_profile;

CREATE TABLE user_profile (
    guild_id       INTEGER   NOT NULL,
    user_id        INTEGER   NOT NULL,
    balance        INTEGER   NOT NULL
                             DEFAULT (0),
    points         INTEGER   NOT NULL
                             DEFAULT (0),
    rank           INTEGER   NOT NULL
                             DEFAULT (0),
    flipped        INTEGER   NOT NULL
                             DEFAULT (0),
    unflipped      INTEGER   NOT NULL
                             DEFAULT (0),
    level          INTEGER   NOT NULL
                             DEFAULT (0),
    werewolf_wins  INTEGER   NOT NULL
                             DEFAULT (0),
    werewolf_games INTEGER   NOT NULL
                             DEFAULT (0),
    cooldown       TIMESTAMP NOT NULL
                             DEFAULT (0),
    title          STRING,
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
                             DEFAULT (1),
    delete_command   BOOLEAN NOT NULL
                             DEFAULT (0),
    event_channel    STRING,
    event_on         BOOLEAN NOT NULL
                             DEFAULT (0) 
);


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
