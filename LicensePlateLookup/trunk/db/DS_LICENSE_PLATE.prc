CREATE OR REPLACE PROCEDURE CHK_PLT.DS_LICENSE_PLATE
/******************************************************************************
   NAME:     DS_LICENSE_PLATE
   PURPOSE:  Upload data from LICENSE_PLATE_STAGING to LICENSE_PLATE_MASTER
   
   REVISIONS:
   Ver        Date        Author           Description
   1.0       20180420     Alan
*/
AS
   v_cnt INTEGER;
BEGIN
   SELECT COUNT(*) 
     INTO v_cnt
   FROM LICENSE_PLATE_STAGING;
   
   IF v_cnt > 0
   THEN
       execute immediate 'TRUNCATE TABLE CHK_PLT.LICENSE_PLATE_MASTER';
       
       INSERT INTO CHK_PLT.LICENSE_PLATE_MASTER (  
                        plate_number
                        ,plate_address
                        ,plate_city
                        ,plate_state
                        ,plate_zip
                        ,vehicle_type
                        ,vehicle_class
                        ,created_date
                        ,created_txn_id
                        ,geo_processed
                        ,in_district
                        ,geocoded
                        ,geo_modified_date
                        ,ds_modified_date
                        ,ds_modified_txn_id
                        )
       SELECT stg.plate_number
                        ,stg.plate_address
                        ,stg.plate_city
                        ,stg.plate_state
                        ,stg.plate_zip
                        ,stg.vehicle_type
                        ,stg.vehicle_class
                        ,stg.created_date
                        ,stg.created_txn_id
                        ,stg.geo_processed
                        ,stg.in_district
                        ,stg.geocoded
                        ,stg.geo_modified_date
                        ,stg.ds_modified_date
                        ,stg.ds_modified_txn_id
       FROM CHK_PLT.LICENSE_PLATE_STAGING stg;
     
       COMMIT;

       dbms_stats.gather_table_stats('CHK_PLT','LICENSE_PLATE_MASTER',cascade=>TRUE);
       
   end if;
          
EXCEPTION
   WHEN OTHERS
   THEN
      raise_application_error (-20010,'DS_LICENSE_PLATE: ' || SQLERRM,TRUE);
END;
-----------------------------------
/
