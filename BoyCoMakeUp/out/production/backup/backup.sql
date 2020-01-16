-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: makeup
-- ------------------------------------------------------
-- Server version	8.0.18

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
-- Current Database: `makeup`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `makeup` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `makeup`;

--
-- Table structure for table `brands`
--

DROP TABLE IF EXISTS `brands`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brands` (
  `brand_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `nip` char(10) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`brand_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brands`
--

LOCK TABLES `brands` WRITE;
/*!40000 ALTER TABLE `brands` DISABLE KEYS */;
INSERT INTO `brands` VALUES (1,'qwe','5260250995','Ulica 1/1 Miasto'),(2,'brand gui','9718655080','branzowa 12');
/*!40000 ALTER TABLE `brands` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clients`
--

DROP TABLE IF EXISTS `clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clients` (
  `client_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) DEFAULT NULL,
  `nip` char(10) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients`
--

LOCK TABLES `clients` WRITE;
/*!40000 ALTER TABLE `clients` DISABLE KEYS */;
INSERT INTO `clients` VALUES (1,'sklepik','5260250995','Kolejowa 2/2'),(2,'testowy klient','8217485838','kwiatowa 12'),(3,'client gui','7016002821','adres qui'),(4,'klient od employee','3933460258','adres'),(5,'123','5246087033','123');
/*!40000 ALTER TABLE `clients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `colors`
--

DROP TABLE IF EXISTS `colors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `colors` (
  `color_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`color_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `colors`
--

LOCK TABLES `colors` WRITE;
/*!40000 ALTER TABLE `colors` DISABLE KEYS */;
INSERT INTO `colors` VALUES (1,'czerwony'),(2,'czarny'),(3,'niebieski');
/*!40000 ALTER TABLE `colors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `concealers`
--

DROP TABLE IF EXISTS `concealers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `concealers` (
  `product_id` int(11) NOT NULL,
  `color_id` int(11) DEFAULT NULL,
  `undertone` enum('pink','yellow','neutral') DEFAULT NULL,
  `coverage` enum('light','medium','full') DEFAULT NULL,
  `fill_weight` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  KEY `color_id` (`color_id`),
  CONSTRAINT `concealers_ibfk_1` FOREIGN KEY (`color_id`) REFERENCES `colors` (`color_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `concealers`
--

LOCK TABLES `concealers` WRITE;
/*!40000 ALTER TABLE `concealers` DISABLE KEYS */;
INSERT INTO `concealers` VALUES (6,3,'pink','light',88),(10,3,'yellow','light',123),(15,3,'pink','light',32);
/*!40000 ALTER TABLE `concealers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deliveries`
--

DROP TABLE IF EXISTS `deliveries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `deliveries` (
  `delivery_id` int(11) NOT NULL AUTO_INCREMENT,
  `delivery_date` date DEFAULT NULL,
  `done` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`delivery_id`),
  KEY `deliveriesDateIndex` (`delivery_date`) USING BTREE,
  KEY `deliveriesDoneIndex` (`done`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deliveries`
--

LOCK TABLES `deliveries` WRITE;
/*!40000 ALTER TABLE `deliveries` DISABLE KEYS */;
INSERT INTO `deliveries` VALUES (3,'2020-02-12',1),(5,'2020-02-12',1),(9,NULL,1),(10,'2020-02-24',1),(11,'2020-02-01',1),(12,'2020-01-31',0),(13,'2020-01-08',0),(14,'2020-01-01',0);
/*!40000 ALTER TABLE `deliveries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deliveries_details`
--

DROP TABLE IF EXISTS `deliveries_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `deliveries_details` (
  `delivery_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `quantity` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`delivery_id`,`product_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `deliveries_details_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  CONSTRAINT `deliveries_details_ibfk_2` FOREIGN KEY (`delivery_id`) REFERENCES `deliveries` (`delivery_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deliveries_details`
--

LOCK TABLES `deliveries_details` WRITE;
/*!40000 ALTER TABLE `deliveries_details` DISABLE KEYS */;
INSERT INTO `deliveries_details` VALUES (3,1,10),(3,2,20),(3,3,30),(5,1,10),(5,2,20),(10,1,1),(10,2,2222),(10,3,33333),(11,4,12),(11,5,15),(11,6,16),(12,4,1333),(12,5,2222),(14,2,32);
/*!40000 ALTER TABLE `deliveries_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lipsticks`
--

DROP TABLE IF EXISTS `lipsticks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lipsticks` (
  `product_id` int(11) NOT NULL,
  `color_id` int(11) DEFAULT NULL,
  `formula` enum('liquid','cream','gloss') DEFAULT NULL,
  `finish` enum('matte','satin','glossy') DEFAULT NULL,
  `fill_weight` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  KEY `color_id` (`color_id`),
  CONSTRAINT `lipsticks_ibfk_1` FOREIGN KEY (`color_id`) REFERENCES `colors` (`color_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lipsticks`
--

LOCK TABLES `lipsticks` WRITE;
/*!40000 ALTER TABLE `lipsticks` DISABLE KEYS */;
INSERT INTO `lipsticks` VALUES (1,1,'liquid','satin',20),(2,3,'cream','matte',40),(4,1,'liquid','matte',55),(5,2,'cream','matte',99),(8,3,'liquid','satin',12),(9,2,'cream','glossy',232),(12,1,'liquid','matte',12),(13,3,'liquid','satin',12),(16,1,'liquid','matte',23);
/*!40000 ALTER TABLE `lipsticks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mascaras`
--

DROP TABLE IF EXISTS `mascaras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mascaras` (
  `product_id` int(11) NOT NULL,
  `color_id` int(11) DEFAULT NULL,
  `brush` enum('silicone','tradicional') DEFAULT NULL,
  `effect` enum('volume','lifting') DEFAULT NULL,
  `fill_weight` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  KEY `color_id` (`color_id`),
  CONSTRAINT `mascaras_ibfk_1` FOREIGN KEY (`color_id`) REFERENCES `colors` (`color_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mascaras`
--

LOCK TABLES `mascaras` WRITE;
/*!40000 ALTER TABLE `mascaras` DISABLE KEYS */;
INSERT INTO `mascaras` VALUES (3,2,'tradicional','lifting',60),(7,3,'tradicional','volume',80),(11,3,'silicone','lifting',23),(14,2,'tradicional','volume',32);
/*!40000 ALTER TABLE `mascaras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `type` enum('lipstick','concealer','mascara') DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `price` decimal(5,2) unsigned DEFAULT NULL,
  `quantity` int(10) unsigned DEFAULT NULL,
  `brand_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  KEY `brand_id` (`brand_id`),
  FULLTEXT KEY `productNameIndex` (`name`),
  CONSTRAINT `products_ibfk_1` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`brand_id`),
  CONSTRAINT `price_constraint` CHECK ((`price` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'lipstick','pomadka 1',10.32,11,1),(2,'lipstick','pomadka 2',14.99,2252,1),(3,'mascara','maskara 1',24.99,31848,1),(4,'lipstick','pomadka 3',23.23,12,1),(5,'lipstick','pomadka 4',44.44,15,1),(6,'concealer','korektor 1',15.15,16,1),(7,'mascara','maskara 2',42.42,0,1),(8,'lipstick','pomadka testowa',22.22,0,1),(9,'lipstick','pomadka z gui',12.34,0,1),(10,'concealer','korektor gui',33.33,0,1),(11,'mascara','maskara gui',55.22,0,1),(12,'lipstick','admin lipstick',111.00,0,1),(13,'lipstick','pomadka test',12.10,0,2),(14,'mascara','maskara test',32.00,0,2),(15,'concealer','flawless concealer',213.00,0,1),(16,'lipstick','123',123.00,0,1);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales`
--

DROP TABLE IF EXISTS `sales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sales` (
  `sale_id` int(11) NOT NULL AUTO_INCREMENT,
  `sale_date` date DEFAULT NULL,
  `client_id` int(11) DEFAULT NULL,
  `done` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`sale_id`),
  KEY `client_id` (`client_id`),
  KEY `salesDateIndex` (`sale_date`) USING BTREE,
  KEY `salesDoneIndex` (`done`) USING BTREE,
  CONSTRAINT `sales_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `clients` (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales`
--

LOCK TABLES `sales` WRITE;
/*!40000 ALTER TABLE `sales` DISABLE KEYS */;
INSERT INTO `sales` VALUES (2,'2020-02-11',NULL,1),(11,'2020-01-17',1,1),(12,'2020-01-17',1,1),(13,'2020-01-16',1,1),(18,'2020-01-16',1,0),(19,'2020-01-16',2,1),(21,'2020-01-15',1,0),(23,'2020-01-24',2,1);
/*!40000 ALTER TABLE `sales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales_details`
--

DROP TABLE IF EXISTS `sales_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sales_details` (
  `sale_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `quantity` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`sale_id`,`product_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `sales_details_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  CONSTRAINT `sales_details_ibfk_2` FOREIGN KEY (`sale_id`) REFERENCES `sales` (`sale_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales_details`
--

LOCK TABLES `sales_details` WRITE;
/*!40000 ALTER TABLE `sales_details` DISABLE KEYS */;
INSERT INTO `sales_details` VALUES (2,1,5),(2,2,10),(2,3,15),(11,3,100),(19,1,5),(19,3,1400),(21,1,3);
/*!40000 ALTER TABLE `sales_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(30) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `type` enum('admin','boss','employee') DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `uniqueLogin` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (2,'admin','749f09bade8aca755660eeb17792da880218d4fbdc4e25fbec279d7fe9f65d70','admin'),(3,'boss','43aecb3506b67a52da88b24dc914eaf476777ab86d043e0d868706e4bbdc2327','boss'),(4,'employee','c30361f5f2e187f26b62e293d84a337db5fd862d575e01e16db0790f4ec037c1','employee'),(5,'qwe','489cd5dbc708c7e541de4d7cd91ce6d0f1613573b7fc5b40d3942ccb9555cf35','admin'),(9,'s','043a718774c572bd8a25adbeb1bfcd5c0256ae11cecf9f9c3f925d0e52beaf89','admin'),(10,'d','18ac3e7343f016890c510e93f935261169d9e3f565436429830faf0934f4f8e4','admin'),(11,'f','252f10c83610ebca1a059c0bae8255eba2f95be4d1d7bcfa89d7248a82d9f111','boss'),(12,'g','cd0aa9856147b6c5b4ff2b7dfee5da20aa38253099ef1b4a64aced233c9afe29','employee'),(14,'admin1','25f43b1486ad95a1398e3eeb3d83bc4010015fcc9bedb35b432e00298d5021f7','admin'),(15,'admin2','1c142b2d01aa34e9a36bde480645a57fd69e14155dacfab5a3f9257b77fdc8d8','admin'),(16,'admin3','4fc2b5673a201ad9b1fc03dcb346e1baad44351daa0503d5534b4dfdcc4332e0','admin'),(17,'user','04f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb','employee'),(18,'admin4','110198831a426807bccd9dbdf54b6dcb5298bc5d31ac49069e0ba3d210d970ae','admin'),(19,'user3','5860faf02b6bc6222ba5aca523560f0e364ccd8b67bee486fe8bf7c01d492ccb','employee'),(20,'user4','5269ef980de47819ba3d14340f4665262c41e933dc92c1a27dd5d01b047ac80e','employee'),(21,'user5','5a39bead318f306939acb1d016647be2e38c6501c58367fdb3e9f52542aa2442','employee');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-16 16:19:33
