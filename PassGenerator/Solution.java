package com.javarush.task.task32.task3204;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

/* 
Генератор паролей
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        ByteArrayOutputStream password = getPassword();
        System.out.println(password.toString());
    }

    public static ByteArrayOutputStream getPassword() throws IOException {
        int[] status = generateStatus(); //массив статусов для каждого символа пароля
        Random random = new Random();
        char[] password = new char[8];
        for (int i = 0; i < password.length; i++){
            switch (status[i]) {
                case 1: password[i] = (char) (48 + random.nextInt(9+1)); break;
                case 2: password[i] = (char) (97 + random.nextInt(25+1)); break;
                case 3: password[i] = (char) (65 + random.nextInt(25+1)); break;
            }
        }
        String pass = new String(password);
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        boas.write(pass.getBytes());
        return boas;
    }

    public static int[] generateStatus(){
        Random ran= new Random();
        int[] status = new int[8];
        int countNum = 0;
        int countLower = 0;
        int countUpper = 0;
        do {
            countNum = 0;
            countLower = 0;
            countUpper = 0;
            for (int i = 0; i < status.length; i++) {
                status[i] = ran.nextInt(3) + 1;
                if (status[i] == 1) countNum++;
                if (status[i] == 2) countLower++;
                if (status[i] == 3) countUpper++;
            }
        } while (countNum == 0 || countLower == 0 || countUpper == 0);
        return status;
    }
}