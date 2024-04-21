# **JokerApp** <img style="vertical-align:down" align="left" src="https://user-images.githubusercontent.com/108992250/210183286-d1f3bcff-1602-4310-9c56-4a5422f70622.jpg" width=50/>

https://user-images.githubusercontent.com/108992250/219718220-00b0d0de-3fbc-43a8-8c89-f2b1f82c7740.mp4

<br>
The application was implemented as an assignment for the Hellenic Open University. The user can view and manage data about draws of Joker game (a lucky game by the company OPAP). Processing data were obtained from the service web api.opap.gr/.

## **Team Members**

- Papaioannou Emmanolia
- Lymberis Dimitrios
- Hatziioannou Ioannis

## **System Description**
Each user has the following options:

### **1. Data management**

If the "Data Management" button is selected, a screen appears where the user can:
- **View Selection Data**
- **Delete Selection Data**

### a) Viewing Data
To display data, users can:
- Select a game ID to view specific details of a Joker game, including the winning column, number of hits, and winnings per hit.
- Choose a date range to see details of games that occurred within those dates.
- Save the displayed data to a suitable database, if it has not been saved already.
- Search for draws that took place within a specified date range.

### b) Deleting Data
To delete data, users can:
- Select "Delete game data" or "Delete game data within date range."
- Upon selection, a confirmation prompt appears for user verification.
- Data is permanently deleted only after the user confirms the action.



### **2. View Data**

When the "View Data" button is selected, users are shown a list of years available in the database. Upon selecting a year, a list of available months (also from the database) is displayed. After choosing a month, users are taken to a new page that displays aggregated data for that specific month and year, including the number of games played, total money distributed, and the number of JACKPOTS awarded.


### **3. View Statistics**


When the "Show Statistics" button is selected, users can view statistics on drawn numbers, including the frequency of occurrence of numbers and joker numbers, as well as average winnings per category, from the API call https://api.opap.gr/games/v1.0/5104/statistics. Users can choose to display these statistics from either the API or a database for a selected date range.

Additionally, users have the option to print the projection table in PDF format.

On the same screen, users can press the "View Statistical Data in Graphical Form" button, which navigates to a screen where they can select data to be graphically represented, such as the frequency of numbers and joker numbers and average winnings per category. Users can also select the date range from which data will be extracted from the database.
</details>

### **4. Exit**
Finally if user selects the Exit button, the application terminates.

## **Project management**

Scrum practice was applied to organize the team and assign responsibilities. First the Product Backlog was defined. Through voting in Planning Poker, the approximate man-hours of project development as well as the prioritization of responsibilities were decided. Moreover, the time limit for each task was setted. Then the Product Backlog was splitted into the 3 sprints. Furthermore, the GANTT and the BurnDown chart were implemented. For project management and collaboration, the Trello tool was used throughout.


## **Tools Used**

<details close>
<summary>Click to see more details.</summary>
<br>
<ul>
<li>Java, JDK 1.8 and Netbeans 8.2 IDE were used for code writing.</li>
<li>For the database Apache Derby - 10.16.1.1 was used.</li>
<li>For the Graphical User Interface (GUI) the Swing tool was used.</li>
<li>The Trello tool was used for project management and team collaboration.</li>
<li>The ProjectLibre application was used to construct the GANTT chart.</li>
<li>Excel application was used to construct the BurnDown chart.</li>
</ul>
</details>


## **Building and Running from Source**
To run the JokerApp application, please follow these steps:
<ul>
<li>Ensure that Java is installed on your machine.</li>
<li>Clone the repository using the following command: <code>git clone https://github.com/emmapapaioan/JokerApp.git</code></li>
<li>Navigate to the project directory.</li>
<li>Compile the project by running the following command: <code>javac JokerApp.java</code></li>
<li>Run the application by running the following command: <code>java JokerApp</code></li>
</ul>

## **Running the Pre-built Application**
Alternatively, you can download a pre-built version of the application from the "Releases" section of the repository or by clicking here <a href="https://github.com/emmapapaioan/JokerApp/releases/tag/JokerGame-Stats.v01" target="_blank">JokerApp Release</a>. Please note that Java must still be installed on your machine to run the application.

## **Screenshots of the application**
<img src="https://user-images.githubusercontent.com/108992250/183464013-6f24b7e6-c50d-4559-af22-32ccc09c83ce.jpg" width=700 />
<img src="https://user-images.githubusercontent.com/108992250/183464206-da5f7dd9-d458-4552-9e79-0c078d5b311b.jpg" width=700 />
<img src="https://user-images.githubusercontent.com/108992250/183942140-02e63960-a0e1-4a9c-8acb-b8e7e9fb1cd3.png" width=700 />
<img src="https://user-images.githubusercontent.com/108992250/183464229-3ed76c60-6e57-4e8f-b8b5-40e89a6781ac.jpg" width=700 />
<img src="https://user-images.githubusercontent.com/108992250/183465783-000e4911-8175-4537-b39f-863002aa3235.png" width=700 />
<img src="https://user-images.githubusercontent.com/108992250/183465792-761bc744-2775-4592-8849-f1e1539575a5.png" width=700 />
<img src="https://user-images.githubusercontent.com/108992250/183465795-d2745c0a-c13f-4f45-94fa-7c454cfb15cd.png" width=700 />
<img src="https://user-images.githubusercontent.com/108992250/183465797-b9abe222-b1d0-42c4-bdc4-de99256ca1f7.png" width=700 />
<img src="https://user-images.githubusercontent.com/108992250/183465799-557cc5e4-b41e-4f50-bd10-52d61eb5aeed.png" width=700 />
<img src="https://user-images.githubusercontent.com/108992250/183466256-232e14fd-368d-4567-ac6c-7a234730054f.png" width=700 />

