# SearchEngine221

local mysql config
## Set Password
update user set authentication_string=PASSWORD('root') where User='root';
flush privileges;

## create database
create database miya;
### create tables

~~~
CREATE TABLE `Document` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'primary key',
  `title` varchar(200) NOT NULL DEFAULT '' COMMENT 'extracted html content',
  `text` varchar(5000) NOT NULL DEFAULT '' COMMENT 'extracted html content',
  `url` varchar(50) NOT NULL DEFAULT '' COMMENT 'url',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'created time',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updated time',
  PRIMARY KEY (`id`),
  KEY `ix_created_at` (`created_at`),
  KEY `ix_updated_at` (`updated_at`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='Document table';
~~~