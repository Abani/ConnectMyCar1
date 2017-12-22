package com.example.user.connectmycar;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.util.ArrayList;
import java.util.Locale;
/*
* 1- pass context on initialization
* 2- to start listening  use startVoiceToTextService()
* 3- to get text use getReply
*
* */

//public class STT implements Runnable{
    public class STT {

    private SpeechRecognizer speech = null;
    private  String Reply="" ;

    STT(Context cnt ){

        speech =SpeechRecognizer.createSpeechRecognizer(cnt);


    }
    public  void  startVoiceToTextService() {
//  public  void  run() {
         //String Reply ;

        final Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);  //opens prompt
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());  //locale.GERMAN for lang
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, Locale.getDefault());


        try {

            //getting results from the speech activity https://developer.android.com/training/basics/intents/result.html
            //  startActivityForResult(intent, REQ_CODE_VOICE);
            speech.startListening(intent);


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
                public  void onEndOfSpeech() {
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
                    setReply(result.get(0));  //most likely output the array has other interpretations of the speech
                   // speech.stopListening();
                    Log.e("TAG", Reply);



                }

                @Override
                    public void onPartialResults(Bundle partialResults) {

                    }

                @Override
                public void onEvent(int eventType, Bundle params) {

                }
            });


            Log.e("startListening'", "startListening ");
        } catch (ActivityNotFoundException a) {
            Log.e("Something went wrong'", "faat bel exception ");
        }

        Log.e("return Reply", Reply);




    }

public  String getReply(){

    return  Reply;

}

private    void setReply(String Reply)
{

    this.Reply =Reply;


}
     public  void destroy (){
        if (speech != null) {
            speech.destroy();
        }

    }
}
