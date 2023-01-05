SELECT r.t_name, row_from, row_to  FROM parallel_tests.ranges r 
WHERE
	NOT call_flag
	AND row_from = (
		SELECT min(row_from)
		FROM parallel_tests.ranges r1
		WHERE t_name = r.t_name
		GROUP BY t_name)
LIMIT 1;

UPDATE parallel_tests.ranges 
SET call_flag = TRUE
WHERE t_name = 'to_block_03' AND row_from = 1 AND row_to = 100;

UPDATE parallel_tests.ranges 
SET call_flag = FALSE 
WHERE call_flag = TRUE;