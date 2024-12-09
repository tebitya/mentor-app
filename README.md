# Mentor App Coursework

## Project Description
The Mentor App is a software application designed to efficiently pair mentors and mentees based on criteria such as:
- Mentors currently available
- Mentor current capacity
- Availability of the mentors (including start and end date)

The administrator:
- Can view the list of available mentors and mentees.
- Can make pairings with a user-friendly interface with dropdowns for simple selection.
- Receives error messages when invalid pairings (e.g., missing mentor or mentee) or attempted, ensuring accurate data entry.
- Can confirm pairings, which are stored in a CSV file for future reference.

This application was designed to simplify the process of pair allocation, making it efficient for administrators. Its modular structure and clean coding principles make it easy to maintain and update.

Additionally, the system also has separate portals for mentors and mentees, allowing them to update their availability and submit support requests as needed.

## Key Features
### User-Friendly Interface
- Dropdowns for displaying mentee and mentor usernames.
- Tables for viewing and managing confirmed pairings.
- Buttons for pairing and confirming selections.

### Error Handling
- System validates that the admin has selected both a mentor and mentee for each pair.
- Issues error messages when the criteria are not met.

### Dashboard Navigation
- All three roles can quickly navigate between pages and log out.
- Dashboard is clean and user-friendly.

### Data Persistence
- System stores confirmed pairs in a CSV file for easy access for future retrieval.


### Mentee / Mentor Portals
- Separate portals allow mentees and mentors to:
  - Submit support requests to administrators.
  - Update their availability.

### Modular Design
- The program is divided into main classes such as AdminView and AdminController, making it easy for developers to maintain and extend.
- Follows the MCV pattern:
- #### Model
  - Classes such as Mentor and Mentee store data about the individual users, such as their availability and preferences.
  - The repository classes handle data storage and retrieval.
- #### View
  - Manages the GUI (Graphical User Interface).
  - The AdminView class provides dropdowns, tables, buttons for pairing.
- #### Controller
  - Controls the interactions between the Model and the View.
  - The AdminController class handles the pairing logic and stores the confirmed pairs in a CSV file.
  - This pairing logic is easy to extend for additional criteria such as qualification matching.


## Explanation of the Implementation
Javadoc comments are included for the main classes and methods, such as:
- AdminController
- AdminView
- MenteeView
- MentorView

These comments provide explanation for the different methods and classes present in the software.

## Credits
- This project was developed by Tabitha Blindu at University of Nottingham for the COMP2013 module.
- Special thanks to Dr Marjahan Begum and Mr Ian Knight for their guidance.