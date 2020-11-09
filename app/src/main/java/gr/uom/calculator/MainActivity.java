package gr.uom.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText result;
    private EditText newNumber;
    private TextView operation;

    private Double operand1 = null;
    private Double operand2 = null;
    private String pendingOperation= "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.resultText);
        newNumber = findViewById(R.id.inputText);
        operation = findViewById(R.id.operationLabel);

        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        Button buttonDot = findViewById(R.id.buttonDot);

        View.OnClickListener listener1 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button buttonClicked = (Button)v;
                newNumber.append(buttonClicked.getText());
            }
        };

        button0.setOnClickListener(listener1);
        button1.setOnClickListener(listener1);
        button2.setOnClickListener(listener1);
        button3.setOnClickListener(listener1);
        button4.setOnClickListener(listener1);
        button5.setOnClickListener(listener1);
        button6.setOnClickListener(listener1);
        button7.setOnClickListener(listener1);
        button8.setOnClickListener(listener1);
        button9.setOnClickListener(listener1);
        buttonDot.setOnClickListener(listener1);


        Button buttonPlus = findViewById(R.id.buttonPlus);
        Button buttonMinus = findViewById(R.id.buttonMinus);
        Button buttonMultiply = findViewById(R.id.buttonMultiply);
        Button buttonDivision = findViewById(R.id.buttonDivision);
        Button buttonClear = findViewById(R.id.buttonClear);
        Button buttonEqual = findViewById(R.id.buttonEqual);

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newNumber.setText("");
                result.setText("");
                operand1 = null;
                operand2 = null;
                pendingOperation = "";
            }
        });

        View.OnClickListener operationListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button)v;
                String operationString = b.getText().toString();
                String newNumberString = newNumber.getText().toString();

                try{
                    Double newValueDouble = Double.valueOf(newNumberString);
                    performOperation(newValueDouble);
                }catch(NumberFormatException nfe){
                    newNumber.setText("");
                }

                pendingOperation = operationString;
                operation.setText(pendingOperation);

            }
        };

        buttonPlus.setOnClickListener(operationListener);
        buttonMinus.setOnClickListener(operationListener);
        buttonMultiply.setOnClickListener(operationListener);
        buttonDivision.setOnClickListener(operationListener);
        buttonEqual.setOnClickListener(operationListener);






    }

    private void performOperation(Double newValueDouble) {
        if(operand1 == null){
            operand1= newValueDouble;
        }else{
            operand2=newValueDouble;



            switch (pendingOperation){
                case "=":
                    operand1=operand2;
                    break;
                case "+":
                    operand1+=operand2;
                    break;
                case "-":
                    operand1-=operand2;
                    break;
                case "*":
                    operand1*=operand2;
                    break;
                case "/":
                    if(operand2!=0){
                        operand1/=operand2;
                    }else{
                        result.setText("ERROR");
                        return;
                    }
                    break;
            }
        }
        result.setText(operand1.toString());
        newNumber.setText("");

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("PENDING_OPERATION", pendingOperation);
        if(operand1!=null){
            outState.putDouble("OPERAND1_STATE",operand1);
        }

        super.onSaveInstanceState(outState);


    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        pendingOperation = savedInstanceState.getString("PENDING_OPERATION");
        operand1= savedInstanceState.getDouble("OPERAND1_STATE");
        operation.setText(pendingOperation);
    }
}