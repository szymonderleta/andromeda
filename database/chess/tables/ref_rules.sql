create table ref_rules
(
    id            int         not null
        primary key,
    name          varchar(50) null,
    total_time    int         null,
    time_per_move int         null,
    constraint ref_rules_pk
        unique (name)
);

INSERT INTO test_chess.ref_rules (id, name, total_time, time_per_move) VALUES (1, 'League Rules', 3600, 10);
INSERT INTO test_chess.ref_rules (id, name, total_time, time_per_move) VALUES (2, 'Derby Rules', 2700, 2);
INSERT INTO test_chess.ref_rules (id, name, total_time, time_per_move) VALUES (3, 'World Cup Rules', 3600, 5);
INSERT INTO test_chess.ref_rules (id, name, total_time, time_per_move) VALUES (4, 'Continental Cup Rules', 1800, 5);
INSERT INTO test_chess.ref_rules (id, name, total_time, time_per_move) VALUES (5, 'Swiss National Rules', 300, 3);
INSERT INTO test_chess.ref_rules (id, name, total_time, time_per_move) VALUES (6, 'Swiss Continental Rules', 300, 3);
INSERT INTO test_chess.ref_rules (id, name, total_time, time_per_move) VALUES (7, 'Swiss World Rules', 300, 3);
INSERT INTO test_chess.ref_rules (id, name, total_time, time_per_move) VALUES (8, 'Swiss Random Rules', 120, 2);
INSERT INTO test_chess.ref_rules (id, name, total_time, time_per_move) VALUES (9, 'Winning Challenge Rules', 600, 5);
INSERT INTO test_chess.ref_rules (id, name, total_time, time_per_move) VALUES (10, 'Fourth Challenge Rules', 600, 5);
INSERT INTO test_chess.ref_rules (id, name, total_time, time_per_move) VALUES (11, 'MASTERS Rules', 1200, 10);
