INSERT INTO jc_street (street_code, street_name) VALUES (1, 'Street First')
UPDATE jc_street SET street_name = 'Super'
DELETE FROM jc_street WHERE street_code = 1
SELECT * FROM jc_street

select street_code, street_name from jc_street where UPPER(street_name) like UPPER)'%fir%'


-- select city and regions
select * from jc_country_struct where area_id like '__0000000000' and area_id <>

select * from jc_country_struct where area_id like '02___0000000' and area_id <> '020000000000'

select * from jc_country_struct where area_id like '02001___0000' and area_id <> '020010000000'

select * from jc_country_struct where area_id like '02001001____' and area_id <> '020010010000'

-- select and order by
SELECT * FROM jc_student_order WHERE student_order_status = 0 ORDER BY student_order_date

