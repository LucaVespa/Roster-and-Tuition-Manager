module com.tuitionpackage.demo {
   requires javafx.controls;
   requires javafx.fxml;
   //requires org.testng;

   opens com.tuitionpackage.demo to javafx.fxml;
   exports com.tuitionpackage.demo;
}