drop table if exists javatry;
CREATE TABLE javatry (
    id SERIAL primary key ,
	from_user_id BIGINT  NOT NULL,
    to_user_id BIGINT NOT NULL,
    body TEXT unique,
    created_at timestamp DEFAULT NOW()
)
insert into javatry(from_user_id, to_user_id, body) values(1,2, 'Voluptatem ut quaerat quia. Pariatur esse amet ratione qui quia. In necessitatibus reprehenderit et. Nam accusantium aut qui quae nesciunt non.')
,(2,1,'Sint dolores et debitis est ducimus. Aut et quia beatae minus. Ipsa rerum totam modi sunt sed. Voluptas atque eum et odio ea molestias ipsam architecto.')
,(3,1,'Sed mollitia quo sequi nisi est tenetur at rerum. Sed quibusdam illo ea facilis nemo sequi. Et tempora repudiandae saepe quo.');

select * from javatry;

insert into javatry(from_user_id, to_user_id, body) values
(1,3,'Quod dicta omnis placeat id et officiis et. Beatae enim aut aliquid neque occaecati odit. Facere eum distinctio assumenda omnis est delectus magnam.')
,(1,6,'Rerum labore culpa et laboriosam eum totam. Quidem pariatur sit alias. Atque doloribus ratione eum rem dolor vitae saepe.')
,(10,10,'Praesentium molestias quia aut odio. Est quis eius ut animi optio molestiae. Amet tempore sequi blanditiis in est.')
,(21,21,'Architecto sunt asperiores modi. A commodi non qui.');

delete from javatry where from_user_id = 1 or from_user_id = 2 or from_user_id = 3

select * from javatry;

update javatry set body = 'qeqeqeqweqweqwasdasdasd asdasdasd qwe' where from_user_id = 21;
select created_at::time from javatry;

create table company(
    id serial primary key,
    user_name varchar(128) not null,
    reg_date date
);
create table employee(
    id serial primary key,
    first_name varchar(128) not null,
    last_name varchar(128) not null,
    salary integer not null,
    company_id integer not null
    references company
);
drop table if exists contact;
create table contact(
    id serial primary key,
    number varchar(256),
    type varchar(256)
);
create table employee_contact(
    employee_id integer not null references employee,
    contact_id integer not null references contact,
    primary key(employee_id,contact_id)
);

insert into company(user_name,reg_date) values ('TikTok','1960-10-10'),('Google','2023-03-24'),('Apple','2001-01-10');
insert into employee(first_name,last_name,salary,company_id) values ('Lev','Popkin',5000,2)
,('Jon','Jonson',500,1),('Ivan','Petrov',15000,1),('Petr','Pukin',1000,1);
insert into contact("number", "type") values ('123456789', 'home'),('987654321', 'work');
insert into employee_contact(employee_id, contact_id) values (1,1),(1,2),(2,1),(2,2),(3,1),(3,2),(4,1),(4,2);

select e.first_name || ' ' ||e.last_name as user_name, c.user_name as company, ec.contact_id, ec.employee_id from employee e
join company c on c.id = e.company_id
join employee_contact ec on ec.employee_id = e."id"
join contact co on co.id = ec.contact_id;

select * from employee
cross join (select count(*) from employee);

select e.first_name || ' ' ||e.last_name as user_name, c.user_name as company from employee e
full join company c on c.id = e.company_id

select c.user_name,count(c.user_name) from employee e
join company c on c.id = e.company_id
group by c.user_name