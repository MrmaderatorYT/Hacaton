package com.example.hacaton;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    private TextView resultText;
    private int userWins = 0; // Змінна для підрахунку перемог користувача

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        resultText = findViewById(R.id.resultText);
    }

    // Обробник натискання на кнопки вибору
    public void onChoiceClick(View view) {
        Button clickedButton = (Button) view;
        String playerChoice = clickedButton.getText().toString().toLowerCase();
        String computerChoice = getComputerChoice();
        String result = determineWinner(playerChoice, computerChoice);

        // Оновлюємо текст результату
        String resultTextString = "Комп'ютер вибрав: " + computerChoice + ". " + result;
        if (result.equals("Ви виграли!")) {
            userWins++;
            resultTextString += " Перемог: " + userWins;
        }
        resultText.setText(resultTextString);
    }

    // Метод для отримання вибору комп'ютера
    private String getComputerChoice() {
        String[] choices = {"камінь", "ножиці", "папір"};
        int randomIndex = (int) (Math.random() * 3);
        return choices[randomIndex];
    }

    // Метод для визначення переможця
    private String determineWinner(String playerChoice, String computerChoice) {
        if (playerChoice.equals(computerChoice)) {
            return "Нічия!";
        } else if (
                (playerChoice.equals("камінь") && computerChoice.equals("ножиці")) ||
                        (playerChoice.equals("ножиці") && computerChoice.equals("папір")) ||
                        (playerChoice.equals("папір") && computerChoice.equals("камінь"))
        ) {
            return "Ви виграли!";
        } else {
            return "Ви програли!";
        }
    }
}