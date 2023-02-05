package com.example.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.example.tipcalculator.databinding.ActivityMainBinding;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

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

        binding.calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{

                    if(!(editText1.getText().toString().equals("0")) && !(editText2.getText().toString().equals("0"))
                    && !(editText3.getText().toString().equals("0"))){
                        String totalBillText = editText1.getText().toString();
                        BigDecimal totalBill = new BigDecimal(totalBillText);

                        String tipPercText = editText2.getText().toString();
                        BigDecimal tipPerc = new BigDecimal(tipPercText);

                        String numOfPeopleText = editText3.getText().toString();
                        BigDecimal numOfPeople = new BigDecimal(numOfPeopleText);

                        BigDecimal result = calculateResult(totalBill, tipPerc, numOfPeople);

                        binding.totalText.setText("Total Per Person: $" + result.toString());
                    }

                }
                catch(Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    private BigDecimal calculateResult(BigDecimal tb, BigDecimal tp, BigDecimal nop){
        BigDecimal totalBill = new BigDecimal(String.valueOf(tb));
        BigDecimal divisor = new BigDecimal("100");
        MathContext mc = new MathContext(4, RoundingMode.HALF_UP);

        // calculate subtotal paid per person
        BigDecimal totalBillPerPerson = totalBill.divide(nop, 2, RoundingMode.HALF_UP); // divide total bill by the number of people

        // calculate tip percentage
        BigDecimal tipDecimal = tp.divide(divisor, 2, RoundingMode.HALF_UP);
        BigDecimal totalTip = tipDecimal.multiply(tb, mc);

        // divide tip by each person
        BigDecimal tipPerPerson = totalTip.divide(nop, 2, RoundingMode.HALF_UP);

        // add tip percentage to subtotal
        BigDecimal result = totalBillPerPerson.add(tipPerPerson, mc);

        return result;
    }
}