create table game_save_achievement
(
    game_save_id   uuid                                  not null,
    achievement_id int                                   not null,
    score          double                                null,
    level          int                                   null,
    to_next_level  double                                null,
    created_at     timestamp default current_timestamp() not null,
    updated_at     timestamp default current_timestamp() not null,
    primary key (achievement_id, game_save_id),
    constraint game_save_achievement_game_save_id_fk
        foreign key (game_save_id) references game_save (id)
            on delete cascade,
    constraint game_save_achievement_ref_achievement_id_fk
        foreign key (achievement_id) references ref_achievement (id)
);

INSERT INTO test_chess.game_save_achievement (game_save_id, achievement_id, score, level, to_next_level, created_at, updated_at) VALUES ('123e4567-e89b-12d3-a456-426614174000', 1, 45.23, 2, 120.5, '2023-01-15 08:30:22', '2023-01-15 08:30:22');
INSERT INTO test_chess.game_save_achievement (game_save_id, achievement_id, score, level, to_next_level, created_at, updated_at) VALUES ('123e4567-e89b-12d3-a456-426614174000', 2, 78.91, 3, 85.75, '2023-01-16 10:15:33', '2023-01-17 09:45:12');
INSERT INTO test_chess.game_save_achievement (game_save_id, achievement_id, score, level, to_next_level, created_at, updated_at) VALUES ('123e4567-e89b-12d3-a456-426614174000', 3, 12.34, 1, 200, '2023-01-18 14:22:45', '2023-01-18 14:22:45');
INSERT INTO test_chess.game_save_achievement (game_save_id, achievement_id, score, level, to_next_level, created_at, updated_at) VALUES ('123e4567-e89b-12d3-a456-426614174000', 4, 156.78, 4, 42.3, '2023-01-20 18:05:11', '2023-01-21 11:33:07');
INSERT INTO test_chess.game_save_achievement (game_save_id, achievement_id, score, level, to_next_level, created_at, updated_at) VALUES ('123e4567-e89b-12d3-a456-426614174000', 5, 230.45, 5, 0, '2023-01-22 20:45:39', '2023-01-23 07:12:58');
INSERT INTO test_chess.game_save_achievement (game_save_id, achievement_id, score, level, to_next_level, created_at, updated_at) VALUES ('123e4567-e89b-12d3-a456-426614174000', 6, 67.89, 2, 110.25, '2023-01-25 12:30:15', '2023-01-25 12:30:15');
INSERT INTO test_chess.game_save_achievement (game_save_id, achievement_id, score, level, to_next_level, created_at, updated_at) VALUES ('123e4567-e89b-12d3-a456-426614174000', 7, 189.12, 4, 35.75, '2023-01-28 09:18:42', '2023-01-29 16:22:31');
INSERT INTO test_chess.game_save_achievement (game_save_id, achievement_id, score, level, to_next_level, created_at, updated_at) VALUES ('123e4567-e89b-12d3-a456-426614174000', 8, 34.56, 1, 180.9, '2023-02-01 13:45:27', '2023-02-01 13:45:27');
INSERT INTO test_chess.game_save_achievement (game_save_id, achievement_id, score, level, to_next_level, created_at, updated_at) VALUES ('123e4567-e89b-12d3-a456-426614174000', 9, 278.34, 5, 0, '2023-02-03 17:33:19', '2023-02-05 08:15:44');
INSERT INTO test_chess.game_save_achievement (game_save_id, achievement_id, score, level, to_next_level, created_at, updated_at) VALUES ('123e4567-e89b-12d3-a456-426614174000', 10, 92.67, 3, 75.4, '2023-02-07 11:22:53', '2023-02-08 14:37:21');
