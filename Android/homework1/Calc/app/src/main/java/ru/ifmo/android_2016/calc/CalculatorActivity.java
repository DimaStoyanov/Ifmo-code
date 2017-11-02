package ru.ifmo.android_2016.calc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.calculator.R;

import java.util.HashMap;

import ru.ifmo.android_2016.calc.parser.ExpressionParser;
import ru.ifmo.android_2016.calc.parser.Parser;

public class CalculatorActivity extends AppCompatActivity {
    private TextView result, instantResult;                              // preresult of expression after every click
    private Toast toast;                                                 // show error messages
    private StringBuilder expression = new StringBuilder();              // store expression
    private Parser parser = new ExpressionParser();                      // parse expression and evaluate result
    private boolean lastEqv = false;                                     // clear result, if after clicking on eqv was clicked some number
    private final int MAX_DIGITS = 20;                                   // Max symbols of expression
    private final int TOAST_DURATION = Toast.LENGTH_LONG;                // duration of error messages
    private HashMap<Integer, String> operations = new HashMap<>();       // store id and string of operations

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        if (savedInstanceState != null) {
            expression.append(savedInstanceState.getString(getString(R.string.keyExpr)));
            lastEqv = savedInstanceState.getBoolean(getString(R.string.keyLastEqv));
            result.setText(expression);
            instantResult.setText(savedInstanceState.getString(getString(R.string.keyInstRes)));
            Log.d(getString(R.string.keyTag), getString(R.string.dLoad));
        }

    }


    private void init() {
        result = (TextView) findViewById(R.id.result);
        instantResult = (TextView) findViewById(R.id.instantResult);
        toast = Toast.makeText(this, R.string.maxDigitsToast, Toast.LENGTH_SHORT);
        operations.put(R.id.add,getString(R.string.opAdd));
        operations.put(R.id.sub,getString(R.string.opSub));
        operations.put(R.id.mul,getString(R.string.opMulReal));
        operations.put(R.id.div,getString(R.string.opDivReal));
        Button clear = (Button) findViewById(R.id.clear);
        clear.setOnLongClickListener(onClearClick);
    }


    public void onEqvClick(View v) {
        try {
            expression = new StringBuilder(parser.parse(expression.toString()));
        } catch (NumberFormatException | ArithmeticException e) {
            showMessage(e.getMessage());
            return;
        }
        result.setText(getInt(expression.toString()));
        lastEqv = true;
        printInstantResult();
    }

    public void onBackspaceClick(View v) {
        if (expression.length() == 0) return;
        lastEqv = false;
        expression.deleteCharAt(expression.length() - 1);
        result.setText(expression.toString());
        printInstantResult();
    }

    private View.OnLongClickListener onClearClick = new View.OnLongClickListener(){
        @Override
        public boolean onLongClick(View v){
            clear();
            return true;
        }
    };

    private void clear(){
        lastEqv = false;
        expression = new StringBuilder();
        result.setText(expression.toString());
        printInstantResult();
    }


    public void onDigitClick(View v) {
        if (checkMaxSymbols(v)) return;
        Button digit = (Button) findViewById(v.getId());
        if (lastEqv) {
            clear();
        }
        expression.append(digit.getText());
        result.setText(expression.toString());
        printInstantResult();
    }

    public void onOperationClick(View v) {
        if (checkMaxSymbols(v)) return;
        Button currentOperation = (Button) findViewById(v.getId());
        lastEqv = false;
        // Expression can have negate at the start, but other operations is forbidden
        if(expression.toString().equals("-")){
            if(currentOperation.getText().equals(getString(R.string.opAdd))){
                clear();
            }
            return;
        }
        if (expression.length() == 0) {
            if (currentOperation.getText().equals(getString(R.string.opSub))) {
                expression.append(getString(R.string.opSub));
                result.setText(expression.toString());
                printInstantResult();
                return;
            }
            return;
        }
        char lastChar = expression.charAt(expression.length() - 1);

        // This shit code replaces operation if it also had the operation
        // but acceptably *- and /-
        if (operations.containsValue(String.valueOf(lastChar)) && expression.length() > 1
                && operations.containsValue(String.valueOf(expression.charAt(expression.length() - 2)))
                && !currentOperation.getText().equals("-")) {
            expression.replace(expression.length() - 2, expression.length(), operations.get(currentOperation.getId()));
        } else if (operations.containsValue(String.valueOf(lastChar))
                && (!currentOperation.getText().equals("-") || lastChar == '+' || lastChar == '-')) {
            expression.replace(expression.length() - 1, expression.length(), operations.get(currentOperation.getId()));
        } else {
            expression.append(operations.get(currentOperation.getId()));
        }
        result.setText(expression.toString());
        printInstantResult();
    }

    // Return true if we don't need to append last symbol
    private boolean checkMaxSymbols(View v) {
        if (expression.length() >= MAX_DIGITS) {
            if (operations.containsKey(v.getId()) && operations.containsValue(String.valueOf(expression.charAt(expression.length() - 1)))) {
                onOperationClick(v);
            }
            showMessage(getString(R.string.maxDigitsToast));
            return true;
        }
        return false;
    }

    // Calculate result of expression after every click
    private void printInstantResult() {
        try {
            instantResult.setText(expression.toString().equals(getString(R.string.opSub)) ? getString(R.string.opSub) : getInt(parser.parse(expression.toString())));
        } catch (NumberFormatException nException) {
            instantResult.setText("");
        } catch (ArithmeticException aException) {
            instantResult.setText(aException.getMessage());
        }
    }


    // If the result is integer, get string in integer format
    private String getInt(String expr) {
        if (expr.equals("")) return expr;
        double dExpr = Double.valueOf(expr);
        int iExpr = (int) dExpr;
        return iExpr == dExpr ? String.valueOf(iExpr) : String.valueOf(dExpr);
    }


    private void showMessage(String message){
        toast.cancel();
        toast = Toast.makeText(this, message, TOAST_DURATION);
        toast.show();
    }




    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(getString(R.string.keyExpr), expression.toString());
        outState.putBoolean(getString(R.string.keyLastEqv), lastEqv);
        outState.putString(getString(R.string.keyInstRes), String.valueOf(instantResult.getText()));
        Log.d(getString(R.string.keyTag), getString(R.string.dSave));
    }


}
