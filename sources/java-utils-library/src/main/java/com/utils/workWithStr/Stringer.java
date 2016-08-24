package com.utils.workWithStr;

/**
 * Created by PROGRAMMER II on 16.12.13.
 */
public class Stringer {
   public static String trunkLeaderZero(String befor){
      String after=befor;
       int index=0;
       while(after.startsWith("0") && after.length()!=0){
         index++;
         after=befor.substring(index);
       }
       //after=befor;
    return after;
   }
    public static String setLeaderZero(String after, int countLiter){
        StringBuffer buffer=new StringBuffer("");
        if(countLiter>after.length()){
           int i=0;
           while(i<(countLiter-after.length())){
               buffer.append("0");i++;
           }
            buffer.append(after);
        }else{buffer=new StringBuffer(after);}
        return buffer.toString();
    }

    public static String trimFromBothSide(String befor){
        String after=befor;
        int index=0;
        while(after.startsWith(" ") && after.length()!=0){
            index++;
            after=befor.substring(index);
        }
        after=after.trim();
        return after;
    }

    public static String trunkLeaderMes(String mes, String befor){
        String after=befor;
        int index=0;
        while(after.startsWith(mes) && after.length()!=0){
            index++;
            after=befor.substring(index);
        }
        //after=befor;
        return after;
    }

   public static String isNumber(String str){
      int i=0;
      String s="digits";
      String s1="literal";
       String s2="symbols";
      String result="";
      StringBuffer check=new StringBuffer("");
      while(i<str.length()) {
          if(Character.isDigit(str.charAt(i))){
              check.append('d');i++;continue;
          }else {
              if (Character.isLetter(str.charAt(i))) {
                  check.append('l');
                  i++;
                  continue;
              }
              check.append('s');
          }
             i++;
      }
      int countD=0, countL=0, countS=0, count=0;
      for(char a:check.toString().toCharArray()){

          if(a=='d'){
              countD++;count++;continue;
           }
          if(a=='l'){
              countL++;count++;continue;
          }
          if(a=='s'){
              countS++;count++;continue;
          }

      }
      if(countD==count)return s;
      if(countL==count)return s1;
      if(countS==count)return s2;
   return result;
   }
   public static void main(String[]args){
      //System.out.println(new Stringer().trunkLeaderZero("00000012345455"));
       System.out.println(isNumber("+-="));
   }
}
