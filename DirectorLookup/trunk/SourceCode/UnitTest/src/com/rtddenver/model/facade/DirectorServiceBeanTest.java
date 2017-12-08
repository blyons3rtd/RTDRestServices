package com.rtddenver.model.facade;

import com.rtddenver.model.dto.DirectorDTO;

import com.rtddenver.service.facade.DirectorServiceBean;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class DirectorServiceBeanTest {
    public DirectorServiceBeanTest() {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * @see DirectorServiceBean#getDirectorByDistrict(String)
     */
    @Test
    public void testGetDirectorByDistrict() {
        //fail("Unimplemented");
        DirectorServiceBean dsb = new DirectorServiceBean();
        DirectorDTO dto = dsb.getDirectorByDistrict("M");
        assertNotNull(dto);
        assertTrue(dto.getDirector().contains("Menton"));
        System.out.println("District:" + dto.getDistrict() + ", Director:" + dto.getDirector());
        System.out.println("Error Code:" + dto.getError().getCode() + ", Error Message:" + dto.getError().getMessage()
                           + ", " + dto.getError().getDetail() + ", " + dto.getError().getStatus() + ", " + dto.getError().getTime());
        assertTrue("".equals(dto.getError().getMessage()));     // Not expecting an error 
        System.out.println("Success");
    }

}
