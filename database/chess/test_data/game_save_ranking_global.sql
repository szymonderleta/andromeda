create table game_save_ranking_global
(
    id                  uuid                                     not null
        primary key,
    game_save_id        uuid                                     not null,
    game_save_player_id uuid                                     not null,
    player_id           bigint                                   not null,
    position            int                                      null,
    difference          int                                      null comment 'store postion differecne from last update',
    points              int          default 500                 null,
    actual_win_in_row   int          default 0                   null,
    best_win_in_row     int          default 0                   null,
    win_ratio           float(23, 0) default 0                   null,
    wins                int          default 0                   null,
    draws               int          default 0                   null,
    loses               int          default 0                   null,
    created_at          timestamp    default current_timestamp() not null,
    updated_at          timestamp    default current_timestamp() not null,
    constraint game_save_ranking_global_pk_2
        unique (player_id, game_save_id),
    constraint game_save_ranking_global_game_save_id_fk
        foreign key (game_save_id) references game_save (id)
            on delete cascade
);

INSERT INTO test_chess.game_save_ranking_global (id, game_save_id, game_save_player_id, player_id, position, difference, points, actual_win_in_row, best_win_in_row, win_ratio, wins, draws, loses, created_at, updated_at) VALUES ('413e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', '713e4567-e89b-12d3-a456-426614174000', 101, 101, 9, 2100, 5, 5, 1, 10, 0, 3, '2025-07-13 08:28:03', '2025-07-13 08:28:03');
INSERT INTO test_chess.game_save_ranking_global (id, game_save_id, game_save_player_id, player_id, position, difference, points, actual_win_in_row, best_win_in_row, win_ratio, wins, draws, loses, created_at, updated_at) VALUES ('423e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', '723e4567-e89b-12d3-a456-426614174000', 102, 102, 8, 1900, 4, 5, 1, 9, 1, 4, '2025-07-13 08:28:03', '2025-07-13 08:28:03');
INSERT INTO test_chess.game_save_ranking_global (id, game_save_id, game_save_player_id, player_id, position, difference, points, actual_win_in_row, best_win_in_row, win_ratio, wins, draws, loses, created_at, updated_at) VALUES ('433e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', '733e4567-e89b-12d3-a456-426614174000', 103, 103, 7, 1700, 3, 3, 1, 8, 2, 5, '2025-07-13 08:28:03', '2025-07-13 08:28:03');
INSERT INTO test_chess.game_save_ranking_global (id, game_save_id, game_save_player_id, player_id, position, difference, points, actual_win_in_row, best_win_in_row, win_ratio, wins, draws, loses, created_at, updated_at) VALUES ('443e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', '743e4567-e89b-12d3-a456-426614174000', 104, 104, 6, 1600, 3, 3, 1, 7, 3, 6, '2025-07-13 08:28:03', '2025-07-13 08:28:03');
INSERT INTO test_chess.game_save_ranking_global (id, game_save_id, game_save_player_id, player_id, position, difference, points, actual_win_in_row, best_win_in_row, win_ratio, wins, draws, loses, created_at, updated_at) VALUES ('453e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', '753e4567-e89b-12d3-a456-426614174000', 105, 105, 5, 1500, 2, 3, 0, 6, 4, 7, '2025-07-13 08:28:03', '2025-07-13 08:28:03');
INSERT INTO test_chess.game_save_ranking_global (id, game_save_id, game_save_player_id, player_id, position, difference, points, actual_win_in_row, best_win_in_row, win_ratio, wins, draws, loses, created_at, updated_at) VALUES ('463e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', '763e4567-e89b-12d3-a456-426614174000', 106, 106, 4, 1400, 3, 3, 0, 5, 4, 8, '2025-07-13 08:28:03', '2025-07-13 08:28:03');
INSERT INTO test_chess.game_save_ranking_global (id, game_save_id, game_save_player_id, player_id, position, difference, points, actual_win_in_row, best_win_in_row, win_ratio, wins, draws, loses, created_at, updated_at) VALUES ('473e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', '773e4567-e89b-12d3-a456-426614174000', 107, 107, 3, 1200, 0, 2, 0, 4, 4, 9, '2025-07-13 08:28:03', '2025-07-13 08:28:03');
INSERT INTO test_chess.game_save_ranking_global (id, game_save_id, game_save_player_id, player_id, position, difference, points, actual_win_in_row, best_win_in_row, win_ratio, wins, draws, loses, created_at, updated_at) VALUES ('483e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', '783e4567-e89b-12d3-a456-426614174000', 108, 108, 2, 1150, 0, 1, 0, 3, 4, 9, '2025-07-13 08:28:03', '2025-07-13 08:28:03');
INSERT INTO test_chess.game_save_ranking_global (id, game_save_id, game_save_player_id, player_id, position, difference, points, actual_win_in_row, best_win_in_row, win_ratio, wins, draws, loses, created_at, updated_at) VALUES ('493e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', '793e4567-e89b-12d3-a456-426614174000', 109, 109, 1, 1120, 0, 3, 0, 2, 4, 10, '2025-07-13 08:28:03', '2025-07-13 08:28:03');
INSERT INTO test_chess.game_save_ranking_global (id, game_save_id, game_save_player_id, player_id, position, difference, points, actual_win_in_row, best_win_in_row, win_ratio, wins, draws, loses, created_at, updated_at) VALUES ('4a3e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', '7a3e4567-e89b-12d3-a456-426614174000', 110, 110, 0, 1050, 0, 3, 0, 1, 4, 11, '2025-07-13 08:28:03', '2025-07-13 08:28:03');
