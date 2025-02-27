-- 题目提交表
create table if not exists question_submit
(
    id         bigint auto_increment comment 'id' primary key,
    language   varchar(128)                       not null comment '编程语言',
    code       text                               not null comment '用户代码',
    judgeInfo  text                               null comment '判题信息（json 对象）',
    status     int      default 0                 not null comment '判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）',
    questionId bigint                             not null comment '题目 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除',
    index idx_questionId (questionId),
    index idx_userId (userId)
) comment '题目提交';

/*Table structure for table `judge` */

DROP TABLE IF EXISTS `judge`;

CREATE TABLE `judge`
(
    `submit_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `pid` bigint(20) unsigned NOT NULL COMMENT '题目id',
    `uid` varchar(32) NOT NULL COMMENT '用户id',
    `username` varchar(255) DEFAULT NULL COMMENT '用户名',
    `submit_time` datetime NOT NULL COMMENT '提交的时间',
    `status` int DEFAULT NULL COMMENT '结果码具体参考文档',
    `share` tinyint(1) DEFAULT '0' COMMENT '0为仅自己可见，1为全部人可见。',
    `error_message` mediumtext COMMENT '错误提醒（编译错误，或者vj提醒）',
    `time` int(11) DEFAULT NULL COMMENT '运行时间(ms)',
    `memory` int(11) DEFAULT NULL COMMENT '运行内存（kb）',
    `score` int(11) DEFAULT NULL COMMENT 'IO判题则不为空',
    `length` int(11) DEFAULT NULL COMMENT '代码长度',
    `code` longtext NOT NULL COMMENT '代码',
    `language` varchar(255) NOT NULL COMMENT '代码语言',
#     `gid` bigint(20) unsigned DEFAULT NULL COMMENT '团队id，不为团队内提交则为null',
    `cid` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '比赛id，非比赛题目默认为0',
    `cpid` bigint(20) unsigned DEFAULT '0' COMMENT '比赛中题目排序id，非比赛题目默认为0',
#     `judger` varchar(20) DEFAULT NULL COMMENT '判题机ip',
#     `ip` varchar(20) DEFAULT NULL COMMENT '提交者所在ip',
#     `version` int(11) NOT NULL DEFAULT '0' COMMENT '乐观锁',
#     `oi_rank_score` int(11) NULL DEFAULT '0' COMMENT 'oi排行榜得分',
    `vjudge_submit_id` bigint(20) unsigned NULL  COMMENT 'vjudge判题在其它oj的提交id',
    `vjudge_username` varchar(255) NULL  COMMENT 'vjudge判题在其它oj的提交用户名',
    `vjudge_password` varchar(255) NULL  COMMENT 'vjudge判题在其它oj的提交账号密码',
    `is_delete` tinyint(1) DEFAULT '0' COMMENT '是否删除',
    `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP,
    `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`submit_id`,`pid`,`uid`,`cid`),
    KEY `pid` (`pid`),
    KEY `uid` (`uid`),
    KEY `username` (`username`),
    CONSTRAINT `judge_ibfk_1` FOREIGN KEY (`pid`) REFERENCES `problem` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `judge_ibfk_2` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `judge_ibfk_3` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;