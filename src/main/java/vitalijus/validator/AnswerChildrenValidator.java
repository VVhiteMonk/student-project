package vitalijus.validator;

import vitalijus.domain.StudentOrder;
import vitalijus.domain.children.AnswerChildren;

public class AnswerChildrenValidator {

    private String hostName;
    private String login;

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

    private String password;
    public AnswerChildren checkChildren(StudentOrder studentOrder){
        System.out.println("Children check is running "  + hostName +"," + login+ "," + password);
        AnswerChildren answerChildren = new AnswerChildren();
        answerChildren.success = false;
        return answerChildren;
    }
}
