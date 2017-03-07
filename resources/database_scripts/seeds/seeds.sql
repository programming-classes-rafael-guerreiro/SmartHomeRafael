delimiter !!

begin
	declare user_id int(11);
	insert into users (name) values ('Rafael Guerreiro');
	select last_insert_id() into user_id;

	insert into devices (name, user_id) values ('Microwave', 2), ('Fridge', 2);
end!!

delimiter ;