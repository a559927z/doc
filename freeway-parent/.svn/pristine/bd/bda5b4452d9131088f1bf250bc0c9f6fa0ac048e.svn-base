/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.5.5-10.1.38-MariaDB : Database - freeway
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`freeway` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `freeway`;

/*Table structure for table `demo_emp_info` */

DROP TABLE IF EXISTS `demo_emp_info`;

CREATE TABLE `demo_emp_info` (
  `emp_id` varchar(32) NOT NULL COMMENT '员工Id',
  `emp_name` varchar(20) DEFAULT NULL COMMENT '员工名称',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '修改人',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `tenant_id` varchar(32) DEFAULT NULL COMMENT '租户Id',
  PRIMARY KEY (`emp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `demo_emp_info` */

insert  into `demo_emp_info`(`emp_id`,`emp_name`,`create_by`,`create_date`,`update_by`,`update_date`,`tenant_id`) values ('17BD00D6409A45C88750B5AFB20EC000','毛小平','352BBA46C61E4217A3D363C29531594B','2019-03-05 17:08:47','352BBA46C61E4217A3D363C29531594B','2019-03-05 17:08:47','F54096CC86A9494DA345B74DD25AE5B0');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
