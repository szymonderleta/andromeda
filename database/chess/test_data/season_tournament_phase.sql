create table season_tournament_phase
(
    id            uuid                                  not null
        primary key,
    name          varchar(50)                           null,
    game_save_id  uuid                                  null,
    season_id     uuid                                  null,
    tournament_id uuid                                  null,
    phase_id      int                                   not null,
    status_id     int                                   null,
    groups_count  int                                   null,
    groups_size   int                                   null,
    type_id       int                                   null,
    created_at    timestamp default current_timestamp() not null,
    updated_at    timestamp default current_timestamp() not null,
    constraint season_tournament_phase_pk
        unique (phase_id, game_save_id, season_id, tournament_id),
    constraint season_tournament_phase_game_save_id_fk
        foreign key (game_save_id) references game_save (id)
            on delete cascade,
    constraint season_tournament_phase_game_save_season_id_fk
        foreign key (season_id) references game_save_season (id),
    constraint season_tournament_phase_ref_status_id_fk
        foreign key (status_id) references ref_status (id),
    constraint season_tournament_phase_ref_tournament_phase_type_id_fk
        foreign key (type_id) references ref_tournament_phase_type (id),
    constraint season_tournament_phase_season_tournament_id_fk
        foreign key (tournament_id) references season_tournament (id)
);

INSERT INTO test_chess.season_tournament_phase (id, name, game_save_id, season_id, tournament_id, phase_id, status_id, groups_count, groups_size, type_id, created_at, updated_at) VALUES ('33069b90-8527-4c27-ba43-0ac2df6e226c', 'TST Phase 1', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', '22369b90-8527-4c27-ba43-0ac2df6e226c', 1, 1, 1, 20, 1, '2025-07-30 16:58:52', '2025-07-30 16:58:52');
INSERT INTO test_chess.season_tournament_phase (id, name, game_save_id, season_id, tournament_id, phase_id, status_id, groups_count, groups_size, type_id, created_at, updated_at) VALUES ('33269b90-8527-4c27-ba43-0ac2df6e226c', 'TST Phase 2', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', '22369b90-8527-4c27-ba43-0ac2df6e226c', 2, 1, 2, 16, 1, '2025-07-30 16:58:52', '2025-07-30 16:58:52');
INSERT INTO test_chess.season_tournament_phase (id, name, game_save_id, season_id, tournament_id, phase_id, status_id, groups_count, groups_size, type_id, created_at, updated_at) VALUES ('34269b90-8527-4c27-ba43-0ac2df6e226c', 'TST Phase 3', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', '22369b90-8527-4c27-ba43-0ac2df6e226c', 3, 1, 4, 12, 1, '2025-07-30 16:58:52', '2025-07-30 16:58:52');
INSERT INTO test_chess.season_tournament_phase (id, name, game_save_id, season_id, tournament_id, phase_id, status_id, groups_count, groups_size, type_id, created_at, updated_at) VALUES ('35269b90-8527-4c27-ba43-0ac2df6e226c', 'TST Phase 4', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', '22369b90-8527-4c27-ba43-0ac2df6e226c', 4, 2, 8, 2, 2, '2025-07-30 16:58:52', '2025-07-30 16:58:52');
INSERT INTO test_chess.season_tournament_phase (id, name, game_save_id, season_id, tournament_id, phase_id, status_id, groups_count, groups_size, type_id, created_at, updated_at) VALUES ('36269b90-8527-4c27-ba43-0ac2df6e226c', 'TST Phase 5', '123e4567-e89b-12d3-a456-426614174000', '550e8400-e29b-41d4-a716-446655440000', '22369b90-8527-4c27-ba43-0ac2df6e226c', 5, 2, 4, 2, 2, '2025-07-30 16:58:52', '2025-07-30 16:58:52');
