package vitalijus;

import vitalijus.dao.DictionaryDaoImpl;
import vitalijus.dao.StudentDaoImpl;
import vitalijus.dao.StudentOrderDao;
import vitalijus.domain.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

public class SaveStudentOrder {

    static long saveStudentOrder(StudentOrder studentOrder){
        long answer = 505;
        System.out.println("Save student order");

        return answer;
    }

}
