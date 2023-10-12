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
