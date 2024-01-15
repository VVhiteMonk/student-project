package vitalijus.validator.register;

import vitalijus.domain.Adult;
import vitalijus.domain.Child;
import vitalijus.domain.register.CityRegisterResponse;
import vitalijus.domain.Person;
import vitalijus.exception.CityRegisterException;
import vitalijus.exception.TransportException;

public class FakeCityRegisterChecker implements CityRegisterChecker {

    private static final String GOOD_1 = "1000";
    private static final String GOOD_2 = "2000";
    private static final String BAD_1 = "1001";
    private static final String BAD_2 = "2001";
    private static final String ERROR_1 = "1002";
    private static final String ERROR_2 = "2002";
    private static final String ERROR_EXCEPTION = "Fake Error: ";
    private static final String ERROR_EXCEPTION_TRANSPORT = "Transport Error: ";
    private static final String ERROR_T_1 = "1003";
    private static final String ERROR_T_2 = "2003";
    public CityRegisterResponse checkPerson(Person person) throws CityRegisterException, TransportException{

        CityRegisterResponse cityRegisterCheckerResponse = new CityRegisterResponse();

            if (person instanceof Adult){
                Adult adult = (Adult) person;
                String passportSeries = adult.getPassportSeries();

                if (passportSeries.equals(GOOD_1) || passportSeries.equals(GOOD_2)){
                    cityRegisterCheckerResponse.setExisting(true);
                    cityRegisterCheckerResponse.setTemporal(false);
                }

                if (passportSeries.equals(BAD_1) || passportSeries.equals(BAD_2)){
                    cityRegisterCheckerResponse.setExisting(false);
                }

                if (passportSeries.equals(ERROR_1) || passportSeries.equals(ERROR_2)){
                    CityRegisterException cityRegisterException = new CityRegisterException("1",ERROR_EXCEPTION + passportSeries);
                    throw cityRegisterException;
                }
                if (passportSeries.equals(ERROR_T_1) || passportSeries.equals(ERROR_T_2)){
                    TransportException transportException = new TransportException(ERROR_EXCEPTION_TRANSPORT + passportSeries);
                    throw transportException;
                }
            }

            if (person instanceof Child){
                cityRegisterCheckerResponse.setExisting(true);
                cityRegisterCheckerResponse.setTemporal(true);
            }

        System.out.println(cityRegisterCheckerResponse);

        return cityRegisterCheckerResponse;
    }
}
