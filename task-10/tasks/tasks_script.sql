use task10db;
/*Задание: 1*/
select model, speed, hd from pc where price < 500;
/*Задание: 2*/
select distinct maker from product where type = 'Printer';
/*Задание: 3*/
select model, ram, screen from laptop where price > 1000;
/*Задание: 4*/
select * from printer where color = 'y';
/*Задание: 5*/
select model, speed, hd from pc where cd in('12x', '24x') and price < 600;
/*Задание: 6*/
select distinct maker, speed from laptop l join product p on l.model = p.model where hd >= 100;
/*Задание: 7*/
select a.model, a.price from 
(select model, price from laptop union select model, price from pc union select model, price from printer) as a
join product p on a.model = p.model where maker = 'B';
/*Задание: 8*/
select distinct maker from product as pc where type = 'PC' and not exists 
       (select maker from product where type = 'Laptop' and maker = pc.maker);
/*Задание: 9*/
select distinct maker from product p join pc on p.model = pc.model where speed >= 450;
/*Задание: 10*/
select model, price from printer where price = (select max(price) from printer);
/*Задание: 11*/
select avg(speed) from pc;
/*Задание: 12*/
select avg(speed) from laptop where price > 1000;
/*Задание: 13*/
select avg(speed) from pc join product p on pc.model = p.model where maker = 'A';
/*Задание: 14*/
select speed, avg(price) from pc group by speed;
/*Задание: 15*/
select hd from pc group by hd having count(*) > 1;
/*Задание: 16*/
select distinct a.model, b.model, a.speed, a.ram from pc a 
join pc b on a.speed = b.speed and a.ram = b.ram where a.model > b.model;
/*Задание: 17*/
select distinct p.type, l.model, speed from laptop l join product p on l.model = p.model where speed < (select min(speed) from pc);
/*Задание: 18*/
select distinct maker, price from printer pr join product p on pr.model = p.model 
where color = 'y' and price = (select min(price) from printer where color = 'y');
/*Задание: 19*/
select maker, avg(screen) from laptop l join product p on l.model = p.model group by maker;
/*Задание: 20*/
select distinct maker, count(model) from product group by maker, type having count(*) >= 3 and type = 'PC';
/*Задание: 21*/
select distinct maker, max(price) from product p join pc on p.model = pc.model group by maker;
/*Задание: 22*/
select speed, avg(price) from pc where speed > 600 group by speed;
/*Задание: 23*/
select distinct maker from product p join pc on p.model = pc.model where speed >= 750 and 
       maker in (select distinct maker from product pl join laptop l on pl.model = l.model where l.speed >= 750);
/*Задание: 24*/
select model from (select model, price from pc union select model, price from laptop union select model, price from printer) as p
where price = (select max(price) from (select price from pc union select price from laptop union select price from printer) as pr);
/*Задание: 25*/
select distinct maker from product p join pc on p.model = pc.model 
where ram = (select min(ram) from pc) and speed = (select max(speed) from pc where ram = (select min(ram) from pc));