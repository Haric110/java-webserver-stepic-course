WITH t AS (
	SELECT
		min(t1.row_from) AS min_row_from
	FROM (
		SELECT
			min(r1.row_from) AS row_from
		FROM
			parallel_tests.ranges r1
		WHERE NOT call_flag
		GROUP BY
			t_name
	) t1
) 
SELECT
	r.t_name,
	t.min_row_from,
	r.row_to
FROM parallel_tests.ranges r
INNER JOIN t ON r.row_from = t.min_row_from
WHERE NOT r.call_flag
LIMIT 1;

UPDATE parallel_tests.ranges 
SET call_flag = TRUE
WHERE t_name = 'to_block_03' AND row_from = 1 AND row_to = 100;

UPDATE parallel_tests.ranges 
SET call_flag = FALSE 
WHERE call_flag = TRUE;