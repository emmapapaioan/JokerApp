package org.emmapap;

import java.util.ArrayList;
import java.util.Date;

public class Draws {

    private int id;
    private Date date;
    private long drawTime;
    public ArrayList<PrizeCategory> prizeCategories;
    private int winningNumber1;
    private int winningNumber2;
    private int winningNumber3;
    private int winningNumber4;
    private int winningNumber5;
    private int bonus;
    private int[] occurrences;

    public Draws() {
        this.prizeCategories = new ArrayList<>();
    }

    public String getWinningNumbers() {
        String winningNumbers = "" + this.winningNumber1 + ", " + this.winningNumber2 + ", " + this.winningNumber3 + ", " + this.winningNumber4 + ", " + this.winningNumber5 + "";
        return winningNumbers;
    }

    public Draws(int id, int winningNumber1, int winningNumber2, int winningNumber3, int winningNumber4, int winningNumber5, int bonus) {
        this.id = id;
        this.date = date;
        this.winningNumber1 = winningNumber1;
        this.winningNumber2 = winningNumber2;
        this.winningNumber3 = winningNumber3;
        this.winningNumber4 = winningNumber4;
        this.winningNumber5 = winningNumber5;
        this.bonus = bonus;
        this.prizeCategories = new ArrayList<>();
    }

    public Draws(int drawId) {
        this.id = drawId;
        this.prizeCategories = new ArrayList<>();
    }

    public Draws(int id, long drawTime) {
        this.id = id;
        this.date = convertDate(drawTime);
        this.prizeCategories = new ArrayList<>();
    }

    public Draws(int id, long drawTime, ArrayList<PrizeCategory> prizeCategories) {
        this.id = id;
        this.date = convertDate(drawTime);
        this.prizeCategories = prizeCategories;

    }

    public Draws(int id, long drawTime, ArrayList<PrizeCategory> prizeCategories, int winningNumber1,
            int winningNumber2, int winningNumber3, int winningNumber4, int winningNumber5, int bonus) {
        this.id = id;
        this.date = convertDate(drawTime);
        this.prizeCategories = prizeCategories;
        this.winningNumber1 = winningNumber1;
        this.winningNumber2 = winningNumber2;
        this.winningNumber3 = winningNumber3;
        this.winningNumber4 = winningNumber4;
        this.winningNumber5 = winningNumber5;
        this.bonus = bonus;
        this.prizeCategories = new ArrayList<>();
    }

    public Draws(int id, long drawTime, int winningNumber1,
            int winningNumber2, int winningNumber3, int winningNumber4, int winningNumber5, int bonus) {
        this.id = id;
        this.date = convertDate(drawTime);
        this.winningNumber1 = winningNumber1;
        this.winningNumber2 = winningNumber2;
        this.winningNumber3 = winningNumber3;
        this.winningNumber4 = winningNumber4;
        this.winningNumber5 = winningNumber5;
        this.bonus = bonus;
        this.prizeCategories = new ArrayList<>();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDrawTime(long drawTime) {
        this.drawTime = drawTime;
    }

    public void setWinningNumber1(int winningNumber1) {
        this.winningNumber1 = winningNumber1;
    }

    public void setWinningNumber2(int winningNumber2) {
        this.winningNumber2 = winningNumber2;
    }

    public void setWinningNumber3(int winningNumber3) {
        this.winningNumber3 = winningNumber3;
    }

    public void setWinningNumber4(int winningNumber4) {
        this.winningNumber4 = winningNumber4;
    }

    public void setWinningNumber5(int winningNumber5) {
        this.winningNumber5 = winningNumber5;
    }

    public void setBonusNumber(int bonusNumber) {
        this.bonus = bonusNumber;
    }

    public long getDrawTime() {
        return drawTime;
    }

    public int getWinningNumber1() {
        return winningNumber1;
    }

    public int getWinningNumber2() {
        return winningNumber2;
    }

    public int getWinningNumber3() {
        return winningNumber3;
    }

    public int getWinningNumber4() {
        return winningNumber4;
    }

    public int getWinningNumber5() {
        return winningNumber5;
    }

    public int getBonusNumber() {
        return bonus;
    }

    public int getId() {
        return id;
    }

    public Date getDrawDate() {
        return date;
    }

    private Date convertDate(long drawTime) {
        Date d = new Date(drawTime);
        return d;
    }

    public void addCategory(PrizeCategory category) {
        prizeCategories.add(category);
    }

    public void printDraw() {
        System.out.println("=====================");
        System.out.println("drawId=" + id);
        System.out.println("date=" + getDrawDate());
        System.out.println("=====================");

    }
}
