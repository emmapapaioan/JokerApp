# **JokerGameStats** <img style="vertical-align:down" align="left" src="https://user-images.githubusercontent.com/108992250/210183286-d1f3bcff-1602-4310-9c56-4a5422f70622.jpg" width=50/>


The application was implemented as an assignment for the Hellenic Open University. The user can view and manage data about draws of Joker game (a lucky game by the company OPAP). Processing data were obtained from the service web api.opap.gr/.

## **Team Members**

- Papaioannou Emmanolia
- Lymberis Dimitrios
- Hatziioannou Ioannis


## **System Description**
Each user has the following options:

## **1. Data management**

If the Data management button is selected, a screen appears where the user can a) view their selection data or b) delete their selection data.
a) If one wants to display data, then the user can select the ID of a game, so that corresponding details for a specific joker game can be displayed (winning column, number of hits, winnings per hit), or choose between some dates, so to view details of the games that have taken place within the selected dates.
Then the user has the possibility, if one wishes, to save the projection data (in a suitable database, as it is enough if it is not already saved).
Also the user has the possibility to search which draws were made within a range of dates.
b) If one wants to delete data then one chooses between "Delete game data" or "Delete game data within date range". If one selects one of the two buttons, then a verification question appears with confirmation for the user. Only in case of confirmation by the user are the respective data finally deleted.


## **2. View Data**

If the user selects the View data button, a list of available years is displayed (available years are those stored in the database). The user then selects year, for which a list of available months is displayed (available months are those stored in the database). Then user selects the month and then a new page shows the aggregated data of the specific month for the specific year (how many games were played, how much total money was distributed, how many JACKPOTS were made).


## **3. View Statistics**

If user selects the Show statistics button, the statistics of drawn numbers (frequency of occurrence of numbers, frequency of occurrence of joker numbers, average winnings per category) in a given date range from the api call https://api.opap.gr/games/v1.0/5104/statistics. Then the user is the one who can choose to display statistical data either drawn from the api or from the database in a date range that the user wants.
Then the user has the possibility, if one wants, to print the projection table in pdf.
Also on this screen the user can press the View statistical data in graphical form button which takes the user to a screen where can choose which data are wanted to be produced in a graph. Frequency of occurrence of numbers, frequency of occurrence of joker numbers and average winnings per category. User also has the ability to select dates from which data will be extracted from the database.
</details>

## **4. Exit**
Finally if user selects the Exit button, the application terminates.

## **Project management**

Scrum practice was applied to organize the team and assign responsibilities. First the Product Backlog was defined. Through voting in Planning Poker, the approximate man-hours of project development as well as the prioritization of responsibilities were decided. Moreover, the time limit for each task was setted. Then the Product Backlog was splitted into the 3 sprints. Furthermore, the GANTT and the BurnDown chart were implemented. For project management and collaboration, the Trello tool was used throughout.


## **Tools Used**

<details close>
<summary>Click to see more details.</summary>
<br>
<ul>
<li>Only Java, JDK 1.8 and Netbeans 8.2 IDE were used for code writing.</li>
<li>For the database Apache Derby - 10.16.1.1.</li>
<li>For the Graphical User Interface (GUI) the Swing tool was used.</li>
<li>The Trello tool was used for project management and team collaboration.</li>
<li>The ProjectLibre application was used to construct the GANTT chart.</li>
</ul>
</details>


## **Run application**

The application runs with all its capabilities through the Netbeans IDE. As an alternative to the releases, there is a version of the JokerGame-Stats application.


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

