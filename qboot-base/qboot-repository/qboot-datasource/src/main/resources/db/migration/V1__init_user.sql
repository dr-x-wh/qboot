CREATE TABLE sys_user
(
    id            uuid                     PRIMARY KEY DEFAULT uuidv4(),
    no            text                     NOT NULL UNIQUE,
    name          text                     NOT NULL,
    create_time   timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    create_by     text                     NOT NULL,
    update_time   timestamp with time zone,
    update_by     text,
    password_hash text                     NOT NULL,
    age           integer                  NOT NULL
);
