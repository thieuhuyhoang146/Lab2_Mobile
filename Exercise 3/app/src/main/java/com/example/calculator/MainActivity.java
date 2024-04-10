package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView resultTv, solutionTv;
    MaterialButton buttonC, buttonBrackOpen, buttonBrackClose;
    MaterialButton buttonDivide, buttonMultiply, buttonPlus, buttonMinus, buttonEquals;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonAC, buttonDot;

    SharedPreferences sharedPreferences;

//    SQLiteDatabase calculatorDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);

        assignId(buttonC, R.id.buttons_C);
        assignId(buttonAC, R.id.buttons_AllClear);
        assignId(buttonBrackOpen, R.id.buttons_open_bracket);
        assignId(buttonBrackClose, R.id.buttons_close_bracket);
        assignId(buttonDivide,R.id.buttons_divide);
        assignId(buttonMultiply,R.id.buttons_multiply);
        assignId(buttonMinus, R.id.buttons_minus);
        assignId(buttonPlus, R.id.buttons_plus);
        assignId(buttonDot, R.id.buttons_dot);
        assignId(buttonEquals, R.id.buttons_equal);
        assignId(button0, R.id.buttons_0);
        assignId(button1, R.id.buttons_1);
        assignId(button2, R.id.buttons_2);
        assignId(button3, R.id.buttons_3);
        assignId(button4, R.id.buttons_4);
        assignId(button5, R.id.buttons_5);
        assignId(button6, R.id.buttons_6);
        assignId(button7, R.id.buttons_7);
        assignId(button8, R.id.buttons_8);
        assignId(button9, R.id.buttons_9);

//        // Create database
//        calculatorDB = this.openOrCreateDatabase("Calculator", MODE_PRIVATE, null);
//
//        calculatorDB.execSQL("CREATE TABLE IF NOT EXISTS results(id INTEGER PRIMARY KEY, content VARCHAR)");
//
//        calculatorDB.execSQL("DELETE FROM results");

        // Shared preferences
        sharedPreferences = this.getSharedPreferences("com.example.calculator", android.content.Context.MODE_PRIVATE);

        // Update previous result
        String previous = sharedPreferences.getString("preResult", "");

        Log.i("previous result", previous);

        solutionTv.setText(previous);

    }

    void assignId(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {

        MaterialButton button =(MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = resultTv.getText().toString();

        if(buttonText.equals("AC")){
            resultTv.setText("0");
            return;
        }

        if(buttonText.equals("=")){
            String finalResult = getResult(dataToCalculate);
            resultTv.setText(finalResult);
            String preResult = dataToCalculate + "=" + finalResult;

//            String sql = "INSERT INTO results (content) VALUES (" + dataToCalculate + "=" + finalResult + ")";
//            SQLiteStatement statement = calculatorDB.compileStatement(sql);
//            statement.execute();
//
//            Cursor c = calculatorDB.rawQuery("SELECT * FROM results", null);
//            int contentIndex = c.getColumnIndex("content");
//
//            if (c.moveToFirst()){
//
//                do{
//                    Log.i("content", c.getString(contentIndex));
//                } while (c.moveToNext());
//            }

            // Store previous result to shared preferences
            sharedPreferences.edit().putString("preResult", preResult).apply();

            // Update previous result
            String previous = sharedPreferences.getString("preResult", "");

            Log.i("previous result", previous);

            solutionTv.setText(previous);

            return;
        }
        if(buttonText.equals("C")){
            dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length()-1);
        }else{
            dataToCalculate = dataToCalculate + buttonText;
        }
        resultTv.setText(dataToCalculate);


    }
    String getResult(String data){
        try{
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable,data,"Javascrip",1, null).toString();
            if(finalResult.endsWith(".0")){
                finalResult = finalResult.replace(".0","");
            }
            return finalResult;
        }catch (Exception e){
            return "ERR";
        }
    }
}