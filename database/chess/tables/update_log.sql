create table update_log
(
    id          int auto_increment
        primary key,
    update_type varchar(50)                           null,
    update_time timestamp default current_timestamp() null
);

INSERT INTO test_chess.update_log (id, update_type, update_time) VALUES (1, 'achievement_progress_success (rows: 1)', '2025-07-20 10:27:42');
INSERT INTO test_chess.update_log (id, update_type, update_time) VALUES (2, 'achievement_progress_success (rows: 0)', '2025-07-20 11:27:42');
