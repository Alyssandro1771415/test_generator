-- MariaDB dump 10.19  Distrib 10.4.28-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: testquestiondb
-- ------------------------------------------------------
-- Server version	10.4.28-MariaDB

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
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `schoolSubject` varchar(50) NOT NULL,
  `content` varchar(100) NOT NULL,
  `question` varchar(500) NOT NULL,
  `difficult` int(11) DEFAULT NULL CHECK (`difficult` in (1,2,3)),
  `itemA` varchar(300) DEFAULT NULL,
  `itemB` varchar(300) DEFAULT NULL,
  `itemC` varchar(300) DEFAULT NULL,
  `itemD` varchar(300) DEFAULT NULL,
  `itemE` varchar(300) DEFAULT NULL,
  `itemF` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (44,'matemática','raízes','Qual a raiz quadrada de 3²:\n',2,'3','9','6','N.D.A','',''),(51,'Português','Tempos Verbais','Qual o tempo verbal da frase \'Eu vejo o futuro repetir o passado\'',3,'Passado','Presente','Futuro','Presente Preterito','Presente Perfeito','Presente Mais que Perfeito'),(52,'matemática','raízes','Qual a raiz de 12:',2,'3','4','2√3','N.D.A','',''),(53,'matemática','raízes','Dada a equação 2.2⁴',3,'É o mesmo que 2⁵','Tem como raíz quadrada 6','Tem como raíz quadrada 9','','',''),(54,'matemática','raízes','Dada a equação quadrática 2 X²−5X+3=02x2−5x+3=0, encontre as raízes usando a fórmula de Bhaskara e, em seguida, verifique se essas raízes satisfazem a condição∣X1−X2∣=1∣x1−x2∣=1. Explique o significado geométrico dessa condição no contexto das raízes da equação quadrática.',3,'','','','','',''),(55,'cálculo','integrais','Resolva as integrais abaixo:',2,'∫sen(2x) dx','∫ x / (x/1) dx','∫ 4x dx','','','');
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-15 15:24:58
