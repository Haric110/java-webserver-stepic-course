CREATE OR REPLACE PROCEDURE parallel_tests.update_range_status()
LANGUAGE PLPGSQL AS $$
DECLARE
	v_range RECORD;
BEGIN
	LOCK TABLE parallel_tests.ranges IN ROW SHARE MODE;
	
	UPDATE parallel_tests.ranges 
	SET call_flag = TRUE
	WHERE t_name = v_range.t_name AND row_from = v_range.row_start_from AND row_to = v_range.row_end_to;
END;
$$;