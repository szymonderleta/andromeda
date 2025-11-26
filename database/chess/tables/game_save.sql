create table game_save
(
    id                   uuid                                  not null
        primary key,
    name                 varchar(255)                          not null,
    game_save_player_id  uuid                                  not null,
    player_id            bigint                                not null,
    status_id            int                                   null,
    current_game_date    date      default '2024-01-01'        not null,
    current_game_year    int       default 2024                null,
    current_game_month   int       default 0                   null,
    achievement_progress double    default 0                   not null,
    created_at           timestamp default current_timestamp() not null,
    updated_at           timestamp default current_timestamp() not null on update current_timestamp(),
    next_step_id         uuid                                  null,
    constraint uk_game_save_name
        unique (name),
    constraint game_save_game_statuses_id_fk
        foreign key (status_id) references ref_status (id)
);

create index game_save_status_id_fk
    on game_save (status_id);
