package com.example.mobil_projekat;

public class Quiz {
    private int num1;
    private int num2;
    private String op;
    private int answer;

    public Quiz(int num1, int num2, String op) {
        this.num1 = num1;
        this.num2 = num2;
        this.op = op;
        //ODMA IZRACUNAMO ODGOVOR
        switch (op){
            case "+":
                this.answer = num1 + num2;
                break;
            case "-":
                this.answer = num1 - num2;
                break;
            case "*":
                this.answer = num1 * num2;
                break;
            case "/":
                double temp = (double) num1 / (double) num2;
                this.answer = (int) temp;
                break;
            default:
                this.answer = 999;
        }
    }
    public String getQuiz(){
        return num1+" "+op+" "+num2+" = ";
    }
    public int getAnswer(){
        return answer;
    }
}
