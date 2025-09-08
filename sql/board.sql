--
-- board
--

desc board;

ALTER TABLE board CHANGE order_no o_no INT(11) NOT NULL;

ALTER TABLE board DROP COLUMN o_no;

ALTER TABLE board 
ADD COLUMN o_no INT(11) NOT NULL DEFAULT 0 AFTER g_no;