import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        try{
            Socket s=new Socket("localhost",6666);
            DataOutputStream out=new DataOutputStream(s.getOutputStream());
            DataInputStream in=new DataInputStream(s.getInputStream());

            String msg;
            String ann;
            System.out.println("enter name: ");
            while(true){
                ann=in.readUTF();
                System.out.print(ann);
                if(ann.equals("> ")){
                    msg=scanner.nextLine();
                    if(msg.equals("disconnect")){
                        out.writeUTF(msg);
                        out.flush();
                        break;
                    }else{
                        out.writeUTF(msg);
                        out.flush();
                    }
                }
            }
            out.close();
            s.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
