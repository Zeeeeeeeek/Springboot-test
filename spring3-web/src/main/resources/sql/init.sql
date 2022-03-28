CREATE DATABASE  IF NOT EXISTS `spring3` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `spring3`;
-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: spring3
-- ------------------------------------------------------
-- Server version	8.0.27

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
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_type` tinyint DEFAULT NULL COMMENT '角色类型',
  `role_name` varchar(32) DEFAULT NULL COMMENT '角色名称',
  `role_function` varchar(255) DEFAULT NULL COMMENT '角色功能',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COMMENT='角色信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,0,'超级管理员','1,2,3,4,5','2021-12-21 10:46:04','2022-02-11 11:45:22'),(2,1,'一般人员','3','2021-12-21 10:46:04','2022-02-11 11:45:22'),(3,2,'条保支撑人员','5','2022-02-11 11:45:22','2022-02-15 15:04:07');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `task_id` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务id',
  `cron` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'cron表达式',
  `class_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'job引用地址',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '描述',
  `status` tinyint NOT NULL COMMENT '定时任务状态 0 停用,1启用',
  `task_type` tinyint NOT NULL DEFAULT '1' COMMENT '任务执行类型:  0单次执行 1 循环执行',
  `schedule_time` datetime DEFAULT NULL COMMENT '单次任务执行时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='定时任务';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` VALUES (1,'TB00001','0 * * * * ?','com.zhejianglab.spring3web.job.MyJob','每一分钟触发一次',0,1,NULL,'2022-03-28 14:51:56','2022-03-28 16:00:50'),(2,'TB00002','*/10 * * * * ?','com.zhejianglab.spring3web.job.MyJobTest','每10s触发一次',0,1,NULL,'2022-03-28 14:51:56','2022-03-28 16:13:43'),(3,'TB00003',NULL,'com.zhejianglab.spring3web.job.MyJobOneTime','单次任务测试',0,0,'2022-03-28 15:47:00','2022-03-28 15:40:06','2022-03-28 16:03:26'),(4,'TB00003',NULL,'com.zhejianglab.spring3web.job.MyJobOneTime','单次任务测试',0,0,'2022-03-29 15:40:00','2022-03-28 16:03:06','2022-03-28 16:03:30');
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_name` varchar(32) DEFAULT NULL COMMENT '用户名',
  `real_name` varchar(32) DEFAULT NULL COMMENT '用户真实姓名',
  `role_type` tinyint DEFAULT NULL COMMENT '角色类型：0：超级管理员；1：一般人员',
  `status` tinyint DEFAULT '1' COMMENT '0:锁定；1:有效；2：失效',
  `password` varchar(255) DEFAULT NULL COMMENT '用户密码',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name_UNIQUE` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb3 COMMENT='用户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','超级管理员',0,0,'e10adc3949ba59abbe56e057f20f883e','2021-12-21 10:46:04','2022-03-25 14:15:54'),(2,'lly','李留洋',0,1,'e10adc3949ba59abbe56e057f20f883e','2022-01-14 11:42:30','2022-03-24 10:38:22'),(3,'lyf','lyf',0,1,'e10adc3949ba59abbe56e057f20f883e','2022-01-14 16:53:18','2022-03-24 10:38:22'),(4,'lyadmin','卢赟',0,1,'e10adc3949ba59abbe56e057f20f883e','2022-01-17 14:14:47','2022-03-24 10:38:22'),(5,'luyun','卢赟（一般）',1,2,'e10adc3949ba59abbe56e057f20f883e','2022-01-20 14:27:30','2022-03-24 10:38:22'),(6,'test','test',0,1,'e10adc3949ba59abbe56e057f20f883e','2022-01-20 14:27:30','2022-01-20 14:30:34'),(7,'cccccccccccccccccccc','测试1号',0,1,'e10adc3949ba59abbe56e057f20f883e','2022-01-21 09:43:54','2022-03-24 10:38:22'),(8,'ceshi2','测试2号',1,1,'e10adc3949ba59abbe56e057f20f883e','2022-01-21 09:45:43','2022-03-24 10:38:22'),(9,'ceshi3','测试3号',1,2,'e10adc3949ba59abbe56e057f20f883e','2022-01-21 09:46:42','2022-03-24 10:38:22'),(10,'ceshi4','测试4号',1,1,'e10adc3949ba59abbe56e057f20f883e','2022-01-21 09:47:07','2022-03-24 10:38:22'),(11,'ceshi5','测试5号',0,2,'e10adc3949ba59abbe56e057f20f883e','2022-01-21 09:47:42','2022-03-24 10:38:22'),(12,'spring3','测试',1,1,'e10adc3949ba59abbe56e057f20f883e',NULL,NULL),(36,'spring3-1','测试',1,1,'827ccb0eea8a706c4c34a16891f84e7b',NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-28 16:14:05
