/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user`
(
    `id` varchar(32) NOT NULL,
    `username` varchar(100) NOT NULL COMMENT '用户名（唯一）',
    `password` varchar(100) NOT NULL COMMENT '密码',
    `nickname` varchar(100) DEFAULT NULL COMMENT '昵称',
    `school` varchar(100) DEFAULT NULL COMMENT '学校',
    `gender` varchar(20) DEFAULT 'secrecy' NOT NULL  COMMENT '性别',
    `github` varchar(255) DEFAULT NULL COMMENT 'github地址',
    `blog` varchar(255) DEFAULT NULL COMMENT '个人博客地址',
    `cf_username` varchar(255) DEFAULT NULL COMMENT 'cf的username',
    `email` varchar(320) DEFAULT NULL COMMENT '邮箱',
    `avatar` varchar(255) DEFAULT NULL COMMENT '头像地址',
    `signature` varchar(512) COMMENT '个性签名',
    `title_name` varchar(255) DEFAULT NULL COMMENT '头衔、称号',
    `title_color` varchar(255) DEFAULT NULL COMMENT '头衔、称号的颜色',
    `role` varchar(255) DEFAULT 'user' COMMENT '用户角色：user/admin/ban',
    `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0可用，1不可用',
    `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `USERNAME_UNIQUE` (`username`),
    UNIQUE KEY `EMAIL_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;