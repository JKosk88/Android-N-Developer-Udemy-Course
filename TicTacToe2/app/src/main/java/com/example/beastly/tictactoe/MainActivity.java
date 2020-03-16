package com.example.beastly.tictactoe;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.reflect.Array;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    //0-x
    //1-o

    int moves = 0;
    int player = 1;
    int[] gameState = {2,2,2,2,2,2,2,2,2};
    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};
    boolean active = true;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public void play (View view) {
        ImageView x = (ImageView) view;
//        x.setRotation(-90);
        x.setAlpha(0f);
        x.setScaleX(0.5f);
        x.setScaleY(0.5f);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.playAgainLayout);
        TextView winnerMessage = (TextView)findViewById(R.id.wonMessage);

        Log.i("teat", x.getTag().toString());

        if (gameState[Integer.parseInt(x.getTag().toString())] == 2 && active) {

            gameState[Integer.parseInt(x.getTag().toString())] = player;

            moves++;

            x.animate().alpha(1).scaleX(1).scaleY(1).rotation(-90).setDuration(500);

            if (player == 1) {
                x.setImageResource(R.drawable.cross);
                player = 0;
            } else {
                x.setImageResource(R.drawable.circle);
                player = 1;
            }
        }

        String playerShape;

        if (player == 0) {
            playerShape = "Cross";
        } else {
            playerShape = "Circle";
        }

        for (int[] winningPosition : winningPositions){
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] != 2){
                for (int i = 0; i < 9; i++){
                    gameState[i] = 2;
                }
                moves = 0;
                winnerMessage.setText(playerShape + " has won!");
                layout.setVisibility(View.VISIBLE);
                active = false;
            } else if (moves == 9){
                for (int i = 0; i < 9; i++){
                    gameState[i] = 2;
                }
                moves = 0;
                winnerMessage.setText("It's a draw!");
                layout.setVisibility(View.VISIBLE);
                active = false;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void playAgain (View view){
        active = true;

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.playAgainLayout);

        layout.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++){
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
