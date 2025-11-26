create table settings_general
(
    game_save_id         uuid                                   not null
        primary key,
    auto_promotion_queen tinyint    default 0                   null,
    difficulty_id        int        default 2                   null,
    bonus_content        tinyint(1) default 0                   null comment 'Flag to activate or deactivate bonus content, default turns off',
    created_at           timestamp  default current_timestamp() not null,
    updated_at           timestamp  default current_timestamp() not null,
    constraint settings_general_game_save_id_fk
        foreign key (game_save_id) references game_save (id)
            on delete cascade,
    constraint settings_general_ref_difficulty_id_fk
        foreign key (difficulty_id) references ref_difficulty (id)
);

INSERT INTO test_chess.settings_general (game_save_id, auto_promotion_queen, difficulty_id, bonus_content, created_at, updated_at) VALUES ('123e4567-e89b-12d3-a456-426614174000', 1, 3, 0, '2025-07-27 08:38:43', '2025-07-27 08:38:44');
