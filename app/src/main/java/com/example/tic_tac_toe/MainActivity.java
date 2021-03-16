package com.example.tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView jugador1Score, jugador2Score, jugadorStatus;
    private Button[] buttons = new Button[9];
    private Button resetGame;

    private int jugador1Count, jugador2Count, rountCount;
    boolean activePlayer;

    //p1 = 0
    //p2 = 1
    //empate = 2
    int [] gameState = {2,2,2,2,2,2,2,2,2};

    int[][] wPositions = {
            {0,1,2}, {3,4,5}, {6,7,8}, //rows
            {0,3,6}, {1,4,7}, {2,4,8}, //columns
            {0,4,8}, {2,4,6} //diagonal
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jugador1Score = (TextView) findViewById(R.id.jugador1Score);
        jugador2Score = (TextView) findViewById(R.id.jugador2Score);
        jugadorStatus = (TextView) findViewById(R.id.jugadorStatus);

        resetGame = (Button) findViewById(R.id.resetGame);

        for(int i=0;i<buttons.length;i++) {
            String buttonID = "btn_" + i;
            int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = (Button) findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }

        rountCount = 0;
        jugador1Count = 0;
        jugador2Count = 0;
        activePlayer = true;
    }

    @Override
    public void onClick(View v) {
        //Log.i("test", "button is clicked!");
        if(!((Button)v).getText().toString().equals("")) {
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1, buttonID.length()));

        if(activePlayer) {
            ((Button) v).setText("X");
            ((Button) v).setTextColor(Color.parseColor("#FFC34A"));
            gameState[gameStatePointer] = 0;
        } else {
            ((Button) v).setText("O");
            ((Button) v).setTextColor(Color.parseColor("#70FFEA"));
            gameState[gameStatePointer] = 1;
        }

        rountCount++;

        if(checkWinner()) {
            if(activePlayer) {
                jugador1Count++;
                updateJugador();
                Toast.makeText(this, "Jugador 1 win", Toast.LENGTH_SHORT).show();
                playAgain();
            } else {
                jugador2Count++;
                updateJugador();
                Toast.makeText(this, "Jugador 2 win", Toast.LENGTH_SHORT).show();
                playAgain();
            }
        } else if(rountCount == 9) {
            playAgain();
            Toast.makeText(this, "No Winner!", Toast.LENGTH_SHORT).show();
        } else {
            activePlayer = !activePlayer;
        }

        if(jugador1Count > jugador2Count) {
            jugadorStatus.setText("Jugador 1 win");
        } else if(jugador2Count > jugador1Count) {
            jugadorStatus.setText("Jugador 2 win");
        } else {
            jugadorStatus.setText("");
        }

        resetGame.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
                jugador1Count = 0;
                jugador2Count = 0;
                jugadorStatus.setText("");
                updateJugador();
            }
        }));
    }

    public boolean checkWinner() {
        boolean winnerResult = false;

        for(int [] winningPosition : wPositions) {
            if(gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] != 2) {
                winnerResult = true;
            }
        }
        return winnerResult;
    }

    public void updateJugador() {
        jugador1Score.setText(Integer.toString(jugador1Count));
        jugador2Score.setText(Integer.toString(jugador2Count));
    }

    public void playAgain() {
        rountCount = 0;
        activePlayer = true;

        for(int i=0;i<buttons.length;i++) {
            gameState[i] = 2;
            buttons[i].setText("");
        }
    }
}