package com.vctapps.starwarscharacters.persistence.dao;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.vctapps.starwarscharacters.persistence.files.ManagerJsonFiles;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Class ManagerJsonFilesTest created on 23/01/2017.
 */
public class ManagerJsonFilesTest {

    private Context context;
    private String simpleJson = "{nome:teste}";
    private String jsonName = "Film@Test";

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getTargetContext();
    }

    @After
    public void clearAll() throws Exception {
        ManagerJsonFiles.delete(context, jsonName);
    }

    @Test
    public void save() throws Exception {
        boolean response = ManagerJsonFiles.save(context, simpleJson, jsonName);

        assertTrue(response);
    }

    @Test
    public void get() throws Exception {
        save();

        String response = ManagerJsonFiles.get(context, jsonName);

        assertNotNull(response);
        assertTrue(response.equals(simpleJson));
    }

    @Test
    public void delete() throws Exception {
        save();

        boolean response = ManagerJsonFiles.delete(context, jsonName);

        assertTrue(response);
    }

}