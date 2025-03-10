import conway.caller.MyCallerWS;

public class Main {
    public static void main(String args[]){
        MyCallerWS caller = new MyCallerWS();
        caller.play();
        caller.closeSession();
    }
}
