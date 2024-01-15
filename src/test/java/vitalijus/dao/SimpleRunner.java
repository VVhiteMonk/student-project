package vitalijus.dao;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class SimpleRunner {
    public static void main(String[] args) {
        SimpleRunner simpleRunner = new SimpleRunner();
        
        simpleRunner.runTest();
    }

    private void runTest() {
        try {
           Class cl =  Class.forName("vitalijus.dao.DictionaryDaoImplTest");

            Constructor constructor = cl.getConstructor();
            Object entity = constructor.newInstance();

            Method[] methods = cl.getMethods();
            for (Method m : methods){
              Test annotation = m.getAnnotation(Test.class);
              if (annotation != null){
                  m.invoke(entity);
              }
            }
        }catch (Exception ex){
            ex.printStackTrace(System.out);
        }
    }
}
