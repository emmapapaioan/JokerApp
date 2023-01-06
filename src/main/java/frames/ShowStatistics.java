/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames;

import com.google.gson.*;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.emmapap.DateValidator;
import org.emmapap.DateValidatorUsingDateFormat;
import org.emmapap.Draws;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pojo.Draw;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparingInt;

/**
 *
 * @authors Papaioannou Emmanolia 
 *          Lymperis Dimitrios 
 *          Chatziioannou Ioannis
 */
public class ShowStatistics extends javax.swing.JFrame {

    /**
     * Creates new form ShowStatistics
     */
    //Μέθοδος που παραμετροποιεί τα 3 πρώτα κελιά ενός πίνακα pdf
    private void addTableHeader(PdfPTable table) throws IOException, DocumentException {
        //Δημιουργία επιθυμητού φόντου που εμφανίζει και ελληνικούς χαρακτήρες
        BaseFont baseFont = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        baseFont.setSubset(true);
        //Ονόματα για το πρώτο κελί κάθε στήλης του πίνακα και επιθυμητό χρώμα και μέγεθος γραμματοσειράς
        Stream.of("Αριθμός", "Εμφανίσεις", "Καθυστερήσεις")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(1);
                    header.setPhrase(new Phrase(Element.ALIGN_LEFT, columnTitle, new Font(baseFont, 12)));
                    table.addCell(header);
                    table.setSpacingBefore(4);
                });
    }

    //Μέθοδος που γεμίζει οριζόντια τα κελιά του πίνακα στο pdf αρχείο (Πίνακας που δείχνει στατιστικά για όλους τους αριθμούς)
    private void addNumberRows(PdfPTable table, JTable numbersTable) throws DocumentException, IOException {
        for (int i = 0; i < 45; i++) {
            for (int j = 0; j < 3; j++) {
                table.addCell(numbersTable.getValueAt(i, j).toString());
            }
        }
    }

    //Μέθοδος που γεμίζει οριζόντια τα κελιά του πίνακα στο pdf αρχείο (Πίνακας που δείχνει στατιστικά για τους αριθμούς τζόκερ)
    private void addBonusRows(PdfPTable table, JTable numbersTable) throws DocumentException, IOException {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 3; j++) {
                table.addCell(numbersTable.getValueAt(i, j).toString());
            }
        }
    }

    public ShowStatistics() {
        initComponents();
        setTitle("JokerGame-Stats");
        addWindowListener(new WindowAdapter() {
            //Eνημερωτικό μήνυμα όταν ο χρήστης κλείνει την εφαρμογή
            public void windowClosing(WindowEvent evt) {
                int result = JOptionPane.showConfirmDialog(null,
                        "Η εφαρμογή θα τερματιστεί. Είστε σίγουροι;", "Τερματισμός εφαρμογής",
                        JOptionPane.YES_NO_OPTION);

                if (JOptionPane.YES_OPTION == result) {
                    //Kαταστροφή παραθύρου
                    dispose();
                    //Τερματισμός
                    System.exit(0);
                }
            }
        });

        //Όταν ανοίγει η οθόνη ShowStatistics εμφανίζονται 
        //αυτόματα τα στατιστικά κληρώσεων joker από 2000-01-01 έως την πιο πρόσφατη κλήρωση
        FromDateComboBox.setMaximumRowCount(6); //Max γραμμές combobox 6
        ToDateComboBox.setMaximumRowCount(6); //Max γραμμές combobox 6

        //Μεταβλητές που αποθηκεύουν τις ημερομηνίες για 2000-01-01 και σήμερα
        LocalDate startDate = LocalDate.of(2000, 01, 01);
        LocalDate endDate = LocalDate.now();
        long numOfDays = ChronoUnit.DAYS.between(startDate, endDate);
        //Για τα έτη 2000 έως 2022 φτιάξε λίστα με ημερομηνίες 
        List<LocalDate> listOfDates1 = Stream.iterate(startDate, date -> date.plusDays(1))
                .limit(numOfDays + 1)
                .collect(Collectors.toList());

        //Πρόσθεσε στo ToDateCombobox ημερομηνίες
        for (int i = 0; i < listOfDates1.size(); i++) {
            ToDateComboBox.addItem(listOfDates1.get(i).toString());
        }

        //Πρόσθεσε στo FromDateCombobox  ημερομηνίες
        for (int i = 0; i < listOfDates1.size(); i++) {
            FromDateComboBox.addItem(listOfDates1.get(i).toString());
        }

        //Eκκίνηση προσπάθειας κλήσης url που προβάλλει στατιστικά για τις κληρώσεις τζόκερ από την πρώτη κλήρωση έως την πιο πρόσφατη 
        //Διαδικασία για κλήση url
        //Περνάμε το url που θα καλέσουμε σε ενα string
        String urlToCall = "https://api.opap.gr/games/v1.0/5104/statistics";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(urlToCall).build();

        //Καλούμε το κατάλληλο url 
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseString = response.body().string();

                //Δημιουργούμε ένα αντικείμενο GsonBuilder
                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                Gson gson = builder.create();

                //Παίρνουμε τα επιθυμητά αποτελέσματα σε JsonArray 
                JsonObject json = gson.fromJson(responseString, JsonObject.class);
                JsonArray numbers = json.getAsJsonArray("numbers");
                JsonArray bonusNumbers = json.getAsJsonArray("bonusNumbers");

                //Φτιάχνουμε βοηθητικές λίστες για να αντλήσουμε από το json δεδομένα
                ArrayList<String> occurrences = new ArrayList<>();
                ArrayList<String> delays = new ArrayList<>();
                ArrayList<String> numbersList = new ArrayList<>();
                ArrayList<String> bonusList = new ArrayList<>();

                //Παραμετροποιούμε τον πίνακα που θα εμφανίζει τα στατιστικά δεδομένα
                DefaultTableModel model = (DefaultTableModel) numbersTable.getModel();
                String[] columnNames = {"Αριθμός", "Εμφανίσεις", "Καθυστερήσεις"};
                model.setColumnIdentifiers(columnNames);
                int i = 0;

                //Άντληση στατιστικών δεδομένων από json και καθορισμός τιμών στον πίνακα στατιστικών δεδομένων
                for (JsonElement number : numbers) {
                    JsonObject numberStatistics = number.getAsJsonObject();
                    occurrences.add(numberStatistics.get("occurrences").getAsString());
                    delays.add(numberStatistics.get("delays").getAsString());
                    numbersList.add(numberStatistics.get("number").getAsString());

                    Object[] rowValues = {numbersList.get(i), occurrences.get(i), delays.get(i)};
                    model.addRow(rowValues);
                    i++;
                }
                //Eμφάνισε τον πίνακα στο jframe
                numbersPane.getViewport().add(numbersTable);

                //Καθαρίζουμε τα ArrayLists occurrences και delays για να περάσουμε τα δεδομένα για τους bonus αριθμούς
                occurrences.clear();
                delays.clear();
                //Παραμετροποιούμε τον πίνακα που θα εμφανίζει τα στατιστικά δεδομένα
                DefaultTableModel model1 = (DefaultTableModel) bonusTable.getModel();
                model1.setColumnIdentifiers(columnNames);
                i = 0;

                //Άντληση στατιστικών δεδομένων από json και καθορισμός τιμών στον πίνακα στατιστικών δεδομένων
                for (JsonElement bonus : bonusNumbers) {
                    JsonObject bonusStatistics = bonus.getAsJsonObject();
                    occurrences.add(bonusStatistics.get("occurrences").getAsString());
                    delays.add(bonusStatistics.get("delays").getAsString());
                    bonusList.add(bonusStatistics.get("number").getAsString());

                    Object[] rowValues = {bonusList.get(i), occurrences.get(i), delays.get(i)};
                    model1.addRow(rowValues);
                    i++;
                }
                //Eμφάνισε τον πίνακα στο jframe
                bonusPane.getViewport().add(bonusTable);

                //Αντλούμε από το json το εύρος ημερομηνιών για το οποίο ισχύουν τα στατιστικά δεδομένα
                JsonObject header = json.getAsJsonObject("header");

                Date dateTo = new Date(header.get("dateTo").getAsLong() * 1000);
                Date dateFrom = new Date(header.get("dateFrom").getAsLong() * 1000);
                String pattern = "yyyy-MM-dd";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                //Βάζουμε τις ημερομηνίες για τις οποίες ισχύουν τα στατιστικά δεδομένα στο label του αντίστοιχου πίνακα
                apiOrBdLabel.setForeground(new java.awt.Color(222, 222, 222));
                apiOrBdLabel.setText("[API]");
                statisticsDatesLabel1.setForeground(new java.awt.Color(222, 222, 222));
                statisticsDatesLabel2.setForeground(new java.awt.Color(222, 222, 222));
                statisticsDatesLabel1.setText("Στατιστικά δεδομένα κληρώσεων από");
                statisticsDatesLabel2.setText("" + simpleDateFormat.format(dateFrom) + " έως " + simpleDateFormat.format(dateTo) + ".");

                //Βάζουμε τα combobox να δείχνουν επιλεγμένες ημερομηνίες το εύρος από το οποίο αντλήθηκαν αυτά τα στατιστικά
                FromDateComboBox.setSelectedItem(simpleDateFormat.format(dateFrom));
                ToDateComboBox.setSelectedItem(simpleDateFormat.format(dateTo));
            }
            //Αποτυχία κλήσης URL
        } catch (IOException e) {
            //Ενημερωτικό μήνυμα
            JOptionPane.showMessageDialog(null, "Πρόβλημα κλήσης URL. Προσπαθήστε ξανά.", "Σφάλμα", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        returnHomeButton = new javax.swing.JButton();
        numbersLabel = new javax.swing.JLabel();
        showStatisticsButton = new javax.swing.JButton();
        saveStatisticsToPdfButton = new javax.swing.JButton();
        numbersPane = new javax.swing.JScrollPane();
        numbersTable = new JTable();
        bonusLabel = new javax.swing.JLabel();
        bonusPane = new javax.swing.JScrollPane();
        bonusTable = new JTable();
        showGraphicStatisticsDataButton = new javax.swing.JButton();
        fromDateLabel = new javax.swing.JLabel();
        FromDateComboBox = new javax.swing.JComboBox<>();
        toDateLabel = new javax.swing.JLabel();
        ToDateComboBox = new javax.swing.JComboBox<>();
        statisticsDatesLabel1 = new javax.swing.JLabel();
        statisticsDatesLabel2 = new javax.swing.JLabel();
        showStatisticsProgressBar = new javax.swing.JProgressBar();
        showStatisticsDBButton = new javax.swing.JButton();
        apiOrBdLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        backgroundLayer = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(785, 680));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        returnHomeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/home_image1_1.png"))); // NOI18N
        returnHomeButton.setBorder(null);
        returnHomeButton.setBorderPainted(false);
        returnHomeButton.setContentAreaFilled(false);
        returnHomeButton.setFocusPainted(false);
        returnHomeButton.setRolloverEnabled(true);
        returnHomeButton.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/home_image1click.png"))); // NOI18N
        returnHomeButton.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/home_image1click.png"))); // NOI18N
        returnHomeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnHomeButtonActionPerformed(evt);
            }
        });
        getContentPane().add(returnHomeButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 60, 60));

        numbersLabel.setForeground(new java.awt.Color(222, 222, 222)); //Αλλαγή χρώμα γραμμάτων
        numbersLabel.setText("Αριθμοί");
        getContentPane().add(numbersLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 210, -1, -1));

        showStatisticsButton.setBackground(new java.awt.Color(49, 144, 226));
        showStatisticsButton.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        showStatisticsButton.setForeground(new java.awt.Color(0, 0, 0));
        showStatisticsButton.setText("Προβολή στατιστικών δεδομένων [API]");
        showStatisticsButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        showStatisticsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showStatisticsButtonActionPerformed(evt);
            }
        });
        getContentPane().add(showStatisticsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, -1, 30));

        saveStatisticsToPdfButton.setBackground(new java.awt.Color(49, 144, 226));
        saveStatisticsToPdfButton.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        saveStatisticsToPdfButton.setForeground(new java.awt.Color(0, 0, 0));
        saveStatisticsToPdfButton.setText(" Εκτύπωση πινάκων σε αρχείο pdf");
        saveStatisticsToPdfButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        saveStatisticsToPdfButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveStatisticsToPdfButtonActionPerformed(evt);
            }
        });
        getContentPane().add(saveStatisticsToPdfButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 540, -1, 30));

        numbersTable.setModel(new DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        numbersPane.setViewportView(numbersTable);

        getContentPane().add(numbersPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 230, 320, 120));

        bonusLabel.setForeground(new java.awt.Color(222, 222, 222)); //Αλλαγή χρώμα γραμμάτων
        bonusLabel.setText("Τζόκερ αριθμοί");
        getContentPane().add(bonusLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 360, -1, -1));

        bonusTable.setModel(new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Αριθμοί", "Εμφανίσεις", "Καθυστερήσεις"
            }
        ));
        bonusPane.setViewportView(bonusTable);

        getContentPane().add(bonusPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 380, 320, 120));

        showGraphicStatisticsDataButton.setBackground(new java.awt.Color(49, 144, 226));
        showGraphicStatisticsDataButton.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        showGraphicStatisticsDataButton.setForeground(new java.awt.Color(0, 0, 0));
        showGraphicStatisticsDataButton.setText(" Προβολή στατιστικών στοιχείων κληρώσεων σε γραφική μορφή [ΒΑΣΗ ΔΕΔΟΜΕΝΩΝ] ");
        showGraphicStatisticsDataButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        showGraphicStatisticsDataButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showGraphicStatisticsDataButtonActionPerformed(evt);
            }
        });
        getContentPane().add(showGraphicStatisticsDataButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 580, -1, 30));

        fromDateLabel.setForeground(new java.awt.Color(222, 222, 222)); //Αλλαγή χρώμα γραμμάτων
        fromDateLabel.setText("Από:");
        getContentPane().add(fromDateLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, -1, 20));

        FromDateComboBox.setBackground(new java.awt.Color(222, 222, 222));
        FromDateComboBox.setEditable(true);
        FromDateComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FromDateComboBoxActionPerformed(evt);
            }
        });
        getContentPane().add(FromDateComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 160, -1));

        toDateLabel.setForeground(new java.awt.Color(222, 222, 222)); //Αλλαγή χρώμα γραμμάτων
        toDateLabel.setText("Εώς:");
        getContentPane().add(toDateLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, -1, 20));

        ToDateComboBox.setBackground(new java.awt.Color(222, 222, 222));
        ToDateComboBox.setEditable(true);
        ToDateComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ToDateComboBoxActionPerformed(evt);
            }
        });
        getContentPane().add(ToDateComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 160, -1));
        getContentPane().add(statisticsDatesLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 150, 310, -1));
        getContentPane().add(statisticsDatesLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 170, 310, -1));

        showStatisticsProgressBar.setStringPainted(true);
        showStatisticsProgressBar.setEnabled(true);
        getContentPane().add(showStatisticsProgressBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 190, 220, -1));

        showStatisticsDBButton.setBackground(new java.awt.Color(49, 144, 226));
        showStatisticsDBButton.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        showStatisticsDBButton.setForeground(new java.awt.Color(0, 0, 0));
        showStatisticsDBButton.setText("Προβολή στατιστικών δεδομένων [ΒΑΣΗ ΔΕΔΟΜΕΝΩΝ]");
        showStatisticsDBButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        showStatisticsDBButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showStatisticsDBButtonActionPerformed(evt);
            }
        });
        getContentPane().add(showStatisticsDBButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, -1, 30));
        getContentPane().add(apiOrBdLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 130, -1, -1));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(222, 222, 222)); //Αλλαγή χρώμα γραμμάτων
        jLabel1.setText("Προβολή στατιστικών δεδομένων");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, -1, 30));

        backgroundLayer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tzoker_image2.jpg"))); // NOI18N
        backgroundLayer.setText("jLabel1");
        backgroundLayer.setFocusCycleRoot(true);
        backgroundLayer.setMaximumSize(new java.awt.Dimension(1000, 720));
        backgroundLayer.setMinimumSize(new java.awt.Dimension(1000, 720));
        backgroundLayer.setPreferredSize(new java.awt.Dimension(570, 429));
        getContentPane().add(backgroundLayer, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 680));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void returnHomeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnHomeButtonActionPerformed
        //Δημιούργησε ένα αντικείμενο τύπου Home
        Home home = new Home();
        //Κάνε το αντικείμενο τύπου Home ορατό (Επιστροφή στην αρχική οθόνη)
        home.setVisible(true);
        //Κάνε το παράθυρο Διαχείρισης δεδομένων όχι ορατό
        this.setVisible(false);
        //Κλείσε το παράθυρο Διαχείρισης δεδομένων
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        //Κατέστρεψε το παράθυρο διαχείρισης δεδομένων
        this.dispose();
    }//GEN-LAST:event_returnHomeButtonActionPerformed

    private void saveStatisticsToPdfButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveStatisticsToPdfButtonActionPerformed
        //Δημιουργία νέου εγγράφου
        Document document = new Document();
        try {
            //Ονομασία εγγράφου statistics.pdf
            PdfWriter.getInstance(document, new FileOutputStream("statistics.pdf"));
            //Άνοιγμα αρχείου
            document.open();
            //Oρισμός γραμματοσειράς arial
            BaseFont baseFont = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            baseFont.setSubset(true);
            Font font = new Font(baseFont, 14, Font.BOLD);
            //Δημιουργία τίτλου εγγράφου
            Chunk chunk1 = new Chunk("               Στατιστικά στοιχεία κληρώσεων από " + FromDateComboBox.getSelectedItem().toString()
                    + " έως " + ToDateComboBox.getSelectedItem() + "\n", font);
            //Προσθήκη τίτλου εγγράφου στο κείμενο
            document.add(new Paragraph(chunk1));
            //Δημιουργία τίτλων πινάκων
            Chunk chunk2 = new Chunk("             *Αριθμοί*", font);
            Chunk chunk3 = new Chunk("             *Τζόκερ Αριθμοί*", font);

            //Κενή γραμμή
            document.add(Chunk.NEWLINE);

            //Δημιουργία πίνακα με 3 στήλες για τους αριθμούς που κληρώνονται
            PdfPTable numbersTab = new PdfPTable(3);
            //Δημιουργία πίνακα με 3 στήλες για τους αριθμούς τζόκερ που κληρώνονται
            PdfPTable bonusTab = new PdfPTable(3);
            //Προσθήκη επικεφαλίδας σε κάθε στήλη
            addTableHeader(numbersTab);
            addTableHeader(bonusTab);
            //Προσθήκη περιεχομένων jtables στους αντίστοιχους πίνακες για το pdf αρχείο
            addNumberRows(numbersTab, numbersTable);
            addBonusRows(bonusTab, bonusTable);

            //Προσθήκη τίτλου πίνακα αριθμών
            document.add(new Paragraph(chunk2));
            //Προσθήκη πινάκων στο έγγραφο και μία κενή γραμμή μεταξύ τους
            document.add(numbersTab);
            document.add(Chunk.NEWLINE);
            //Προσθήκη τίτλου πίνακα τζόκερ αριθμών
            document.add(new Paragraph(chunk3));
            document.add(bonusTab);
            //Κλείσιμο αρχείου
            document.close();
            JOptionPane.showMessageDialog(null, "Το αρχείο pdf δημιουργήθηκε επιτυχώς.", "Ενημέρωση", JOptionPane.INFORMATION_MESSAGE);

            //Προσπάθεια εμφάνισης pdf αρχείου 
            File file = new File("statistics.pdf");
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Σφάλμα κατά την προβολή του αρχείου pdf.", "Σφάλμα", JOptionPane.INFORMATION_MESSAGE);
                Logger.getLogger(ShowStatistics.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (FileNotFoundException | DocumentException ex) {
            JOptionPane.showMessageDialog(null, "Πρόβλημα δημιουργίας αρχείου pdf. Ελέγξτε αν το αρχείο είναι ανοιχτό και προσπαθήστε ξανά.", "Σφάλμα", JOptionPane.INFORMATION_MESSAGE);
            Logger.getLogger(ShowStatistics.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ShowStatistics.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_saveStatisticsToPdfButtonActionPerformed

    private void showGraphicStatisticsDataButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showGraphicStatisticsDataButtonActionPerformed
        //Δημιουργία αντικειμένου τύπου jframe StatisticsSelection
        StatisticsSelection st = new StatisticsSelection();
        st.setVisible(true);
    }//GEN-LAST:event_showGraphicStatisticsDataButtonActionPerformed

    private void FromDateComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FromDateComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FromDateComboBoxActionPerformed

    private void ToDateComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ToDateComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ToDateComboBoxActionPerformed

    private void showStatisticsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showStatisticsButtonActionPerformed
        //Αποτέλεσμα αναζήτησης με εύρος ημερομηνιών

        //Αρχικοποιούμε τη μπάρα εργασίας που δείχνει πόσο προχωράνε οι εργασίες στο background
        int value = 0;
        showStatisticsProgressBar.setValue(value);
        showStatisticsProgressBar.setMinimum(0);
        showStatisticsProgressBar.setMaximum(100);
        showStatisticsProgressBar.update(showStatisticsProgressBar.getGraphics());

        //Αν ο χρήστης έχει εισάγει ημερομηνίες
        if (((FromDateComboBox.getSelectedItem() != null) && (ToDateComboBox.getSelectedItem() != null))) {

            //Έλεγχος αν ημερομηνία είναι σε σωστή μορφή
            DateValidator validator = new DateValidatorUsingDateFormat("yyyy-mm-dd");
            if (!validator.isValid(FromDateComboBox.getSelectedItem().toString()) || !validator.isValid(ToDateComboBox.getSelectedItem().toString())) {
                JOptionPane.showMessageDialog(null, "Οι ημερομηνίες δεν είναι σε σωστή μορφή (εεεε-μμ-ηη).", "Ενημέρωση", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            //Έλεγχος αν η ημερομηνία είναι έγκυρη (πχ 2022-02-30 είναι άκυρη ημερομηνία)
            try {
                LocalDate fromDate = LocalDate.parse(FromDateComboBox.getSelectedItem().toString());
                LocalDate toDate = LocalDate.parse(ToDateComboBox.getSelectedItem().toString());

                //Έλεγχος αν οι εισαγώμενες ημερομηνίες είναι μέχρι σήμερα
                if (LocalDate.now().isBefore(fromDate) || LocalDate.now().isBefore(toDate)) {
                    JOptionPane.showMessageDialog(null, "Έχετε εισάγει ημερομηνίες που ξεπερνούν τη σημερινή ημέρα. Προσπαθήστε ξανά.", "Ενημέρωση", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(null, "Μη έγκυρη ημερομηνία.", "Ενημέρωση", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            //Υπολογίζουμε από πόσες ημέρες επιλέγει ο χρήστης να αντλήσει πληροφορίες διότι μπορεί να αντλήσει πληροφορίες έως 3 μηνών (94 ημερών)
            LocalDate fromDate = LocalDate.parse(FromDateComboBox.getSelectedItem().toString());
            LocalDate toDate = LocalDate.parse(ToDateComboBox.getSelectedItem().toString());
            long fromDateDays = fromDate.toEpochDay();
            long toDateDays = toDate.toEpochDay();
            long duration = toDateDays - fromDateDays + 1;

            //Ενημερωτικό μήνυμα προς το χρήστη σε περίπτωση που η fromDate ημερομηνία είναι μεγαλύτερη από την toDate ημερομηνία
            if (duration < 0) {
                JOptionPane.showMessageDialog(null, "Λάθος εισαγωγή εύρους ημερομηνιών. Προσπαθήστε ξανά. ", "Ενημέρωση", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            //Αρχικοποιούμε τη ζώνη ώρας γιατί θα μας χρειαστεί στις μετατροπές ημερομηνιών
            ZoneId defaultZoneId = ZoneId.systemDefault();
            //Δημιουργούμε μία λίστα με αντικείμενα τύπου Draw για να αποθηκεύσουμε τα δεδομένα που αντλούμε από το json
            List<Draws> draws = new ArrayList<>();
            //Αν το εύρος ημερομηνιών που εισάγει ο χρήστης ξεπερνάει τις 94 ημέρες
            //το url δεν επιστρέφει δεδομένα
            //Οπότε "σπάμε" τις ημερομηνίες σε κομμάτια και καλούμε το url για να αντλήσουμε δεδομένα ανά "κομμάτι"
            while (duration > 94) {

                toDate = fromDate.plusDays(93);
                Date dateTo = Date.from(toDate.atStartOfDay(defaultZoneId).toInstant());
                Date dateFrom = Date.from(fromDate.atStartOfDay(defaultZoneId).toInstant());
                String pattern = "yyyy-MM-dd";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String df = simpleDateFormat.format(dateFrom);
                String dt = simpleDateFormat.format(dateTo);
                //Eνημερώνουμε τη μπάρα εργασίας πόσο πρέπει να γεμίσει
                value++;
                showStatisticsProgressBar.setValue(value);
                showStatisticsProgressBar.update(showStatisticsProgressBar.getGraphics());

                //Διαδικασία για κλήση url
                //Περνάμε το url που θα καλέσουμε σε ενα string
                String urlToCall = "https://api.opap.gr/draws/v3.0/5104/draw-date/" + df + "/" + dt + "/?limit=180";
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder().url(urlToCall).build();

                //Καλούμε το κατάλληλο url 
                try (Response response = client.newCall(request).execute()) {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseString = response.body().string();

                        //Διαδικασία άντλησης των δεδομένων του Json
                        //Δημιουργούμε ένα αντικείμενο GsonBuilder
                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();
                        //Δημιουργούμε ένα JsonObject που περιέχει το Json που αντλήσαμε
                        JsonObject jsonObj = gson.fromJson(responseString, JsonObject.class);
                        //Δημιουργούμε ενα JsonArray που περιέχει τα Objects 
                        JsonArray content = jsonObj.getAsJsonArray("content");
                        //Aντλούμε τα δεδομένα από το json και τα περνάμε σε μια λίστα που περιέχει αντικείμενα τύπου draw
                        for (JsonElement draw : content) {
                            int drawId = draw.getAsJsonObject().get("drawId").getAsInt();
                            long drawTime = draw.getAsJsonObject().get("drawTime").getAsLong();
                            JsonArray winningNumbers = draw.getAsJsonObject().get("winningNumbers").getAsJsonObject().get("list").getAsJsonArray();
                            JsonArray bonusNumbers = draw.getAsJsonObject().get("winningNumbers").getAsJsonObject().get("bonus").getAsJsonArray();
                            Draws drawObj = new Draws(drawId);
                            drawObj.setWinningNumber1(winningNumbers.get(0).getAsInt());
                            drawObj.setWinningNumber2(winningNumbers.get(1).getAsInt());
                            drawObj.setWinningNumber3(winningNumbers.get(2).getAsInt());
                            drawObj.setWinningNumber4(winningNumbers.get(3).getAsInt());
                            drawObj.setWinningNumber5(winningNumbers.get(4).getAsInt());
                            drawObj.setBonusNumber(bonusNumbers.get(0).getAsInt());
                            draws.add(drawObj);

                        }

                        //Yπολογίζουμε τις νέες ημερομηνίες για τις οποίες θα πραγματοποιηθεί κλήση Url
                        duration = duration - 94;
                        fromDate = toDate.plusDays(1);
                        if (duration == 0) {
                            break;
                        } else if (duration < 95) {
                            toDate = toDate.plusDays(duration);
                        }

                    } else {
                        //Ενημερωτικό μήνυμα σε περίπτωση λανθασμένης εισαγωγής ημερομηνιών (FromDate>ToDate)
                        JOptionPane.showMessageDialog(null, "Λάθος εισαγωγή ημερομηνιών. ", "Ενημέρωση", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Πρόβλημα κλήσης URL. Προσπαθήστε ξανά.", "Σφάλμα", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            //Αν έχουν απομείνει μέρες που δεν αντλήσαμε δεδομένα υπαναυπολογισμός ημερομηνιών και επανάκληση url
            if (duration > 0 && duration < 95) {

                Date dateTo = Date.from(toDate.atStartOfDay(defaultZoneId).toInstant());
                Date dateFrom = Date.from(fromDate.atStartOfDay(defaultZoneId).toInstant());
                String pattern = "yyyy-MM-dd";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String df = simpleDateFormat.format(dateFrom);
                String dt = simpleDateFormat.format(dateTo);

                //Ενημερώνουμε τη μπάρα εργασίας πόσο πρέπει να γεμίσει
                value++;
                showStatisticsProgressBar.setValue(value);
                showStatisticsProgressBar.update(showStatisticsProgressBar.getGraphics());
                //Διαδικασία για κλήση url

                //Περνάμε το url που θα καλέσουμε σε ενα string
                String urlToCall = "https://api.opap.gr/draws/v3.0/5104/draw-date/" + df + "/" + dt + "/?limit=180";
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder().url(urlToCall).build();

                //Καλούμε το κατάλληλο url 
                try (Response response = client.newCall(request).execute()) {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseString = response.body().string();

                        //Διαδικασία άντλησης των δεδομένων του Json
                        //Δημιουργούμε ένα αντικείμενο GsonBuilder
                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();
                        //Δημιουργούμε ένα JsonObject που περιέχει το Json που αντλήσαμε
                        JsonObject jsonObj = gson.fromJson(responseString, JsonObject.class);

                        //Δημιουργούμε ενα JsonArray που περιέχει τα Objects 
                        JsonArray content = jsonObj.getAsJsonArray("content");

                        for (JsonElement draw : content) {
                            int drawId = draw.getAsJsonObject().get("drawId").getAsInt();
                            JsonArray winningNumbers = draw.getAsJsonObject().get("winningNumbers").getAsJsonObject().get("list").getAsJsonArray();
                            JsonArray bonusNumbers = draw.getAsJsonObject().get("winningNumbers").getAsJsonObject().get("bonus").getAsJsonArray();
                            Draws drawObj1 = new Draws(drawId);
                            drawObj1.setWinningNumber1(winningNumbers.get(0).getAsInt());
                            drawObj1.setWinningNumber2(winningNumbers.get(1).getAsInt());
                            drawObj1.setWinningNumber3(winningNumbers.get(2).getAsInt());
                            drawObj1.setWinningNumber4(winningNumbers.get(3).getAsInt());
                            drawObj1.setWinningNumber5(winningNumbers.get(4).getAsInt());
                            drawObj1.setBonusNumber(bonusNumbers.get(0).getAsInt());
                            draws.add(drawObj1);
                            //Ενημερώνουμε τη μπαρα εργασίας πόσο πρέπει να γεμίσει
                            value++;
                            showStatisticsProgressBar.setValue(value);
                            showStatisticsProgressBar.update(showStatisticsProgressBar.getGraphics());

                        }

                    } else {
                        //Ενημερωτικό μήνυμα σε περίπτωση λανθασμένης εισαγωγής ημερομηνιών (FromDate>ToDate)
                        JOptionPane.showMessageDialog(null, "Λάθος εισαγωγή ημερομηνιών. ", "Ενημέρωση", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Πρόβλημα κλήσης URL. Προσπαθήστε ξανά.", "Σφάλμα", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            //Φτιάχνουμε βοηθητικές λίστες για να αντλήσουμε από το json δεδομένα
            int[] occurrences = new int[45];
            int[] delays = new int[45];
            int[] numbersList = new int[45];

            for (int i = 0; i < 45; i++) {
                numbersList[i] = i + 1;
            }
            for (int i = 0; i < 45; i++) {
                occurrences[i] = 0;
                delays[i] = 0;
            }

            //Παραμετροποιούμε τον πίνακα που θα εμφανίζει τα στατιστικά δεδομένα των αριθμών 1-45
            DefaultTableModel model = (DefaultTableModel) numbersTable.getModel();
            model.setRowCount(0);
            String[] columnNames = {"Αριθμός", "Εμφανίσεις", "Καθυστερήσεις"};
            model.setColumnIdentifiers(columnNames);
            //Παραμετροποιούμε τον πίνακα που θα εμφανίζει τα στατιστικά δεδομένα των αριθμών bonus
            DefaultTableModel model1 = (DefaultTableModel) bonusTable.getModel();
            model1.setRowCount(0);
            model1.setColumnIdentifiers(columnNames);

            //Ταξινόμηση draws σύμφωνα με κωδικό κλήρωσης
            draws.sort(comparingInt((type) -> type.getId()));
            //Yπλογίζουμε τις εμφανίσεις των αριθμών 1-45 και τις περνάμε στον πίνακα occurrences
            for (Draws draw : draws) {
                //Η μπάρα εργασίας ξεκινάει πάλι από το 0 αφου ξεκινάει νέα διεργασία
                value = 0;

                occurrences[draw.getWinningNumber1() - 1]++;
                occurrences[draw.getWinningNumber2() - 1]++;
                occurrences[draw.getWinningNumber3() - 1]++;
                occurrences[draw.getWinningNumber4() - 1]++;
                occurrences[draw.getWinningNumber5() - 1]++;

                //Υπολογίζουμε τις καθυστερήσεις των αριθμών 1-45 και τις περνάμε στον πίνακα delays
                for (int i = 0; i < 45; i++) {
                    delays[i]++;
                }
                delays[draw.getWinningNumber1() - 1] = 0;
                delays[draw.getWinningNumber2() - 1] = 0;
                delays[draw.getWinningNumber3() - 1] = 0;
                delays[draw.getWinningNumber4() - 1] = 0;
                delays[draw.getWinningNumber5() - 1] = 0;
                //Ενημερώνουμε τη μπάρα εργασίας
                value++;
                showStatisticsProgressBar.setValue(value);
                showStatisticsProgressBar.update(showStatisticsProgressBar.getGraphics());
            }

            //Πρσθήκη δεδομένων στον πίνακα που εμφανίζεται στο παράθυρο σχετικά με τα στατιστικά δεδομένα των αριθμών 1-45
            for (int i = 0; i < 45; i++) {
                Object[] rowValues = {numbersList[i], occurrences[i], delays[i]};
                model.addRow(rowValues);
                //Ενημερώνουμε τη μπάρα εργασίας
                value++;
                showStatisticsProgressBar.setValue(value);
                showStatisticsProgressBar.update(showStatisticsProgressBar.getGraphics());
            }

            //Μηδενισμός των τιμών στους πίνακες occurrences και delays
            for (int i = 0; i < 45; i++) {
                occurrences[i] = 0;
                delays[i] = 0;
                //Ενημερώνουμε τη μπάρα εργασίας
                value++;
                showStatisticsProgressBar.setValue(value);
                showStatisticsProgressBar.update(showStatisticsProgressBar.getGraphics());
            }

            for (Draws draw : draws) {
                occurrences[draw.getBonusNumber() - 1]++;
                for (int i = 0; i < 20; i++) {
                    delays[i]++;
                }
                delays[draw.getBonusNumber() - 1] = 0;
                //Ενημερώνουμε τη μπάρα εργασίας
                value++;
                showStatisticsProgressBar.setValue(value);
                showStatisticsProgressBar.update(showStatisticsProgressBar.getGraphics());
            }
            //Πρσθήκη δεδομένων στον πίνακα που εμφανίζεται στο παράθυρο σχετικά με τα στατιστικά δεδομένα των bonus αριθμών
            for (int i = 0; i < 20; i++) {
                Object[] rowValues = {numbersList[i], occurrences[i], delays[i]};
                model1.addRow(rowValues);
                //Εημερώνουμε τη μπάρα εργασίας
                value++;
                showStatisticsProgressBar.setValue(value);
                showStatisticsProgressBar.update(showStatisticsProgressBar.getGraphics());
            }

            numbersPane.getViewport().add(numbersTable);
            bonusPane.getViewport().add(bonusTable);
            //Βάζουμε τις ημερομηνίες για τις οποίες ισχύουν τα στατιστικά δεδομένα στο label του αντίστοιχου πίνακα
            apiOrBdLabel.setForeground(new java.awt.Color(222, 222, 222));
            apiOrBdLabel.setText("[API]");
            statisticsDatesLabel1.setForeground(new java.awt.Color(222, 222, 222));
            statisticsDatesLabel2.setForeground(new java.awt.Color(222, 222, 222));
            statisticsDatesLabel1.setText("Στατιστικά δεδομένα κληρώσεων από");
            statisticsDatesLabel2.setText(FromDateComboBox.getSelectedItem() + " έως " + ToDateComboBox.getSelectedItem() + ".");

        } //Αν ο χρήστης πατήσει αναζήτηση χωρίς να έχει επιλέξει εύρος ημερομηνιών τότε του εμφανίζει ενημερωτικό μήνυμα  
        else if (((FromDateComboBox.getSelectedItem() == null) || (ToDateComboBox.getSelectedItem() == null))) {
            //Ενημερωτικό μήνυμα
            JOptionPane.showMessageDialog(null, "Πρέπει να εισάγετε ημερομηνίες. ", "Λάθος Επιλογή", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_showStatisticsButtonActionPerformed

    private void showStatisticsDBButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showStatisticsDBButtonActionPerformed
        //Αποτέλεσμα αναζήτησης στατιστικών δεδομένων σε βάση δεδομένων με εύρος ημερομηνιών

        showStatisticsProgressBar.setValue(0);
        showStatisticsProgressBar.setMinimum(0);
        //Αν ο χρήστης έχει εισάγει ημερομηνίες
        if (((FromDateComboBox.getSelectedItem() != null) && (ToDateComboBox.getSelectedItem() != null))) {
            //Έλεγχος αν η ημερομηνία είναι έγκυρη (πχ 2022-02-30 είναι άκυρη ημερομηνία)
            try {
                LocalDate fromDate = LocalDate.parse(FromDateComboBox.getSelectedItem().toString());
                LocalDate toDate = LocalDate.parse(ToDateComboBox.getSelectedItem().toString());

                //Έλεγχος αν οι εισαγώμενες ημερομηνίες είναι μέχρι σήμερα
                if (LocalDate.now().isBefore(fromDate) || LocalDate.now().isBefore(toDate)) {
                    JOptionPane.showMessageDialog(null, "Έχετε εισάγει ημερομηνίες που ξεπερνούν τη σημερινή ημέρα. Προσπαθήστε ξανά.", "Ενημέρωση", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(null, "Μη έγκυρη ημερομηνία.", "Ενημέρωση", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            //Υπολογίζουμε τη διάρκεια ημερών που έχει βάλει ο χρήστης
            LocalDate fromDate = LocalDate.parse(FromDateComboBox.getSelectedItem().toString());
            LocalDate toDate = LocalDate.parse(ToDateComboBox.getSelectedItem().toString());
            long fromDateDays = fromDate.toEpochDay();
            long toDateDays = toDate.toEpochDay();
            long duration = toDateDays - fromDateDays + 1;

            //Ενημερωτικό μήνυμα προς το χρήστη σε περίπτωση που η fromDate ημερομηνία είναι μεγαλύτερη από την toDate ημερομηνία
            if (duration < 0) {
                JOptionPane.showMessageDialog(null, "Λάθος εισαγωγή εύρους ημερομηνιών. Προσπαθήστε ξανά. ", "Λάθος Εισαγωγή", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            //Δημιουργία του EntityManagerFactory
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("JokerGameStatsPU");
            //Δημιουργία του EntityManager
            EntityManager em = emf.createEntityManager();

            //Έναρξη προσπάθειας δοσοληψίας με τη βάση δεδομένων
            try {
                //Έναρξη δοσοληψίας
                em.getTransaction().begin();

                //Δημιουργία κατάλληλης ερώτησης προς βάση δεδομένων για να αντλήσουμε δεδομένα σε εύρος ημερομηνιών
                Query findDraws = em.createNativeQuery("SELECT * FROM DRAW WHERE DATE BETWEEN '" + FromDateComboBox.getSelectedItem().toString() + "' AND '"
                        + ToDateComboBox.getSelectedItem().toString() + "'", Draw.class);
                //Παίρνουμε τα αποτελέσματα της βάσης σε μία λίστα
                List<Draw> drawsPj = findDraws.getResultList();

                //Αν η λίστα αποτελεσμάτων απο τη βάση δεδομένων δεν είναι άδεια εκκίνηση διαδιακασίας υπολογισμού στατιστικών
                if (!drawsPj.isEmpty()) {
                    //Δημιουργία πινάκων για να υπολογίσουμε εμφανίσεις και καθυστερήσεις αριθμών
                    int[] occurrences = new int[45];
                    int[] delays = new int[45];
                    int[] numbersList = new int[45];
                    //Μηδενισμός τιμών στους πίνακες
                    for (int i = 0; i < 45; i++) {
                        numbersList[i] = i + 1;
                    }
                    for (int i = 0; i < 45; i++) {
                        occurrences[i] = 0;
                        delays[i] = 0;
                    }

                    //Παραμετροποιούμε τον πίνακα που θα εμφανίζει τα στατιστικά δεδομένα των αριθμών 1-45
                    DefaultTableModel model = (DefaultTableModel) numbersTable.getModel();
                    model.setRowCount(0);
                    String[] columnNames = {"Αριθμός", "Εμφανίσεις", "Καθυστερήσεις"};
                    model.setColumnIdentifiers(columnNames);
                    //Παραμετροποιούμε τον πίνακα που θα εμφανίζει τα στατιστικά δεδομένα των αριθμών bonus
                    DefaultTableModel model1 = (DefaultTableModel) bonusTable.getModel();
                    model1.setRowCount(0);
                    model1.setColumnIdentifiers(columnNames);

                    //Ταξινόμηση draws σύμφωνα με κωδικό κλήρωσης ώστε να υπολογιστούν σωστά οι καθυστερήσεις
                    drawsPj.sort(comparingInt((type) -> type.getId()));
                    showStatisticsProgressBar.setMaximum(drawsPj.size() * 45);
                    //Yπολογίζουμε τις εμφανίσεις των αριθμών 1-45 και τις περνάμε στον πίνακα occurrences
                    for (Draw draw : drawsPj) {
                        occurrences[draw.getWinningnumber1() - 1]++;
                        occurrences[draw.getWinningnumber2() - 1]++;
                        occurrences[draw.getWinningnumber3() - 1]++;
                        occurrences[draw.getWinningnumber4() - 1]++;
                        occurrences[draw.getWinningnumber5() - 1]++;
                        //Υπολογίζουμε τις καθυστερήσεις των αριθμών 1-45 και τις περνάμε στον πίνακα delays
                        for (int j = 0; j < 45; j++) {
                            delays[j]++;
                            int value = showStatisticsProgressBar.getValue() + 1;
                            showStatisticsProgressBar.setValue(value);
                            showStatisticsProgressBar.update(showStatisticsProgressBar.getGraphics());
                        }
                        delays[draw.getWinningnumber1() - 1] = 0;
                        delays[draw.getWinningnumber2() - 1] = 0;
                        delays[draw.getWinningnumber3() - 1] = 0;
                        delays[draw.getWinningnumber4() - 1] = 0;
                        delays[draw.getWinningnumber5() - 1] = 0;
                    }

                    //Πρσθήκη δεδομένων στον πίνακα που εμφανίζεται στο παράθυρο σχετικά με τα στατιστικά δεδομένα των αριθμών 1-45
                    for (int i = 0; i < 45; i++) {
                        Object[] rowValues = {numbersList[i], occurrences[i], delays[i]};
                        model.addRow(rowValues);
                    }

                    //Μηδενισμός των τιμών στους πίνακες occurrences και delays
                    for (int i = 0; i < 45; i++) {
                        occurrences[i] = 0;
                        delays[i] = 0;
                    }
                    //Yπολογίζουμε τις εμφανίσεις των αριθμών τζόκερ 1-20 και τις περνάμε στον πίνακα ocurrences
                    for (Draw draw : drawsPj) {
                        occurrences[draw.getBonus() - 1]++;
                        //Yπολογίζουμε τις καθυστερήσεις των αριθμών τζόκερ 1-20 και τις περνάμε στον πίνακα delays
                        for (int i = 0; i < 20; i++) {
                            delays[i]++;
                        }
                        delays[draw.getBonus() - 1] = 0;
                    }
                    //Πρσθήκη δεδομένων στον πίνακα που εμφανίζεται στο παράθυρο σχετικά με τα στατιστικά δεδομένα των bonus αριθμών
                    for (int i = 0; i < 20; i++) {
                        Object[] rowValues = {numbersList[i], occurrences[i], delays[i]};
                        model1.addRow(rowValues);
                    }

                    numbersPane.getViewport().add(numbersTable);
                    bonusPane.getViewport().add(bonusTable);

                    //Βάζουμε τις ημερομηνίες για τις οποίες ισχύουν τα στατιστικά δεδομένα στο label του αντίστοιχου πίνακα
                    apiOrBdLabel.setForeground(new java.awt.Color(222, 222, 222));
                    apiOrBdLabel.setText("[ΒΑΣΗ ΔΕΔΟΜΕΝΩΝ]");
                    statisticsDatesLabel1.setForeground(new java.awt.Color(222, 222, 222));
                    statisticsDatesLabel2.setForeground(new java.awt.Color(222, 222, 222));
                    statisticsDatesLabel1.setText("Στατιστικά δεδομένα κληρώσεων από");
                    statisticsDatesLabel2.setText(FromDateComboBox.getSelectedItem() + " έως " + ToDateComboBox.getSelectedItem() + ".");
                } else {
                    JOptionPane.showMessageDialog(null, "Δεν υπάρχουν αποθηκευμένα δεδομένα για αυτές τις ημερομηνίες.\nΔοκιμάστε ξανά.", "Eνημέρωση", JOptionPane.INFORMATION_MESSAGE);
                }

                //Τερματισμός δοσοληψίας
                em.getTransaction().commit();
            } catch (Exception e) {
                //Ενημερωτικό μήνυμα
                JOptionPane.showMessageDialog(null, "Η διαδικασία δεν ήταν επιτυχής. Προσπαθήστε ξανά. ", "Σφάλμα", JOptionPane.INFORMATION_MESSAGE);
                e.printStackTrace();
            }

            //Καταστροφή του EntityManagerFactory και του EntityManager
            em.close();
            emf.close();

        } //Αν ο χρήστης πατήσει αναζήτηση χωρίς να έχει επιλέξει εύρος ημερομηνιών τότε του εμφανίζει ενημερωτικό μήνυμα  
        else if (((FromDateComboBox.getSelectedItem() == null) || (ToDateComboBox.getSelectedItem() == null))) {
            //Ενημερωτικό μήνυμα
            JOptionPane.showMessageDialog(null, "Πρέπει να εισάγετε ημερομηνίες. ", "Λάθος Επιλογή", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_showStatisticsDBButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
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
            Logger.getLogger(ShowStatistics.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(ShowStatistics.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ShowStatistics.class.getName()).log(Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            Logger.getLogger(ShowStatistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ShowStatistics().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> FromDateComboBox;
    private javax.swing.JComboBox<String> ToDateComboBox;
    private javax.swing.JLabel apiOrBdLabel;
    private javax.swing.JLabel backgroundLayer;
    private javax.swing.JLabel bonusLabel;
    private javax.swing.JScrollPane bonusPane;
    private JTable bonusTable;
    private javax.swing.JLabel fromDateLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel numbersLabel;
    private javax.swing.JScrollPane numbersPane;
    private JTable numbersTable;
    private javax.swing.JButton returnHomeButton;
    private javax.swing.JButton saveStatisticsToPdfButton;
    private javax.swing.JButton showGraphicStatisticsDataButton;
    private javax.swing.JButton showStatisticsButton;
    private javax.swing.JButton showStatisticsDBButton;
    private javax.swing.JProgressBar showStatisticsProgressBar;
    private javax.swing.JLabel statisticsDatesLabel1;
    private javax.swing.JLabel statisticsDatesLabel2;
    private javax.swing.JLabel toDateLabel;
    // End of variables declaration//GEN-END:variables
}
