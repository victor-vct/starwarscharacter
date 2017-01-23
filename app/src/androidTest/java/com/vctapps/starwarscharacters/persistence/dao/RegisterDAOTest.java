package com.vctapps.starwarscharacters.persistence.dao;

import android.support.test.InstrumentationRegistry;

import com.vctapps.starwarscharacters.model.Register;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Class RegisterDAOTest created on 23/01/2017.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegisterDAOTest {

    RegisterDAO dao;
    Register register;
    String userName = "Android Marshmallow";
    String url = "http://www.swapi.co/api/people/1/";
    double lat = 14.45646;
    double lng = -47.546465;
    String characterName = "R2-D2";

    int id_reg;

    @Before
    public void setUp() throws Exception {
        dao = new RegisterDAO(InstrumentationRegistry.getTargetContext());

        register = new Register();
        register.setUserName(userName);
        register.setLink(url);
        register.setLat(lat);
        register.setLng(lng);
        register.setCharacterName(characterName);
    }

    @Test
    public void save() throws Exception {
        Long resp = dao.save(register);

        assertTrue(resp > 0);

        register.setCod(resp.intValue());
    }

    @Test
    public void readById() throws Exception {
        save();

        Register register = dao.readById(this.register.getCod());

        assertNotNull(register);
        assertTrue(register.getUserName().equals(userName));
        assertTrue(register.getCharacterName().equals(characterName));
        assertTrue(register.getLink().equals(url));
        assertTrue(register.getLat() == lat);
        assertTrue(register.getLng() == lng);
    }

    @Test
    public void readAll() throws Exception {
        save();

        List<Register> list = dao.readAll();

        assertNotNull(list);
        assertTrue(list.size() > 0);
        assertNotNull(list.get(0));

        Register register = list.get(0);
        assertTrue(register.getUserName().equals(userName));
        assertTrue(register.getCharacterName().equals(characterName));
        assertTrue(register.getLink().equals(url));
        assertTrue(register.getLat() == lat);
        assertTrue(register.getLng() == lng);
    }

    @Test
    public void update() throws Exception {
        save();

        register.setUserName("C3-PO");

        int resp = dao.update(register);

        assertTrue(resp > 0);

        Register reg = dao.readById(this.register.getCod());

        assertNotNull(reg);
        assertTrue(reg.getUserName().equals("C3-PO"));
    }

    @Test
    public void delete() throws Exception {
        save();

        int resp = dao.delete(this.register.getCod());

        assertTrue(resp > 0);
    }

}