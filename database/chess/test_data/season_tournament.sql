create table season_tournament
(
    id            uuid                                  not null
        primary key,
    name          varchar(100)                          null,
    game_save_id  uuid                                  null,
    season_id     uuid                                  null,
    tournament_id int                                   null,
    status_id     int                                   null,
    created_at    timestamp default current_timestamp() not null,
    updated_at    timestamp default current_timestamp() not null,
    constraint season_tournament_game_save_id_fk
        foreign key (game_save_id) references game_save (id)
            on delete cascade,
    constraint season_tournament_game_save_season_id_fk
        foreign key (season_id) references game_save_season (id),
    constraint season_tournament_ref_status_id_fk
        foreign key (status_id) references ref_status (id),
    constraint season_tournament_ref_tournament_id_fk
        foreign key (tournament_id) references ref_tournament (id)
);

INSERT INTO test_chess.season_tournament (id, name, game_save_id, season_id, tournament_id, status_id, created_at, updated_at) VALUES ('22269b90-8527-4c27-ba43-0ac2df6e226c', 'Test Tournament 1', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', 3, 1, '2025-07-30 16:44:43', '2025-07-30 16:45:06');
INSERT INTO test_chess.season_tournament (id, name, game_save_id, season_id, tournament_id, status_id, created_at, updated_at) VALUES ('22369b90-8527-4c27-ba43-0ac2df6e226c', 'Test Tournament 2', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', 1, 2, '2025-07-30 16:44:43', '2025-07-30 16:45:06');
INSERT INTO test_chess.season_tournament (id, name, game_save_id, season_id, tournament_id, status_id, created_at, updated_at) VALUES ('22469b90-8527-4c27-ba43-0ac2df6e226c', 'TEst Tournament 3', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', 1, 4, '2025-07-30 16:44:43', '2025-07-30 16:45:06');
