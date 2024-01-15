package vitalijus.validator.register;

import vitalijus.domain.register.CityRegisterResponse;
import vitalijus.domain.Person;
import vitalijus.exception.CityRegisterException;
import vitalijus.exception.TransportException;

public interface CityRegisterChecker {
    CityRegisterResponse checkPerson(Person person)
            throws CityRegisterException, TransportException;
}
