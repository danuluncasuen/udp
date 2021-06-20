package com.utm.server.service;

import java.util.Arrays;
import java.util.List;

public class ResponseService {

    private int clueNumber = 0;
    private List<String> clues = Arrays.asList("110010100", "RG9lcyBsaWZlIGhhdmUgYSBzZW5zZT8=", "Jg zpv sfbe uijt nfttbhf zpv bsf fjuifs tnbsu up dsbdl uif hbnf ps tnbsu up difdl uif tpvsdf dpef uzqf xjodpef up foe uif hbnf", "You won!");
    private List<String> answers = Arrays.asList("not found", "42", "wincode");
    private boolean gameOn;
    private boolean gameActive = false;
    private int availableTries = 10;

    public ResponseService() {
        gameOn = true;
    }

    public String getResponse(String input) {
        if (input.equals("game on")) {
            gameActive = true;
            return "The game has started, here is the first clue: " + clues.get(clueNumber);
        }
        if (gameActive) {
            if (input.equals(answers.get(clueNumber))) {
                clueNumber++;
                if (clueNumber == 3) {
                    gameOn = false;
                    clueNumber = 0;
                    return clues.get(3);
                }
                return "The next clue is: " + clues.get(clueNumber);
            }
            availableTries--;
            return "Sorry, wrong answer";
        }
        return "The game has not yet started or you managed to crack it, type <<game on>> to start";
    }

    public int getClueNumber() {
        return clueNumber;
    }

    public void setClueNumber(int clueNumber) {
        this.clueNumber = clueNumber;
    }

    public List<String> getClues() {
        return clues;
    }

    public void setClues(List<String> clues) {
        this.clues = clues;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public boolean isGameOn() {
        return gameOn;
    }

    public void setGameOn(boolean gameOn) {
        this.gameOn = gameOn;
    }

    public int getAvailableTries() {
        return availableTries;
    }

    public void setAvailableTries(int availableTries) {
        this.availableTries = availableTries;
    }
}
