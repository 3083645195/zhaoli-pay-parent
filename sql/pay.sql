CREATE TABLE `pay_consumer_log` (
                                    `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                    `message_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                    `relation_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                    `remarks` varchar(255) DEFAULT NULL,
                                    `create_date` datetime DEFAULT NULL,
                                    `update_date` datetime DEFAULT NULL,
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3 COMMENT='消费者记录消费日志表';

CREATE TABLE `pay_merchant_info` (
                                     `id` int NOT NULL AUTO_INCREMENT,
                                     `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商户id',
                                     `enterprise_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '企业名称',
                                     `social_credit_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '社会信用代码',
                                     `business_license_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '营业执照图片',
                                     `juridical_person` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '法人',
                                     `juridical_person_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '法人身份证信息',
                                     `contacts` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系人s',
                                     `contact_number` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系人手机号码',
                                     `contact_wx_openid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系人微信openid',
                                     `audit_status` int DEFAULT NULL COMMENT '审核状态 0未审核1审核通过2审核拒绝',
                                     `create_date` datetime DEFAULT NULL COMMENT '创建时间',
                                     `update_date` datetime DEFAULT NULL,
                                     PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3 COMMENT='商户信息表';

CREATE TABLE `merchant_secret_key` (
                                       `id` int NOT NULL AUTO_INCREMENT,
                                       `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '商户id',
                                       `app_id` varchar(255) DEFAULT NULL COMMENT '账号',
                                       `app_key` varchar(255) DEFAULT NULL COMMENT '密钥',
                                       `disabled` int DEFAULT NULL COMMENT '0正常 ，1停止',
                                       `create_date` datetime DEFAULT NULL COMMENT '创建时间',
                                       `update_date` datetime DEFAULT NULL COMMENT '修改时间',
                                       PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='API接口验证签名表';

CREATE TABLE `merchant_secret_key` (
                                       `id` int NOT NULL AUTO_INCREMENT,
                                       `merchant_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
                                       `app_id` varchar(255) DEFAULT NULL COMMENT '账号',
                                       `app_key` varchar(255) DEFAULT NULL COMMENT '密钥',
                                       `disabled` int DEFAULT NULL COMMENT '0正常 ，1停止',
                                       `create_date` datetime DEFAULT NULL COMMENT '创建时间',
                                       `update_date` datetime DEFAULT NULL COMMENT '修改时间',
                                       `salt_key` varchar(255) DEFAULT NULL COMMENT '盐值',
                                       `permission_list` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '允许访问路径',
                                       PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='API接口验证签名表更改后';


CREATE TABLE `payment_channel` (
                                   `ID` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                   `CHANNEL_NAME` varchar(32) NOT NULL COMMENT '渠道名称',
                                   `CHANNEL_ID` varchar(32) NOT NULL COMMENT '渠道ID',
                                   `SYNC_URL` text NOT NULL COMMENT '同步回调URL',
                                   `ASYN_URL` text NOT NULL COMMENT '异步回调URL',
                                   `PUBLIC_KEY` text COMMENT '公钥',
                                   `PRIVATE_KEY` text COMMENT '私钥',
                                   `REVISION` int DEFAULT NULL COMMENT '乐观锁',
                                   `CREATED_BY` varchar(32) DEFAULT NULL COMMENT '创建人',
                                   `CREATED_TIME` datetime DEFAULT NULL COMMENT '创建时间',
                                   `UPDATED_BY` varchar(32) DEFAULT NULL COMMENT '更新人',
                                   `UPDATED_TIME` datetime DEFAULT NULL COMMENT '更新时间',
                                   `pay_bean_id` varchar(255) DEFAULT NULL,
                                   `syn_callback_bean_id` varchar(255) DEFAULT NULL,
                                   `is_delete` int DEFAULT NULL,
                                   PRIMARY KEY (`ID`,`CHANNEL_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COMMENT='支付渠道表';


