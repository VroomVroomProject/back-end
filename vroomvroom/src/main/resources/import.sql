insert into tm_post_category (post_category_id, crt_dt, crt_user_id, updt_dt, updt_user_id, use_yn, admin_write_yn, orders, url, url_name, view_name) values
(1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, NULL, 'Y', 'N', 1, '/post/all', 'all', '전체'),
(2, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, NULL, 'Y', 'N', 2, '/post/questions', 'questions', 'Q&A'),
(3, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, NULL, 'Y', 'N', 3, '/post/free', 'free', '자유'),
(4, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, NULL, 'Y', 'Y', 4, '/post/notice', 'notice', '공지사항');


insert into tb_post (post_id, crt_dt, crt_user_id, updt_dt, updt_user_id, use_yn, comment_count, contents, notice_yn, post_category_id, title, views) values
(1, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -1 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -1 MINUTE), NULL, 'Y', 0, '전체 내용 1', 'N', 1, '전체1', 0),
(2, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -5 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -5 MINUTE), NULL, 'Y', 0, '전체 내용 2', 'N', 1, '전체2', 0),
(3, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -10 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -10 MINUTE), NULL, 'Y', 0, '전체 내용 3', 'N', 1, '전체3', 0),
(4, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -2 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -2 MINUTE), NULL, 'Y', 0, '전체 내용 4', 'N', 1, '전체4', 0),
(5, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -3 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -3 MINUTE), NULL, 'Y', 0, '전체 내용 5', 'N', 1, '전체5', 0),
(6, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -4 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -4 MINUTE), NULL, 'Y', 0, '전체 내용 6', 'N', 1, '전체6', 0),
(7, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -6 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -6 MINUTE), NULL, 'Y', 0, '전체 내용 7', 'N', 1, '전체7', 0),
(8, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -7 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -7 MINUTE), NULL, 'Y', 0, '전체 내용 8', 'N', 1, '전체8', 0),
(9, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -8 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -8 MINUTE), NULL, 'Y', 0, '전체 내용 9', 'N', 1, '전체9', 0),
(10, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -9 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -9 MINUTE), NULL, 'Y', 0, '전체 내용 10', 'N', 1, '전체10', 0),
(11, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -11 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -11 MINUTE), NULL, 'Y', 0, '전체 내용 11', 'N', 1, '전체11', 0),
(12, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -12 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -12 MINUTE), NULL, 'Y', 0, '전체 내용 12', 'N', 1, '전체12', 0),
(13, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -13 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -13 MINUTE), NULL, 'Y', 0, '전체 내용 13', 'N', 1, '전체13', 0),
(14, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -14 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -14 MINUTE), NULL, 'Y', 0, '전체 내용 14', 'N', 1, '전체14', 0),
(15, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -15 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -15 MINUTE), NULL, 'Y', 0, '전체 내용 15', 'N', 1, '전체15', 0),
(16, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -1 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -1 MINUTE), NULL, 'Y', 0, 'Q&A 내용 1', 'N', 2, 'Q&A1', 0),
(17, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -5 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -5 MINUTE), NULL, 'Y', 0, 'Q&A 내용 2', 'N', 2, 'Q&A2', 0),
(18, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -10 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -10 MINUTE), NULL, 'Y', 0, 'Q&A 내용 3', 'N', 2, 'Q&A3', 0),
(19, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -2 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -2 MINUTE), NULL, 'Y', 0, 'Q&A 내용 4', 'N', 2, 'Q&A4', 0),
(20, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -3 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -3 MINUTE), NULL, 'Y', 0, 'Q&A 내용 5', 'N', 2, 'Q&A5', 0),
(21, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -4 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -4 MINUTE), NULL, 'Y', 0, 'Q&A 내용 6', 'N', 2, 'Q&A6', 0),
(22, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -6 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -6 MINUTE), NULL, 'Y', 0, 'Q&A 내용 7', 'N', 2, 'Q&A7', 0),
(23, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -7 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -7 MINUTE), NULL, 'Y', 0, 'Q&A 내용 8', 'N', 2, 'Q&A8', 0),
(24, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -8 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -8 MINUTE), NULL, 'Y', 0, 'Q&A 내용 9', 'N', 2, 'Q&A9', 0),
(25, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -9 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -9 MINUTE), NULL, 'Y', 0, 'Q&A 내용 10', 'N', 2, 'Q&A10', 0),
(26, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -11 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -11 MINUTE), NULL, 'Y', 0, 'Q&A 내용 11', 'N', 2, 'Q&A11', 0),
(27, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -12 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -12 MINUTE), NULL, 'Y', 0, 'Q&A 내용 12', 'N', 2, 'Q&A12', 0),
(28, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -13 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -13 MINUTE), NULL, 'Y', 0, 'Q&A 내용 13', 'N', 2, 'Q&A13', 0),
(29, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -14 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -14 MINUTE), NULL, 'Y', 0, 'Q&A 내용 14', 'N', 2, 'Q&A14', 0),
(30, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -15 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -15 MINUTE), NULL, 'Y', 0, 'Q&A 내용 15', 'N', 2, 'Q&A15', 0),
(31, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -1 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -1 MINUTE), NULL, 'Y', 0, '공지사항 내용 1', 'Y', 4, '공지사항1', 0),
(32, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -5 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -5 MINUTE), NULL, 'Y', 0, '공지사항 내용 2', 'Y', 4, '공지사항2', 0),
(33, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -10 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -10 MINUTE), NULL, 'Y', 0, '공지사항 내용 3', 'Y', 4, '공지사항3', 0),
(34, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -2 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -2 MINUTE), NULL, 'Y', 0, '공지사항 내용 4', 'Y', 4, '공지사항4', 0),
(35, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -3 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -3 MINUTE), NULL, 'Y', 0, '공지사항 내용 5', 'Y', 4, '공지사항5', 0),
(36, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -4 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -4 MINUTE), NULL, 'Y', 0, '공지사항 내용 6', 'Y', 4, '공지사항6', 0),
(37, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -6 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -6 MINUTE), NULL, 'Y', 0, '공지사항 내용 7', 'Y', 4, '공지사항7', 0),
(38, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -7 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -7 MINUTE), NULL, 'Y', 0, '공지사항 내용 8', 'Y', 4, '공지사항8', 0),
(39, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -8 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -8 MINUTE), NULL, 'Y', 0, '공지사항 내용 9', 'Y', 4, '공지사항9', 0),
(40, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -9 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -9 MINUTE), NULL, 'Y', 0, '공지사항 내용 10', 'Y', 4, '공지사항10', 0),
(41, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -11 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -11 MINUTE), NULL, 'Y', 0, '공지사항 내용 11', 'Y', 4, '공지사항11', 0),
(42, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -12 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -12 MINUTE), NULL, 'Y', 0, '공지사항 내용 12', 'Y', 4, '공지사항12', 0),
(43, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -13 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -13 MINUTE), NULL, 'Y', 0, '공지사항 내용 13', 'Y', 4, '공지사항13', 0),
(44, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -14 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -14 MINUTE), NULL, 'Y', 0, '공지사항 내용 14', 'Y', 4, '공지사항14', 0),
(45, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -15 MINUTE), NULL, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -15 MINUTE), NULL, 'Y', 0, '공지사항 내용 15', 'Y', 4, '공지사항15', 0);


insert into tb_comment (comment_id, crt_dt, crt_user_id, updt_dt, updt_user_id, use_yn, contents, post_id) values
(1, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -3 MINUTE), 1, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -3 MINUTE), NULL, 'Y', '태스트 댓글 첫번째 작성', 1),
(2, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -2 MINUTE), 1, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -2 MINUTE), NULL, 'Y', '태스트 댓글 두번째 작성', 1),
(3, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -1 MINUTE), 1, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -1 MINUTE), NULL, 'Y', '태스트 댓글 세번째 작성', 1),
(4, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -3 MINUTE), 1, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -3 MINUTE), NULL, 'Y', '태스트 댓글 첫번째 작성', 2),
(5, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -2 MINUTE), 1, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -2 MINUTE), NULL, 'Y', '태스트 댓글 두번째 작성', 2);


insert into tm_user (user_id, crt_dt, crt_user_id, updt_dt, updt_user_id, use_yn, email, login_id, nickname, password, refresh_token) values
(1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, NULL, 'Y', 'test1234@naver.com', 'test1234', '테스트1', '$2a$10$hEcF3Hy1K/M.A78xVGLv2.4R1uI6Va3k6rRH9foQhYpOt7Y8O0v4e', NULL);