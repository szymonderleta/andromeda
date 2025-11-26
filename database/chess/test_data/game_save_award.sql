create table game_save_award
(
    id                       uuid                                  not null
        primary key,
    game_save_id             uuid                                  not null,
    season_id                uuid                                  not null,
    award_id                 int                                   not null,
    best_game_save_player_id uuid                                  not null,
    best_player_id           bigint                                null,
    best_score               double                                null,
    player_score             double                                null,
    created_at               timestamp default current_timestamp() not null,
    updated_at               timestamp default current_timestamp() not null,
    constraint game_save_award_pk
        unique (game_save_id, season_id, award_id),
    constraint game_save_award_game_save_id_fk
        foreign key (game_save_id) references game_save (id)
            on delete cascade,
    constraint game_save_award_game_save_season_id_fk
        foreign key (season_id) references game_save_season (id)
);

INSERT INTO test_chess.game_save_award (id, game_save_id, season_id, award_id, best_game_save_player_id, best_player_id, best_score, player_score, created_at, updated_at) VALUES ('f47ac10b-58cc-4372-a567-0e02b2c3d479', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', 1, '713e4567-e89b-12d3-a456-426614174000', 101, 95.5, 87.3, '2025-07-11 09:35:00', '2025-07-11 09:35:00');
INSERT INTO test_chess.game_save_award (id, game_save_id, season_id, award_id, best_game_save_player_id, best_player_id, best_score, player_score, created_at, updated_at) VALUES ('b47ac10b-58cc-4372-a567-0e02b2c3d480', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', 2, '723e4567-e89b-12d3-a456-426614174000', 102, 88.7, 82.1, '2025-07-11 09:35:00', '2025-07-11 09:35:00');
INSERT INTO test_chess.game_save_award (id, game_save_id, season_id, award_id, best_game_save_player_id, best_player_id, best_score, player_score, created_at, updated_at) VALUES ('c47ac10b-58cc-4372-a567-0e02b2c3d481', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', 3, '733e4567-e89b-12d3-a456-426614174000', 103, 92.3, 89.9, '2025-07-11 09:35:00', '2025-07-11 09:35:00');
INSERT INTO test_chess.game_save_award (id, game_save_id, season_id, award_id, best_game_save_player_id, best_player_id, best_score, player_score, created_at, updated_at) VALUES ('d47ac10b-58cc-4372-a567-0e02b2c3d482', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', 4, '743e4567-e89b-12d3-a456-426614174000', 104, 97.8, 94.2, '2025-07-11 09:35:00', '2025-07-11 09:35:00');
INSERT INTO test_chess.game_save_award (id, game_save_id, season_id, award_id, best_game_save_player_id, best_player_id, best_score, player_score, created_at, updated_at) VALUES ('e47ac10b-58cc-4372-a567-0e02b2c3d483', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', 5, '753e4567-e89b-12d3-a456-426614174000', 105, 85.4, 80, '2025-07-11 09:35:00', '2025-07-11 09:35:00');
INSERT INTO test_chess.game_save_award (id, game_save_id, season_id, award_id, best_game_save_player_id, best_player_id, best_score, player_score, created_at, updated_at) VALUES ('f47ac10b-58cc-4372-a567-0e02b2c3d484', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', 6, '763e4567-e89b-12d3-a456-426614174000', 106, 91.2, 88.7, '2025-07-11 09:35:00', '2025-07-11 09:35:00');
INSERT INTO test_chess.game_save_award (id, game_save_id, season_id, award_id, best_game_save_player_id, best_player_id, best_score, player_score, created_at, updated_at) VALUES ('a47ac10b-58cc-4372-a567-0e02b2c3d485', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', 7, '773e4567-e89b-12d3-a456-426614174000', 107, 94.6, 90.3, '2025-07-11 09:35:00', '2025-07-11 09:35:00');
INSERT INTO test_chess.game_save_award (id, game_save_id, season_id, award_id, best_game_save_player_id, best_player_id, best_score, player_score, created_at, updated_at) VALUES ('b47ac10b-58cc-4372-a567-0e02b2c3d486', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', 8, '783e4567-e89b-12d3-a456-426614174000', 108, 89.9, 85.5, '2025-07-11 09:35:00', '2025-07-11 09:35:00');
INSERT INTO test_chess.game_save_award (id, game_save_id, season_id, award_id, best_game_save_player_id, best_player_id, best_score, player_score, created_at, updated_at) VALUES ('c47ac10b-58cc-4372-a567-0e02b2c3d487', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', 9, '793e4567-e89b-12d3-a456-426614174000', 109, 96.7, 93.1, '2025-07-11 09:35:00', '2025-07-11 09:35:00');
INSERT INTO test_chess.game_save_award (id, game_save_id, season_id, award_id, best_game_save_player_id, best_player_id, best_score, player_score, created_at, updated_at) VALUES ('d47ac10b-58cc-4372-a567-0e02b2c3d488', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', 10, '7a3e4567-e89b-12d3-a456-426614174000', 110, 93.4, 91.8, '2025-07-11 09:35:00', '2025-07-11 09:35:00');
