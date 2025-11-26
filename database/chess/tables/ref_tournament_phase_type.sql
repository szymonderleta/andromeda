create table ref_tournament_phase_type
(
    id          int          not null
        primary key,
    name        varchar(100) null,
    description varchar(255) null,
    constraint ref_tournament_phase_type_pk_2
        unique (name)
);

INSERT INTO test_chess.ref_tournament_phase_type (id, name, description) VALUES (1, 'Group Type', null);
INSERT INTO test_chess.ref_tournament_phase_type (id, name, description) VALUES (2, 'Knock Out Type', null);
