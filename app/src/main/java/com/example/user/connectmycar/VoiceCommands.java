package com.example.user.connectmycar;

import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.StringTokenizer;



public class VoiceCommands  extends AppCompatActivity {
    private String command = null;
    private StringTokenizer tokenizer = null; //https://www.tutorialspoint.com/java/util/java_util_stringtokenizer.htm
    private Intent exec ;    //use for any actions
    private  Context context;
    VoiceCommands(Context context ,String Reply ) {
        setContext(context);
        tokenizer = new StringTokenizer(Reply);
        command = tokenizer.nextToken();
        Log.e("command ", command);
        String temp = tokenizer.nextToken(); //phone number or contact name
        Log.e("temp is ", temp);
        switch (command) { //add command list

            case "call": //syntax : call contact|number
                exec = new Intent(Intent.ACTION_CALL ,null,context,MainActivity.class);


                if (temp.charAt(0) == '0') { //for number
                    //phone numbers always starts with a zero
                    exec.setData(Uri.parse("tel:" + temp));
                } else { //for contact
                    exec.setData(Uri.parse("tel:" + "12354"));

                }
                if(CheckCallPermission ())
                    context.startActivity(exec);

                //startActivity(exec);
               //Intent(String action, Uri uri, Context packageContext, Class<?> cls)


                break;

        }



    }
    private boolean CheckCallPermission (){

        return (PackageManager.PERMISSION_GRANTED==context.checkCallingOrSelfPermission("android.permission.CALL_PHONE"));

    }
    public void setContext(Context context) {
        this.context = context.getApplicationContext();
    }

}
