Maintaining the BOARD_DIRECTOR Table

To identify the current Board of Directors:

http://www.rtd-denver.com/BoardDirectors.shtml


To update the BOARD_DIRECTOR Table:

1. In-Activate the Old Director Entry

update board_director
set ACTIVE = 'N', updated_date = SYSDATE, updated_by = '<your RTD emp no>'
where DISTRICT = 'N' 
and ACTIVE = 'Y'
-- If further granularity is required also base on current director name
and director_first_name = '<Previous director's first name>'


2. Add the New Director Record

insert into BOARD_DIRECTOR values ('<District Code>', '<First Name>', '<Last name>', 'Y', SYSDATE, '<your RTD emp no>', SYSDATE, '<your RTD emp no>')
