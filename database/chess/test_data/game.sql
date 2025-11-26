create table game
(
    id              uuid                                  not null
        primary key,
    name            varchar(255)                          null,
    game_save_id    uuid                                  null,
    season_id       uuid                                  null,
    white_player_id uuid                                  not null,
    black_player_id uuid                                  not null,
    status_id       int                                   null,
    rules_id        int                                   null,
    game_type_id    int                                   not null,
    tournament_id   int                                   null,
    phase_id        int                                   null,
    group_id        int                                   null,
    date            date                                  null,
    updated_at      timestamp default current_timestamp() not null,
    created_at      timestamp default current_timestamp() not null,
    constraint game_game_save_id_fk
        foreign key (game_save_id) references game_save (id)
            on delete cascade,
    constraint game_game_save_player_id_fk
        foreign key (white_player_id) references game_save_player (id),
    constraint game_game_save_player_id_fk_2
        foreign key (black_player_id) references game_save_player (id),
    constraint game_game_save_season_id_fk
        foreign key (season_id) references game_save_season (id),
    constraint game_ref_game_type_id_fk
        foreign key (game_type_id) references ref_game_type (id),
    constraint game_ref_rules_id_fk
        foreign key (rules_id) references ref_rules (id),
    constraint game_ref_status_id_fk
        foreign key (status_id) references ref_status (id),
    constraint game_ref_tournament_id_fk
        foreign key (tournament_id) references ref_tournament (id)
);

INSERT INTO test_chess.game (id, name, game_save_id, season_id, white_player_id, black_player_id, status_id, rules_id, game_type_id, tournament_id, phase_id, group_id, date, updated_at, created_at) VALUES ('f47ac10b-58cc-4372-a567-0e02b2c3d479', 'TEST Game No Score', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', '733e4567-e89b-12d3-a456-426614174000', '753e4567-e89b-12d3-a456-426614174000', 1, 1, 1, 1, 1, 2, '2025-07-26', '2025-07-24 16:39:01', '2025-07-24 16:39:01');
INSERT INTO test_chess.game (id, name, game_save_id, season_id, white_player_id, black_player_id, status_id, rules_id, game_type_id, tournament_id, phase_id, group_id, date, updated_at, created_at) VALUES ('a47ac10b-58cc-4372-a567-0e02b2c3d480', 'Test 2023 Game', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', '713e4567-e89b-12d3-a456-426614174000', '723e4567-e89b-12d3-a456-426614174000', 1, 1, 1, 1, 1, 1, '2023-01-11', '2025-07-30 16:08:31', '2025-07-30 16:08:31');
INSERT INTO test_chess.game (id, name, game_save_id, season_id, white_player_id, black_player_id, status_id, rules_id, game_type_id, tournament_id, phase_id, group_id, date, updated_at, created_at) VALUES ('aa7ac10b-58cc-4372-a567-0e02b2c3d480', 'Test 2023 Game', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', '723e4567-e89b-12d3-a456-426614174000', '713e4567-e89b-12d3-a456-426614174000', 2, 1, 1, 1, 1, 1, '2024-01-19', '2025-07-30 16:08:31', '2025-07-30 16:08:31');
INSERT INTO test_chess.game (id, name, game_save_id, season_id, white_player_id, black_player_id, status_id, rules_id, game_type_id, tournament_id, phase_id, group_id, date, updated_at, created_at) VALUES ('e47ac10b-58cc-4372-a567-0e02b2c3d480', 'TEST Scond Game No Score', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', '733e4567-e89b-12d3-a456-426614174000', '753e4567-e89b-12d3-a456-426614174000', 1, 1, 1, 1, 1, 2, '2023-02-15', '2025-07-24 16:39:01', '2025-07-24 16:39:01');
INSERT INTO test_chess.game (id, name, game_save_id, season_id, white_player_id, black_player_id, status_id, rules_id, game_type_id, tournament_id, phase_id, group_id, date, updated_at, created_at) VALUES ('f47ac10b-58cc-4372-a567-0e02b2c3d480', 'TEST Scond Game No Score', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', '733e4567-e89b-12d3-a456-426614174000', '753e4567-e89b-12d3-a456-426614174000', 1, 1, 1, 1, 1, 2, '2023-01-10', '2025-07-24 16:39:01', '2025-07-24 16:39:01');
INSERT INTO test_chess.game (id, name, game_save_id, season_id, white_player_id, black_player_id, status_id, rules_id, game_type_id, tournament_id, phase_id, group_id, date, updated_at, created_at) VALUES ('a1b2c3d4-e5f6-4a7b-8c9d-0e1f2a3b4c5d', 'TEST  Game With Score', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', '713e4567-e89b-12d3-a456-426614174000', '723e4567-e89b-12d3-a456-426614174000', 2, 1, 1, 1, 1, 1, '2023-01-03', '2025-07-24 16:39:01', '2025-07-24 16:39:01');
INSERT INTO test_chess.game (id, name, game_save_id, season_id, white_player_id, black_player_id, status_id, rules_id, game_type_id, tournament_id, phase_id, group_id, date, updated_at, created_at) VALUES ('a1e2c3d4-e5f6-4a7b-8c9d-0e1f2a3b4c5d', 'TEST  Game With Score', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', '753e4567-e89b-12d3-a456-426614174000', '713e4567-e89b-12d3-a456-426614174000', 2, 1, 1, 1, 1, 1, '2023-01-07', '2025-07-24 16:39:01', '2025-07-24 16:39:01');
