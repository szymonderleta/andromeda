create table settings_display
(
    game_save_id uuid                                   not null
        primary key,
    auto_change  tinyint(1) default 1                   null comment 'Themes will be changed during game progress, defalut is turing on.',
    theme_id     int                                    null,
    created_at   timestamp  default current_timestamp() not null,
    updated_at   timestamp  default current_timestamp() not null,
    constraint settings_display_game_save_id_fk
        foreign key (game_save_id) references game_save (id)
            on delete cascade,
    constraint settings_display_ref_theme_id_fk
        foreign key (theme_id) references ref_theme (id)
);

INSERT INTO test_chess.settings_display (game_save_id, auto_change, theme_id, created_at, updated_at) VALUES ('123e4567-e89b-12d3-a456-426614174000', 0, 13, '2025-07-27 08:39:34', '2025-07-27 08:39:34');
