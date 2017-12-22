package com.example.user.connectmycar;


import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {


    private SpeechRecognizer speech = null;
    boolean MIC = true;
    private Button b;
    private BottomNavigationView Bnav;
    private MenuItem btn;
    private TextView out;//tester
    private  STT stt;
    private  TTS tts ;
    private int res; //used for tts
   // TextToSpeech TTS = null; //text to speech object https://developer.android.com/reference/android/speech/tts/TextToSpeech.html
    private String Reply = "Text to speech test";// text to be read out loud
    //private boolean CallPermission  =(PackageManager.PERMISSION_GRANTED==getApplicationContext().checkCallingOrSelfPermission("android.permission.CALL_PHONE"));
   // private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /***************************************************************/

        /*this button is used to call the text to speech function TTS(String outloud)*/

        b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.Read(Reply);
            }
        });

        /************************************************************/
        out = (TextView) findViewById(R.id.output); //out points to the textView

        //speech = SpeechRecognizer.createSpeechRecognizer(this);
        stt =new STT(this);
        //the following is a test code to recognize the input voice msg
        Bnav = (BottomNavigationView) findViewById(R.id.design_navigation_view); //upper navigation menu
        Bnav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            //listener for selection

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                btn = item;
                switch (item.getItemId()) {
                    case R.id.MicButton:

                       stt.startVoiceToTextService(); //call function for MicButton

                        Reply= stt.getReply();
                        out.setText(Reply);
                        //Reply now contains the value of what the user has said

                        break;
                    case R.id.RecordButton:
                        //call function for recod button

                        break;

                    case R.id.SettingsButton:

                        //call function for SettingsButton
                        break;


                }
                return true;
            }
        });

        // for testing the output text from voice


//        to initialize Text to speech use this constructor TextToSpeech(Context context, TextToSpeech.OnInitListener listener)
        tts= new TTS(this);


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
         tts.destroy();

       stt.destroy();
            Log.i("LOG_TAG", "destroy");

    }

    public void blue (View view){

       // LayoutInflater inflater = LayoutInflater.from(getBaseContext());
        //View theInflatedView = inflater.inflate(R.layout.activity_main2, null);
        Intent intent = new Intent(this , Main2Activity.class);
        startActivity(intent);

    }
/*
    private void startVoiceToTextService() {
/*for details on RECOGNIZERINTENT
https://developer.android.com/reference/android/speech/RecognizerIntent.html
 */
/*

       Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);  //opens prompt
        //Intent intent = new Intent(RecognizerIntent.ACTION_VOICE_SEARCH_HANDS_FREE); //opens S voice
        //You can set here own local Language.
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());  //locale.GERMAN for lang
        /*In order for it to work offline set the device as follows 
http://stackandroid.com/tutorial/how-to-enable-offline-speech-to-text-in-android/
*//*
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, Locale.getDefault());
        //  intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hiiii, Tell Me SomeThing");  //customize prompt


        try {

            //getting results from the speech activity https://developer.android.com/training/basics/intents/result.html
            //  startActivityForResult(intent, REQ_CODE_VOICE);

            speech.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {
                    Log.e("LOG_TAG", "onReadyForSpeech");


                }

                @Override
                public void onBeginningOfSpeech() {
                    Log.e("LOG_TAG", "onBeginningOfSpeech");

                }

                @Override
                public void onRmsChanged(float rmsdB) {

                }

                @Override
                public void onBufferReceived(byte[] buffer) {

                }

                @Override
                public void onEndOfSpeech() {
                    speech.stopListening();
                    //   speech.destroy();
                    Log.e("LOG_TAG", "onEndOfSpeech");

                }

                @Override
                public void onError(int error) {

                }

                @Override
                public void onResults(Bundle results) {
                    Log.e("TAG", "onResults");
                    ArrayList<String> result = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    Reply = result.get(0);  //most likely output the array has other interpretations of the speech
                    out.setText(Reply);
                   // VoiceCommands v = new VoiceCommands(getApplicationContext() ,Reply);
//                    VoiceCommands(Reply);
                    Log.e("TAG", Reply);


                }

                @Override
                public void onPartialResults(Bundle partialResults) {

                }

                @Override
                public void onEvent(int eventType, Bundle params) {

                }
            });
            speech.startListening(intent);
        } catch (ActivityNotFoundException a) {
            Log.e("Something went wrong'", "faat bel exception ");
        }
    }
*/

    void VoiceCommands(String Reply) {
        String command = null;
        StringTokenizer tokenizer = null; //https://www.tutorialspoint.com/java/util/java_util_stringtokenizer.htm
        Intent exec ;    //use for any actions
        this.Reply = Reply;

        tokenizer = new StringTokenizer(Reply);
        command = tokenizer.nextToken();
        Log.e("command ", command);
        String temp = tokenizer.nextToken(); //phone number or contact name
        Log.e("temp is ", temp);
        switch (command) { //add command list

            case "call": //syntax : call contact|number
               exec = new Intent(Intent.ACTION_CALL);


                if (temp.charAt(0) == '0') { //for number
                    //phone numbers always starts with a zero
                    exec.setData(Uri.parse("tel:" + temp));
                } else { //for contact

                    exec.setData(Uri.parse("tel:" + "12354"));
                }
                //  startActivity(intent);
               if (CheckCallPermission ()) startActivity(exec);



                break;

        }

    }
private boolean CheckCallPermission (){

    return (PackageManager.PERMISSION_GRANTED==getApplicationContext().checkCallingOrSelfPermission("android.permission.CALL_PHONE"));
}

    /**
     * Device list.
     *
     * @author Lorensius W. L. T <lorenz@londatiga.net>
     *
     */
    public static class DeviceListActivity extends Activity {
        private ListView mListView;
        private DeviceListAdapter mAdapter;
        private ArrayList<BluetoothDevice> mDeviceList;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_paired_devices);

            mDeviceList		= getIntent().getExtras().getParcelableArrayList("device.list");

            mListView		= (ListView) findViewById(R.id.lv_paired);

            mAdapter		= new DeviceListAdapter(this);

            mAdapter.setData(mDeviceList);
            mAdapter.setListener(new DeviceListAdapter.OnPairButtonClickListener() {
                @Override
                public void onPairButtonClick(int position) {
                    BluetoothDevice device = mDeviceList.get(position);

                    if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                        unpairDevice(device);
                    } else {
                        showToast("Pairing...");

                        pairDevice(device);
                    }
                }
            });

            mListView.setAdapter(mAdapter);

            registerReceiver(mPairReceiver, new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED));
        }

        @Override
        public void onDestroy() {
            unregisterReceiver(mPairReceiver);

            super.onDestroy();
        }


        private void showToast(String message) {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }

        private void pairDevice(BluetoothDevice device) {
            try {
                Method method = device.getClass().getMethod("createBond", (Class[]) null);
                method.invoke(device, (Object[]) null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void unpairDevice(BluetoothDevice device) {
            try {
                Method method = device.getClass().getMethod("removeBond", (Class[]) null);
                method.invoke(device, (Object[]) null);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private final BroadcastReceiver mPairReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                     final int state 		= intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);
                     final int prevState	= intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, BluetoothDevice.ERROR);

                     if (state == BluetoothDevice.BOND_BONDED && prevState == BluetoothDevice.BOND_BONDING) {
                         showToast("Paired");
                     } else if (state == BluetoothDevice.BOND_NONE && prevState == BluetoothDevice.BOND_BONDED){
                         showToast("Unpaired");
                     }

                     mAdapter.notifyDataSetChanged();
                }
            }
        };
    }
}

