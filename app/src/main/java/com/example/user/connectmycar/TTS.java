package com.example.user.connectmycar;


import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

/*
* 1- initialize object and pass context as "this"
* 2- use read(string ) method to her the string out loud
*
* */


public class TTS {
    private TextToSpeech TTS = null;
    private  String Reply ;
    private int res;
    TTS(Context cnt ){

        TTS = new TextToSpeech(cnt, new TextToSpeech.OnInitListener() {
            //https://developer.android.com/reference/android/speech/tts/TextToSpeech.OnInitListener.html
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS)
                    res = TTS.setLanguage(Locale.getDefault());   //set language  to the phone's default so it would work offline


            }
        });

    }



     public  void Read(String Reply) {


        this.Reply = Reply;

        ///view as an input so we could check on Icon later on
        //if mic is on MIC = True




            if (res != TextToSpeech.LANG_MISSING_DATA && res != TextToSpeech.LANG_NOT_SUPPORTED) { //check for errors
                //
                // Reply = "Whatever ";
                //        btn.setEnabled(true);
                Log.e("in TTS", Reply);
                TTS.speak(Reply, TextToSpeech.QUEUE_FLUSH, null);
                //TTS.stop();

            }



        }

    public  void destroy(){

        if (TTS != null) {

            TTS.stop();
            TTS.shutdown();
        }

    }
}
