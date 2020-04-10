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
-- Dumping data for table `bank_account`
--

LOCK TABLES `bank_account` WRITE;
/*!40000 ALTER TABLE `bank_account` DISABLE KEYS */;
INSERT INTO `bank_account` VALUES ('5581165186','myBank','i@i.com');
/*!40000 ALTER TABLE `bank_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (15),(15);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `relation_email`
--

LOCK TABLES `relation_email` WRITE;
/*!40000 ALTER TABLE `relation_email` DISABLE KEYS */;
INSERT INTO `relation_email` VALUES (1,'i@i.com','j@i.com');
/*!40000 ALTER TABLE `relation_email` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (2,150,'2020-04-03 12:44:22.138941','withdraw 150$','i@i.com',0,'myBank',_binary '\0'),(3,-38.47,'2020-04-03 12:50:49.727670','hello guy','i@i.com',-2.03,'j@i.com',_binary ''),(4,38.47,'2020-04-03 12:50:49.739666','hello guy','j@i.com',0,'i@i.com',_binary '\0'),(5,-50,'2020-04-03 12:52:14.297649','from transferApps','i@i.com',0,'myBank',_binary ''),(6,-14.73,'2020-04-03 13:24:34.737287','a transfer','i@i.com',-0.78,'j@i.com',_binary ''),(7,14.73,'2020-04-03 13:24:34.770288','a transfer','j@i.com',0,'i@i.com',_binary '\0'),(8,-11.93,'2020-04-03 13:33:57.425867','','i@i.com',-0.62,'j@i.com',_binary ''),(9,11.93,'2020-04-03 13:33:57.455857','','j@i.com',0,'i@i.com',_binary '\0'),(10,-19,'2020-04-07 09:04:11.533912','test','i@i.com',-1,'j@i.com',_binary ''),(11,19,'2020-04-07 09:04:11.571899','test','j@i.com',0,'i@i.com',_binary '\0'),(12,-4.75,'2020-04-07 10:00:56.607975','','i@i.com',-0.25,'j@i.com',_binary ''),(13,4.75,'2020-04-07 10:00:56.640965','','j@i.com',0,'i@i.com',_binary '\0'),(14,800,'2020-04-08 08:35:08.800060','','i@i.com',0,'myBank',_binary '\0');
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_account`
--

LOCK TABLES `user_account` WRITE;
/*!40000 ALTER TABLE `user_account` DISABLE KEYS */;
INSERT INTO `user_account` VALUES ('i@i.com','1970-01-01 00:00:00.000000',806.44,'hans','$2a$12$h097gzB2Fl0CGtG7y4Xc/euTM3jgrqIeFiyhgHvA05SrQ5uBGeUIS','USER'),('j@i.com','1970-01-01 00:00:00.000000',88.91,'jeff','$2a$12$z6TlN9iL6K8ohfr/WmIwHOkCWs1HR65qmJZedKQ2hBlkBsZGRls5W','USER');
/*!40000 ALTER TABLE `user_account` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-10 10:11:52
