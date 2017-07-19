-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: cajero
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.13-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `mae_cli`
--

DROP TABLE IF EXISTS `mae_cli`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mae_cli` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT NULL,
  `numeroTarjeta` int(11) DEFAULT NULL,
  `clave` int(11) DEFAULT NULL,
  `saldo` int(11) DEFAULT NULL,
  `ult_mov_monto` int(11) DEFAULT NULL,
  `fecha_ult` varchar(15) DEFAULT NULL,
  `hora_ult` varchar(15) DEFAULT NULL,
  `mail` varchar(100) NOT NULL DEFAULT 'manuel@gmail.com',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mae_cli`
--

LOCK TABLES `mae_cli` WRITE;
/*!40000 ALTER TABLE `mae_cli` DISABLE KEYS */;
INSERT INTO `mae_cli` VALUES (1,'Miguel',70937537,1234,1000,100,'19072017','111617','manuel.992260598@gmail.com'),(2,'luis',73035770,1234,1000,0,'01012001','010101','manuel@gmail.com'),(3,'bryan',73096984,1234,1000,0,'01012001','010101','manuel@gmail.com'),(4,'sergio',44818267,1234,1000,0,'01012001','010101','sergiogma12@gmail.com'),(5,'Juan',12345678,1234,2000,600,'19072017','060019','aplidistribuidas@gmail.com');
/*!40000 ALTER TABLE `mae_cli` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'cajero'
--

--
-- Dumping routines for database 'cajero'
--
/*!50003 DROP PROCEDURE IF EXISTS `sp_DevolverMaeClienteId` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_DevolverMaeClienteId`(IN `prtmIdCliente` INT)
begin
select 
	id,
	nombre,
	numeroTarjeta,
	clave,
	saldo,
	ult_mov_monto,
	fecha_ult,
	hora_ult,
        mail
from mae_cli where id = prtmIdCliente;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_EditarMaeCliente` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_EditarMaeCliente`(
	in prtmidClientes int,
	in prtmsaldo int,
	in prtmultMovMonto int,
	in prtmfechaUlt varchar(15),
	in prtmhoraUlt varchar(15))
begin
update mae_cli set 
	saldo=prtmsaldo,
	ult_mov_monto=prtmultMovMonto,
	fecha_ult=prtmfechaUlt,
	hora_ult=prtmhoraUlt where id=prtmidClientes;

end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_nuevoMaeCliente` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_nuevoMaeCliente`(
	in prtmnombre varchar(50),
	in prtmnumeroTarjeta int,
	in prtmclave varchar(4),
	in prtmsaldo int,
	in prtmultMovMonto int,
	in prtmfechaUlt varchar(15),
	in prtmhoraUlt varchar(15))
begin
insert into mae_cli(
	nombre,
	numeroTarjeta,
	clave,
	saldo,
	ult_mov_monto,
	fecha_ult,
	hora_ult
	)values(
	prtmnombre,
	prtmnumeroTarjeta,
	prtmclave,
	prtmsaldo,
	prtmultMovMonto,
	prtmfechaUlt,
	prtmhoraUlt
	);
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_VerificarAccesoCliente` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_VerificarAccesoCliente`(IN `prtmnumeroTarjeta` INT, IN `prtmClave` VARCHAR(4))
begin
select 
	id,
	nombre,
	numeroTarjeta,
	clave,
	saldo,
	ult_mov_monto,
	fecha_ult,
	hora_ult,
mail
from mae_cli where numeroTarjeta=prtmnumeroTarjeta and clave=prtmClave;
end ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-07-19 18:06:52
