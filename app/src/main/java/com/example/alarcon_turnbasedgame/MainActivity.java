package com.example.alarcon_turnbasedgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    TextView TxtHeroName,TxtMonsName,TxtHeroHP,TxtHeroMP,TxtMonsHP,TxtMonsMP,TxtHeroDPS,TxtMonsDPS,TxtLog;
    Button btnNextTurn;
    ImageButton skill1,skill2,skill3,skill4;

    //Hero Stats
    String HeroName = "Igor Turashev";
    int heroHP = 2000;
    int heroMP = 1500;
    int heroMinDamage = 100;
    int heroMaxDamage = 150;


    //Monster Stats
    String MonsName = "Yakubets";
    int monsterHP = 2000;
    int monsterMP = 1000;
    int monsterMinDamage = 60;
    int monsterMaxDamage = 100;
    //Game Turn
    int turnNumber = 1;


    boolean disabledstatus = false;
    int statuscounter = 0;
    int buttoncounter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //XML ids for txt and btn
        TxtHeroName = findViewById(R.id.TxtHeroName);
        TxtMonsName = findViewById(R.id.TxtMonsName);
        TxtHeroHP = findViewById(R.id.TxtHeroHP);
        TxtMonsHP = findViewById(R.id.TxtMonsHP);
        TxtHeroMP = findViewById(R.id.TxtHeroMP);
        TxtMonsMP = findViewById(R.id.TxtMonsMP);
        TxtHeroDPS = findViewById(R.id.TxtHeroDPS);
        TxtMonsDPS = findViewById(R.id.TxtMonsDPS);
        btnNextTurn = findViewById(R.id.btnNextTurn);

        TxtLog = findViewById(R.id.TxtCombatLog);

        TxtHeroName.setText(HeroName);
        TxtHeroHP.setText(String.valueOf(heroHP));
        TxtHeroMP.setText(String.valueOf(heroMP));

        TxtMonsName.setText(MonsName);
        TxtMonsHP.setText(String.valueOf(monsterHP));
        TxtMonsMP.setText(String.valueOf(monsterMP));

        TxtHeroDPS.setText(String.valueOf(heroMinDamage)+ " ~ " + String.valueOf(heroMaxDamage));
        TxtMonsDPS.setText(String.valueOf(monsterMinDamage)+ " ~ " + String.valueOf(monsterMaxDamage));

        skill1 = findViewById(R.id.btnskill1);
        skill2 = findViewById(R.id.btnskill2);
        skill3 = findViewById(R.id.btnskill3);
        skill4 = findViewById(R.id.btnskill4);



        //btn OnClick Listener
        btnNextTurn.setOnClickListener(this);
        skill1.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){

        TxtLog = findViewById(R.id.TxtCombatLog);

        Random randomizer = new Random();
        int herodps = randomizer.nextInt(heroMaxDamage - heroMinDamage) + heroMaxDamage ;
        int monsdps = randomizer.nextInt(monsterMaxDamage - monsterMinDamage) + monsterMaxDamage ;


        if (turnNumber % 2 !=1){ //if enemy's turn, disable button
            skill1.setEnabled(false);
        }

        else if (turnNumber % 2 == 1){
            skill1.setEnabled(true);
        }

        if (buttoncounter > 0){
            skill1.setEnabled(false);
            buttoncounter--;
        }
        else if (buttoncounter==0){
            skill1.setEnabled(true);
        }

        switch(v.getId()) {
            case R.id.btnskill1:

                monsterHP = monsterHP - 200;
                turnNumber++;
                TxtMonsHP.setText(String.valueOf (monsterHP));
                btnNextTurn.setText(" Next Turn ("+ String.valueOf(turnNumber)+ ")");

                TxtLog.setText(" Our Hero "+String.valueOf(HeroName) +" used stun!. It dealt "+ String.valueOf(200) + " to the enemy. The enemy is stunned for 4 turns ");

                disabledstatus = true;
                statuscounter = 4;

                if (monsterHP < 0){
                    TxtLog.setText(" Our Hero "+String.valueOf(HeroName) +" dealt "+ String.valueOf(herodps) + " to the enemy. You are Victorious ");
                    heroMP = 1500;
                    monsterHP = 1000;
                    turnNumber = 1;
                    btnNextTurn.setText("Reset Game");

                }

                buttoncounter = 12;

                break;
            case R.id.btnNextTurn:
                //

                if(turnNumber % 2 ==1){ //odd
                    monsterHP = monsterHP - herodps;
                    turnNumber++;
                    TxtMonsHP.setText(String.valueOf (monsterHP));
                    btnNextTurn.setText(" Next Turn ("+ String.valueOf(turnNumber)+ ")");

                    TxtLog.setText(" Our Hero "+String.valueOf(HeroName) +" dealt "+ String.valueOf(herodps) + " to the enemy. ");

                    if (monsterHP < 0){
                        TxtLog.setText(" Our Hero "+String.valueOf(HeroName) +" dealt "+ String.valueOf(herodps) + " to the enemy. You are Victorious ");
                        heroMP = 1500;
                        monsterHP = 1000;
                        turnNumber = 1;
                        btnNextTurn.setText("Reset Game");

                    }

                    if (statuscounter > 0){ //if the enemy is still stunned, reduced the stun for 1 turn.
                        statuscounter--;
                        if (statuscounter==0){
                            disabledstatus=false;
                        }
                    }
                    buttoncounter--;

                }
                else if(turnNumber % 2 != 1){ //even

                    if (disabledstatus==true){
                        TxtLog.setText(" The enemy is still stunned. " + String.valueOf(statuscounter) + " turns ");
                        statuscounter--;
                        if (statuscounter==0){
                            disabledstatus=false;
                        }


                    }
                    else {
                        heroHP = heroHP - monsdps;
                        turnNumber++;
                        TxtHeroHP.setText(String.valueOf(heroHP));
                        btnNextTurn.setText("Next Turn (" + String.valueOf(turnNumber) + ")");

                        TxtLog.setText(" The Monster " + String.valueOf(MonsName) + " dealt " + String.valueOf(monsdps) + " to the enemy. ");

                        if (heroHP < 0) {
                            TxtLog.setText(" The Monster " + String.valueOf(MonsName) + " dealt " + String.valueOf(monsdps) + " to the enemy. Game Over ");
                            heroMP = 1500;
                            monsterHP = 1000;
                            turnNumber = 1;
                            btnNextTurn.setText("Reset Game");
                            {

                            }
                        }
                        buttoncounter--;
                    }
                }
                    break;

        }

    }

}