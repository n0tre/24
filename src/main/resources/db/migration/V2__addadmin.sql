insert into usr (id, username, password, enabled, account_non_locked, failed_attempt)
values (1, 'admin', '1' , true, true, 0);

insert into user_role(user_id, roles) values (1, 'USER'), (1, 'ADMIN');

