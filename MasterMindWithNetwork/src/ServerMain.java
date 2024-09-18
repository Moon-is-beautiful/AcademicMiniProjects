

import java.io.*;
import java.net.*;
import java.util.*;

public class ServerMain {
    private static ArrayList<String> clientNames = new ArrayList<>();
    private static Map<String,String> cMap=new HashMap<>();
    private static String secret;
    public static void main(String[] args){
        secret = SecretCodeGenerator.getInstance().getNewSecretCode();
        Scanner scanner=new Scanner(System.in);
        boolean boo=false;
        try{
            boo=args[0].equals("1");
        }catch(Exception e){
        }
        try (ServerSocket ss = new ServerSocket(6666)) {
            while (true) {
                Socket s = ss.accept(); // establishes connection for each client

                // Create a new thread to handle each client separately
                Thread t = new ClientHandler(s, boo);
                t.start();
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

     static class ClientHandler extends Thread {
         private String answer;
         private List<String[]>history;
         private int remainingAttempts;
         private boolean firstTime;
         private Socket socket;
         private Boolean boo;
         private String username;
         private Boolean ft;


        public ClientHandler(Socket socket,Boolean boo) {
            this.socket = socket;
            this.boo=boo;
            ft=false;
            answer=secret;
            history = new ArrayList<>();
            remainingAttempts = GameConfiguration.guessNumber;
            firstTime=true;
        }

         private  void displayHistory(StringBuilder str){
             for (String[] strings : history) {
                 str.append(strings[0] + "        " + strings[1]+"\n");
             }
         }

          String comparing(String guess,StringBuilder str){
             int countB = 0;
             int countW = 0;
             boolean[] used1 = new boolean[guess.length()];
             boolean[] used2 = new boolean[guess.length()];
             if(guess.length() != GameConfiguration.pegNumber){
                 if(guess.isEmpty()){
                     str.append("\n-> INVALID GUESS\n\n");
                     return str.toString();
                 }
                 if(guess.equals("HISTORY")){
                     str.append("\n");
                     displayHistory(str);
                     str.append("\nYou have " + remainingAttempts + " guesses left.\n");
                     return str.toString();
                 }
                 str.append("\n" + guess + " -> INVALID GUESS\n\n");
                 return str.toString();
             }
             for(int i = 0; i < guess.length(); i++){
                 if(guess.charAt(i) <= 'z' && guess.charAt(i) >= 'a'){
                     str.append("\n" + guess + " -> INVALID GUESS\n\n");
                     return str.toString();
                 }
                 if(!(Arrays.asList(GameConfiguration.colors).contains(""+guess.charAt(i)))){
                     str.append("\n" + guess + " -> INVALID GUESS\n\n");
                     return str.toString();
                 }
             }
             for (int i = 0; i < guess.length(); i++) {
                 char g = guess.charAt(i), a = answer.charAt(i);
                 if (g == a) countB++;
                 else {
                     for (int j = 0; j < guess.length(); j++) {
                         if (g == answer.charAt(j) && !used1[i] && !used2[j]) {
                             countW++;
                             used1[i] = used2[j] = true;
                             break;
                         }
                     }
                 }
             }
             if (countB == answer.length()) {
                 str.append("\n" + guess + " -> Result: " + countB + "B_0W - You win!!\n");
                 cMap.put(username,"W");
                 remainingAttempts = 0;
             } else {
                 remainingAttempts--;

                 if (remainingAttempts == 0) {
                     str.append("\nSorry, you are out of guesses. You lose, boo-hoo.\n");
                     cMap.put(username,"L");
                 } else {
                     str.append("\n" + guess + " -> Result: " + countB + "B_" + countW + "W\n\n"+"You have " + remainingAttempts + " guesses left.\n");
                     String[] tempSA=new String[]{guess, countB + "B_" + countW + "W"};
                     history.add(tempSA);
                 }
             }
              return str.toString();
         }
        void newgame(){
            for(Map.Entry<String,String> entry:cMap.entrySet()){
                entry.setValue("G");
            }
            secret = SecretCodeGenerator.getInstance().getNewSecretCode();
        }
        String welcome(String in){
            clientNames.add(in);
            cMap.put(username,"G");
            System.out.println("New player enters");
            StringBuilder result=new StringBuilder();
            result.append("Welcome to the game!\n\nCurrent Player list: \n");
            for(String i:clientNames){
                result.append("     "+i+"\n");
            }
            return result.toString();
        }


        public void run() {
            try {
                DataInputStream dis=new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeUTF("> ");
                dos.flush();
                String str = dis.readUTF();
                username=str;
                dos.writeUTF(welcome(str));
                dos.flush();
                dos.writeUTF("Welcome to Mastermind. Here are the rules.\n\nThis is a text version of the classic board game Mastermind.\nThe computer will think of a secret code. The code consists of "+GameConfiguration.pegNumber +
                        "\ncolored pegs. The pegs MUST be one of six colors: blue, green,\norange, purple, red, or yellow. A color may appear more than once in\n" +
                        "the code. You try to guess what colored pegs are in the code and\nwhat order they are in. After you make a valid guess the result\n(feedback) will be displayed.\n\n" +
                        "The result consists of a black peg for each peg you have guessed\nexactly correct (color and position) in your guess. For each peg in\nthe guess that is the correct color, but is out of position, you get\n" +
                        "a white peg. For each peg, which is fully incorrect, you get no\nfeedback.\n\nOnly the first letter of the color is displayed. B for Blue, R for\nRed, and so forth. When entering guesses you only need to enter the\n" +
                        "first character of each color as a capital letter.\n\nYou have 12 guesses to figure out the secret code or you lose the\ngame.\n");
                dos.flush();
                while(true){
                    if(boo){
                        dos.writeUTF("Generating secret code ...(for this example the secret code is "+ secret +")\n" );
                        dos.flush();
                    } else{
                        dos.writeUTF("Generating secret code ... \n" );
                        dos.flush();
                    }
                    if(firstTime){
                        dos.writeUTF("You have " + remainingAttempts + " guesses left.\n");
                        dos.flush();
                        firstTime=false;
                    }
                    while(remainingAttempts > 0){
                        dos.writeUTF("What is your next guess?\nType in the characters for your guess and press enter.\nEnter guess: ");
                        dos.flush();
                        dos.writeUTF("> ");
                        dos.flush();
                        StringBuilder newS= new StringBuilder();
                        str=dis.readUTF();
                        if(str.equals("disconnect")){
                            dis.close();
                            dos.close();
                            cMap.remove(username);
                            System.out.println(username+" disconnected");
                            ft=true;
                            break;
                        }
                        dos.writeUTF(comparing(str,newS));
                        dos.flush();
                        if(cMap.containsValue("W")&&!cMap.get(username).equals("W")){
                            cMap.put(username,"L");
                            dos.writeUTF("GG! someone else won\n");
                            dos.flush();
                            break;
                        }
                    }
                    if(!cMap.containsValue("G")){
                        newgame();
                    }else{
                        dos.writeUTF("\nNow Waiting...");
                        dos.flush();
                    }
                    if(ft){
                        break;
                    }
                    history.clear();
                    remainingAttempts=GameConfiguration.guessNumber;
                    while(true){
                        if(cMap.get(username).equals("G")){break;}
                    }
                    dos.writeUTF("\n\n\tLoading new Game...\n\n");
                    dos.flush();
                    answer=secret;

                }
                //socket.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}
