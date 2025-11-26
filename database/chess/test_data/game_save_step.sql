create table game_save_step
(
    id            uuid                                   not null
        primary key,
    next_step_id  int                                    not null,
    game_save_id  uuid                                   not null,
    season_id     uuid                                   not null,
    tournament_id uuid                                   null,
    phase_id      uuid                                   null,
    round         int                                    null,
    priority      int                                    not null,
    `order`       int                                    not null,
    date          date                                   null,
    completed     tinyint(1) default 0                   null,
    created_at    timestamp  default current_timestamp() not null,
    updated_at    timestamp  default current_timestamp() not null,
    constraint game_save_step_pk
        unique (game_save_id, `order`, season_id),
    constraint game_save_step_ref_next_step_id_fk
        foreign key (next_step_id) references ref_next_step (id)
);

INSERT INTO test_chess.game_save_step (id, next_step_id, game_save_id, season_id, tournament_id, phase_id, round, priority, `order`, date, completed, created_at, updated_at) VALUES ('00000000-0000-0000-0000-000000000001', 4, '11111111-1111-1111-1111-111111111111', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', null, null, null, 1, 1, '2025-01-01', 0, '2025-11-12 18:32:39', '2025-11-12 18:32:39');
INSERT INTO test_chess.game_save_step (id, next_step_id, game_save_id, season_id, tournament_id, phase_id, round, priority, `order`, date, completed, created_at, updated_at) VALUES ('00000000-0000-0000-0000-000000000002', 7, '11111111-1111-1111-1111-111111111111', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', null, null, null, 1, 2, '2025-01-01', 0, '2025-11-12 18:32:39', '2025-11-12 18:32:39');
INSERT INTO test_chess.game_save_step (id, next_step_id, game_save_id, season_id, tournament_id, phase_id, round, priority, `order`, date, completed, created_at, updated_at) VALUES ('00000000-0000-0000-0000-000000000003', 7, '11111111-1111-1111-1111-111111111111', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', null, null, null, 1, 3, '2025-01-01', 0, '2025-11-12 18:32:39', '2025-11-12 18:32:39');
INSERT INTO test_chess.game_save_step (id, next_step_id, game_save_id, season_id, tournament_id, phase_id, round, priority, `order`, date, completed, created_at, updated_at) VALUES ('00000000-0000-0000-0000-000000000004', 5, '11111111-1111-1111-1111-111111111111', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', null, null, null, 2, 1, '2025-01-01', 0, '2025-11-12 18:32:39', '2025-11-12 18:32:39');
INSERT INTO test_chess.game_save_step (id, next_step_id, game_save_id, season_id, tournament_id, phase_id, round, priority, `order`, date, completed, created_at, updated_at) VALUES ('00000000-0000-0000-0000-000000000005', 2, '11111111-1111-1111-1111-111111111111', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', null, null, null, 1, 4, '2025-01-02', 0, '2025-11-12 18:32:39', '2025-11-12 18:32:39');
INSERT INTO test_chess.game_save_step (id, next_step_id, game_save_id, season_id, tournament_id, phase_id, round, priority, `order`, date, completed, created_at, updated_at) VALUES ('00000000-0000-0000-0000-000000000006', 1, '22222222-2222-2222-2222-222222222222', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', null, null, null, 1, 1, '2025-02-01', 0, '2025-11-12 18:32:39', '2025-11-12 18:32:39');
INSERT INTO test_chess.game_save_step (id, next_step_id, game_save_id, season_id, tournament_id, phase_id, round, priority, `order`, date, completed, created_at, updated_at) VALUES ('00000000-0000-0000-0000-000000000007', 1, '22222222-2222-2222-2222-222222222222', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', null, null, null, 1, 2, '2025-02-01', 0, '2025-11-12 18:32:39', '2025-11-12 18:32:39');
INSERT INTO test_chess.game_save_step (id, next_step_id, game_save_id, season_id, tournament_id, phase_id, round, priority, `order`, date, completed, created_at, updated_at) VALUES ('00000000-0000-0000-0000-000000000008', 11, '22222222-2222-2222-2222-222222222222', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', null, null, null, 1, 1, '2025-03-01', 0, '2025-11-12 18:32:39', '2025-11-12 18:32:39');
INSERT INTO test_chess.game_save_step (id, next_step_id, game_save_id, season_id, tournament_id, phase_id, round, priority, `order`, date, completed, created_at, updated_at) VALUES ('00000000-0000-0000-0000-000000000009', 3, '33333333-3333-3333-3333-333333333333', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', null, null, null, 1, 1, '2024-12-31', 1, '2025-11-12 18:32:39', '2025-11-12 18:32:39');
INSERT INTO test_chess.game_save_step (id, next_step_id, game_save_id, season_id, tournament_id, phase_id, round, priority, `order`, date, completed, created_at, updated_at) VALUES ('00000000-0000-0000-0000-00000000000a', 6, '11111111-1111-1111-1111-111111111111', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', null, null, null, 3, 2, '2025-01-01', 0, '2025-11-12 18:32:39', '2025-11-12 18:32:39');
