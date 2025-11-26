create table season_group_player
(
    id                  uuid                                     not null
        primary key,
    game_save_id        uuid                                     not null,
    season_id           uuid                                     not null,
    tournament_id       uuid                                     not null,
    phase_id            uuid                                     not null,
    group_id            uuid                                     not null,
    game_save_player_id uuid                                     not null,
    player_id           bigint                                   null,
    position            int unsigned                             null,
    points              int          default 0                   null,
    wins                int unsigned default 0                   null,
    draws               int unsigned default 0                   null,
    loses               int unsigned default 0                   null,
    matches             int unsigned default 0                   null,
    pair_id             int                                      null,
    created_at          timestamp    default current_timestamp() not null,
    updated_at          timestamp    default current_timestamp() not null invisible,
    constraint season_group_player_game_save_id_fk
        foreign key (game_save_id) references game_save (id)
            on delete cascade,
    constraint season_group_player_game_save_player_id_fk
        foreign key (game_save_player_id) references game_save_player (id),
    constraint season_group_player_game_save_season_id_fk
        foreign key (season_id) references game_save_season (id),
    constraint season_group_player_season_tournament_group_id_fk
        foreign key (group_id) references season_phase_group (id),
    constraint season_group_player_season_tournament_id_fk
        foreign key (tournament_id) references season_tournament (id),
    constraint season_group_player_season_tournament_phase_id_fk
        foreign key (phase_id) references season_tournament_phase (id)
);

INSERT INTO test_chess.season_group_player (id, game_save_id, season_id, tournament_id, phase_id, group_id, game_save_player_id, player_id, position, points, wins, draws, loses, matches, pair_id, created_at) VALUES ('613e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', '22369b90-8527-4c27-ba43-0ac2df6e226c', '33069b90-8527-4c27-ba43-0ac2df6e226c', '77769b90-8527-4c27-ba43-0ac2df6e226c', '713e4567-e89b-12d3-a456-426614174000', 101, 1, 9, 3, 0, 0, 3, 1, '2025-07-31 15:52:29');
INSERT INTO test_chess.season_group_player (id, game_save_id, season_id, tournament_id, phase_id, group_id, game_save_player_id, player_id, position, points, wins, draws, loses, matches, pair_id, created_at) VALUES ('623e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', '22369b90-8527-4c27-ba43-0ac2df6e226c', '33069b90-8527-4c27-ba43-0ac2df6e226c', '77769b90-8527-4c27-ba43-0ac2df6e226c', '723e4567-e89b-12d3-a456-426614174000', 102, 2, 4, 1, 1, 1, 3, 1, '2025-07-31 15:52:29');
INSERT INTO test_chess.season_group_player (id, game_save_id, season_id, tournament_id, phase_id, group_id, game_save_player_id, player_id, position, points, wins, draws, loses, matches, pair_id, created_at) VALUES ('633e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', '22369b90-8527-4c27-ba43-0ac2df6e226c', '33069b90-8527-4c27-ba43-0ac2df6e226c', '77769b90-8527-4c27-ba43-0ac2df6e226c', '733e4567-e89b-12d3-a456-426614174000', 103, 3, 3, 1, 0, 2, 3, 1, '2025-07-31 15:52:29');
INSERT INTO test_chess.season_group_player (id, game_save_id, season_id, tournament_id, phase_id, group_id, game_save_player_id, player_id, position, points, wins, draws, loses, matches, pair_id, created_at) VALUES ('643e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', '22369b90-8527-4c27-ba43-0ac2df6e226c', '33069b90-8527-4c27-ba43-0ac2df6e226c', '77769b90-8527-4c27-ba43-0ac2df6e226c', '743e4567-e89b-12d3-a456-426614174000', 104, 4, 1, 0, 1, 2, 3, 1, '2025-07-31 15:52:29');
