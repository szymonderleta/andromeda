create table game_save_season
(
    id           uuid                                  not null
        primary key,
    game_save_id uuid                                  not null,
    name         varchar(100)                          not null,
    status_id    int                                   not null,
    year         int                                   null,
    ranking_id   int                                   null,
    created_at   timestamp default current_timestamp() not null,
    updated_at   timestamp default current_timestamp() not null,
    constraint game_save_season_game_save_id_fk
        foreign key (game_save_id) references game_save (id)
            on delete cascade,
    constraint game_save_season_ref_ranking_id_fk
        foreign key (ranking_id) references ref_ranking (id),
    constraint game_save_season_ref_status_id_fk
        foreign key (status_id) references ref_status (id)
);

INSERT INTO test_chess.game_save_season (id, game_save_id, name, status_id, year, ranking_id, created_at, updated_at) VALUES ('550e8400-e29b-41d4-a716-446655440000', '123e4567-e89b-12d3-a456-426614174000', 'TEST 2024 Season', 1, 2024, 2024, '2025-07-10 17:35:59', '2025-07-10 17:35:59');
INSERT INTO test_chess.game_save_season (id, game_save_id, name, status_id, year, ranking_id, created_at, updated_at) VALUES ('67e55044-10b1-426f-9247-bb680e5fe0c8', '123e4567-e89b-12d3-a456-426614174000', 'TEST 2023 Season', 1, 2023, 2023, '2025-07-10 17:35:59', '2025-07-10 17:35:59');
