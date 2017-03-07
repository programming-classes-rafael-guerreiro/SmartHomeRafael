alter table devices add column (user_id int not null);
alter table devices add foreign key (user_id) references users(user_id);