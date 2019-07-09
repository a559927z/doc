CREATE TABLE "t_test" (
	"id" BIGINT identity(1,1) PRIMARY KEY ,
	"int_field" INT NULL ,
	"string_field" VARCHAR(50) NULL,
	"date_field" Date NULL,
	"bool_field" TINYINT NULL
);