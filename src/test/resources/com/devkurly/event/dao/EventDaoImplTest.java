package com.devkurly.event.dao;

import com.devkurly.event.domain.EventDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class EventDaoImplTest {
    @Autowired
    private EventDao eventDao;

    //   C
    @Test
    public void insertTest() throws Exception {
        eventDao.deleteAll();
        EventDto testDto = new EventDto("test nm", "test desc", "product-image.kurly.com/cdn-cgi/image/format=auto/banner/event/8622ba29-6cbf-438e-8865-880838ec3d7a.jpg", "test alt", "test mft", "A001", new Date(), new Date(), 0, 10);
        assertTrue(eventDao.insert(testDto) == 1);
        eventDao.deleteAll();
    }

    //    R
    @Test
    public void countTest() throws Exception {
        assertTrue(eventDao.count() == 1);
    }

    //    U

    //    D
    @Test
    public void deleteAllTest() throws Exception {
        eventDao.deleteAll();
        assertTrue(eventDao.count() == 0);
    }
}