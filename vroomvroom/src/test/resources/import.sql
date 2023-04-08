insert into tm_email_auth (id, auth_code, email, send_date) values
(1, 'ABCD12', 'test1234@naver.com', TIMESTAMPADD(MINUTE, -5, NOW()));


insert into tm_user (user_id, crt_dt, crt_user_id, updt_dt, updt_user_id, use_yn, email, login_id, money, nickname, password, refresh_token) values
(1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, NULL, 'Y', 'test1234@naver.com', 'test1234', 500000 , '테스트1', '$2a$10$BatyPwoVxRfcpkj7apFHM.xgH.2WRDLyanxmEFQAa.Ae7HNoAFDs.', NULL);