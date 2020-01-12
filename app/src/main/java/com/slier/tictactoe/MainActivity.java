package com.slier.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private short player = 0;
    private TextView status;
    private boolean isGameOver = false;

    private ImageView cell1;
    private ImageView cell2;
    private ImageView cell3;
    private ImageView cell4;
    private ImageView cell5;
    private ImageView cell6;
    private ImageView cell7;
    private ImageView cell8;
    private ImageView cell9;
    private Button restart;

    private int[][] winningPosition = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };

    private int[] boardCellValue = {-1, -1, -1, -1, -1, -1, -1, -1, -1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.status = findViewById(R.id.status);
        this.status.setAlpha(0);

        this.setupCellEvents();
        this.setupRestartButton();
    }

    private void setupCellEvents() {
        this.cell1 = findViewById(R.id.cell1);
        this.cell2 = findViewById(R.id.cell2);
        this.cell3 = findViewById(R.id.cell3);
        this.cell4 = findViewById(R.id.cell4);
        this.cell5 = findViewById(R.id.cell5);
        this.cell6 = findViewById(R.id.cell6);
        this.cell7 = findViewById(R.id.cell7);
        this.cell8 = findViewById(R.id.cell8);
        this.cell9 = findViewById(R.id.cell9);

        this.cell1.setOnClickListener(this::cellEvent);
        this.cell2.setOnClickListener(this::cellEvent);
        this.cell3.setOnClickListener(this::cellEvent);
        this.cell4.setOnClickListener(this::cellEvent);
        this.cell5.setOnClickListener(this::cellEvent);
        this.cell6.setOnClickListener(this::cellEvent);
        this.cell7.setOnClickListener(this::cellEvent);
        this.cell8.setOnClickListener(this::cellEvent);
        this.cell9.setOnClickListener(this::cellEvent);
    }

    private void setupRestartButton() {
        this.restart = findViewById(R.id.restart);
        this.restart.setAlpha(0);

        this.restart.setOnClickListener(v -> {
            this.cell1.animate().alpha(0);
            this.cell2.animate().alpha(0);
            this.cell3.animate().alpha(0);
            this.cell4.animate().alpha(0);
            this.cell5.animate().alpha(0);
            this.cell6.animate().alpha(0);
            this.cell7.animate().alpha(0);
            this.cell8.animate().alpha(0);
            this.cell9.animate().alpha(0);

            for (int i = 0; i < this.boardCellValue.length; i++) {
                this.boardCellValue[i] = -1;
            }

            this.player = 0;
            this.isGameOver = false;

            status.setAlpha(0);
            restart.setAlpha(0);
        });
    }

    public void cellEvent(View v) {
        ImageView cell = (ImageView) v;
        int tempPlayer = this.player;

        int boardPosition = Integer.parseInt(cell.getTag().toString());

        if (this.boardCellValue[boardPosition] != -1 || this.isGameOver) {
            return;
        }

        this.boardCellValue[boardPosition] = tempPlayer;

        if (this.player == 0) {
            cell.setImageResource(R.drawable.red);
            this.player = 1;
        } else {
            cell.setImageResource(R.drawable.yellow);
            this.player = 0;
        }

        cell.setTranslationY(-1500);
        cell.animate().alpha(1).translationYBy(1500).setDuration(400);
        this.updateGame();
    }

    private void updateGame() {
        this.isGameOver();
        this.checkWinner();
    }

    private void isGameOver() {
        int cellFilled = 0;

        for (int i = 0; i < this.boardCellValue.length; i++) {
            if (this.boardCellValue[i] != -1) {
                cellFilled++;
            }
        }

        if (cellFilled == this.boardCellValue.length) {
            this.isGameOver = true;
            this.status.setText("Nobody win");
            this.status.setAlpha(1);
            this.restart.setAlpha(1);
        }
    }

    private void checkWinner() {
        for (int[] winPosition : this.winningPosition) {
            if (boardCellValue[winPosition[0]] == boardCellValue[winPosition[1]] &&
                    boardCellValue[winPosition[1]] == boardCellValue[winPosition[2]] &&
                    boardCellValue[winPosition[0]] != -1
            ) {
                this.isGameOver = true;
                String message;

                if (boardCellValue[winPosition[0]] == 0) {
                    message = "Red has win";
                } else {
                    message = "Yellow has win";
                }

                this.status.setText(message);
                this.status.setAlpha(1);
                this.restart.setAlpha(1);
            }
        }
    }
}
