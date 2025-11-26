create table game_save_player
(
    id             uuid                                   not null
        primary key,
    game_save_id   uuid                                   not null,
    player_id      bigint                                 not null,
    first_name     varchar(50)                            null,
    last_name      varchar(50)                            null,
    nick_name      varchar(50)                            null,
    gender_id      int                                    null,
    nationality_id int                                    null,
    birth_date     date                                   null,
    game_age       int                                    null,
    is_human       tinyint(1) default 0                   null,
    created_at     timestamp  default current_timestamp() not null,
    updated_at     timestamp  default current_timestamp() not null,
    constraint game_save_player_pk_2
        unique (game_save_id, player_id),
    constraint game_save_player_game_save_id_fk
        foreign key (game_save_id) references game_save (id)
            on delete cascade,
    constraint game_save_player_ref_gender_id_fk
        foreign key (gender_id) references ref_gender (id),
    constraint game_save_player_ref_nationality_id_fk
        foreign key (nationality_id) references ref_nationality (id)
);

INSERT INTO test_chess.game_save_player (id, game_save_id, player_id, first_name, last_name, nick_name, gender_id, nationality_id, birth_date, game_age, is_human, created_at, updated_at) VALUES ('713e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', 101, 'Zollie', 'Stutte', null, 2, 60, '1889-04-20', 54, 0, '2025-07-12 14:35:32', '2025-07-12 14:35:32');
INSERT INTO test_chess.game_save_player (id, game_save_id, player_id, first_name, last_name, nick_name, gender_id, nationality_id, birth_date, game_age, is_human, created_at, updated_at) VALUES ('723e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', 102, 'Karlee', 'Grigolon', null, 1, 52, '1926-08-13', 88, 0, '2025-07-12 14:35:32', '2025-07-12 14:35:32');
INSERT INTO test_chess.game_save_player (id, game_save_id, player_id, first_name, last_name, nick_name, gender_id, nationality_id, birth_date, game_age, is_human, created_at, updated_at) VALUES ('733e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', 103, 'Ky', 'Marven', null, 2, 190, '1878-12-18', 72, 0, '2025-07-12 14:35:32', '2025-07-12 14:35:32');
INSERT INTO test_chess.game_save_player (id, game_save_id, player_id, first_name, last_name, nick_name, gender_id, nationality_id, birth_date, game_age, is_human, created_at, updated_at) VALUES ('743e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', 104, 'Darnell', 'Dougan', null, 2, 109, '1937-04-28', 67, 0, '2025-07-12 14:35:32', '2025-07-12 14:35:32');
INSERT INTO test_chess.game_save_player (id, game_save_id, player_id, first_name, last_name, nick_name, gender_id, nationality_id, birth_date, game_age, is_human, created_at, updated_at) VALUES ('753e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', 105, 'Lindon', 'Adnett', null, 3, 192, '1957-03-10', 52, 0, '2025-07-12 14:35:32', '2025-07-12 14:35:32');
INSERT INTO test_chess.game_save_player (id, game_save_id, player_id, first_name, last_name, nick_name, gender_id, nationality_id, birth_date, game_age, is_human, created_at, updated_at) VALUES ('763e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', 106, 'Gilberto', 'Louis', null, 2, 112, '1883-07-29', 59, 0, '2025-07-12 14:35:32', '2025-07-12 14:35:32');
INSERT INTO test_chess.game_save_player (id, game_save_id, player_id, first_name, last_name, nick_name, gender_id, nationality_id, birth_date, game_age, is_human, created_at, updated_at) VALUES ('773e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', 107, 'Zora', 'McGonagle', null, 3, 150, '1162-01-01', 62, 0, '2025-07-12 14:35:32', '2025-07-12 14:35:32');
INSERT INTO test_chess.game_save_player (id, game_save_id, player_id, first_name, last_name, nick_name, gender_id, nationality_id, birth_date, game_age, is_human, created_at, updated_at) VALUES ('783e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', 108, 'Anne-corinne', 'Gilardengo', null, 1, 128, '1942-09-13', 67, 0, '2025-07-12 14:35:32', '2025-07-12 14:35:32');
INSERT INTO test_chess.game_save_player (id, game_save_id, player_id, first_name, last_name, nick_name, gender_id, nationality_id, birth_date, game_age, is_human, created_at, updated_at) VALUES ('793e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', 109, 'Carola', 'Telling', null, 1, 242, '1890-05-19', 77, 0, '2025-07-12 14:35:32', '2025-07-12 14:35:32');
INSERT INTO test_chess.game_save_player (id, game_save_id, player_id, first_name, last_name, nick_name, gender_id, nationality_id, birth_date, game_age, is_human, created_at, updated_at) VALUES ('7a3e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', 110, 'Phebe', 'Dorgan', null, 1, 44, '1893-12-26', 81, 0, '2025-07-12 14:35:32', '2025-07-12 14:35:32');
