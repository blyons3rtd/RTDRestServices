package com.rtddenver.model.facade;

import com.rtddenver.model.dto.DirectorDTO;

import com.rtddenver.model.dto.DistrictDTO;

import com.rtddenver.service.facade.GisDistrictServiceBean;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class GisDistrictServiceBeanTest {
    public GisDistrictServiceBeanTest() {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * @see GisDistrictServiceBean#getDistrictForAddress(String,String,String)
     */
    @Test
    public void testGetDistrictForAddress() {
        //fail("Unimplemented");
        GisDistrictServiceBean dsb = new GisDistrictServiceBean();
        DistrictDTO dto = dsb.getDistrictForAddress("1800 Lawrence St", "Denver", "80202");
        assertNotNull(dto);
        assertTrue(!"".equals(dto.getValue()));
        System.out.println("Value:" + dto.getValue() + ", Message:" + dto.getMessage() + ", Address:" + dto.getAddress());
        System.out.println("Success");
    }
}
