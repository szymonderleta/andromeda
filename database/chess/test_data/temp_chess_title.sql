create table temp_chess_title
(
    id             uuid   not null
        primary key,
    player_id      bigint not null,
    chess_title_id int    not null,
    constraint temp_chess_title_pk_2
        unique (player_id, chess_title_id)
);

INSERT INTO test_chess.temp_chess_title (id, player_id, chess_title_id) VALUES ('332ee14d-0b3e-4e9b-af67-2fbecc7deb68', 1, 1);
