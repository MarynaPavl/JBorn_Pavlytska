create table service_users(
                      id            SERIAL PRIMARY KEY,
                      first_name    VARCHAR(255) NOT NULL,
                      last_name     VARCHAR(255) NULL,
                      email_address VARCHAR(255) NOT NULL,
                      password      VARCHAR(255)   NOT NULL
);

create table account(
                        id         SERIAL PRIMARY KEY,
                        name_account VARCHAR(255)   NULL,
                        balance    DECIMAL(10, 2) NOT NULL,
                        currency   VARCHAR(255)   NOT NULL,
                        user_id    INT REFERENCES service_users (id) NOT NULL
);

create table transaction(
                            id              SERIAL PRIMARY KEY,
                            id_account_from INT REFERENCES account (id) NULL,
                            id_account_to   INT REFERENCES account (id) NULL,
                            sum             DECIMAL (10, 2)              NOT NULL,
                            time            date                        NOT NULL
);

create table category(
                         id         SERIAL PRIMARY KEY,
                         assignment VARCHAR(255) NOT NULL
);

create table transaction_to_category(
                                        transaction_id INT REFERENCES transaction (id) NOT NULL,
                                        category_id    INT REFERENCES category (id) NO NULL
);


select u.id,
       u.first_name,
       c.name_account,
       c.balance
from account as c
         join users u on c.user_id = u.id
where c.user_id = 1;

select u.id,
       u.first_name,
       t.sum,
       count(t.id_account_from),
       count(t.id_account_to)
from transaction as t
         join account a on a.id = t.id_account_from or a.id = t.id_account_to
         join users u on u.id = a.user_id
where t.time = '23.09.2020' and u.id = 1
group by u.id, u.first_name, t.sum;

select u.id,
       u.first_name,
       c.balance
from account as c
         left join users u on u.id = c.user_id;