delete from RecargaEntity;
delete from TarjetaDeCreditoEntity;
delete from FotoEntity;
delete from ComentarioEntity;
delete from ProductoEntity;
delete from CompraEntity;
delete from EventoEntity;
delete from SucursalEntity;
delete from TarjetaPuntosEntity;
delete from UbicacionEntity;
delete from ClienteEntity;
delete from RestauranteEntity;
delete from AdministradorEntity;

insert into ClienteEntity (usuario, contrasena, imagen, nombre) values ('C1','C1','C1.jpg','C1');
insert into ClienteEntity (usuario, contrasena, imagen, nombre) values ('C2','C2','C2.jpg','C2');
insert into ClienteEntity (usuario, contrasena, imagen, nombre) values ('C3','C3','C3.jpg','C3');
insert into ClienteEntity (usuario, contrasena, imagen, nombre) values ('C4','C4','C4.jpg','C4');
insert into ClienteEntity (usuario, contrasena, imagen, nombre) values ('C5','C5','C5.jpg','C5');

insert into TarjetaDeCreditoEntity (banco, numero, cliente_usuario) values ('Bancolombia', 4562182, 'C1' );
insert into TarjetaDeCreditoEntity (banco, numero, cliente_usuario) values ('Bancopichincha', 4578236, 'C1' );
insert into TarjetaDeCreditoEntity (banco, numero, cliente_usuario) values ('Piramide', 54126, 'C1' );
insert into TarjetaDeCreditoEntity (banco, numero, cliente_usuario) values ('Bancolombia', 4562182, 'C2' );
insert into TarjetaDeCreditoEntity (banco, numero, cliente_usuario) values ('BancoLadron', 897521, 'C3' );

insert into TarjetaPuntosEntity ( montoactual, montobasico, numpuntos, cliente_usuario) values ( 5000,5000,10,'C1');
insert into TarjetaPuntosEntity ( montoactual, montobasico, numpuntos, cliente_usuario) values ( 5000,5000,20,'C2');
insert into TarjetaPuntosEntity ( montoactual, montobasico, numpuntos, cliente_usuario) values ( 1000,5000,20,'C3');
insert into TarjetaPuntosEntity ( montoactual, montobasico, numpuntos, cliente_usuario) values ( 8000,5010,20,'C4');
insert into TarjetaPuntosEntity ( montoactual, montobasico, numpuntos, cliente_usuario) values ( 5900,2000,20,'C4');

insert into RecargaEntity (fecha, valor, cliente_usuario,  tarjetaPuntos_id, tarjetaDeCredito_id) values ('4/7/1965', 8000, 'C1' ,(SELECT MAX(ID) FROM TarjetaPuntosEntity WHERE cliente_usuario='C1'),(SELECT MAX(ID) FROM TarjetaDeCreditoEntity WHERE cliente_usuario='C1') );
insert into RecargaEntity (fecha, valor, cliente_usuario,  tarjetaPuntos_id, tarjetaDeCredito_id) values ('4/7/1965', 8000, 'C1' ,(SELECT MAX(ID) FROM TarjetaPuntosEntity WHERE cliente_usuario='C1'),(SELECT MIN(ID) FROM TarjetaDeCreditoEntity WHERE cliente_usuario='C1') );
insert into RecargaEntity (fecha, valor, cliente_usuario,  tarjetaPuntos_id, tarjetaDeCredito_id) values ('4/8/1967', 8000, 'C2' ,(SELECT MAX(ID) FROM TarjetaPuntosEntity WHERE cliente_usuario='C1'),(SELECT MAX(ID) FROM TarjetaDeCreditoEntity WHERE cliente_usuario='C2') );
insert into RecargaEntity (fecha, valor, cliente_usuario,  tarjetaPuntos_id, tarjetaDeCredito_id) values ('10/9/1968', 8000, 'C3',(SELECT MAX(ID) FROM TarjetaPuntosEntity WHERE cliente_usuario='C1'),(SELECT MAX(ID) FROM TarjetaDeCreditoEntity WHERE cliente_usuario='C3') );

insert into AdministradorEntity (USUARIO, CONTRASENA) values ('aa','fofo');
insert into AdministradorEntity (USUARIO, CONTRASENA) values ('Andres','1234');

insert into RestauranteEntity (NIT, NOMBRE, TIPOCOMIDA,ADMINISTRADOR_USUARIO) values ('1','BBC','alemana','aa');
insert into RestauranteEntity (NIT, NOMBRE, TIPOCOMIDA,ADMINISTRADOR_USUARIO) values ('111','BC','mex','aa');
insert into RestauranteEntity (NIT, NOMBRE, TIPOCOMIDA,ADMINISTRADOR_USUARIO) values ('A','BC','mex','aa');

insert into UbicacionEntity (DIRECCION, LATITUD, LONGITUD) values ('asd',1002,120);

insert into SucursalEntity (ID, DESCRIPCION, HORAAPERTURA, HORACIERRE, NOMBRE, RESTAURANTE_NIT, UBICACION_DIRECCION) values (87, 'hola', '4/7/1965', '4/7/1965', 'Mc Donals','A', 'asd');
insert into ProductoEntity (id, nombre, valorDinero, valorpuntos, RESTAURANTE_NIT) values (1,'pollo',5000,200, '1');
insert into ProductoEntity (id, nombre, valorDinero, valorpuntos, RESTAURANTE_NIT) values (100,'res',8000,300, '1');
insert into ProductoEntity (id, nombre, valorDinero, valorpuntos, RESTAURANTE_NIT) values (10,'caca molida',8000,300, '1');

insert into ComentarioEntity (id, comentario, calificacion, sucursal_id, cliente_usuario) values (1, 'Que buen servicio +10', 10, 87, 'C1');
insert into ComentarioEntity (id, comentario, calificacion, sucursal_id, cliente_usuario) values (2, 'Mal momo', 1, 87, 'C2');

insert into EventoEntity(nombre, fechaInicio, fechaFin, descripcion) values ('Taquitos gratis','4/7/1965' , '6/6/2010' , 'Se regalaran tacos gratis.' );
insert into EventoEntity(nombre, fechaInicio, fechaFin, descripcion) values ('Super Hamburguesas','4/7/1965' , '5/8/2010' , 'Se bajaran de precio las mejores hamburguesas de cada restaurante.' );
insert into EventoEntity(nombre, fechaInicio, fechaFin, descripcion) values ('Taquitos gratis 1','4/7/1965' , '2/6/2010' , 'Se regalaran tacos gratis.' );
insert into EventoEntity(nombre, fechaInicio, fechaFin, descripcion) values ('Taquitos gratis 2','4/7/1965' , '2/6/2010' , 'Se regalaran tacos gratis.' );
insert into CompraEntity (ID, PAGOCONPUNTOS, CLIENTE_USUARIO, SUCURSAL_ID, TARJETAPUNTOS_ID) values (20, 1, 'C1', 87, (SELECT MAX(ID) FROM TarjetaPuntosEntity WHERE cliente_usuario='C1'));
insert into CompraEntity (ID, PAGOCONPUNTOS, CLIENTE_USUARIO, SUCURSAL_ID, TARJETAPUNTOS_ID) values (60, 0, 'C1', 87, (SELECT MAX(ID) FROM TarjetaPuntosEntity WHERE cliente_usuario='C1'));