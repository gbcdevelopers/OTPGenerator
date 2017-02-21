package gbc.sa.otpgenerator;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    EditText et_accesscode;
    EditText et_activitycode;
    TextView tv_digit1;
    TextView tv_digit2;
    TextView tv_digit3;
    TextView tv_digit4;
    TextView tv_digit5;
    TextView tv_digit6;
    Button btn_generate_otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_accesscode = (EditText)findViewById(R.id.accesscode);
        //et_activitycode = (EditText)findViewById(R.id.activitycode);
        tv_digit1 = (TextView)findViewById(R.id.digit1);
        tv_digit2 = (TextView)findViewById(R.id.digit2);
        tv_digit3 = (TextView)findViewById(R.id.digit3);
        tv_digit4 = (TextView)findViewById(R.id.digit4);
        tv_digit5 = (TextView)findViewById(R.id.digit5);
        tv_digit6 = (TextView)findViewById(R.id.digit6);
        btn_generate_otp = (Button)findViewById(R.id.btn_generate_otp);
        btn_generate_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    hideKeyboard();
                    String accessCode = et_accesscode.getText().toString();
                    if(accessCode.equals("")){
                        Toast.makeText(MainActivity.this,getString(R.string.accesscode_valid),Toast.LENGTH_LONG).show();
                    }
                    byte[] code = accessCode.getBytes();
                    final String genCode = OTPGenerator.generateOTP(code,1,6,false,1);
                    Log.e("Generated Code","" + genCode);
                    for(int i=0;i<genCode.length();i++){
                        if(i==0){
                            startCountAnimation(tv_digit1,Integer.parseInt(String.valueOf(genCode.charAt(i))));
                        }
                        else if(i==1){
                            startCountAnimation(tv_digit2,Integer.parseInt(String.valueOf(genCode.charAt(i))));
                        }
                        else if(i==2){
                            startCountAnimation(tv_digit3,Integer.parseInt(String.valueOf(genCode.charAt(i))));
                        }
                        else if(i==3){
                            startCountAnimation(tv_digit4,Integer.parseInt(String.valueOf(genCode.charAt(i))));
                        }
                        else if(i==4){
                            startCountAnimation(tv_digit5,Integer.parseInt(String.valueOf(genCode.charAt(i))));
                        }
                        else if(i==5){
                            startCountAnimation(tv_digit6,Integer.parseInt(String.valueOf(genCode.charAt(i))));
                        }
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    private void startCountAnimation(final TextView element,Integer value) {

        final Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/counterFont.ttf");
        ValueAnimator animator = new ValueAnimator();
        animator.setObjectValues(0, value);
        animator.setDuration(1500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                element.setText("" + (int) animation.getAnimatedValue());
                //element.setTypeface(custom_font);
            }
        });
        animator.start();
    }

    void hideKeyboard(){
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        View focusedView = this.getCurrentFocus();
        if (focusedView != null) {
            inputManager.hideSoftInputFromWindow(focusedView.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
