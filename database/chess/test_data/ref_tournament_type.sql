create table ref_tournament_type
(
    id   int         not null
        primary key,
    name varchar(50) null,
    constraint ref_tournament_type_pk_2
        unique (name)
);

INSERT INTO test_chess.ref_tournament_type (id, name) VALUES (1, 'Groups');
INSERT INTO test_chess.ref_tournament_type (id, name) VALUES (2, 'Knock-Out System');
INSERT INTO test_chess.ref_tournament_type (id, name) VALUES (3, 'Mixed');
INSERT INTO test_chess.ref_tournament_type (id, name) VALUES (4, 'Special');
