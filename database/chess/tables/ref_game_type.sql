create table ref_game_type
(
    id             int          not null
        primary key,
    name           varchar(100) null,
    board_theme_id int          null,
    constraint ref_game_type_pk_2
        unique (name),
    constraint ref_game_type_ref_board_theme_id_fk
        foreign key (board_theme_id) references ref_board_theme (id)
);

INSERT INTO test_chess.ref_game_type (id, name, board_theme_id) VALUES (1, 'League Match', 1);
INSERT INTO test_chess.ref_game_type (id, name, board_theme_id) VALUES (2, 'Derby Rules', 2);
INSERT INTO test_chess.ref_game_type (id, name, board_theme_id) VALUES (3, 'World Cup Rules', 3);
INSERT INTO test_chess.ref_game_type (id, name, board_theme_id) VALUES (4, 'Continental Cup Rules', 4);
INSERT INTO test_chess.ref_game_type (id, name, board_theme_id) VALUES (5, 'Swiss National Rules', 5);
INSERT INTO test_chess.ref_game_type (id, name, board_theme_id) VALUES (6, 'Swiss Continental Rules', 6);
INSERT INTO test_chess.ref_game_type (id, name, board_theme_id) VALUES (7, 'Swiss World Rules', 7);
INSERT INTO test_chess.ref_game_type (id, name, board_theme_id) VALUES (8, 'Swiss Random Rules', 8);
INSERT INTO test_chess.ref_game_type (id, name, board_theme_id) VALUES (9, 'Winning Challenge Rules', 9);
INSERT INTO test_chess.ref_game_type (id, name, board_theme_id) VALUES (10, 'Fourth Challenge Rules', 10);
INSERT INTO test_chess.ref_game_type (id, name, board_theme_id) VALUES (11, 'MASTERS Rules', 11);
