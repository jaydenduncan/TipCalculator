package com.example.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.tipcalculator.databinding.ActivityMainBinding;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        EditText editText1 = binding.totalBillEditText;
        EditText editText2 = binding.tipPercEditText;
        EditText editText3 = binding.numOfPeopleEditText;

        DecimalFormat df = new DecimalFormat("0.00");

        binding.calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{

                    if(!(editText1.getText().toString().equals("0")) && !(editText2.getText().toString().equals("0"))
                    && !(editText3.getText().toString().equals("0"))){
                        String totalBillText = editText1.getText().toString();

                        double totalBill = Double.valueOf(totalBillText);

                        String tipPercText = editText2.getText().toString();

                        int tipPerc = Integer.valueOf(tipPercText);

                        String numOfPeopleText = editText3.getText().toString();

                        int numOfPeople = Integer.valueOf(numOfPeopleText);

                        double result = calculateResult(totalBill, tipPerc, numOfPeople);
                        String finalResult = df.format(result);

                        binding.totalText.setText("Total Per Person: $" + finalResult);
                    }

                }
                catch(Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    private double calculateResult(double tb, int tp, int nop){
        BigDecimal totalBill = new BigDecimal(String.valueOf(tb));
        BigDecimal totalPerc = new BigDecimal(String.valueOf(tp));
        BigDecimal numOfPeople = new BigDecimal(String.valueOf(nop));

        // calculate subtotal paid per person
        BigDecimal totalBillPerPerson = totalBill.divide(numOfPeople, 2, RoundingMode.HALF_UP);

        // calculate tip percentage
        BigDecimal tipDecimal = totalPerc.divide(new BigDecimal("100.0"),  2, RoundingMode.HALF_UP);
        BigDecimal totalTip = tipDecimal.multiply(totalBill);
        totalTip.setScale(2, RoundingMode.HALF_UP);

        // divide tip by each person
        BigDecimal tipPerPerson = totalTip.divide(numOfPeople,  2, RoundingMode.HALF_UP);

        // add tip percentage to subtotal
        BigDecimal result = totalBillPerPerson.add(tipPerPerson);
        result.setScale(2, RoundingMode.HALF_UP);

        return result.doubleValue();
    }
}