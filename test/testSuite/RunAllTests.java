/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testSuite;

import facade.FacadePersonTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author bo
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({

    FacadePersonTest.class,
    RestAssured.RestTest.class

})
public class RunAllTests {}
