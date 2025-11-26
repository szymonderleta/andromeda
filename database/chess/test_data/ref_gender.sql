create table ref_gender
(
    id   int      not null
        primary key,
    name tinytext null,
    constraint ref_gender_pk
        unique (name) using hash
);

INSERT INTO test_chess.ref_gender (id, name) VALUES (1, 'Female');
INSERT INTO test_chess.ref_gender (id, name) VALUES (2, 'Male');
INSERT INTO test_chess.ref_gender (id, name) VALUES (3, 'Unknown');
