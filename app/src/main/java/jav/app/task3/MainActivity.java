package jav.app.task3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView resultText;
    EditText inputText;
    String input = "";

    int i = -1;
    int stackValueTop = -1;
    int stackOperatorTop = -1;
    int stackPostfixTop = -1;
    int capacity = 100;
    boolean flag = false; //when it will false it mean add operand first and then you can add operator
    private String[] stackArray;
    private String[] stackValue;
    private String[] stackOperator;
    private String[] stackPostfix;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        variableInitialization();
    }

    public void variableInitialization(){
        stackArray = new String[capacity];
        stackValue = new String[capacity];
        stackOperator = new String[capacity];
        stackPostfix = new String[capacity];

        inputText = findViewById(R.id.input_textView);
        resultText = findViewById(R.id.result_textView);
        inputText.setShowSoftInputOnFocus(false);
    }


    public void button7(View view) {
        uniqueMethod("7");
    }

    public void button8(View view) {
        uniqueMethod("8");
    }

    public void button9(View view) {
        uniqueMethod("9");
    }

    public void button4(View view) {
        uniqueMethod("4");
    }

    public void button5(View view) {
        uniqueMethod("5");
    }

    public void button6(View view) {
        uniqueMethod("6");
    }

    public void button1(View view) {
        uniqueMethod("1");
    }

    public void button2(View view) {
        uniqueMethod("2");
    }

    public void button3(View view) {
        uniqueMethod("3");
    }

    public void zero(View view) {
        uniqueMethod("0");
    }

    public void point(View view) {
        uniqueMethod(".");
    }
    public void addition(View view) {
        if (flag){
            ++i;
            stackArray[i] = "+";
            input = input+"+";
            inputText.setText(input);
            flag = false;
        }
    }

    public void subtraction(View view) {
        if (flag){
            ++i;
            stackArray[i] = "-";
            input = input+"-";
            inputText.setText(input);
            flag = false;
        }
    }
    public void multiply(View view) {
        if (flag){
            ++i;
            stackArray[i] = "x";
            input = input+"x";
            inputText.setText(input);
            flag = false;
        }
    }
    public void division(View view) {
        if (flag){
            ++i;
            stackArray[i] = "/";
            input = input+"/";
            inputText.setText(input);
            flag = false;
        }
    }

    public void uniqueMethod(String s){
        push(s);
        input = input+s;
        inputText.setText(input);
        flag = true;
        calculation();
    }

    public void calculation(){
        stackValueTop = -1;
        String string = "";
        for (int j=0; j<=i; j++){
            if(j==i){
                string = string+stackArray[j];
                pushStackValues(string);
                break;
            }
            if (stackArray[j].equals("x") || stackArray[j].equals("/") ||
                    stackArray[j].equals("+")|| stackArray[j].equals("-")){
                pushStackValues(string); // pushing value to stack
                pushStackValues(""+stackArray[j]); //pushing operator to stack
                string = "";
            }else {
                string = string+stackArray[j];
            }
        }
        calculation2();
    }

    public void calculation2(){
        stackOperatorTop = -1;
        stackPostfixTop = -1;

        for (int j=0; j<=stackValueTop; j++) {
            Log.d("stack value"+j, "" + stackValue[j]);
            if (stackValue[j].equals("x") || stackValue[j].equals("/") ||
                    stackValue[j].equals("+")|| stackValue[j].equals("-")) {
//                pushing in operator stack
                pushStackOperator(stackValue[j]);

            }
            else {
                pushStackPostfix(stackValue[j]);
                // push in postfix Stack
            }
        }
        remainingOperator();
        operatorStack();
        postfix();
    }

    public void remainingOperator(){
        if (!isEmptyStackOperator()){
            for (int j=0; j<=stackOperatorTop; j++){
                pushStackPostfix(popStackOperator());
            }
        }
    }
    public void operatorStack(){

        for (int j=0; j<=stackOperatorTop; j++) {
            Log.d("stack Operator" + j, "" + stackOperator[j]);
        }
    }
    public void postfix(){

        for (int j=0; j<=stackPostfixTop; j++) {
            Log.d("stack Postfix" + j, "" + stackPostfix[j]);
        }
    }


    public double addition(double first,double second){
        double result = first + second;
        return result;
    }
    public double subtraction(double first,double second){
        double result = first - second;
        return result;
    }
    public double multiplication(double first,double second){
        double result = first * second;
        return result;
    }
    public double division(double first,double second){
        double result = first / second;
        return result;
    }


    public void push(String x) {
        if (isFull())
        {
            Toast.makeText(this, "You cannot insert more value", Toast.LENGTH_SHORT).show();

        }else {
            stackArray[++i] = x;
        }
    }

    public String pop(){
        if (isEmpty())
        {
            Log.d("empty","stack is empty");
        }
        return stackArray[--i];
    }

    public String peek() {
        if (!isEmpty()) {
            return stackArray[i];
        }
        return null;
    }

    public int size() {
        return i + 1;
    }

    public Boolean isEmpty() {
        return i == -1;               // or return size() == 0;
    }

    public Boolean isFull() {
        return i == capacity - 1;     // or return size() == capacity;
    }


    // stackValue method's

    public void pushStackValues(String x) {
        if (isFullStackValues())
        {
            Toast.makeText(this, "You cannot insert more value", Toast.LENGTH_SHORT).show();

        }else {
            stackValue[++stackValueTop] = x;
        }
    }

    public String popStackValues(){
        if (isEmptyStackValues())
        {
            Log.d("empty","stack is empty");
        }
        return stackValue[--stackValueTop];
    }

    public String peekStackValues() {
        if (!isEmpty()) {
            return stackValue[stackValueTop];
        }
        return null;
    }

    public int sizeStackValues() {
        return stackValueTop + 1;
    }

    public Boolean isEmptyStackValues()
    {
        return stackValueTop == -1;               // or return size() == 0;
    }

    public Boolean isFullStackValues() {
        return stackValueTop == capacity - 1;     // or return size() == capacity;
    }

