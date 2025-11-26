create table game_save_chess_title
(
    id             uuid                                  not null
        primary key,
    game_save_id   uuid                                  not null,
    player_id      bigint                                not null,
    chess_title_id int                                   not null,
    created_at     timestamp default current_timestamp() not null,
    updated_at     timestamp default current_timestamp() not null,
    constraint game_save_chess_title_pk
        unique (game_save_id, player_id, chess_title_id),
    constraint game_save_chess_title_game_save_id_fk
        foreign key (game_save_id) references game_save (id)
            on delete cascade,
    constraint game_save_chess_title_ref_chess_title_id_fk
        foreign key (chess_title_id) references ref_chess_title (id)
);

INSERT INTO test_chess.game_save_chess_title (id, game_save_id, player_id, chess_title_id, created_at, updated_at) VALUES ('513e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', 101, 1, '2025-07-12 17:09:42', '2025-07-12 17:09:42');
INSERT INTO test_chess.game_save_chess_title (id, game_save_id, player_id, chess_title_id, created_at, updated_at) VALUES ('523e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', 102, 1, '2025-07-12 17:09:42', '2025-07-12 17:09:42');
INSERT INTO test_chess.game_save_chess_title (id, game_save_id, player_id, chess_title_id, created_at, updated_at) VALUES ('533e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', 103, 1, '2025-07-12 17:09:42', '2025-07-12 17:09:42');
INSERT INTO test_chess.game_save_chess_title (id, game_save_id, player_id, chess_title_id, created_at, updated_at) VALUES ('543e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', 104, 2, '2025-07-12 17:09:42', '2025-07-12 17:09:42');
INSERT INTO test_chess.game_save_chess_title (id, game_save_id, player_id, chess_title_id, created_at, updated_at) VALUES ('553e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', 105, 2, '2025-07-12 17:09:42', '2025-07-12 17:09:42');
INSERT INTO test_chess.game_save_chess_title (id, game_save_id, player_id, chess_title_id, created_at, updated_at) VALUES ('563e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', 106, 2, '2025-07-12 17:09:42', '2025-07-12 17:09:42');
INSERT INTO test_chess.game_save_chess_title (id, game_save_id, player_id, chess_title_id, created_at, updated_at) VALUES ('573e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', 107, 3, '2025-07-12 17:09:42', '2025-07-12 17:09:42');
INSERT INTO test_chess.game_save_chess_title (id, game_save_id, player_id, chess_title_id, created_at, updated_at) VALUES ('583e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', 108, 3, '2025-07-12 17:09:42', '2025-07-12 17:09:42');
INSERT INTO test_chess.game_save_chess_title (id, game_save_id, player_id, chess_title_id, created_at, updated_at) VALUES ('593e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', 109, 4, '2025-07-12 17:09:42', '2025-07-12 17:09:42');
INSERT INTO test_chess.game_save_chess_title (id, game_save_id, player_id, chess_title_id, created_at, updated_at) VALUES ('5a3e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', 110, 5, '2025-07-12 17:09:42', '2025-07-12 17:09:42');
INSERT INTO test_chess.game_save_chess_title (id, game_save_id, player_id, chess_title_id, created_at, updated_at) VALUES ('813e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', 101, 5, '2025-07-12 17:09:42', '2025-07-12 17:09:42');
INSERT INTO test_chess.game_save_chess_title (id, game_save_id, player_id, chess_title_id, created_at, updated_at) VALUES ('823e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', 102, 6, '2025-07-12 17:09:42', '2025-07-12 17:09:42');
INSERT INTO test_chess.game_save_chess_title (id, game_save_id, player_id, chess_title_id, created_at, updated_at) VALUES ('833e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', 103, 7, '2025-07-12 17:09:42', '2025-07-12 17:09:42');
INSERT INTO test_chess.game_save_chess_title (id, game_save_id, player_id, chess_title_id, created_at, updated_at) VALUES ('843e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', 104, 8, '2025-07-12 17:09:42', '2025-07-12 17:09:42');
INSERT INTO test_chess.game_save_chess_title (id, game_save_id, player_id, chess_title_id, created_at, updated_at) VALUES ('853e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', 105, 9, '2025-07-12 17:09:42', '2025-07-12 17:09:42');
INSERT INTO test_chess.game_save_chess_title (id, game_save_id, player_id, chess_title_id, created_at, updated_at) VALUES ('863e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', 106, 9, '2025-07-12 17:09:42', '2025-07-12 17:09:42');
INSERT INTO test_chess.game_save_chess_title (id, game_save_id, player_id, chess_title_id, created_at, updated_at) VALUES ('873e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', 107, 8, '2025-07-12 17:09:42', '2025-07-12 17:09:42');
INSERT INTO test_chess.game_save_chess_title (id, game_save_id, player_id, chess_title_id, created_at, updated_at) VALUES ('883e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', 108, 7, '2025-07-12 17:09:42', '2025-07-12 17:09:42');
INSERT INTO test_chess.game_save_chess_title (id, game_save_id, player_id, chess_title_id, created_at, updated_at) VALUES ('893e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', 109, 6, '2025-07-12 17:09:42', '2025-07-12 17:09:42');
INSERT INTO test_chess.game_save_chess_title (id, game_save_id, player_id, chess_title_id, created_at, updated_at) VALUES ('8a3e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', 110, 6, '2025-07-12 17:09:42', '2025-07-12 17:09:42');
