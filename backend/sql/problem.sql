-- 题目表
create table if not exists question
(
    id         int auto_increment comment 'id' primary key,
    title      varchar(512)                       null comment '标题',
    content    text                               null comment '内容',
    tags       varchar(1024)                      null comment '标签列表（json 数组）',
    answer     text                               null comment '题目答案',
    submitNum  int  default 0 not null comment '题目提交数',
    acceptedNum  int  default 0 not null comment '题目通过数',
    judgeCase text null comment '判题用例（json 数组）',
    judgeConfig text null comment '判题配置（json 对象）',
    thumbNum   int      default 0                 not null comment '点赞数',
    favourNum  int      default 0                 not null comment '收藏数',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除',
    index idx_userId (userId)
    ) AUTO_INCREMENT = 1000 comment '题目' collate = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `problem`;

CREATE TABLE `problem`
(
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `problem_id` varchar(255) NOT NULL COMMENT '问题的自定义ID 例如（HOJ-1000）',
    `title` varchar(255) NOT NULL COMMENT '题目',
    `author` varchar(100) DEFAULT '未知' COMMENT '作者 username',
    `type` int(11) NOT NULL DEFAULT '0' COMMENT '0为ACM,1为OI',
    `time_limit` int(11) DEFAULT '1000' COMMENT '单位ms',
    `memory_limit` int(11) DEFAULT '65535' COMMENT '单位kb',
    `stack_limit` int(11) DEFAULT '128' COMMENT '单位mb',
    `description` longtext COMMENT '描述',
    `input` longtext COMMENT '输入描述',
    `output` longtext COMMENT '输出描述',
    `examples` longtext COMMENT '题面样例',
    `favour_num` int(11) DEFAULT '0' COMMENT '收藏数',
    `submit_num` int(11) DEFAULT '0' COMMENT '提交数',
    `accepted_num` int(11) DEFAULT '0' COMMENT '通过数',
    `tags` varchar(1024) DEFAULT NULL COMMENT '标签列表（json 数组）',
    `is_remote` tinyint(1) DEFAULT '0' COMMENT '是否为vj判题',
    `source` text COMMENT '题目来源',
    `difficulty` int(11) DEFAULT '0' COMMENT '题目难度,0简单，1中等，2困难',
    `hint` longtext COMMENT '备注,提醒',
    `auth` int(11) DEFAULT '1' COMMENT '默认为1公开，2为私有，3为比赛题目',
    `io_score` int(11) DEFAULT '100' COMMENT '当该题目为io题目时的分数',
    `code_share` tinyint(1) DEFAULT '1' COMMENT '该题目对应的相关提交代码，用户是否可用分享',
    `judge_mode` varchar(255) DEFAULT 'default' COMMENT '题目评测模式,default、spj、interactive',
    `judge_case_mode` varchar(255) DEFAULT 'default' COMMENT '题目样例评测模式,default,subtask_lowest,subtask_average',
    `user_extra_file` mediumtext DEFAULT NULL COMMENT '题目评测时用户程序的额外文件 json key:name value:content',
    `judge_extra_file` mediumtext DEFAULT NULL COMMENT '题目评测时交互或特殊程序的额外文件 json key:name value:content',
    `spj_code` longtext COMMENT '特判程序或交互程序代码',
    `spj_language` varchar(255) DEFAULT NULL COMMENT '特判程序或交互程序代码的语言',
    `is_remove_end_blank` tinyint(1) DEFAULT '1' COMMENT '是否默认去除用户代码的文末空格',
    `open_case_result` tinyint(1) DEFAULT '1' COMMENT '是否默认开启该题目的测试样例结果查看',
    `is_upload_case` tinyint(1) DEFAULT '1' COMMENT '题目测试数据是否是上传文件的',
#     `case_version` varchar(40) DEFAULT '0' COMMENT '题目测试数据的版本号',
    `modified_user` varchar(100) DEFAULT NULL COMMENT '最新修改题目的username',
#     `is_group` tinyint(1) DEFAULT '0' COMMENT '是否为团队内部题目',
#     `gid` bigint(20) unsigned DEFAULT NULL COMMENT '团队id',
#     `apply_public_progress` int(11) DEFAULT NULL COMMENT '申请公开的进度：null为未申请，1为申请中，2为申请通过，3为申请拒绝',
#     `is_file_io` tinyint(1) DEFAULT '0' COMMENT '是否是file io自定义输入输出文件模式',
#     `io_read_file_name` varchar(255) DEFAULT NULL COMMENT '题目指定的file io输入文件的名称',
#     `io_write_file_name` varchar(255) DEFAULT NULL COMMENT '题目指定的file io输出文件的名称',
    `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP,
    `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `author` (`author`),
    KEY `problem_id` (`problem_id`),
    CONSTRAINT `problem_ibfk_1` FOREIGN KEY (`author`) REFERENCES `user` (`username`) ON DELETE NO ACTION ON UPDATE CASCADE,
    CONSTRAINT `problem_ibfk_2` FOREIGN KEY (`modified_user`) REFERENCES `user` (`username`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;

-- 添加外键约束
ALTER TABLE problem
    ADD CONSTRAINT problem_ibfk_2
        FOREIGN KEY (modified_user)
            REFERENCES user (username)
            ON DELETE NO ACTION
            ON UPDATE CASCADE;


ALTER TABLE problem
    ADD CONSTRAINT unique_problem_id UNIQUE (problem_id);