//    stackOperator methods

    public void pushStackOperator(String x) {

        if (isFullStackOperator())
        {
            Toast.makeText(this, "You cannot insert more value", Toast.LENGTH_SHORT).show();
        }else {

            if(!isEmptyStackOperator()){
                String currentOperator = x;
                String peekOperator = peekStackOperator();
                if (currentOperator.equals("x") || currentOperator.equals("/")){
                    if(peekOperator.equals("+") || peekOperator.equals("-")){
                        stackOperator[++stackOperatorTop] = currentOperator;
                    }else if(peekOperator.equals("x") || peekOperator.equals("/")){
                        pushStackPostfix(peekOperator);
                        popStackOperator();
                        pushStackOperator(currentOperator);
                    }
                }
                if (currentOperator.equals("+") || currentOperator.equals("-")){
                    if (peekOperator.equals("+") || peekOperator.equals("-")){
                        pushStackPostfix(peekOperator);
                        popStackOperator();
                        pushStackOperator(currentOperator);
                    }else if(peekOperator.equals("x") || peekOperator.equals("/")){
                        pushStackPostfix(peekOperator);
                        popStackOperator();
                        pushStackOperator(currentOperator);
                    }
                }
            }else{
                stackOperator[++stackOperatorTop] = x;
            }
        }
    }

    public String popStackOperator(){
        if (isEmptyStackValues())
        {
            Log.d("empty","stack is empty");
        }
        String s = stackOperator[stackOperatorTop];
        stackOperatorTop--;
        return s;
    }

    public String peekStackOperator() {
        if (!isEmpty()) {
            return stackOperator[stackOperatorTop];
        }
        return null;
    }

    public int sizeStackOperator() {
        return stackOperatorTop + 1;
    }

    public Boolean isEmptyStackOperator() {
        return stackOperatorTop == -1;               // or return size() == 0;
    }

    public Boolean isFullStackOperator() {
        return stackOperatorTop == capacity - 1;     // or return size() == capacity;
    }

    // stackPostfix methods

    public void pushStackPostfix(String x) {
        if (isFullStackPostfix())
        {
            Toast.makeText(this, "You cannot insert more value", Toast.LENGTH_SHORT).show();

        }else {

            try {
                stackPostfix[++stackPostfixTop] = x;

                if (x.equals("+")||x.equals("-")||x.equals("x")||x.equals("/")){

                    popStackPostfix();
                    double second = Double.parseDouble(popStackPostfix());
                    double first = Double.parseDouble(popStackPostfix());
                    switch (x){

                        case "+":
                            pushStackPostfix(""+addition(first,second));
                            resultText.setText(stackPostfix[stackPostfixTop]);
                            break;
                        case "-":
                            pushStackPostfix(""+subtraction(first,second));
                            resultText.setText(stackPostfix[stackPostfixTop]);
                            break;
                        case "x":
                            pushStackPostfix(""+multiplication(first,second));
                            resultText.setText(stackPostfix[stackPostfixTop]);
                            break;
                        case "/":
                            pushStackPostfix(""+division(first,second));
                            resultText.setText(stackPostfix[stackPostfixTop]);
                            break;
                        default:
                            break;
                    }
                    Log.d("hellsss",""+first+""+x+""+second);
                }

            }catch (Exception c){
                inputText.setText("Undefined Expression");
            }
        }
    }

    public String popStackPostfix(){
        if (isEmptyStackValues())
        {
            Log.d("empty","stack is empty");
        }
        String s = stackPostfix[stackPostfixTop];
        stackPostfixTop--;
        return s;
    }


    public Boolean isFullStackPostfix() {
        return stackPostfixTop == capacity - 1;     // or return size() == capacity;
    }

    public void equal(View view) {
        try {
            remainingOperator();
            resultText.setText(""+popStackPostfix());
        }catch (Exception e){
            inputText.setText("Undefined Expression");
            resultText.setText("");
        }

    }

    public void backbButton(View view) {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}