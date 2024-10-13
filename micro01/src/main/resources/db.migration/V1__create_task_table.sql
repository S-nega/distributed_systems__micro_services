create table tasks (
     id serial primary key,
--    id bigint primary key,
    title varchar(255),
    description text,
    due_date timestamp,
    status int default 1,
    user_id serial
);

create index idx_tasks_user on tasks (user_id);