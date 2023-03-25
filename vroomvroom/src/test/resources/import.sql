insert into tm_email_auth (id, auth_code, email, send_date) values
(1, 'ABCD12', 'test1234@naver.com', TIMESTAMPADD(MINUTE, -5, NOW()));