package vitalijus.dao;

import vitalijus.domain.CountryArea;
import vitalijus.domain.PassportOffice;
import vitalijus.domain.RegisterOffice;
import vitalijus.domain.Street;
import vitalijus.exception.DaoException;

import java.util.List;

public interface DictionaryDao {
    List<Street> findStreet(String pattern) throws DaoException;
    List<PassportOffice> findPassportOffice(String areaId) throws DaoException;
    List<RegisterOffice> findRegisterOffice(String areaId) throws DaoException;
    List<CountryArea> findAreas(String areaId) throws DaoException;
}
