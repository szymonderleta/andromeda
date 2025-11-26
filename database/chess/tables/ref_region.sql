create table ref_region
(
    id   int      not null
        primary key,
    name tinytext null,
    constraint ref_region_pk
        unique (name) using hash
);

INSERT INTO test_chess.ref_region (id, name) VALUES (1, 'Africa');
INSERT INTO test_chess.ref_region (id, name) VALUES (2, 'North America');
INSERT INTO test_chess.ref_region (id, name) VALUES (3, 'South America');
INSERT INTO test_chess.ref_region (id, name) VALUES (4, 'Antarctica');
INSERT INTO test_chess.ref_region (id, name) VALUES (5, 'Asia');
INSERT INTO test_chess.ref_region (id, name) VALUES (6, 'Australia');
INSERT INTO test_chess.ref_region (id, name) VALUES (7, 'Europe');
