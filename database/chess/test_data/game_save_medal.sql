create table game_save_medal
(
    id                  uuid                                  not null
        primary key,
    game_save_id        uuid                                  not null,
    season_id           uuid                                  not null,
    trophy_id           int                                   not null,
    game_save_player_id uuid                                  not null,
    player_id           mediumtext                            not null,
    position            int                                   null,
    created_at          timestamp default current_timestamp() null,
    updated_at          timestamp default current_timestamp() not null,
    constraint game_save_medal_pk
        unique (season_id, trophy_id, game_save_player_id, position),
    constraint game_save_medal_game_save_id_fk
        foreign key (game_save_id) references game_save (id)
            on delete cascade,
    constraint game_save_medal_game_save_player_id_fk
        foreign key (game_save_player_id) references game_save_player (id),
    constraint game_save_medal_game_save_season_id_fk
        foreign key (season_id) references game_save_season (id),
    constraint game_save_medal_ref_trophy_id_fk
        foreign key (trophy_id) references ref_trophy (id)
);

INSERT INTO test_chess.game_save_medal (id, game_save_id, season_id, trophy_id, game_save_player_id, player_id, position, created_at, updated_at) VALUES ('00112233-e29b-41d4-0000-446655440000', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', 1, '713e4567-e89b-12d3-a456-426614174000', '1', 1, '2025-07-19 14:05:26', '2025-07-19 14:05:26');
INSERT INTO test_chess.game_save_medal (id, game_save_id, season_id, trophy_id, game_save_player_id, player_id, position, created_at, updated_at) VALUES ('00112233-e29b-41d4-0000-446655440001', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', 1, '713e4567-e89b-12d3-a456-426614174000', '1', 2, '2025-07-19 14:05:26', '2025-07-19 14:05:26');
INSERT INTO test_chess.game_save_medal (id, game_save_id, season_id, trophy_id, game_save_player_id, player_id, position, created_at, updated_at) VALUES ('00112233-e29b-41d4-0000-446655440002', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', 1, '713e4567-e89b-12d3-a456-426614174000', '1', 3, '2025-07-19 14:05:26', '2025-07-19 14:05:26');
INSERT INTO test_chess.game_save_medal (id, game_save_id, season_id, trophy_id, game_save_player_id, player_id, position, created_at, updated_at) VALUES ('00112233-e29b-41d4-0000-446655440003', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', 2, '713e4567-e89b-12d3-a456-426614174000', '1', 1, '2025-07-19 14:05:26', '2025-07-19 14:05:26');
INSERT INTO test_chess.game_save_medal (id, game_save_id, season_id, trophy_id, game_save_player_id, player_id, position, created_at, updated_at) VALUES ('00112233-e29b-41d4-0000-446655440004', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', 2, '713e4567-e89b-12d3-a456-426614174000', '1', 2, '2025-07-19 14:05:26', '2025-07-19 14:05:26');
INSERT INTO test_chess.game_save_medal (id, game_save_id, season_id, trophy_id, game_save_player_id, player_id, position, created_at, updated_at) VALUES ('00112233-e29b-41d4-0000-446655440005', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', 2, '713e4567-e89b-12d3-a456-426614174000', '1', 3, '2025-07-19 14:05:26', '2025-07-19 14:05:26');
INSERT INTO test_chess.game_save_medal (id, game_save_id, season_id, trophy_id, game_save_player_id, player_id, position, created_at, updated_at) VALUES ('00112233-e29b-41d4-0000-446655440006', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', 3, '713e4567-e89b-12d3-a456-426614174000', '1', 1, '2025-07-19 14:05:26', '2025-07-19 14:05:26');
INSERT INTO test_chess.game_save_medal (id, game_save_id, season_id, trophy_id, game_save_player_id, player_id, position, created_at, updated_at) VALUES ('00112233-e29b-41d4-0000-446655440007', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', 3, '713e4567-e89b-12d3-a456-426614174000', '1', 2, '2025-07-19 14:05:26', '2025-07-19 14:05:26');
INSERT INTO test_chess.game_save_medal (id, game_save_id, season_id, trophy_id, game_save_player_id, player_id, position, created_at, updated_at) VALUES ('00112233-e29b-41d4-0000-446655440008', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', 3, '713e4567-e89b-12d3-a456-426614174000', '1', 3, '2025-07-19 14:05:26', '2025-07-19 14:05:26');
