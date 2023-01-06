package org.emmapap;

public class ChartData {
    
    public double x;
    public double y;
    
    public ChartData(double x, double y){
        this.x = x;
        this.y = y;
    }
    
    public ChartData(){
        this.x=0;
        this.y=0;
    }
  
    public void printChartData(){
        System.out.println("Number="+(int)x+"   Number occurrences="+(int)y);
    }
}
