package com.javarush.task.task37.task3702.male;

import com.javarush.task.task37.task3702.AbstractFactory;
import com.javarush.task.task37.task3702.Human;

public class MaleFactory implements AbstractFactory {
    public <T extends Human> T getPerson(int age){
        if (age>19){
            return (T) new Man();
        } else if (age>12 && age<20){
            return (T) new TeenBoy();
        } else
        return (T) new KidBoy();
    }
}
