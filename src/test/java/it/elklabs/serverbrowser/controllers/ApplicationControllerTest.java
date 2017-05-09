package it.elklabs.serverbrowser.controllers;

import com.j256.ormlite.support.ConnectionSource;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;

import java.io.ByteArrayOutputStream;

/**
 * Created by petermckinney on 08/05/2017.
 */
public class ApplicationControllerTest {

    @Injectable
    ConnectionSource connectionSource;

    @Tested
    ApplicationController applicationController;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    public void setUp()
    {
        applicationController = new ApplicationController(connectionSource);
    }

    public void  testHelpCall()
    {






    }



}
