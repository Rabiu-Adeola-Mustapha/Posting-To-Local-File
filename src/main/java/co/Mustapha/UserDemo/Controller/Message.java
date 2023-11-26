package co.Mustapha.UserDemo.Controller;


import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class Message{



    File msgfile = new File("messages.txt");
    File logfile = new File("log.txt");

    @PostMapping("/message")
    public String sendMssg(@RequestBody  MessageBody myMsg ){

        try(BufferedWriter writeMssg = new BufferedWriter( new FileWriter(msgfile, true));
            BufferedWriter writeLog = new BufferedWriter(new FileWriter(logfile, true));){

             myMsg = (new MessageBody(myMsg.getTitle(), myMsg.getBody()) );

            // writing to the meessage.txt
            writeMssg.write(myMsg + "# \n");
            // logging to log.txt
            int num = MessageBody.messageCount/2; // Is God not Wonderful
            writeLog.write("New massage posted is the:  " + num + "Massage \n");


        } catch (IOException e) {
            //throw new RuntimeException(e);
            System.out.println(e.getMessage());
        }

        return "Message Posted Successfully";
    }

    @GetMapping("/messages")
    public String getMssg(){

        StringBuilder stringBuilder = new StringBuilder();
        String msg;
        try(BufferedReader mssgReader = new BufferedReader(new FileReader(msgfile));){

            while( ( msg = mssgReader.readLine() ) != null ) {
                stringBuilder.append(msg);
            }



        }catch (IOException e){
            System.out.println(e.getMessage());
        }

        return stringBuilder.toString();
    }

    @GetMapping("/msg-count")
    public int mssgCount(){
        
        int msgCount = 0;
        List<String> myList = new ArrayList<String>();
        String read;
        String myRead = null;
        StringBuilder sb = new StringBuilder();
        try(BufferedReader msgCounter = new BufferedReader(new FileReader(msgfile));){

            while( (read = msgCounter.readLine()) != null) {
                
                sb.append(read);
                myRead = sb.toString();
                
            }

            myList  = Arrays.asList(myRead.split("#"));
            msgCount = myList.size() - 1;


        }catch(IOException e){
            System.out.println(e.getMessage());
        }

         //return  MessageBody.getMessageCount(); // wonderful GOD...
        return  msgCount;

    }


}

class MessageBody{

   private String title;
   private String body;
   static int messageCount = 0; // just to maintain encapsulation

    public MessageBody( String title, String body) {
        this.title = title;
        this.body = body;
        if(title != null && body != null) {
            MessageBody.messageCount++;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    @Override
    public String toString() {
        return " title : " + title + ", body : " + body;

//                "MessageBody{" +
//                "title='" + getTitle() + '\'' +
//                ", body='" + getBody() + '\'' +
//                '}';
    }


     //Maintaining encapsulation
    public static int getMessageCount() {
        return messageCount/2;
    }


}