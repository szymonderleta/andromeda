create table settings_sound
(
    game_save_id   uuid                                   not null
        primary key,
    muted          tinyint(1) default 0                   null comment 'If true all sounds are muted',
    volume_master  int        default 100                 null,
    volume_music   int        default 100                 null,
    volume_effects int                                    null,
    created_at     timestamp  default current_timestamp() not null,
    updated_at     timestamp  default current_timestamp() not null,
    constraint settings_sound_game_save_id_fk
        foreign key (game_save_id) references game_save (id)
            on delete cascade
);

INSERT INTO test_chess.settings_sound (game_save_id, muted, volume_master, volume_music, volume_effects, created_at, updated_at) VALUES ('123e4567-e89b-12d3-a456-426614174000', 0, 100, 90, 90, '2025-07-27 08:42:54', '2025-07-27 08:42:54');
