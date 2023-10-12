# Advanced Programming - s3955624 - Data-Analytics-Hub

#### Student Number: s3955624
#### Student Name: Go Chee Kin

## Tech Stack
**IDE**: Eclipse IDE for Java Developers - 2023-06

**Java**: 20.0.2

**JavaFX**: 20.0.2

**SQLite-JDBC**: 3.34.0

## Getting Started
### Clone the project

```bash
  https://github.com/rmit-kent-go/s3955624-Data-Analytics-Hub
```

### Add .jar files to build path

In ```DataAnalyticsHub/lib``` , select ```sqlite-jdbc-3.34.0.jar``` and right-click to select ```Build Path > Add to Build Path```

In ```DataAnalyticsHub/javafx-sdk-20.0.2/lib``` , select the following jar files and right-click to select ```Build Path > Add to Build Path```:
```bash
  javafx.base.jar
  javafx.controls.jar
  javafx.fxml.jar
  javafx.graphics.jar
  javafx.media.jar
  javafx.swing.jar
  javafx.web.jar
  javafx-swt.jar
```
The jar files will be added as a Reference library.

### Running the application

Go to ```DataAnalyticsHub/src/analytics/Main.java```, right-click to select ```Run As > Run Configurations```.

Go to ```Arguments``` tab, paste the following arguments into ```VM arguments```:
```bash
  --module-path "javafx-sdk-20.0.2/lib" --add-modules=javafx.controls,javafx.fxml,javafx.graphics,javafx.base
```

Make sure to untick the boxes underneath the VM arguments. 

To run, click the ```Apply``` button followed by the ```Run``` button.

## Class Design
- ```analytics```:
  - ```Main```: Entry point for the Data Analytics Hub application.

- ```analytics.model```:
  - ```DatabaseConnection```: Serves as a SQLite Database connection for UserDataabseHandler and PostDatabaseHandler to access, retrieve, save and modify records in DataAnalyticsHub.db.
  - ```Post```: Serves as a Post object with properties include id, content, author, likes, shares and date-time with getter methods to access the private instance variables.
  - ```User```: Serves as a User object with properties include username, password, firstName, lastName and vip with getter methods to access the User's private instance variables.
  - ```PostModel```: Serves as the Model for the Post in Data Analytics Hub application.
  - ```UserModel```: Serves as the Model for the User in Data Analytics Hub application.
  - ```PostDatabaseHandler```: Provides CRUD methods for post-related SQL query to access SQLite Database.
  - ```UserDatabaseHandler```: Provides CRUD methods for user-related SQL query to access SQLite Database.
  - ```PostComparator```: Implement the Comparator interface for Post object to sort post based on likes in descending order.
  - ```Validator```: Provides validation logics for user and post related inputs entered by users.

- ```analytics.model.exceptions```:
  - ```ExistedPostIDException```: User-defined exception for invalid post ID integer.
  - ```EmptyInputException```: User-defined exception for empty string.
  - ```InvalidContentException```: User-defined exception for string content with "," included.
  - ```InvalidNegativeIntegerException```: User-defined exception for negative integer.
  - ```InvalidNonPositiveIntegerException```: User-defined exception for non-positive integer.
  - ```InvalidPasswordLengthException```: User-defined exception for incorrect password length.
  - ```UsernameExistedException```: User-defined exception for existed username in SQLite Database.
  - ```UserVerificationFailException```: User-defined exception for failed username and password verification.

- ```analytics.view```:
  - ```DashboardViewer```: Serves as the view for the Dashboard interface presentation.
  - ```EditProfileViewer```: Serves as the view for the Edit Profile interface presentation.
  - ```LoginViewer```: Serves as the view for the Login interface presentation.
  - ```SignUpViewer```: Serves as the view for the Sign Up interface presentation.

- ```analytics.controller```:
  - ```DashboardController```: Serves as the controller for the Dashboard logic.
  - ```EditProfileController```: Serves as the controller for the Edit Profile logic.
  - ```LoginController```: Serves as the controller for the Login logic.
  - ```SignUpController```: Serves as the controller for the Sign Up logic.
