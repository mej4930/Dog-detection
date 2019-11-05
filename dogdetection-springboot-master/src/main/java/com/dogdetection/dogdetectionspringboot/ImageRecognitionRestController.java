package com.dogdetection.dogdetectionspringboot;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class ImageRecognitionRestController {

    final String serverkey = "TEST";
    final String userkey = "TEST";
    final String deviceToken = "TEST";

    @PostMapping("/AlarmTest")
    @ResponseBody
    public String alarmtest(){
        NotifyToAndroid();
        return "Alarm Goes.";
    }


    @PostMapping("/imageInputTest")
    @ResponseBody
    public String imageInputTest(
            @RequestParam("file") MultipartFile uploadFile) throws IOException {

        String msgFromPython = null;
        String sourceFileName = uploadFile.getOriginalFilename();

        System.out.println("SourceFile :" + sourceFileName);
        try{
            File dest = new File("/home/arch/springboot/uploadData/"+sourceFileName);
            System.out.println(dest.getAbsolutePath());
            uploadFile.transferTo(dest);
        }
        catch(IOException e){
            e.printStackTrace();
            return "File IOException occurred";
        }

        String lowerCaseFileName = sourceFileName.toLowerCase();

        int indexOfDot = lowerCaseFileName.lastIndexOf('.');
        String fileExt = lowerCaseFileName.substring(indexOfDot+1);

        List allowedFileExt = Arrays.asList(new String[]{"jpg","png","bmp","jpeg"});

        String fetching = "python3 " + "test.py /home/arch/springboot/uploadData/"+sourceFileName;
        String[] commandToExecute = new String[]{"/bin/bash", "-c", fetching};

        try{
            Process process = Runtime.getRuntime().exec(commandToExecute);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            System.out.println("Python running msg below");

            msgFromPython = stdInput.readLine();
            if(msgFromPython.equals("normal")){
                boolean normalFlag = true;
                System.out.println("Normal : Flag is " + normalFlag);
            }
            if(msgFromPython.equals("abnormal")){
                boolean abnormalFlag = false;
                System.out.println("Abnormal : Flag is " + abnormalFlag);
                NotifyToAndroid();
            }

            while( (msgFromPython = stdInput.readLine()) != null){
                System.out.println(msgFromPython);
            }

            System.out.println("-----------------------");
            System.out.println("Python Error msg below ");

            msgFromPython = stdError.readLine();
            while( (msgFromPython = stdError.readLine()) != null){
                System.out.println(msgFromPython);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        if(allowedFileExt.contains(fileExt)){
            return "It is image file!";
        }
        else{
            return "It is not image file!";
        }
    }

    public String NotifyToAndroid() throws JSONException {
 
        OkHttpClient client = new OkHttpClient.Builder().build();

        okhttp3.RequestBody requestbody = new FormBody.Builder()
                .add("to", deviceToken)
                .add("project_id", userkey)
                .add("notification", "FCM Test")
                .add("data","Hello world!!")
                .build();

        Request request = new Request.Builder()
                .url("https://fcm.googleapis.com/fcm/send")
                .addHeader("Authorization", "key=" + serverkey)
                .post(requestbody)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(e.getMessage() + "\n ERROR");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    System.out.println(response.code() + "\n" + response.body().string() + "\n SUCCESS");
                } else {
                    System.out.println(response.body().string());
                }
            }
        });
        return "Call End";
    }
}
