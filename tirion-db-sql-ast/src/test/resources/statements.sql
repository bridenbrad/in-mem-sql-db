SELECT trades.* FROM table;

SELECT table.* FROM table WHERE x BETWEEN 1 AND 111;

SELECT table.* FROM table WHERE x IN (1, 2, 3, 4, 5, 6);

SELECT table.* FROM table WHERE x IS NOT NULL AND zz IS NULL;

SELECT table.* FROM table ORDER BY x,t, zzz;

SELECT table.* FROM table ORDER BY x,t, zzz LIMIT 1000;

SELECT a, b, c, d FROM table ORDER BY x,t, zzz LIMIT 1000;

SELECT a, b, c, d, sum(x), avg(rr) FROM table GROUP BY a,b,c,d ORDER BY x,t, zzz LIMIT 1000;

SELECT a, b, c, d, sum(x) AS dd, avg(rr) FROM table GROUP BY a,b,c,d HAVING dd > 100 ORDER BY x,t, zzz LIMIT 1000;

SELECT a, b, c FROM dd INNER JOIN kk ON (jj < xx) WHERE x = 10 GROUP BY xx;

SELECT a, b, c FROM dd INNER JOIN kk ON (jj < xx) LEFT JOIN ttttt ON k22 != sss WHERE x = 10 GROUP BY xx;

SELECT a, b, c FROM dd INNER JOIN kk ON (jj < xx) LEFT JOIN ttttt ON k22 != sss WHERE x = 10 GROUP BY xx HAVING frrt != 193939 ORDER BY dd11 LIMIT 100;

SET "x.x.x.x.x" = "111";

SET "xxxxx" = "sdfsdfsdf";

GET "xxx.x.xxx";

SELECT table.* FROM trades WHERE ( (x != 21) OR (ssss >= cvxcvxcv) );

SELECT table.* FROM trades WHERE ( (x != 21 AND (tt <= kkk OR gfggg >= 23423)) OR (ssss >= cvxcvxcv) );

LOAD TABLE trades FROM FILE "/tmp/trades.csv";

LOAD TABLE trades FROM FILE "/tmp/trades.csv";

SELECT 
	a, b, c, count(*) AS cnt 
FROM 
	(
		SELECT b, v AS fff, c, sum(aa) AS dd 
		FROM xx 
		GROUP BY b, v, c
		ORDER BY FFF
	
	) AS t
	INNER JOIN
	(
		SELECT t.*
		FROM 
			(
				SELECT mm
				FROM t
				GROUP BY mm
				ORDER BY kk
			) AS kk
		WHERE x = "sdfsdfsdfdsf" OR ggg <= 234234.234234
		GROUP BY k
	) AS gg
	ON (gg.f != t.t)
WHERE
	t.t > 10 AND ff IN ("sdfsdf", "sdfsdfsd")
GROUP BY 
	sss, aaa, ss
HAVING 
	ddd != 11000;	

INSERT INTO TABLE ttt SELECT t.* FROM t;

INSERT INTO ttt SELECT a FROM fddd;

DELETE FROM XXX WHERE gff > 10;

DELETE FROM TABLE XXX WHERE gff > 10 AND (ttt != 1100 OR sss IS NOT NULL OR sss IS NULL);

LOAD TABLE trades FROM FILE "/tmp/trades.csv";











