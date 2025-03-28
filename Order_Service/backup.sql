-- MySQL dump 10.13  Distrib 8.3.0, for Linux (x86_64)
--
-- Host: localhost    Database: order_service
-- ------------------------------------------------------
-- Server version	8.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item` (
  `price` float DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint NOT NULL,
  `productid` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `price` float DEFAULT NULL,
  `productid` bigint DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `order_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbioxgbv59vetrxe0ejfubep1w` (`order_id`),
  CONSTRAINT `FKbioxgbv59vetrxe0ejfubep1w` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` VALUES (1,1000,4,3,1),(2,1000,4,3,2),(3,1000,4,4,3),(4,995,1,3,4),(5,1000,4,2,4),(6,995,1,2,5),(7,1000,4,2,5),(8,1000,4,2,6),(9,995,1,2,6),(10,1000,4,2,7),(11,995,1,2,7),(12,1000,4,3,8),(13,1000,4,2,9),(14,1000,4,3,10),(15,1000,4,3,11),(16,1000,4,3,12),(17,995,1,3,13),(18,1000,4,1,14),(19,1000,4,1,15),(20,1000,4,1,16);
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `addressid` bigint DEFAULT NULL,
  `orderdate` date DEFAULT NULL,
  `orderstatus` varchar(255) DEFAULT NULL,
  `orderupdatedate` date DEFAULT NULL,
  `payer_id` varchar(255) DEFAULT NULL,
  `payment_id` varchar(255) DEFAULT NULL,
  `payment_method` varchar(255) DEFAULT NULL,
  `payment_status` varchar(255) DEFAULT NULL,
  `total_amount` float DEFAULT NULL,
  `userid` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,2,'2024-12-04','inProcess','2024-12-04',NULL,'','paypal','pending',3000,27),(2,2,'2024-12-04','pending','2024-12-04',NULL,'','paypal','pending',3000,27),(3,2,'2024-12-04','pending','2024-12-04',NULL,'','paypal','pending',4000,27),(4,2,'2024-12-04','pending','2024-12-04',NULL,'','paypal','pending',4985,27),(5,2,'2024-12-04','pending','2024-12-04',NULL,'','paypal','pending',3990,27),(6,2,'2024-12-04','confirmed','2024-12-04','2WWUW64VVZMNG','PAYID-M5H7A4A2RF7444979186123N','paypal','Success',3990,27),(7,2,'2024-12-04','confirmed','2024-12-04','2WWUW64VVZMNG','PAYID-M5H75KI9AE77762WY312044X','paypal','Success',3990,27),(8,2,'2024-12-04','confirmed','2024-12-04','2WWUW64VVZMNG','PAYID-M5IG2VY6FW26886W4763343K','paypal','Success',3000,27),(9,2,'2024-12-07','pending','2024-12-07',NULL,'','paypal','pending',2000,27),(10,2,'2024-12-07','pending','2024-12-07',NULL,'','paypal','pending',3000,27),(11,2,'2024-12-07','pending','2024-12-07',NULL,'','paypal','pending',3000,27),(12,2,'2024-12-07','confirmed','2024-12-07','2WWUW64VVZMNG','PAYID-M5J6UOY3V279341NY3279014','paypal','Success',3000,27),(13,2,'2024-12-07','confirmed','2024-12-07','2WWUW64VVZMNG','PAYID-M5J6VFY1XK29359AS8270735','paypal','Success',2985,27),(14,2,'2024-12-10','pending','2024-12-10',NULL,'','paypal','pending',1000,27),(15,2,'2024-12-10','pending','2024-12-10',NULL,'','paypal','pending',1000,27),(16,2,'2024-12-10','confirmed','2024-12-10','2WWUW64VVZMNG','PAYID-M5L5SJA7WC25663AJ235314W','paypal','Success',1000,27);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-10 15:23:46
