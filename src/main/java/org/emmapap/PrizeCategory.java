package org.emmapap;

public class PrizeCategory {

    private int prizeCategoryId;
    private String prizeCategoryName;
    private int drawId;
    private int winners;
    private double divident;

    public PrizeCategory() {

    }

    public PrizeCategory(int prizeCategoryId, int drawId, int winners, double divident) {
        this.prizeCategoryId = prizeCategoryId;
        this.drawId = drawId;
        this.winners = winners;
        this.divident = divident;
        this.setPrizeCategoryName();
    }

    public PrizeCategory(int prizeCategoryId, int drawId, double divident) {
        this.prizeCategoryId = prizeCategoryId;
        this.drawId = drawId;
        this.divident = divident;
    }

    public PrizeCategory(int drawId, int prizeCategoryId) {
        this.drawId = drawId;
        this.prizeCategoryId = prizeCategoryId;
    }

    public PrizeCategory(double divident) {
        this.divident = divident;
    }

    public void setPrizeCategoryName() {
        if (prizeCategoryId == 1) {
            prizeCategoryName = "5+1";
        }
        if (prizeCategoryId == 2) {
            prizeCategoryName = "5";
        }
        if (prizeCategoryId == 3) {
            prizeCategoryName = "4+1";
        }
        if (prizeCategoryId == 4) {
            prizeCategoryName = "4";
        }
        if (prizeCategoryId == 5) {
            prizeCategoryName = "3+1";
        }
        if (prizeCategoryId == 6) {
            prizeCategoryName = "3";
        }
        if (prizeCategoryId == 7) {
            prizeCategoryName = "2+1";
        }
        if (prizeCategoryId == 8) {
            prizeCategoryName = "1+1";
        }
    }

    public int getPrizeCategoryId() {
        return prizeCategoryId;
    }

    public String getPrizeCategoryName() {
        return prizeCategoryName;
    }

    public int getWinners() {
        return winners;
    }

    public double getDivident() {
        return divident;
    }

    public int getDrawId() {
        return drawId;
    }

    public String getFormatedNumber(double x) {
        String formatedNumber = String.format("%.02f", x);
        return formatedNumber;
    }

    public void printPrizeCategory() {
        System.out.println("id=" + prizeCategoryId);
        System.out.println("winners=" + winners);
        System.out.println("divident=" + getFormatedNumber(divident));
    }
}
