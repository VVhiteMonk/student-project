package vitalijus.dao;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import vitalijus.domain.*;
import vitalijus.exception.DaoException;

import java.time.LocalDate;
import java.util.List;

public class StudentDaoImplTest {

    @BeforeClass
    public static void startUp() throws Exception{
        DBInit.startUp();
    }


    @Test
    public void saveStudentOrder() throws DaoException {
        StudentOrder studentOrder = buildStudentOrder(10);
        Long id =  new StudentDaoImpl().saveStudentOrder(studentOrder);
    }

    //TODO - doesn't work. Surname is null.
    @Test(expected = DaoException.class)
    //@Ignore
    public void saveStudentOrderError() throws DaoException{
            StudentOrder studentOrder = buildStudentOrder(10);
            studentOrder.getHusband().setSurname(null);
            Long id = new StudentDaoImpl().saveStudentOrder(studentOrder);
    }


    @Test
    public void getStudentOrder() throws DaoException {
        List<StudentOrder> list =  new StudentDaoImpl().getStudentOrder();
    }

    public static StudentOrder buildStudentOrder(long id){
        StudentOrder studentOrder = new StudentOrder();
        studentOrder.setStudentOrderId(id);
        studentOrder.setMarriageCertificatedId("" +(123456000 + id));
        studentOrder.setMarriageDate(LocalDate.of(2016, 7, 4));
        RegisterOffice registerOffice = new RegisterOffice(1L, "","");
        studentOrder.setMarriageOffice(registerOffice);

        Street street = new Street(1L, "S.Žukausko");

        Address address = new Address("15232", street, "34","51");

        //Husband
        Adult husband = new Adult("Vitalijus", "Rusakevič", LocalDate.of(1989, 2, 25));
        husband.setPassportSeries("" + (1000 + id));
        husband.setPassportNumber("" + (10000 + id));
        husband.setIssueDate(LocalDate.of(2001,1,1));
        PassportOffice passportOffice = new PassportOffice(1L, "", "");
        husband.setIssueDepartment(passportOffice);
        husband.setStudentId("" + (100000 + id));
        husband.setAddress(address);
        husband.setUniversity(new University(2L, ""));
        husband.setStudentId("HH_12345");

        //Wife
        Adult wife = new Adult("Veronika", "Rusakevic", LocalDate.of(1989, 5, 4));
        wife.setPassportSeries("" + (2000 + id));
        wife.setPassportNumber("" + (20000 + id));
        wife.setIssueDate(LocalDate.of(2002,2,2));
        PassportOffice passportOffice2 = new PassportOffice(2L, "", "");
        wife.setIssueDepartment(passportOffice2);
        wife.setStudentId("" + (200000 + id));
        wife.setAddress(address);
        wife.setUniversity(new University(2L, ""));
        wife.setStudentId("WW_12345");

        //Child
        Child child = new Child("Leonidas", "Rusakevic", LocalDate.of(2023,11,29));
        child.setCertificateNumber("" + (3000 + id));
        child.setIssueDate(LocalDate.of(2023, 12, 3));
        RegisterOffice registerOffice2 = new RegisterOffice(2L, "", "");
        child.setIssuesDepartment(registerOffice2);
        child.setAddress(address);

        //Child
        Child child2 = new Child("Vytautas", "Ruskkevic", LocalDate.of(2023,11,29));
        child2.setCertificateNumber("" + (4000 + id));
        child2.setIssueDate(LocalDate.of(2023, 12, 3));
        RegisterOffice registerOffice3 = new RegisterOffice(3L, "", "");
        child2.setIssuesDepartment(registerOffice3);
        child2.setAddress(address);

        studentOrder.setHusband(husband);
        studentOrder.setWife(wife);
        studentOrder.addChild(child);
        studentOrder.addChild(child2);

        return studentOrder;
    }

}