/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

/**
 *
 * @author Barış
 */
public class Message {
    String mes = "empty_for_now";
    String who = "nobody_for_now";
    
    public Message (String m,String w){
        this.mes = m;
        this.who = w;
    }
    
    public String getMessage(){
        return this.mes;
    }
    
    public String getWho(){
        return this.who;
    }
}
