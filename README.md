# SearchEngine221

local mysql config
## Set Password
update user set authentication_string=PASSWORD('root') where User='root';
flush privileges;

## create database
create database miya;
### create tables

CREATE TABLE `Document` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `url` int(11) NOT NULL DEFAULT '0' COMMENT 'url',
  `text` bigint(20) DEFAULT '0' COMMENT 'extracted html content',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'created time',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updated time',
  PRIMARY KEY (`id`),
  KEY `ix_created_at` (`created_at`),
  KEY `ix_updated_at` (`updated_at`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='Document table';