package com.javarush.task.task37.task3702.female;

import com.javarush.task.task37.task3702.AbstractFactory;
import com.javarush.task.task37.task3702.Human;

public class FemaleFactory implements AbstractFactory {
    public <T extends Human> T getPerson(int age){
        if (age>19){
            return (T) new Woman();
        } else if (age>12 && age<20){
            return (T) new TeenGirl();
        } else
            return (T) new KidGirl();
    }
}
