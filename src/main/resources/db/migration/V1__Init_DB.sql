create sequence hibernate_sequence start 1 increment 1;
create table chat_messages
           (
               message_id int8 not null,
               chat       varchar(255),
               content    varchar(255),
               receiver   varchar(255),
               sender     varchar(255),
               type       int4,
               primary key (message_id)
           );
create table chats
           (
               chat_id        int8 not null,
               first_user_id  int8,
               second_user_id int8,
               primary key (chat_id),
               unique (first_user_id, second_user_id)

           );
create table message
           (
               id       int8 not null,
               filename varchar(255),
               tag      varchar(255),
               text     varchar(2048) not null,
               user_id  int8,
               primary key (id)
           );
create table user_role
           (
               user_id int8 not null,
               roles   varchar(255)
           );
create table usr
           (
               id                 int8    not null,
               account_non_locked boolean not null,
               enabled            boolean not null,
               failed_attempt     int4,
               lock_time          timestamp,
               password           varchar(255) not null,
               username           varchar(255) not null,
               primary key (id)
           );
alter table if exists chats add constraint chat_create_unique unique (first_user_id, second_user_id);
alter table if exists usr add constraint username_unique  unique (username);
alter table if exists message add constraint message_fk foreign key (user_id) references usr;
alter table if exists user_role add constraint user_role_fk foreign key (user_id) references usr;