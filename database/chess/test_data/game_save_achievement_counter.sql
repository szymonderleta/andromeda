create table game_save_achievement_counter
(
    achievement_id int                                   not null,
    game_save_id   uuid                                  not null,
    value          double                                null,
    created_at     timestamp default current_timestamp() not null,
    updated_at     timestamp default current_timestamp() not null,
    primary key (achievement_id, game_save_id),
    constraint game_save_achievement_counter_game_save_id_fk
        foreign key (game_save_id) references game_save (id)
            on delete cascade,
    constraint game_save_achievement_counter_ref_achievement_id_fk
        foreign key (achievement_id) references ref_achievement (id)
);

INSERT INTO test_chess.game_save_achievement_counter (achievement_id, game_save_id, value, created_at, updated_at) VALUES (1, '123e4567-e89b-12d3-a456-426614174000', 2, '2025-08-15 11:59:57', '2025-08-15 11:59:57');
INSERT INTO test_chess.game_save_achievement_counter (achievement_id, game_save_id, value, created_at, updated_at) VALUES (2, '123e4567-e89b-12d3-a456-426614174000', 5, '2025-08-15 11:59:57', '2025-08-15 11:59:57');
INSERT INTO test_chess.game_save_achievement_counter (achievement_id, game_save_id, value, created_at, updated_at) VALUES (56, '123e4567-e89b-12d3-a456-426614174000', 3, '2025-08-15 11:59:57', '2025-08-15 11:59:57');
