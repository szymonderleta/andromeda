create table game_save_trophy
(
    id              uuid                                  not null
        primary key,
    game_save_id    uuid                                  not null,
    trophy_id       int                                   not null,
    best_place      int                                   null,
    times           int       default 1                   null,
    first_season_id uuid                                  null,
    created_at      timestamp default current_timestamp() not null,
    updated_at      timestamp default current_timestamp() not null,
    constraint game_save_trophy_pk_2
        unique (best_place, game_save_id, trophy_id),
    constraint game_save_trophy_game_save_id_fk
        foreign key (game_save_id) references game_save (id)
            on delete cascade,
    constraint game_save_trophy_game_save_season_id_fk
        foreign key (first_season_id) references game_save_season (id),
    constraint game_save_trophy_ref_trophy_id_fk
        foreign key (trophy_id) references ref_trophy (id)
);

INSERT INTO test_chess.game_save_trophy (id, game_save_id, trophy_id, best_place, times, first_season_id, created_at, updated_at) VALUES ('999e4567-e89b-12d3-a456-426614174001', '123e4567-e89b-12d3-a456-426614174000', 1, 2, 2, '550e8400-e29b-41d4-a716-446655440000', '2025-07-19 14:54:05', '2025-07-19 14:54:05');
INSERT INTO test_chess.game_save_trophy (id, game_save_id, trophy_id, best_place, times, first_season_id, created_at, updated_at) VALUES ('999e4567-e89b-12d3-a456-426614174002', '123e4567-e89b-12d3-a456-426614174000', 2, 3, 1, '67e55044-10b1-426f-9247-bb680e5fe0c8', '2025-07-19 14:54:05', '2025-07-19 14:54:05');
INSERT INTO test_chess.game_save_trophy (id, game_save_id, trophy_id, best_place, times, first_season_id, created_at, updated_at) VALUES ('999e4567-e89b-12d3-a456-426614174003', '123e4567-e89b-12d3-a456-426614174000', 3, 1, 1, '67e55044-10b1-426f-9247-bb680e5fe0c8', '2025-07-19 14:54:05', '2025-07-19 14:54:05');
INSERT INTO test_chess.game_save_trophy (id, game_save_id, trophy_id, best_place, times, first_season_id, created_at, updated_at) VALUES ('999e4567-e89b-12d3-a456-426614174004', '123e4567-e89b-12d3-a456-426614174000', 4, 4, 3, '550e8400-e29b-41d4-a716-446655440000', '2025-07-19 14:54:05', '2025-07-19 14:54:05');
