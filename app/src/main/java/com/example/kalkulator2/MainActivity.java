package com.example.kalkulator2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {


    // klasa do obsługi przycisków
    class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            TextView result = findViewById(R.id.result);
            CharSequence resultText = result.getText();
            Button buttonClicked = findViewById(view.getId());
            CharSequence buttonText = buttonClicked.getText();

            // usuwanie zera wiodacego
            if(result.getText().equals("0")){
                if(Character.isDigit(buttonText.charAt(0))){
                    result.setText(buttonText);
                }
                else{
                    result.append(buttonText);
                }
            }else{
                if(!Character.isDigit(buttonText.charAt(0)) &&
                        !Character.isDigit(resultText.charAt(resultText.length()-1))){
                    return;
                }

                result.append(buttonText);
            }


        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.b0).setOnClickListener(new ButtonListener());
        findViewById(R.id.b1).setOnClickListener(new ButtonListener());
        findViewById(R.id.b2).setOnClickListener(new ButtonListener());
        findViewById(R.id.b3).setOnClickListener(new ButtonListener());
        findViewById(R.id.b4).setOnClickListener(new ButtonListener());
        findViewById(R.id.b5).setOnClickListener(new ButtonListener());
        findViewById(R.id.b6).setOnClickListener(new ButtonListener());
        findViewById(R.id.b7).setOnClickListener(new ButtonListener());
        findViewById(R.id.b8).setOnClickListener(new ButtonListener());
        findViewById(R.id.b9).setOnClickListener(new ButtonListener());
        findViewById(R.id.bAdd).setOnClickListener(new ButtonListener());
        findViewById(R.id.bDivde).setOnClickListener(new ButtonListener());
        findViewById(R.id.bMultiply).setOnClickListener(new ButtonListener());
        findViewById(R.id.bsubstraction).setOnClickListener(new ButtonListener());
        findViewById(R.id.bPoint).setOnClickListener(new ButtonListener());

        Button bC = findViewById(R.id.bC);
        bC.setOnClickListener((view -> {
            TextView result = findViewById(R.id.result);
            result.setText("0");
        }));

        Button bCE = findViewById(R.id.bCE);
        bCE.setOnClickListener(view -> {
            TextView result = findViewById(R.id.result);
            CharSequence resultText = result.getText();
            String resultString = resultText.toString();
            if(resultString.length() <= 1){
                result.setText(0);
            }else{
                resultString = resultString.substring(0, resultString.length()-1);
                result.setText(resultString);
            }

        });

        Button bResult = findViewById(R.id.bResult);
        bResult.setOnClickListener(view -> {
            TextView result = findViewById(R.id.result);
            String resultText = result.getText().toString();

            // walidacja wyrażenia, czy kończy się cyfrą
            if(!Character.isDigit(resultText.charAt(resultText.length()-1))){
                return;
            }

            // podział na liczby i operatory
            String[] elements = resultText.split("[+\\-/*]");
            List<Double> numbers = new ArrayList<>();
            for(String s: elements){
                numbers.add(Double.parseDouble(s));
            }

            // szukam operatory
            Pattern pattern = Pattern.compile("[+\\-/*]");
            Matcher matcher = pattern.matcher(resultText);
            List<String> operators = new ArrayList<>();
            while (matcher.find()){
                operators.add(matcher.group());
            }

            double outcome = numbers.get(0);
            for(int i=0; i<operators.size(); i++) {
                switch (operators.get(i)) {
                    case "+":
                        outcome += numbers.get(i + 1);
                        break;
                    case "-":
                        outcome -= numbers.get(i + 1);
                        break;
                    case "*":
                        outcome *= numbers.get(i + 1);
                        break;
                    case "/":
                        outcome /= numbers.get(i + 1);
                        break;

                }
            }

            result.setText(Double.valueOf(outcome).toString());


        });

    }
}