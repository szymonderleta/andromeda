create table enum_game_result
(
    id         tinyint unsigned                       not null
        primary key,
    code       varchar(20)                            not null,
    name       varchar(100)                           not null,
    points     decimal(3, 1)                          not null,
    sort_order tinyint unsigned                       not null,
    is_active  tinyint(1) default 1                   null,
    created_at timestamp  default current_timestamp() null,
    constraint uk_result_code
        unique (code)
)
    comment 'Dictionary of scores chess games';

INSERT INTO test_chess.enum_game_result (id, code, name, points, sort_order, is_active, created_at) VALUES (1, 'WIN', 'Win', 1.0, 10, 1, '2025-07-22 14:08:31');
INSERT INTO test_chess.enum_game_result (id, code, name, points, sort_order, is_active, created_at) VALUES (2, 'DRAW', 'Draw', 0.5, 20, 1, '2025-07-22 14:08:31');
INSERT INTO test_chess.enum_game_result (id, code, name, points, sort_order, is_active, created_at) VALUES (3, 'LOSS', 'Lose', 0.0, 30, 1, '2025-07-22 14:08:31');
