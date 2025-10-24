#  ToDoList Firebase App (Android - Java)

A simple and efficient **ToDoList Android application** built using **Java** and **Firebase Authentication**.  
This app allows users to **Sign Up, Login, Add, Update, and Delete daily tasks** with real-time cloud synchronization. It provides a clean and user-friendly interface for task management.


## Features

- **Firebase Authentication**: Secure Login & Register (Email & Password)  
- **Add Tasks**: Users can create personal daily tasks  
- **Update Tasks**: Edit existing tasks anytime  
- **Delete Tasks**: Remove completed or unnecessary tasks   
- **Material Design UI**: Clean and simple interface  


##  Tech Stack

| Layer      | Technology              |
|-----------|------------------------|
| Frontend  | Android (Java)          |
| Backend   | Firebase Authentication |
| Tools     | Android Studio, Gradle  |

---

##  Project Structure
app/
┣ java/
┃ ┣ com.example.todolist/
┃ ┃ ┣ LoginActivity.java
┃ ┃ ┣ SignupActivity.java
┃ ┃ ┣ MainActivity.java
┃ ┃ ┗ TaskAdapter.java
┣ res/
┃ ┣ layout/
┃ ┃ ┣ activity_login.xml
┃ ┃ ┣ activity_main.xml
┃ ┃ ┗ item_task.xml
┃ ┗ drawable/
┗ google-services.json ← (Updated for security)

##  setup instruction

1. **Clone the repository**
   ```bash
   git clone https://github.com/Nira-28/ToDoList-Firebase-App.git
Open the project in Android Studio

Connect Firebase

Go to: Tools → Firebase → Authentication → Email/Password Sign-In

Add your google-services.json file

Download from Firebase Console

Place it inside app/ folder

Sync Gradle and run the app

*** Firebase Security Note
For security, the included google-services.json file has been replaced.

You must add your own Firebase project's google-services.json to run the app.
##  Future Enhancements

Add Task Notifications 

Add Google Sign-In 

Add Task Priority Tags 

Implement Dark Mode     

##  screenshots
Login page: <img width="1918" height="1023" alt="Screenshot 2025-10-13 122022" src="https://github.com/user-attachments/assets/834a6e8d-0552-4bae-889a-6311a28b64ed" />
Firebase authentication: <img width="1919" height="980" alt="Screenshot 2025-10-13 122308" src="https://github.com/user-attachments/assets/2d7ec783-8332-4e28-b6bb-78fa95ec5833" />
Task list:<img width="1885" height="1079" alt="Screenshot 2025-10-13 122540" src="https://github.com/user-attachments/assets/976995a0-eb7e-4a48-9b78-7e9cb51b20d4" />

##  Contributing

Contributions are welcome!
Feel free to open an issue or submit a pull request.

##  Author

Niranjani
linkedin: https://www.linkedin.com/in/niranjani-karunakaran/


