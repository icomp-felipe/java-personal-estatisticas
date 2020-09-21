-- MySQL dump 10.15  Distrib 10.0.15-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: ESTATISTICAS
-- ------------------------------------------------------
-- Server version	10.0.15-MariaDB-log

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
-- Table structure for table `DADOS`
--

CREATE DATABASE IF NOT EXISTS ESTATISTICAS;
USE ESTATISTICAS;

DROP TABLE IF EXISTS `DADOS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DADOS` (
  `DADO_CPF_PK` varchar(11) NOT NULL,
  `DADO_NOME` text,
  `DADO_DTNASC` text DEFAULT NULL,
  `DADO_LOGRADOURO` text,
  `DADO_COMPLEMENTO` text,
  `DADO_NUM_CASA` text,
  `DADO_BAIRRO` text,
  `DADO_CIDADE` text,
  `DADO_UF` varchar(2) DEFAULT NULL,
  `DADO_CEP` varchar(8) DEFAULT NULL,
  `DADO_TEL01` text,
  `DADO_TEL02` text,
  `DADO_EMAIL` text,
  `DADO_OBSERVACAO` text,
  `DADO_DTINSERT` datetime DEFAULT NULL,
  PRIMARY KEY (`DADO_CPF_PK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DADOS`
--

LOCK TABLES `DADOS` WRITE;
/*!40000 ALTER TABLE `DADOS` DISABLE KEYS */;
/*!40000 ALTER TABLE `DADOS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USUARIO`
--

DROP TABLE IF EXISTS `USUARIO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USUARIO` (
  `USER_LOGIN_PK` varchar(50) NOT NULL,
  `USER_PASSWORD` text,
  PRIMARY KEY (`USER_LOGIN_PK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USUARIO`
--

LOCK TABLES `USUARIO` WRITE;
/*!40000 ALTER TABLE `USUARIO` DISABLE KEYS */;
INSERT INTO `USUARIO` VALUES ('admin','*4ACFE3202A5FF5CF467898FC58AAB1D615029441');
/*!40000 ALTER TABLE `USUARIO` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

GRANT ALL PRIVILEGES ON ESTATISTICAS.* TO 'java'@'%' IDENTIFIED BY 'javaApplet' WITH GRANT OPTION;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-12-15 20:49:08