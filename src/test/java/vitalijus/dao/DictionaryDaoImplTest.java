package vitalijus.dao;

import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vitalijus.domain.CountryArea;
import vitalijus.domain.PassportOffice;
import vitalijus.domain.RegisterOffice;
import vitalijus.domain.Street;
import vitalijus.exception.DaoException;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class DictionaryDaoImplTest {

    private static final Logger logger = LoggerFactory.getLogger(DictionaryDaoImplTest.class);

    @BeforeClass
    public static void startup() throws Exception {
        DBInit.startUp();
    }

    @Test
    public void testStreet() throws DaoException {
        LocalDateTime localDateTime = LocalDateTime.now();
        //TODO doesn't work. No SLE4J providers were found.
        logger.info("{}: Test street.", localDateTime);
        List<Street> street = new DictionaryDaoImpl().findStreet("d");
        Assert.assertEquals(1, street.size());
    }

    @Test
    public void testPassportOffice() throws DaoException{
        List<PassportOffice> passportOffices = new DictionaryDaoImpl().findPassportOffice("010020000000");
        Assert.assertEquals(2, passportOffices.size());
    }

    @Test
    public void testRegisterOffice() throws DaoException{
        List<RegisterOffice> registerOffices = new DictionaryDaoImpl().findRegisterOffice("010010000000");
        Assert.assertEquals(2, registerOffices.size());
    }

    @Test
    public void testArea() throws DaoException{
        List<CountryArea> countryAreas = new DictionaryDaoImpl().findAreas("");
        Assert.assertEquals(2, countryAreas.size());
        List<CountryArea> countryAreas2 = new DictionaryDaoImpl().findAreas("020000000000");
        Assert.assertEquals(2, countryAreas2.size());
        List<CountryArea> countryAreas3 = new DictionaryDaoImpl().findAreas("020010000000");
        Assert.assertEquals(1, countryAreas3.size());
        List<CountryArea> countryAreas4 = new DictionaryDaoImpl().findAreas("020010010000");
        Assert.assertEquals(2, countryAreas4.size());
    }
}