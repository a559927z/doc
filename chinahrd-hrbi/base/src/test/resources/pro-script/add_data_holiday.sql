TRUNCATE holiday;
SET @dFrom = '2015-01-01', @dTo ='2015-01-31';
SET @days = DATEDIFF(@dTo, @dFrom)+0;
SET @rs = FLOOR(@days/7)*2 ;
INSERT INTO holiday VALUES(replace(uuid(),'-',''), 'b5c9410dc7e4422c8e0189c7f8056b5f', @rs, 3, 201501);

SET @dFrom = '2015-02-01', @dTo ='2015-02-28';
SET @days = DATEDIFF(@dTo, @dFrom)+0;
SET @rs = FLOOR(@days/7)*2 ;
INSERT INTO holiday VALUES(replace(uuid(),'-',''), 'b5c9410dc7e4422c8e0189c7f8056b5f', @rs, 5, 201502);

SET @dFrom = '2015-03-01', @dTo ='2015-03-30';
SET @days = DATEDIFF(@dTo, @dFrom)+0;
SET @rs = FLOOR(@days/7)*2 ;
INSERT INTO holiday VALUES(replace(uuid(),'-',''), 'b5c9410dc7e4422c8e0189c7f8056b5f', @rs, 2, 201503);

SET @dFrom = '2015-04-01', @dTo ='2015-04-31';
SET @days = DATEDIFF(@dTo, @dFrom)+0;
SET @rs = FLOOR(@days/7)*2 ;
INSERT INTO holiday VALUES(replace(uuid(),'-',''), 'b5c9410dc7e4422c8e0189c7f8056b5f', 2, 1, 201504);

SET @dFrom = '2015-05-01', @dTo ='2015-05-30';
SET @days = DATEDIFF(@dTo, @dFrom)+0;
SET @rs = FLOOR(@days/7)*2 ;
INSERT INTO holiday VALUES(replace(uuid(),'-',''), 'b5c9410dc7e4422c8e0189c7f8056b5f', @rs, 1, 201505);


SET @dFrom = '2015-06-01', @dTo ='2015-06-30';
SET @days = DATEDIFF(@dTo, @dFrom)+0;
SET @rs =  FLOOR(@days/7)*2 ;
INSERT INTO holiday VALUES(replace(uuid(),'-',''), 'b5c9410dc7e4422c8e0189c7f8056b5f', @rs, 1, 201506);


SET @dFrom = '2015-07-01', @dTo ='2015-07-31';
SET @days = DATEDIFF(@dTo, @dFrom)+0;
SET @rs = FLOOR(@days/7)*2 ;
INSERT INTO holiday VALUES(replace(uuid(),'-',''), 'b5c9410dc7e4422c8e0189c7f8056b5f', @rs, 0, 201507);


SET @dFrom = '2015-08-01', @dTo ='2015-08-30';
SET @days = DATEDIFF(@dTo, @dFrom)+0;
SET @rs = FLOOR(@days/7)*2 ;
INSERT INTO holiday VALUES(replace(uuid(),'-',''), 'b5c9410dc7e4422c8e0189c7f8056b5f', @rs, 0, 201508);


SET @dFrom = '2015-09-01', @dTo ='2015-09-30';
SET @days = DATEDIFF(@dTo, @dFrom)+0;
SET @rs = FLOOR(@days/7)*2 ;
INSERT INTO holiday VALUES(replace(uuid(),'-',''), 'b5c9410dc7e4422c8e0189c7f8056b5f', @rs, 3, 201509);


SET @dFrom = '2015-10-01', @dTo ='2015-10-31';
SET @days = DATEDIFF(@dTo, @dFrom)+0;
SET @rs = FLOOR(@days/7)*2 ;
INSERT INTO holiday VALUES(replace(uuid(),'-',''), 'b5c9410dc7e4422c8e0189c7f8056b5f', 6, 7, 201510);


SET @dFrom = '2015-11-01', @dTo ='2015-11-30';
SET @days = DATEDIFF(@dTo, @dFrom)+0;
SET @rs = FLOOR(@days/7)*2 ;
INSERT INTO holiday VALUES(replace(uuid(),'-',''), 'b5c9410dc7e4422c8e0189c7f8056b5f', 9, 0, 201511);


SET @dFrom = '2015-12-01', @dTo ='2015-12-31';
SET @days = DATEDIFF(@dTo, @dFrom)+0;
SET @rs = FLOOR(@days/7)*2 ;
INSERT INTO holiday VALUES(replace(uuid(),'-',''), 'b5c9410dc7e4422c8e0189c7f8056b5f', 8, 0, 201512);


