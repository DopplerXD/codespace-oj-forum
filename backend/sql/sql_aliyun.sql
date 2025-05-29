# aliyun 47.93.189.76

CREATE DATABASE IF NOT EXISTS `code_space`
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- user 用户表

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user`
(
    `id`          int(11) UNSIGNED       NOT NULL AUTO_INCREMENT COMMENT '数据库自增ID',
    `account`     varchar(30)            NOT NULL COMMENT '账户，手机号（唯一）',
    `password`    varchar(100)           NOT NULL COMMENT '密码',
    `username`    varchar(30) DEFAULT NULL COMMENT '昵称',
    `school`      varchar(255) DEFAULT NULL COMMENT '学校',
    `gender`      tinyint(1)   DEFAULT 2 NOT NULL COMMENT '性别，0为女，1为男，2为保密',
    `email`       varchar(320) DEFAULT NULL COMMENT '邮箱',
    `avatar`      varchar(255) DEFAULT NULL COMMENT '头像地址',
    `signature`   varchar(512) COMMENT '个性签名',
    `role`        tinyint(1)   DEFAULT 1 COMMENT '用户角色 0 ban, 1 user, 2 vip, 3 admin',
    `weixin_id`   varchar(50)  DEFAULT NULL COMMENT '微信号',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `WEIXIN_UNIQUE` (`weixin_id`),
    UNIQUE KEY `ACCOUNT_UNIQUE` (`account`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- problem 题目表

DROP TABLE IF EXISTS `problem`;

CREATE TABLE `problem`
(
    `id`                  bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `problem_id`          varchar(255)        NOT NULL COMMENT '问题的自定义ID 例如（P-1000）',
    `title`               varchar(255)        NOT NULL COMMENT '题目标题',
    `author`              varchar(100)                 DEFAULT '未知' COMMENT '作者 username',
    `type`                int(11)             NOT NULL DEFAULT '0' COMMENT '0为ACM, 1为OI ',
    `time_limit`          int(11)                      DEFAULT '1000' COMMENT '单位ms',
    `memory_limit`        int(11)                      DEFAULT '65535' COMMENT '单位kb',
    `stack_limit`         int(11)                      DEFAULT '128' COMMENT '单位mb',
    `description`         longtext COMMENT '描述',
    `input`               longtext COMMENT '输入描述',
    `output`              longtext COMMENT '输出描述',
    `examples`            longtext COMMENT '题面样例',
    `favour_num`          int(11)                      DEFAULT '0' COMMENT '收藏数',
    `submit_num`          int(11)                      DEFAULT '0' COMMENT '提交数',
    `accepted_num`        int(11)                      DEFAULT '0' COMMENT '通过数',
    `tags`                varchar(1024)                DEFAULT NULL COMMENT '标签列表（json 数组）',
    `is_remote`           tinyint(1)                   DEFAULT '0' COMMENT '是否为vj判题',
    `source`              text COMMENT '题目来源',
    `difficulty`          int(11)                      DEFAULT '0' COMMENT '题目难度, 0简单，1中等，2困难',
    `hint`                longtext COMMENT '备注, 提醒 ',
    `auth`                int(11)                      DEFAULT '1' COMMENT '默认为1公开，2为私有，3为比赛题目',
    `io_score`            int(11)                      DEFAULT '100' COMMENT '当该题目为io题目时的分数',
    `code_share`          tinyint(1)                   DEFAULT '1' COMMENT '该题目对应的相关提交代码，用户是否可用分享',
    `judge_mode`          varchar(255)                 DEFAULT 'default' COMMENT '题目评测模式, default、spj、interactive',
    `judge_case_mode`     varchar(255)                 DEFAULT 'default' COMMENT '题目样例评测模式, default, subtask_lowest, subtask_average',
    `user_extra_file`     mediumtext                   DEFAULT NULL COMMENT '题目评测时用户程序的额外文件 json key:name value:content',
    `judge_extra_file`    mediumtext                   DEFAULT NULL COMMENT '题目评测时交互或特殊程序的额外文件 json key:name value:content',
    `spj_code`            longtext COMMENT '特判程序或交互程序代码',
    `spj_language`        varchar(255)                 DEFAULT NULL COMMENT '特判程序或交互程序代码的语言',
    `is_remove_end_blank` tinyint(1)                   DEFAULT '1' COMMENT '是否默认去除用户代码的文末空格',
    `open_case_result`    tinyint(1)                   DEFAULT '1' COMMENT '是否默认开启该题目的测试样例结果查看',
    `is_upload_case`      tinyint(1)                   DEFAULT '1' COMMENT '题目测试数据是否是上传文件的',
    `modified_user`       varchar(100)                 DEFAULT NULL COMMENT '最新修改题目的username',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `problem_id` (`problem_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1000
  DEFAULT CHARSET = utf8;