package vitalijus.validator;

import vitalijus.domain.Child;
import vitalijus.domain.Person;
import vitalijus.domain.register.AnswerCityRegisterItem;
import vitalijus.domain.register.CityRegisterResponse;
import vitalijus.domain.StudentOrder;
import vitalijus.domain.register.AnswerCityRegister;
import vitalijus.exception.CityRegisterException;
import vitalijus.exception.TransportException;
import vitalijus.validator.register.CityRegisterChecker;
import vitalijus.validator.register.FakeCityRegisterChecker;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.List;

public class CityRegisterValidator {
    public static String IN_CODE = "NO_GRN";

    private String hostName;
    private String login;
    private String password;
    private CityRegisterChecker personChecker;

    public CityRegisterValidator(){
        personChecker = new FakeCityRegisterChecker();
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AnswerCityRegister checkCityRegister(StudentOrder studentOrder){
        AnswerCityRegister answerCityRegister = new AnswerCityRegister();

        answerCityRegister.addItem(checkPerson(studentOrder.getHusband()));
        answerCityRegister.addItem(checkPerson(studentOrder.getWife()));

        for(Child child : studentOrder.getChildren()){
            answerCityRegister.addItem(checkPerson(child));
        }

        return answerCityRegister;
    }

    private AnswerCityRegisterItem checkPerson(Person person){
        AnswerCityRegisterItem.CityStatus status = null;
        AnswerCityRegisterItem.CityError error = null;
        try{
           CityRegisterResponse cityRegisterResponse = personChecker.checkPerson(person);
           status = cityRegisterResponse.isExisting() ? AnswerCityRegisterItem.CityStatus.YES :
                   AnswerCityRegisterItem.CityStatus.NO;
        }catch (CityRegisterException ex){
            ex.printStackTrace(System.out);
            status = AnswerCityRegisterItem.CityStatus.ERROR;
            error = new AnswerCityRegisterItem.CityError(ex.getCode(), ex.getMessage());
        }catch (TransportException ex){
            ex.printStackTrace(System.out);
            status = AnswerCityRegisterItem.CityStatus.ERROR;
            error = new AnswerCityRegisterItem.CityError(IN_CODE, ex.getMessage());
        }catch (Exception ex){
            ex.printStackTrace(System.out);
            status = AnswerCityRegisterItem.CityStatus.ERROR;
            error = new AnswerCityRegisterItem.CityError(IN_CODE, ex.getMessage());
        }

        AnswerCityRegisterItem answerCityRegisterItem = new AnswerCityRegisterItem(status, person, error);

        return answerCityRegisterItem;
    }
}
