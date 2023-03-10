/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames;

import org.emmapap.ChartData;
import pojo.Draw;
import pojo.Prizecategory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @authors Papaioannou Emmanolia 
 *          Lymperis Dimitrios 
 *          Chatziioannou Ioannis
 */
public class StatisticsSelection extends JFrame {

    /**
     * Creates new form StatisticsSelection
     */
    public StatisticsSelection() {
        initComponents();
        setTitle("Επιλογή Στατιστικών Στοιχείων");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //When the window opens where the user selects which statistics he wants to be produced, the dates are updated in the combobox
        //Variables that store the dates for 2000-01-01 through today
        LocalDate startDate = LocalDate.of(2000, 01, 01);
        LocalDate endDate = LocalDate.now();
        long numOfDays = ChronoUnit.DAYS.between(startDate, endDate);
        //For the years 2000 to 2022 make a list of dates
        List<LocalDate> listOfDates1 = Stream.iterate(startDate, date -> date.plusDays(1))
                .limit(numOfDays + 1)
                .collect(Collectors.toList());

        //Add dates to the ToDateCombobox
        for (int i = 0; i < listOfDates1.size(); i++) {
            ToDateComboBox.addItem(listOfDates1.get(i).toString());
        }

        //Add dates to the FromDateCombobox
        for (int i = 0; i < listOfDates1.size(); i++) {
            FromDateComboBox.addItem(listOfDates1.get(i).toString());
        }

        //Initial selection comboboxes NULL
        FromDateComboBox.setSelectedItem(null);
        ToDateComboBox.setSelectedItem(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        numbersRadioButton = new javax.swing.JRadioButton();
        bonusRadioButton = new javax.swing.JRadioButton();
        averageDividentRadioButton = new javax.swing.JRadioButton();
        searchStatisticsButton = new javax.swing.JButton();
        fromDateLabel = new javax.swing.JLabel();
        toDateLabel = new javax.swing.JLabel();
        FromDateComboBox = new javax.swing.JComboBox<>();
        ToDateComboBox = new javax.swing.JComboBox<>();
        searchStatisticsByDateButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setForeground(new java.awt.Color(222, 222, 222)); //Αλλαγή χρώμα γραμμάτων
        jLabel2.setText("Επιλέξτε ποια στατιστικά στοιχεία θέλετε να παραχθούν:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 60, -1, 30));

        numbersRadioButton.setForeground(new java.awt.Color(222, 222, 222)); //Αλλαγή χρώμα γραμμάτων
        numbersRadioButton.setSelected(true);
        numbersRadioButton.setText("Συχνότητα εμφάνισης αριθμών");
        numbersRadioButton.setOpaque(false);
        numbersRadioButton.setRequestFocusEnabled(false);
        numbersRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numbersRadioButtonActionPerformed(evt);
            }
        });
        getContentPane().add(numbersRadioButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 90, 290, -1));

        bonusRadioButton.setForeground(new java.awt.Color(222, 222, 222)); //Αλλαγή χρώμα γραμμάτων
        bonusRadioButton.setText("Συχνότητα εμφάνισης αριθμών joker");
        bonusRadioButton.setOpaque(false);
        bonusRadioButton.setRequestFocusEnabled(false);
        bonusRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bonusRadioButtonActionPerformed(evt);
            }
        });
        getContentPane().add(bonusRadioButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 110, 290, -1));

        averageDividentRadioButton.setForeground(new java.awt.Color(222, 222, 222)); //Αλλαγή χρώμα γραμμάτων
        averageDividentRadioButton.setText("Μέσος όρος κερδών ανά κατηγορία");
        averageDividentRadioButton.setOpaque(false);
        averageDividentRadioButton.setRequestFocusEnabled(false);
        averageDividentRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                averageDividentRadioButtonActionPerformed(evt);
            }
        });
        getContentPane().add(averageDividentRadioButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 130, 290, -1));

        searchStatisticsButton.setBackground(new java.awt.Color(49, 144, 226));
        searchStatisticsButton.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        searchStatisticsButton.setForeground(new java.awt.Color(0, 0, 0)); //Αλλαγή χρώμα γραμμάτων
        searchStatisticsButton.setText("Προβολή στατιστικών");
        searchStatisticsButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        searchStatisticsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchStatisticsButtonActionPerformed(evt);
            }
        });
        getContentPane().add(searchStatisticsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 160, -1, -1));

        fromDateLabel.setForeground(new java.awt.Color(222, 222, 222)); //Αλλαγή χρώμα γραμμάτων
        fromDateLabel.setText("Από:");
        getContentPane().add(fromDateLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 220, -1, 20));

        toDateLabel.setForeground(new java.awt.Color(222, 222, 222)); //Αλλαγή χρώμα γραμμάτων
        toDateLabel.setText("Εώς:");
        getContentPane().add(toDateLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 250, -1, 20));

        FromDateComboBox.setBackground(new java.awt.Color(222, 222, 222));
        FromDateComboBox.setEditable(false);
        FromDateComboBox.setRequestFocusEnabled(false);
        FromDateComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FromDateComboBoxActionPerformed(evt);
            }
        });
        getContentPane().add(FromDateComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 220, 160, -1));

        ToDateComboBox.setBackground(new java.awt.Color(222, 222, 222));
        ToDateComboBox.setEditable(false);
        ToDateComboBox.setRequestFocusEnabled(false);
        ToDateComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ToDateComboBoxActionPerformed(evt);
            }
        });
        getContentPane().add(ToDateComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 250, 160, -1));

        searchStatisticsByDateButton.setBackground(new java.awt.Color(49, 144, 226));
        searchStatisticsByDateButton.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        searchStatisticsByDateButton.setText("Προβολή στατιστικών βάση εύρους ημερομηνιών");
        searchStatisticsByDateButton.setForeground(new java.awt.Color(0, 0, 0)); //Αλλαγή χρώμα γραμμάτων
        searchStatisticsByDateButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        searchStatisticsByDateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchStatisticsByDateButtonActionPerformed(evt);
            }
        });
        getContentPane().add(searchStatisticsByDateButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 290, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tzoker_image2.jpg"))); // NOI18N
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-6, -5, 610, 370));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void FromDateComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FromDateComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FromDateComboBoxActionPerformed

    private void ToDateComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ToDateComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ToDateComboBoxActionPerformed

    private void searchStatisticsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchStatisticsButtonActionPerformed
        //Process of calculating statistical data and displaying it in a chart

        //Start process of fetching data from database
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JokerGameStatsPU");
        EntityManager em = emf.createEntityManager();

        //Begin attempting to transact with the database
        try {
            //Start transaction
            em.getTransaction().begin();

            //Create appropriate queries to the database to retrieve data
            Query findDraws = em.createNamedQuery("Draw.findAll", Draw.class);
            Query findPrizeCategories = em.createNamedQuery("Prizecategory.findAll", Prizecategory.class);
            List<Draw> drawsPj = findDraws.getResultList();
            List<Prizecategory> prizeCategoryPj = findPrizeCategories.getResultList();

            if (!drawsPj.isEmpty()) {
                //Create arrays to count how many times each number appeared
                int[] countNumbers = new int[45];
                int[] countBonusNumbers = new int[20];
                //Initialize values in arrays
                for (int i = 0; i < 45; i++) {
                    countNumbers[i] = 0;
                }
                for (int i = 0; i < 20; i++) {
                    countBonusNumbers[i] = 0;
                }
                //Count how many times each number appeared and map to the appropriate array
                for (Draw draw : drawsPj) {
                    countNumbers[draw.getWinningnumber1() - 1]++;
                    countNumbers[draw.getWinningnumber2() - 1]++;
                    countNumbers[draw.getWinningnumber3() - 1]++;
                    countNumbers[draw.getWinningnumber4() - 1]++;
                    countNumbers[draw.getWinningnumber5() - 1]++;
                    countBonusNumbers[draw.getBonus() - 1]++;
                }

                //Create table to calculate average earnings per hit
                double[] dividentAvg = new double[8];
                //Initialize values in dividendAvg array
                for (int i = 0; i < 8; i++) {
                    dividentAvg[i] = 0.0;
                }

                //We pass all the earnings per success into the array and sum them by success category
                int count = 0;
                for (Prizecategory prizeCategory : prizeCategoryPj) {
                    dividentAvg[prizeCategory.getPrizecategoryPK().getId() - 1] += prizeCategory.getDivident();
                    count++;
                }

                //End transaction with database
                em.getTransaction().commit();

                //We create 3 lists of type ChartData to store the data we will show on the chart
                List<ChartData> dividentAvgChartData = new ArrayList<>(); //Average earnings per category
                List<ChartData> maxNumberChartData = new ArrayList<>();//5 most frequently occurring joker numbers and their occurrences
                List<ChartData> maxBonusNumberChartData = new ArrayList<>();//5 most frequently occurring numbers and their occurrences

                //Calculate 5 most frequently occurring numbers 1-45
                int max = 0;
                int maxPos = 0;
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 45; j++) {
                        if (countNumbers[j] >= max) {
                            max = countNumbers[j];
                            maxPos = j;
                        }
                    }
                    ChartData data = new ChartData();
                    data.x = maxPos + 1;
                    data.y = max;
                    maxNumberChartData.add(data);
                    countNumbers[maxPos] = 0;
                    max = 0;
                }

                //Compute 5 most frequently occurring joker numbers
                max = 0;
                maxPos = 0;
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 20; j++) {
                        if (countBonusNumbers[j] >= max) {
                            max = countBonusNumbers[j];
                            maxPos = j;
                        }
                    }
                    ChartData bonusData = new ChartData();
                    bonusData.x = maxPos + 1;
                    bonusData.y = max;
                    maxBonusNumberChartData.add(bonusData);
                    countBonusNumbers[maxPos] = 0;
                    max = 0;
                }

                //Calculate average earnings per category
                count = count / 8;//Calculation of number of draws

                for (int i = 0; i < 8; i++) {
                    ChartData dividentAvgData = new ChartData();
                    dividentAvgData.x = i;
                    dividentAvgData.y = (double) dividentAvg[i] / count;
                    dividentAvgChartData.add(dividentAvgData);

                }

                //Depending on which statistics the user has chosen, the corresponding diagram is also displayed
                if (numbersRadioButton.isSelected()) {
                    StatisticsGraphicForm form = new StatisticsGraphicForm();
                    form.plotChart("5 πιο συχνά εμφανιζόμενοι αριθμοί", "Αριθμός", "Εμφανίσεις", maxNumberChartData);
                } else if (bonusRadioButton.isSelected()) {
                    StatisticsGraphicForm form = new StatisticsGraphicForm();
                    form.plotChart("5 πιο συχνά εμφανιζόμενοι αριθμοί ΤΖΟΚΕΡ", "Αριθμός ΤΖΟΚΕΡ", "Εμφανίσεις", maxBonusNumberChartData);
                } else if (averageDividentRadioButton.isSelected()) {
                    StatisticsGraphicForm form = new StatisticsGraphicForm();
                    form.plotAvgChart("Μ.Ο. κερδών ανά κατηγορία", "Κατηγορία επιτυχίας", "Μέσος όρος κερδών", dividentAvgChartData);
                }
            } else {
                em.getTransaction().commit();
                JOptionPane.showMessageDialog(null, "Η βάση δεδομένων είναι άδεια. ", "Ενημέρωση", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Η διαδικασία δεν ήταν επιτυχής. Προσπαθήστε ξανά. ", "Σφάλμα", JOptionPane.INFORMATION_MESSAGE);
            e.printStackTrace();
        }

        em.close();
        emf.close();
    }//GEN-LAST:event_searchStatisticsButtonActionPerformed

    private void searchStatisticsByDateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchStatisticsByDateButtonActionPerformed
        //Process to fetch data from date range database

        //If the user does not enter dates and clicks view data, display an informational message
        if ((FromDateComboBox.getSelectedItem() == null) || (ToDateComboBox.getSelectedItem() == null)) {
            //Informative message
            JOptionPane.showMessageDialog(null, "Πρέπει να εισάγετε εύρος ημερομηνιών.", "Ενημέρωση", JOptionPane.INFORMATION_MESSAGE);
        } else {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("JokerGameStatsPU");
            EntityManager em = emf.createEntityManager();

            //Begin attempting to transact with the database
            try {
                //ΈStart the transaction
                em.getTransaction().begin();

                //Create appropriate database query to retrieve data in date range
                Query findDraws = em.createNativeQuery("SELECT * FROM DRAW WHERE DATE BETWEEN '" + FromDateComboBox.getSelectedItem().toString() + "' AND '"
                        + ToDateComboBox.getSelectedItem().toString() + "'", Draw.class);
                //Get the database results into a list
                List<Draw> drawsPj = findDraws.getResultList();
                //If the database contains data for the requested dates
                if (!drawsPj.isEmpty()) {
                    //Create arrays to count how many times each number appeared
                    int[] countNumbers = new int[45];
                    int[] countBonusNumbers = new int[20];
                    //Initialize values in arrays
                    for (int i = 0; i < 45; i++) {
                        countNumbers[i] = 0;
                    }
                    for (int i = 0; i < 20; i++) {
                        countBonusNumbers[i] = 0;
                    }
                    //Count how many times each number appeared and map to the appropriate array
                    for (Draw draw : drawsPj) {
                        countNumbers[draw.getWinningnumber1() - 1]++;
                        countNumbers[draw.getWinningnumber2() - 1]++;
                        countNumbers[draw.getWinningnumber3() - 1]++;
                        countNumbers[draw.getWinningnumber4() - 1]++;
                        countNumbers[draw.getWinningnumber5() - 1]++;
                        countBonusNumbers[draw.getBonus() - 1]++;
                    }

                    //Create table to calculate average earnings per hit
                    double[] dividentAvg = new double[8];
                    //Initialize values in dividendAvg array
                    for (int i = 0; i < 8; i++) {
                        dividentAvg[i] = 0.0;
                    }

                    //We pass all the earnings per success into the array and sum them by success category
                    int count = 0;

                    for (Draw draw : drawsPj) {
                        List<Prizecategory> prizeCategory = draw.getPrizecategoryList();
                        for (Prizecategory prizeCategories : prizeCategory) {
                            dividentAvg[prizeCategories.getPrizecategoryPK().getId() - 1] += prizeCategories.getDivident();
                            count++;
                        }
                    }

                    em.getTransaction().commit();

                    //We create 3 lists of type ChartData to store the data we will show on the chart
                    List<ChartData> dividentAvgChartData = new ArrayList<>(); //Average earnings per category
                    List<ChartData> maxNumberChartData = new ArrayList<>();//5 most frequently occurring joker numbers and their occurrences
                    List<ChartData> maxBonusNumberChartData = new ArrayList<>();//5 most frequently occurring numbers and their occurrences

                    //Calculate 5 most frequently occurring numbers 1-45 and insert into appropriate list of type ChartData
                    int max = 0;
                    int maxPos = 0;
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 45; j++) {
                            if (countNumbers[j] >= max) {
                                max = countNumbers[j];
                                maxPos = j;
                            }
                        }
                        ChartData data = new ChartData();
                        data.x = maxPos + 1;
                        data.y = max;
                        maxNumberChartData.add(data);
                        countNumbers[maxPos] = 0;
                        max = 0;
                    }

                    //Calculate 5 most frequently occurring wildcard numbers and write to appropriate list of type ChartData
                    max = 0;
                    maxPos = 0;
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 20; j++) {
                            if (countBonusNumbers[j] >= max) {
                                max = countBonusNumbers[j];
                                maxPos = j;
                            }
                        }
                        ChartData bonusData = new ChartData();
                        bonusData.x = maxPos + 1;
                        bonusData.y = max;
                        maxBonusNumberChartData.add(bonusData);
                        countBonusNumbers[maxPos] = 0;
                        max = 0;
                    }

                    //Calculate average earnings per category
                    count = count / 8; //Calculate number of draws
                    for (int i = 0; i < 8; i++) {
                        ChartData dividentAvgData = new ChartData();
                        dividentAvgData.x = i;
                        dividentAvgData.y = (double) dividentAvg[i] / count;
                        dividentAvgChartData.add(dividentAvgData);

                    }

                    //Depending on which statistics the user has chosen, the corresponding diagram is also displayed
                    if (numbersRadioButton.isSelected()) {
                        StatisticsGraphicForm form = new StatisticsGraphicForm();
                        form.plotChart("5 πιο συχνά εμφανιζόμενοι αριθμοί", "Αριθμός", "Εμφανίσεις", maxNumberChartData);
                    } else if (bonusRadioButton.isSelected()) {
                        StatisticsGraphicForm form = new StatisticsGraphicForm();
                        form.plotChart("5 πιο συχνά εμφανιζόμενοι αριθμοί ΤΖΟΚΕΡ", "Αριθμός ΤΖΟΚΕΡ", "Εμφανίσεις", maxBonusNumberChartData);
                    } else if (averageDividentRadioButton.isSelected()) {
                        StatisticsGraphicForm form = new StatisticsGraphicForm();
                        form.plotAvgChart("Μ.Ο. κερδών ανά κατηγορία", "Κατηγορία επιτυχίας", "Μέσος όρος κερδών", dividentAvgChartData);
                    }
                    //If there is no data stored for the requested date range display an informational message
                } else {
                    //Informative message
                    JOptionPane.showMessageDialog(null, "Δεν υπάρχουν αποθηκευμένα δεδομένα για αυτές τις ημερομηνίες. ", "Ενημέρωση", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                //Informative message
                JOptionPane.showMessageDialog(null, "Η σύνδεση με τη βάση δεδομένων δεν ήταν επιτυχής. Προσπαθήστε ξανά. ", "Σφάλμα", JOptionPane.INFORMATION_MESSAGE);
            }

            em.close();
            emf.close();
        }
    }//GEN-LAST:event_searchStatisticsByDateButtonActionPerformed

    private void numbersRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numbersRadioButtonActionPerformed
        //User can select only one case to be displayed in graphic form
        bonusRadioButton.setSelected(false);
        averageDividentRadioButton.setSelected(false);
        numbersRadioButton.setSelected(true);
    }//GEN-LAST:event_numbersRadioButtonActionPerformed

    private void bonusRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bonusRadioButtonActionPerformed
        //User can select only one case to be displayed in graphic form
        bonusRadioButton.setSelected(true);
        averageDividentRadioButton.setSelected(false);
        numbersRadioButton.setSelected(false);
    }//GEN-LAST:event_bonusRadioButtonActionPerformed

    private void averageDividentRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_averageDividentRadioButtonActionPerformed
        //User can select only one case to be displayed in graphic form
        bonusRadioButton.setSelected(false);
        averageDividentRadioButton.setSelected(true);
        numbersRadioButton.setSelected(false);
    }//GEN-LAST:event_averageDividentRadioButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StatisticsSelection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StatisticsSelection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StatisticsSelection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StatisticsSelection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StatisticsSelection().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> FromDateComboBox;
    private javax.swing.JComboBox<String> ToDateComboBox;
    private javax.swing.JRadioButton averageDividentRadioButton;
    private javax.swing.JRadioButton bonusRadioButton;
    private javax.swing.JLabel fromDateLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JRadioButton numbersRadioButton;
    private javax.swing.JButton searchStatisticsButton;
    private javax.swing.JButton searchStatisticsByDateButton;
    private javax.swing.JLabel toDateLabel;
    // End of variables declaration//GEN-END:variables
}
