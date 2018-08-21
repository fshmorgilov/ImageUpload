USE db;
CREATE TABLE IF NOT EXISTS `images` (
	`id` INT(4) NOT NULL PRIMARY KEY,
	`image_blob` BLOB NULL,
	`file_name` VARCHAR(40) NULL,
	`description` VARCHAR(40) NULL
);