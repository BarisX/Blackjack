/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 *
 * @author Barış
 */
public class GUI extends JFrame {
    Random rand = new Random();
    
    int tempC;
    
    boolean dHitter = false;
    
    ArrayList <Card> Cards = new ArrayList <Card>();
    
    ArrayList <Message> Log = new ArrayList <Message>();
    
    Font fontCard = new Font("Times New Roman", Font.PLAIN,40);
    Font fontQuest = new Font("Times New Roman", Font.BOLD,40);
    Font fontButton = new Font("Times New Roman", Font.PLAIN,25);
    Font fontLog = new Font("Times New Roman", Font.ITALIC,30);
    
    Color cDealer = Color.RED;
    Color cPlayer = new Color (25,55,255);
    
    String questHitStay = new String("Tamam mı devam mı?");
    String questPlayMore = new String("Son bi kez daha?");
    
    Color colorBackground = new Color(39,119,20);
    Color colorButton = new Color(204,204,0);
    
    JButton bHit = new JButton();
    JButton bStay = new JButton();
    JButton bYes = new JButton();
    JButton bNo = new JButton();
    
    int sW = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    int sH = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    
    int aW = 1300;
    int aH= 800;
    
    int gridX = 50;
    int gridY = 50;
    int gridW = 900;
    int gridH = 400;
    
    int spacing = 10;
    int rounding = 10;
    int tCardW = (int) gridW/6;
    int tCardH = (int) gridH/2;
    int cardW = tCardW - spacing*2;
    int cardH = tCardH - spacing*2;
    
    boolean hit_stay_q = true;
    boolean dealer_turn = false;
    boolean play_more_q = false;
    
    ArrayList<Card> pCards = new ArrayList<Card>();
    ArrayList<Card> dCards = new ArrayList<Card>();
    
    int pMinTotal = 0;
    int pMaxTotal = 0;
    int dMinTotal = 0;
    int dMaxTotal = 0;
    
    int[] polyX = new int[4];
    int[] polyY = new int[4];
    
    public GUI(){
        this.setTitle("Blackjack");
        this.setBounds((sW-aW-6)/2,(sH-));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
        Board board = new Board();
        this.setContentPane(board);
        board.setLayout(null);
        
        Click click = new Click();
        this.addMouseListener(click);
        
        ActHit actHit = new ActHit();
        bHit.addActionListener(actHit);
        bHit.setBounds(1000,200,100,50);
        bHit.setBackground(colorButton);
        bHit.setFont(fontButton);
        bHit.setText("DEVAM");
        board.add(bHit);
        
        ActYes actYes = new ActYes();
        bYes.addActionListener(actYes);
        bYes.setBounds(1000,600,100,50);
        bYes.setBackground(colorButton);
        bYes.setFont(fontButton);
        bYes.setText("EVET");
        board.add(bYes);
        
        ActNo actNo = new ActNo();
        bNo.addActionListener(actNo);
        bNo.setBounds(1150,600,100,50);
        bNo.setBackground(colorButton);
        bNo.setFont(fontButton);
        bNo.setText("HAYIR");
        board.add(bNo);
        
        String temp_str = "starting_temp_str_name";
        for(int i=0;i<52;i++){
            if(i%4==0){
                temp_str = "Spades"; 
            } else if(i%4==1) {
                temp_str = "Hearts";
            } else if(i%4==2) {
                temp_str = "Diamonds";
            } else if(i%4==3) {
                temp_str = "Clubs";
            }
            Cards.add(new Card((i/4)+1,temp_str,i));
        }
        
        tempC = rand.nextInt(52);
        pCards.add(Cards.get(tempC));
        Cards.get(tempC).setUsed();
        
        tempC = rand.nextInt(52);
        while(Cards.get(tempC).used == true){
            tempC = rand.nextInt(52);
        }
        dCards.add(Cards.get(tempC));
        Cards.get(tempC).setUsed();
        
        tempC=rand.nextInt(52);
        while(Cards.get(tempC).used== true){
            tempC = rand.nextInt(52);
        }
        pCards.add(Cards.get(tempC));
        Cards.get(tempC).setUsed();
        
        tempC=rand.nextInt(52);
        while(Cards.get(tempC).used== true){
            tempC = rand.nextInt(52);
        }
        pCards.add(Cards.get(tempC));
        Cards.get(tempC).setUsed();
        
    }
    
    public void totalsChecker(){
        int acesCount;
        
        pMinTotal = 0;
        pMaxTotal = 0;
        acesCount = 0;
        
        for(Card c : pCards){
            pMinTotal += c.value;
            pMaxTotal += c.value;
            if(c.name == "ACE")
                acesCount++;
        }
        if(acesCount>0)
            pMaxTotal +=10;
        
        dMinTotal = 0;
        dMaxTotal = 0;
        acesCount = 0;
     
        for(Card c : pCards){
            pMinTotal += c.value;
            pMaxTotal += c.value;
            if(c.name == "ACE")
                acesCount++;
        }
        if(acesCount>0)
            dMaxTotal +=10;
    }
    
    public void setWinner(){
        int pPoints = 0;
        int dPoints = 0;
        
        if(pMaxTotal > 21) {
            pPoints = pMinTotal;
        } else {
            pPoints = pMaxTotal;
        }
        
        if(dMaxTotal > 21) {
            dPoints = dMinTotal;
        } else {
            dPoints = dMaxTotal;
        }
        
        if(pPoints >21 && dPoints >21) {
            Log.add(new Message("Kimse Kazanmadı!","Kasa"));
        } else if (dPoints > 21) {
            Log.add(new Message("Kazandınız!","Oyuncu"));
            Main.pWins++;
        } else if (pPoints>21) {
            Log.add(new Message("Kasa Kazandı!","Kasa"));
            Main.dWins++;
        } else if (pPoints > dPoints) {
            Log.add(new Message("Kazandınız!","Oyuncu"));
            Main.pWins++;
        } else {
            Log.add(new Message("Kasa Kazandı!","Kasa"));
            Main.dWins++;
        }
    }
    public void dealerHitStay(){
        dHitter = true;
        
        int dAvailable = 0;
        if(dMaxTotal > 21){
            dAvailable = dMinTotal;
        } else {
            dAvailable = dMaxTotal;
        }
        
        int pAvailable = 0;
        if(pMaxTotal > 21) {
            pAvailable = pMinTotal;
        } else {
            pAvailable = pMaxTotal;
        }
        
        repaint();
        
        try{
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        if((dAvailable < pAvailable && pAvailable <=21) || dAvailable < 16) {
            int tempMax = 0;
            if(dMaxTotal <= 21) {
                tempMax = dMaxTotal;
            } else {
                tempMax = dMinTotal;
            }
            
            String mess = ("Kasa devam etti!  (total: "+Integer.toString(tempMax)+")");
            Log.add(new Message(mess,"Dealer"));
            tempC = rand.nextInt(52);
            while(Cards.get(tempC).used == true){
                tempC = rand.nextInt(52);
            }
            dCards.add(Cards.get(tempC));
            Cards.get(tempC).setUsed();
            
        } else {
            int tempMax = 0;
            if(dMaxTotal <= 21) {
                tempMax = dMaxTotal;
            } else {
                tempMax = dMinTotal;
            }
            String mess = ("Dealer decided to stay! (total:  "+ Integer.toString(tempMax) + ")");
            Log.add(new Message(mess,"Dealer"));
            setWinner();
            dealer_turn = false;
            play_more_q = true;
        }
        dHitter = false;
        
    }
    
    
    //refesher
    
}
