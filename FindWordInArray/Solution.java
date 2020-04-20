package com.javarush.task.task20.task2027;



import java.util.ArrayList;
import java.util.List;

/* 
Кроссворд
*/
public class Solution {
    public static void main(String[] args) {
        int[][] crossword = new int[][]{
                {'f', 'd', 'e', 'r', 'l', 'k'},
                {'u', 's', 'a', 'm', 'e', 'o'},
                {'l', 'n', 'g', 'r', 'o', 'v'},
                {'m', 'l', 'p', 'r', 'r', 'h'},
                {'p', 'o', 'e', 'e', 'j', 'j'}
        };
        detectAllWords(crossword, "home", "same");
        System.out.println(detectAllWords(crossword, "home", "same","grj"));
        /*
Ожидаемый результат
home - (5, 3) - (2, 0)
same - (1, 1) - (4, 1)
         */
    }

    public static List<Word> detectAllWords(int[][] crossword, String... words) {
        List<Word> result = new ArrayList<>();

        for (String word:words){
            char a = word.charAt(0);
            for (int y = 0; y < crossword.length; y++) {
                for (int x = 0; x < crossword[0].length; x++) {
                    if (a == crossword[y][x]) {
                        if (horizontToTheLeft(crossword, word, y,x).equals(word)) {
                            Word res = new Word(word);
                            res.setStartPoint(x,y);
                            res.setEndPoint(x-(word.length()-1),y);
                            result.add(res);
                        }
                        if (horizontToTheRight(crossword, word, y,x).equals(word)) {
                            Word res = new Word(word);
                            res.setStartPoint(x,y);
                            res.setEndPoint(x+(word.length()-1),y);
                            result.add(res);
                        }
                        if (vetricalDown(crossword, word, y,x).equals(word)) {
                            Word res = new Word(word);
                            res.setStartPoint(x,y);
                            res.setEndPoint(x,y+(word.length()-1));
                            result.add(res);
                        }
                        if (vetricalUp(crossword, word, y,x).equals(word)) {
                            Word res = new Word(word);
                            res.setStartPoint(x,y);
                            res.setEndPoint(x,y-(word.length()-1));
                            result.add(res);
                        }
                        if (diagLeftUp(crossword, word, y,x).equals(word)) {
                            Word res = new Word(word);
                            res.setStartPoint(x,y);
                            res.setEndPoint(x-(word.length()-1),y-(word.length()-1));
                            result.add(res);
                        }
                        if (diagLeftDown(crossword, word, y,x).equals(word)) {
                            Word res = new Word(word);
                            res.setStartPoint(x,y);
                            res.setEndPoint(x-(word.length()-1),y+(word.length()-1));
                            result.add(res);
                        }
                        if (diagRightUp(crossword, word, y,x).equals(word)) {
                            Word res = new Word(word);
                            res.setStartPoint(x,y);
                            res.setEndPoint(x+(word.length()-1),y-(word.length()-1));
                            result.add(res);
                        }
                        if (diagRightDown(crossword, word, y,x).equals(word)) {
                            Word res = new Word(word);
                            res.setStartPoint(x,y);
                            res.setEndPoint(x+(word.length()-1),y+(word.length()-1));
                            result.add(res);
                        }
                    }
                }
            }
        }
        return result;
    }
    //проверяем налево по горизонтали
    public static String horizontToTheLeft(int[][] crossword, String word, int startY, int startX){
        StringBuilder sb=new StringBuilder();
        for (int i=0;i<word.length();i++){
            if((startX-i)>=0) {
                sb.append((char) crossword[startY][startX - i]);
            }
        }
        return sb.toString();
    }
    //проверяем направо по горизонтали
    public static String horizontToTheRight(int[][] crossword, String word, int startY, int startX) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            if ((startX + i) < crossword[0].length) {
                sb.append((char) crossword[startY][startX + i]);
            }
        }
        return sb.toString();
    }
    //проверяем вниз по вертикали
    public static String vetricalDown(int[][] crossword, String word, int startY, int startX) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            if ((startY + i) < crossword.length) {
                sb.append((char) crossword[startY+i][startX]);
            }
        }
        return sb.toString();
    }
    //проверяем вверх по вертикали
    public static String vetricalUp(int[][] crossword, String word, int startY, int startX) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            if ((startY - i) >=0) {
                sb.append((char) crossword[startY-i][startX]);
            }
        }
        return sb.toString();
    }
    //проверяем диагональ налево-вверх
    public static String diagLeftUp(int[][] crossword, String word, int startY, int startX) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            if ((startY - i) >=0 && (startX-i)>=0) {
                sb.append((char) crossword[startY-i][startX-i]);
            }
        }
        return sb.toString();
    }
    //проверяем диагональ налево-вниз
    public static String diagLeftDown(int[][] crossword, String word, int startY, int startX) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            if ((startY + i) < crossword.length && (startX-i)>=0) {
                sb.append((char) crossword[startY+i][startX-i]);
            }
        }
        return sb.toString();
    }
    //проверяем диагональ направо-вверх
    public static String diagRightUp(int[][] crossword, String word, int startY, int startX) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            if ((startY - i) >=0 && (startX + i) < crossword[0].length) {
                sb.append((char) crossword[startY-i][startX+i]);
            }
        }
        return sb.toString();
    }
    //проверяем диагональ направо-вниз
    public static String diagRightDown(int[][] crossword, String word, int startY, int startX) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            if ((startY + i) < crossword.length && (startX + i) < crossword[0].length) {
                sb.append((char) crossword[startY+i][startX+i]);
            }
        }
        return sb.toString();
    }

    public static class Word {
        private String text;
        private int startX;
        private int startY;
        private int endX;
        private int endY;

        public Word(String text) {
            this.text = text;
        }

        public void setStartPoint(int i, int j) {
            startX = i;
            startY = j;
        }

        public void setEndPoint(int i, int j) {
            endX = i;
            endY = j;
        }

        @Override
        public String toString() {
            return String.format("%s - (%d, %d) - (%d, %d)", text, startX, startY, endX, endY);
        }
    }
}
