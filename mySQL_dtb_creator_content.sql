-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: transferapp
-- ------------------------------------------------------
-- Server version	8.0.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bank_account`
--

DROP TABLE IF EXISTS `bank_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bank_account` (
  `iban` varchar(50) NOT NULL,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  PRIMARY KEY (`iban`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bank_account`
--

LOCK TABLES `bank_account` WRITE;
/*!40000 ALTER TABLE `bank_account` DISABLE KEYS */;
INSERT INTO `bank_account` VALUES ('\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0','mybank','j@i.com'),('14','anotherBankAccount','j@i.com');
/*!40000 ALTER TABLE `bank_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `connections`
--

DROP TABLE IF EXISTS `connections`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `connections` (
  `connection_id` int(11) NOT NULL AUTO_INCREMENT,
  `accountEmail` varchar(100) NOT NULL,
  `relationEmail` varchar(100) NOT NULL,
  PRIMARY KEY (`connection_id`),
  KEY `clientaccount_connections_fk` (`accountEmail`),
  CONSTRAINT `clientaccount_connections_fk` FOREIGN KEY (`accountEmail`) REFERENCES `useraccount` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `connections`
--

LOCK TABLES `connections` WRITE;
/*!40000 ALTER TABLE `connections` DISABLE KEYS */;
INSERT INTO `connections` VALUES (1,'j@i.com','j@a.com'),(2,'j@i.com','j@o.com');
/*!40000 ALTER TABLE `connections` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction` (
  `transaction_id` int(11) NOT NULL AUTO_INCREMENT,
  `sendingOrReceiving` tinyint(1) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `amount` decimal(6,2) NOT NULL,
  `email` varchar(100) NOT NULL,
  `relativeEmail` varchar(100) NOT NULL,
  `date` timestamp NOT NULL,
  `perceiveAmountForApp` double(6,2) NOT NULL,
  PRIMARY KEY (`transaction_id`),
  KEY `clientaccount_historic_transfer_fk` (`email`),
  CONSTRAINT `clientaccount_historic_transfer_fk` FOREIGN KEY (`email`) REFERENCES `useraccount` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (1,1,'new transaction',50.00,'j@i.com','j@a.com','2020-02-27 15:37:08',2.50),(2,0,'new transaction',47.50,'j@a.com','j@i.com','2020-02-27 15:37:08',2.50),(3,1,'another transaction for my friend',80.00,'j@i.com','j@o.com','2020-02-27 15:56:04',4.00),(4,0,'another transaction for my friend',76.00,'j@o.com','j@i.com','2020-02-27 15:56:04',4.00),(5,1,'another transaction for my friend',150.00,'j@i.com','j@o.com','2020-02-27 15:57:16',7.50),(6,0,'another transaction for my friend',142.50,'j@o.com','j@i.com','2020-02-27 15:57:16',7.50),(7,1,'yet a transaction',20.00,'j@i.com','j@a.com','2020-02-27 16:20:35',1.00),(8,0,'yet a transaction',19.00,'j@a.com','j@i.com','2020-02-27 16:20:35',1.00),(9,1,'Sending my money to a bank Account',-20.00,'j@i.com','mybank','2020-02-27 16:24:50',0.00);
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount`
--

DROP TABLE IF EXISTS `useraccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `useraccount` (
  `email` varchar(100) NOT NULL,
  `name` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `moneyAmount` decimal(6,2) NOT NULL,
  `dateLog` timestamp NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount`
--

LOCK TABLES `useraccount` WRITE;
/*!40000 ALTER TABLE `useraccount` DISABLE KEYS */;
INSERT INTO `useraccount` VALUES ('j@a.com','Jeff','loomis',520.00,'0000-00-00 00:00:00'),('j@i.com','John','schaffer',148.00,'2020-02-27 17:35:49'),('j@o.com','Sarah','claudius',230.00,'0000-00-00 00:00:00'),('j@u.com','Michael','amott',0.00,'0000-00-00 00:00:00');
/*!40000 ALTER TABLE `useraccount` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-02-27 18:42:15
