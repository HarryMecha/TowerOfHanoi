/** ---------------------------------------------------------------------
 * Author: Harry McCoy
 * Module: COMP5180
 *  Assignment 2 (2021-22)
 *  Task 1
 * Program:
 * Solve Tower of Hanoi problem based on user input
 ---------------------------------------------------------------------  */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;



public class hm474_task1 {
    
   
     static int initialn; //The value of n taken from arguments
      static  int initialt;//The value of t taken from arguments
       static  int initials;//The value of s taken from arguments
       static int initiald; //The value of d taken from arguments
      static File ToHOut;//The file that is written out to


    /** ---------------------------------------------------------------------
     * Function: move
     * This function moves the discs from their source tower to the destination tower
     * Also the writing the move into the text file
     ---------------------------------------------------------------------  */
    public static void move(int disc, HashMap ToH, int s, int d,int t){
       if(s!=d){
       ArrayList source = (ArrayList)ToH.get("Tower "+s);
       ArrayList dest =  (ArrayList)ToH.get("Tower "+d);
       source.remove(source.indexOf(disc));
       dest.add(disc);
       System.out.println("Move disk "+disc+" from T"+s+" to T"+d);
       
        try {  
      FileOutputStream fOut = new FileOutputStream(ToHOut,true);
      OutputStreamWriter osw = new OutputStreamWriter(fOut);
      osw.write(disc+"\t"+s+"\t"+d+"\n");
      osw.flush();
      osw.close();
    } catch (IOException e) {
    } 
    }
    }

    /** ---------------------------------------------------------------------
     * Function: ToHSetup
     * This function is used to set up the Tower of Hanoi using the users
     * arguments, it's defined as a hashmap, tower names as keys. Also populating
     * the first tower.
     ---------------------------------------------------------------------  */
    
    public static HashMap ToHSetup(int n,int t,int s,int d){
    HashMap<String,ArrayList> ToH =  new HashMap<String,ArrayList>(); 
        for (int i = 1; i <= t; i++)
        {
            ArrayList Tower = new ArrayList<Integer>();
            if(i==(s)){
                for (int j = n; j >= 1; j--) {
                    Tower.add(j);
                }
            }
            ToH.put(("Tower "+i), Tower);
        }
       
       
        return ToH;
    }

    /** ---------------------------------------------------------------------
     * Function: bufferTowerCheck
     * This is the function that allocates Buffer Towers, it checks through
     * all available towers to see if they are empty or if they are the destination
     * tower, then once it has sorted through it will place the disc on the furthest
     * empty tower, if no tower is empty it will then check to see which tower
     * a disc can be placed on, i.e. only larger discs on tower.
     ---------------------------------------------------------------------  */
    public static int bufferTowerCheck(HashMap ToH,int t,int d, int n){
        int BufferTower = 0;
        for (int i = 1; i <= t; i++) {
            if((((ArrayList)ToH.get("Tower "+i)).isEmpty()) && i != d){
                BufferTower = i;
            }
        }if (BufferTower!= 0 )return BufferTower;
        boolean isSmaller = false;      
        for (int i = 1; i <= t; i++) {
            for (int j = n; j >= 1; j--) {
              if((((ArrayList)ToH.get("Tower "+i)).contains(j))){ 
                  isSmaller= true;
            }
        }      
            if(isSmaller == false){
                BufferTower = i;
                return BufferTower;
            }
            else{
                
                isSmaller = false;
            }
            }
               
                
        return -1;
    }

    /** ---------------------------------------------------------------------
     * Function: runToH
     * This function runs the recursive algorithm, it will keep performing the
     * Tower of Hanoi for each of the set of discs, e.g. if there are 5 discs,
     * it would first perform Tower of Hanoi with 4 discs, which would then require
     * you to do ToH with 3 discs etc. it uses the buffer towers as a destination towers
     * finally resolving to do ToH with all the discs at the end.
     ---------------------------------------------------------------------  */
    public static void runToH(HashMap ToH, int n, int s, int d, int t){
        if(n==1){
            move(n,ToH,s,d,t);
            
        }else{
        int BufferTower = bufferTowerCheck(ToH, t,d,n);
        runToH(ToH,n-1,s,BufferTower, t);
        move(n,ToH,s,d,t);
        runToH(ToH,n-1,BufferTower,d, t);
    }
    }

    /** ---------------------------------------------------------------------
     * Function: main
     * Assigns all the variables, sets up the InputStream and runs the runToH function.
     ---------------------------------------------------------------------  */
    public static void main(String[] args) {
        initialn = Integer.parseInt(args[0]);
        initialt = Integer.parseInt(args[1]);
        initials = Integer.parseInt(args[2]);
        initiald = Integer.parseInt(args[3]);
        ToHOut = new File("hm474_ToH_n"+initialn+"_t"+initialt+"_s"+initials+"_d"+initiald+".txt");
          try {  
      FileOutputStream fOut = new FileOutputStream(ToHOut,true);
      OutputStreamWriter osw = new OutputStreamWriter(fOut);
      osw.write(initialn+"\t"+initialt+"\t"+initials+"\t"+initiald+"\n");
      osw.flush();
      osw.close();
    } catch (IOException e) {
    } 
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        int s = Integer.parseInt(args[2]);
        int d = Integer.parseInt(args[3]);
        HashMap ToH = ToHSetup(n,t,s,d);
        runToH(ToH,n,s,d,t);
              
    }
    
}
