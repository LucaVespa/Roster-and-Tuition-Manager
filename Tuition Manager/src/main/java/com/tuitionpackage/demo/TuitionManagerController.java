package com.tuitionpackage.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


/**
 * This class functions as the controller for the whole project.
 */
public class TuitionManagerController {

   private static final int MINIMUM_ADD_COMMAND_LENGTH = 6;
   private static final int MAXIMUM_SCHOLARSHIP = 10000;
   private static final int MINIMUM_SCHOLARSHIP = 0;
   private static final int CREDITS_FOR_GRADUATION = 120;
   private static final int MINIMUM_CREDITS = 3;
   private static final int MIN_CREDITS_FULL_TIME = 12;
   private static final int MAXIMUM_CREDITS = 24;
   private static final int EMPTY = 0;

   private Roster roster = new Roster();
   private Scanner input = new Scanner(System.in);
   private Enrollment enrollments = new Enrollment();

   @FXML
   private ToggleGroup major;

   @FXML
   private ToggleGroup Status;

   @FXML
   private TextField completedCreditsRoster;

   @FXML
   private RadioButton ctButton;

   @FXML
   private DatePicker dobRoster;

   @FXML
   private TextField fnameRoster;

   @FXML
   private TextField fnameScholarship;

   @FXML
   private TextField lnameScholarship;

   @FXML
   private DatePicker dobScholarship;

   @FXML
   private TextField fnameEnrollment;

   @FXML
   private TextField lnameEnrollment;

   @FXML
   private DatePicker dobEnrollment;

   @FXML
   private TextField creditsEnrollment;

   @FXML
   private TextField scholarshipRoster;

   @FXML
   private RadioButton internationalButton;

   @FXML
   private TextField lnameRoster;

   @FXML
   private RadioButton majorBAIT;

   @FXML
   private RadioButton majorCS;

   @FXML
   private RadioButton majorEE;

   @FXML
   private RadioButton majorITI;

   @FXML
   private RadioButton majorMATH;

   @FXML
   private TextArea messageBoard;

   @FXML
   private RadioButton nonResidentButton;

   @FXML
   private RadioButton nyButton;

   @FXML
   private RadioButton residentButton;

   @FXML
   private CheckBox studyAbroadBox;

   @FXML
   private RadioButton triStateButton;

   /**
    * Ensures that the correct button options are selectable when clicking the International button on the Roster tab.
    * @param event represents the event associated with clicking this button.
    */
   @FXML
   void internationalSelected(ActionEvent event) {
      ctButton.setSelected(false);
      nyButton.setSelected(false);
      studyAbroadBox.setDisable(false);
      nyButton.setDisable(true);
      ctButton.setDisable(true);
   }

   /**
    * Ensures that the correct button options are selectable when clicking the Non-Resident button on the Roster tab.
    * @param event represents the event associated with clicking this button.
    */
   @FXML
   void nonResidentSelected(ActionEvent event) {
      internationalButton.setDisable(false);
      triStateButton.setDisable(false);
      studyAbroadBox.setSelected(false);
      nyButton.setSelected(false);
      ctButton.setSelected(false);
   }

   /**
    * Ensures that the correct button options are selectable when clicking the Resident button on the Roster tab.
    * @param event represents the event associated with clicking this button.
    */
   @FXML
   void residentSelected(ActionEvent event) {
      triStateButton.setDisable(false);
      internationalButton.setDisable(false);
      ctButton.setDisable(true);
      nyButton.setDisable(true);
      studyAbroadBox.setDisable(true);
   }

   /**
    * Ensures that the correct button options are selectable when clicking the Tri-State button on the Roster tab.
    * @param event represents the event associated with clicking this button.
    */
   @FXML
   void triStateSelected(ActionEvent event) {
      nyButton.setDisable(false);
      ctButton.setDisable(false);
      studyAbroadBox.setDisable(true);
      nyButton.setSelected(true);
   }

   /**
    * Executes the appropriate commands associated with the selections on the Roster tab when the Add button is pressed and everything is filled out correctly.
    * @param event represents the event associated with clicking this button.
    */
   @FXML
   void addStudent_Roster(ActionEvent event) {
      if (!allFilledRoster()) {
         return;
      }
      if(triStateButton.isSelected()) {
         String state = "NY";
         if (ctButton.isSelected()) {
            state = "CT";
         }
         processCommands("AT " + fnameRoster.getText() + " " + lnameRoster.getText() + " " + dobConverter(dobRoster.getValue().toString()) + " " + returnMajorAsString()
                 + " " + completedCreditsRoster.getText() + " " + state);
      } else if (internationalButton.isSelected()) {
         processCommands("AI " + fnameRoster.getText() + " " + lnameRoster.getText() + " " + dobConverter(dobRoster.getValue().toString()) + " " + returnMajorAsString()
                 + " " + completedCreditsRoster.getText() + " " + studyAbroadBox.isSelected());
      } else if (nonResidentButton.isSelected()) {
         processCommands("AN " + fnameRoster.getText() + " " + lnameRoster.getText() + " " + dobConverter(dobRoster.getValue().toString()) + " " + returnMajorAsString()
                 + " " + completedCreditsRoster.getText());
      } else {
         processCommands("AR " + fnameRoster.getText() + " " + lnameRoster.getText() + " " + dobConverter(dobRoster.getValue().toString()) + " " + returnMajorAsString()
                 + " " + completedCreditsRoster.getText());
      }
   }

   /**
    * Executes the commands necessary to delete a student from the roster when the Remove button is pressed on the Roster tab.
    * @param event represents the event associated with clicking this button.
    */
   @FXML
   void removeStudent_Roster(ActionEvent event) {
      messageBoard.setText("");
      Boolean allFilled = true;
      if (fnameRoster.getText().isEmpty()) {
         messageBoard.appendText("First name is empty!\n");
         allFilled = false;
      }
      if (lnameRoster.getText().isEmpty()) {
         messageBoard.appendText("Last name is empty!\n");
         allFilled = false;
      }
      if (dobRoster.getValue() == null) {
         messageBoard.appendText("No date selected!");
         allFilled = false;
      }
      if (!allFilled) {
         return;
      }
      processCommands("R " + fnameRoster.getText() + " " + lnameRoster.getText() + " " + dobConverter(dobRoster.getValue().toString()));
   }

   /**
    * Loads content from a predetermined file into the roster when the Load From File button is pressed on the Roster tab.
    * @param event represents the event associated with clicking this button.
    */
   @FXML
   void LoadFromFile(ActionEvent event) {
      processCommands("LS studentList.txt");
   }

   /**
    * Changes the major of the corresponding student when the Change Major button is pressed on the Roster tab.
    * @param event represents the event associated with clicking this button.
    */
   @FXML
   void changeMajor_Roster(ActionEvent event) {
      messageBoard.setText("");
      Boolean allFilled = true;
      if (fnameRoster.getText().isEmpty()) {
         messageBoard.appendText("First name is empty!\n");
         allFilled = false;
      }
      if (lnameRoster.getText().isEmpty()) {
         messageBoard.appendText("Last name is empty!\n");
         allFilled = false;
      }
      if (completedCreditsRoster.getText().isEmpty()) {
         messageBoard.appendText("Number of credits is empty!\n");
         allFilled = false;
      }
      if (dobRoster.getValue() == null) {
         messageBoard.appendText("No date selected!");
         allFilled = false;
      }
      if (!allFilled) {
         return;
      }
      processCommands("C " + fnameRoster.getText() + " " + lnameRoster.getText() + " " + dobConverter(dobRoster.getValue().toString()) + " " + returnMajorAsString());
   }

   /**
    * Enrolls the desired student when the Enroll button is pressed on the Enroll/Drop tab.
    * @param event represents the event associated with clicking this button.
    */
   @FXML
   void enrollStudent_EnrollmentRoster(ActionEvent event) {
      messageBoard.setText("");
      Boolean allFilled = true;
      if (fnameEnrollment.getText().isEmpty()) {
         messageBoard.appendText("First name is empty!\n");
         allFilled = false;
      }
      if (lnameEnrollment.getText().isEmpty()) {
         messageBoard.appendText("Last name is empty!\n");
         allFilled = false;
      }
      if (creditsEnrollment.getText().isEmpty()) {
         messageBoard.appendText("Number of credits is empty!\n");
         allFilled = false;
      }
      if (dobEnrollment.getValue() == null) {
         messageBoard.appendText("No date selected!");
         allFilled = false;
      }
      if (!allFilled) {
         return;
      }
      processCommands("E " + fnameEnrollment.getText() + " " + lnameEnrollment.getText() + " " + dobConverter(dobEnrollment.getValue().toString()) + " " + creditsEnrollment.getText());
   }

   /**
    * Drops the desired student when the Drop button is pressed on the Enroll/Drop tab.
    * @param event represents the event associated with clicking this button.
    */
   @FXML
   void dropStudent_EnrollmentRoster(ActionEvent event) {
      messageBoard.setText("");
      Boolean allFilled = true;
      if (fnameEnrollment.getText().isEmpty()) {
         messageBoard.appendText("First name is empty!\n");
         allFilled = false;
      }
      if (lnameEnrollment.getText().isEmpty()) {
         messageBoard.appendText("Last name is empty!\n");
         allFilled = false;
      }
      if (dobEnrollment.getValue() == null) {
         messageBoard.appendText("No date selected!");
         allFilled = false;
      }
      if (!allFilled) {
         return;
      }
      if (dobEnrollment.getValue() == null) {
         messageBoard.setText("Missing data in line command.");
         return;
      }
      processCommands("D " + fnameEnrollment.getText() + " " + lnameEnrollment.getText() + " " + dobConverter(dobEnrollment.getValue().toString()));
   }

   /**
    * Updates the scholarship of a student if possible when clicking the Update Scholarship amount on the Scholarship tab.
    * @param event represents the event associated with clicking this button.
    */
   @FXML
   void updateScholarship_Roster(ActionEvent event) {
      messageBoard.setText("");
      Boolean allFilled = true;
      if (fnameScholarship.getText().isEmpty()) {
         messageBoard.appendText("First name is empty!\n");
         allFilled = false;
      }
      if (lnameScholarship.getText().isEmpty()) {
         messageBoard.appendText("Last name is empty!\n");
         allFilled = false;
      }
      if (dobScholarship.getValue() == null) {
         messageBoard.appendText("No date selected!\n");
         allFilled = false;
      }
      if (scholarshipRoster.getText().isEmpty()) {
         messageBoard.appendText("Scholarship amount is empty!");
         allFilled = false;
      }
      if (!allFilled) {
         return;
      }
      processCommands("S " + fnameScholarship.getText() + " " + lnameScholarship.getText() + " " + dobConverter(dobScholarship.getValue().toString()) + " " + scholarshipRoster.getText());
   }

   /**
    * Prints all the students on the roster by school and major when the Print by School and Major button is clicked on the Print tab.
    * @param event represents the event associated with clicking this button.
    */
   @FXML
   void printBySchoolAndMajor_Roster(ActionEvent event) {
      processCommands("PC");
   }

   /**
    * Prints all the students on the roster by standing and roster when the Print by Standing button is clicked on the Print tab.
    * @param event represents the event associated with clicking this button.
    */
   @FXML
   void printByStanding_Roster(ActionEvent event) {
      processCommands("PS");
   }

   /**
    * Prints all the students on the roster by profile when the Print by Profile button is clicked on the Print tab.
    * @param event represents the event associated with clicking this button.
    */
   @FXML
   void printByProfile_Roster(ActionEvent event) {
      processCommands("P");
   }

   /**
    * Prints all the students in RBS when the Print Students in RBS button is clicked on the Print tab.
    * @param event represents the event associated with clicking this button.
    */
   @FXML
   void printRBS_Roster(ActionEvent event) {
      processCommands("L RBS");
   }

   /**
    * Prints all the students in SC&I when the Print Students in SC&I button is clicked on the Print tab.
    * @param event represents the event associated with clicking this button.
    */
   @FXML
   void printSCI_Roster(ActionEvent event) {
      processCommands("L SC&I");
   }

   /**
    * Prints all the students in SAS when the Print Students in SAS button is clicked on the Print tab.
    * @param event represents the event associated with clicking this button.
    */
   @FXML
   void printSAS_Roster(ActionEvent event) {
      processCommands("L SAS");
   }

   /**
    * Prints all the students in SOE when the Print Students in SOE button is clicked on the Print tab.
    * @param event represents the event associated with clicking this button.
    */
   @FXML
   void printSOE_Roster(ActionEvent event) {
      processCommands("L SOE");
   }

   /**
    * Prints all the students on the enrollment roster when the Print Enrolled Students button is clicked on the Print tab.
    * @param event represents the event associated with clicking this button.
    */
   @FXML
   void printEnrolledStudents_EnrollmentRoster(ActionEvent event) {
      processCommands("PE");
   }

   /**
    * Prints all the students on the enrollment roster including their tuition when the Print Tuition Due button is clicked on the Print tab.
    * @param event represents the event associated with clicking this button.
    */
   @FXML
   void printTuitionDue_EnrollmentRoster(ActionEvent event) {
      processCommands("PT");
   }

   /**
    * Prints all the students who may graduate by the end of the semester and removes all students from the enrollment roster when the Semester End button is pressed on the Print tab.
    * @param event represents the event associated with clicking this button.
    */
   @FXML
   void semesterEnd_EnrollmentRoster(ActionEvent event) {
      processCommands("SE");
   }

   /**
    * Copies the First Name, Last Name, and Date of Birth from the Roster tab over to the Enroll/Drop tab when pressing the Copy from Roster button on the Enroll/Drop tab.
    * @param event represents the event associated with clicking this button.
    */
   @FXML
   void copyFromRoster_EnrollmentRoster(ActionEvent event) {
      fnameEnrollment.setText(fnameRoster.getText());
      lnameEnrollment.setText(lnameRoster.getText());
      dobEnrollment.setValue(dobRoster.getValue());
   }

   /**
    * Copies the First Name, Last Name, and Date of Birth from the Enroll/Drop tab over to the Scholarship tab when pressing the Copy from Enrollment button on the Scholarship tab.
    * @param event represents the event associated with clicking this button.
    */
   @FXML
   void copyFromEnrollment_Scholarship(ActionEvent event) {
      fnameScholarship.setText(fnameEnrollment.getText());
      lnameScholarship.setText(lnameEnrollment.getText());
      dobScholarship.setValue(dobEnrollment.getValue());
   }

   /**
    * Checks if every box on the Roster tab is filled.
    * @return true if every box is filled, false otherwise.
    */
   private Boolean allFilledRoster() {
      messageBoard.setText("");
      Boolean allFilled = true;
      if (fnameRoster.getText().isEmpty()) {
         messageBoard.appendText("First name is empty!\n");
         allFilled = false;
      }
      if (lnameRoster.getText().isEmpty()) {
         messageBoard.appendText("Last name is empty!\n");
         allFilled = false;
      }
      if (completedCreditsRoster.getText().isEmpty()) {
         messageBoard.appendText("Number of credits is empty!\n");
         allFilled = false;
      }
      if (dobRoster.getValue() == null) {
         messageBoard.appendText("No date selected!");
         allFilled = false;
      }
      return allFilled;
   }

   /**
    * Converts the date provided by DatePicker into a string that is compatible with the rest of the code.
    * @param oldFormat the old String representation of the date.
    * @return the new String representation of the date.
    */
   private String dobConverter(String oldFormat) {
      String [] dateItems = oldFormat.split("-");
      return dateItems[1] + "/" + dateItems[2] + "/" + dateItems[0];
   }

   /**
    * Returns the major of the student as a String based on which button is selected on the Roster tab.
    * @return a String representation of the student's major.
    */
   private String returnMajorAsString() {
      String major = "";
      if (majorBAIT.isSelected()) {
         major = "BAIT";
      } else if (majorCS.isSelected()) {
         major = "CS";
      } else if (majorEE.isSelected()) {
         major = "EE";
      } else if (majorITI.isSelected()) {
         major = "ITI";
      } else if (majorMATH.isSelected()) {
         major = "MATH";
      }
      return major;
   }

   /**
    * Creates an instance of a NonResident student.
    * @param components the String values needed to create a NonResident student.
    * @return the NonResident student created.
    */
   private Student createNonResident(String [] components) {
      Date newDate = new Date(components[3]);
      Profile newProfile = new Profile(components[2], components[1], newDate);
      if (components[0].equals("S")) {
         return new NonResident(newProfile);
      }
      if (components.length == MINIMUM_ADD_COMMAND_LENGTH - 1) {
         Major newMajor = Major.valueOf(components[4].toUpperCase());
         return new NonResident(newProfile, newMajor);

      }
      if (components.length == MINIMUM_ADD_COMMAND_LENGTH) {
         Major newMajor = Major.valueOf(components[4].toUpperCase());
         return new NonResident(newProfile, newMajor, Integer.parseInt(components[5]));

      }
      return new NonResident(newProfile);
   }

   /**
    * Creates an instance of a Resident student.
    * @param components the String values needed to create a Resident student.
    * @return the Resident student created.
    */
   private Student createResident(String [] components) {
      Date newDate = new Date(components[3]);
      Profile newProfile = new Profile(components[2], components[1], newDate);
      Major newMajor = Major.valueOf(components[4].toUpperCase());
      return new Resident(newProfile, newMajor, Integer.parseInt(components[5]));
   }

   /**
    * Creates an instance of a TriState student.
    * @param components the String values needed to create a TriState student.
    * @return the TriState student created.
    */
   private Student createTriState(String [] components) {
      Date newDate = new Date(components[3]);
      Profile newProfile = new Profile(components[2], components[1], newDate);
      Major newMajor = Major.valueOf(components[4].toUpperCase());
      return new TriState(newProfile, newMajor, Integer.parseInt(components[5]), components[6].toUpperCase());
   }

   /**
    * Creates an instance of an International student.
    * @param components the String values needed to create an International student.
    * @return the International student created.
    */
   private Student createInternational(String [] components) {
      Date newDate = new Date(components[3]);
      Profile newProfile = new Profile(components[2], components[1], newDate);
      Major newMajor = Major.valueOf(components[4].toUpperCase());
      if (components.length == MINIMUM_ADD_COMMAND_LENGTH) {
         return new International(newProfile, newMajor, Integer.parseInt(components[5]));
      }
      return new International(newProfile, newMajor, Integer.parseInt(components[5]), Boolean.parseBoolean(components[6]));
   }

   /**
    * Creates a student of type EnrollmentStudent.
    * @param components the String values needed to create an EnrollmentStudent.
    * @return the EnrollmentStudent created.
    */
   private EnrollStudent createEnrollmentStudent(String [] components) {
      Date newDate = new Date(components[3]);
      Profile newProfile = new Profile(components[2], components[1], newDate);
      if (components.length == 4) {
         return new EnrollStudent(newProfile);
      }
      return new EnrollStudent(newProfile, Integer.parseInt(components[4]));
   }

   /**
    * Adds a resident student to the roster array.
    * @param components the String values needed to create a resident student.
    * @param isLSCommand checks if the command was called from the "executeCommandLS" function.
    */
   private void addNonResident(String [] components, Boolean isLSCommand) {
      addStudent(createNonResident(components), isLSCommand);
   }

   /**
    * Adds a non-resident student to the roster array.
    * @param components the String values needed to create a non-resident student.
    * @param isLSCommand checks if the command was called from the "executeCommandLS" function.
    */
   private void addResident(String [] components, Boolean isLSCommand) {
      addStudent(createResident(components), isLSCommand);
   }

   /**
    * Adds a tri-state student to the roster array and outputs any error messages.
    * @param components the String values needed to create a tri-state student.
    * @param isLSCommand checks if the command was called from the "executeCommandLS" function.
    */
   private void addTriState(String [] components, Boolean isLSCommand) {
      if(components.length == MINIMUM_ADD_COMMAND_LENGTH) {
         messageBoard.setText("Missing the state code.");
         return;
      }
      if(!(components[6].equalsIgnoreCase("NY") || components[6].equalsIgnoreCase("CT"))) {
         messageBoard.setText(components[6] + ": Invalid state code.");
         return;
      }
      addStudent(createTriState(components), isLSCommand);
   }

   /**
    * Adds an international student to the roster array.
    * @param components the String values needed to create an International student.
    * @param isLSCommand checks if the command was called from the "executeCommandLS" function.
    */
   private void addInternational(String [] components, Boolean isLSCommand) {
      addStudent(createInternational(components), isLSCommand);
   }


   /**
    * Check if the inputted Date is valid and print the according error messages if not.
    * @param student the student whose date of birth will be checked.
    * @return true if the date of birth is valid, false otherwise.
    */
   private boolean checkValidDate(Student student) {
      if(!student.getProfile().getDob().isValid()) {
         messageBoard.setText("DOB invalid: " + student.getProfile().getDob().toString() + " not a valid calendar date!");
         return false;
      }
      if(!student.getProfile().getDob().isOldEnough()) {
         messageBoard.setText("DOB invalid: " + student.getProfile().getDob().toString() + " younger than 16 years old.");
         return false;
      }
      return true;
   }

   /**
    * Check if the inputted Major is valid.
    * @param str the string input for the major.
    * @return true if the major is a valid major, false otherwise.
    */
   private boolean checkIfValidMajor (String str ) {
      return (str.equalsIgnoreCase("CS") || str.equalsIgnoreCase("BAIT") || str.equalsIgnoreCase("MATH") || str.equalsIgnoreCase("ITI") || str.equalsIgnoreCase("EE"));
   }

   /**
    * Check if the inputted School is valid.
    * @param str the string input for the school.
    * @return true if the major is a valid major, false otherwise.
    */
   private boolean checkIfValidSchool (String str) {
      return (str.equalsIgnoreCase("RBS") || str.equalsIgnoreCase("SAS") || str.equalsIgnoreCase("SC&I") || str.equalsIgnoreCase("SOE"));
   }

   /**
    * Check if the inputted Number of Credits is valid and print the according error messages if not.
    * @param str the String input for the number of credits.
    * @return true if the number of credits is valid, false otherwise.
    */
   private boolean checkIfValidCredits (String str) {
      try {
         int test = Integer.parseInt(str);
         if (test < 0) {
            messageBoard.setText("Credits completed invalid: cannot be negative!");
            return false;
         }
      } catch (NumberFormatException ex) {
         messageBoard.setText("Credits completed invalid: not an integer!");
         return false;
      }
      return true;
   }

   /**
    * Checks if the number of credits a student is taking for the semester is valid.
    * @param commands the String inputs needed to verify if the number of credits taken is valid.
    * @return true if the number of credits enrolled is valid, false otherwise.
    */
   private boolean checkIfValidCreditHours(String [] commands) {
      int credits = Integer.parseInt(commands[4]);
      Date newDate = new Date(commands[3]);
      Profile newProfile = new Profile(commands[2], commands[1], newDate);
      Student student = new NonResident(newProfile);
      student = roster.getStudent(roster.find(student));
      if(!student.isValid(credits)) {
         return false;
      }
      if (student instanceof International intStudent) {
         if (intStudent.isStudyAbroad()) {
            return credits >= MINIMUM_CREDITS && credits <= MIN_CREDITS_FULL_TIME;
         }
         return credits >= MIN_CREDITS_FULL_TIME && credits <= MAXIMUM_CREDITS;
      } else {
         return credits >= MINIMUM_CREDITS && credits <= MAXIMUM_CREDITS;
      }
   }

   /**
    * Performs the necessary checks before adding a student to the roster and prints the corresponding message.
    * @param student the Student to be added to the roster
    */
   private void addStudent (Student student, boolean isLSCommand) {
      if (!checkValidDate(student)) {
         return;
      }
      if (roster.contains(student)) {
         messageBoard.setText(student.getProfile().toString() + " is already in the roster.");
         return;
      }
      roster.add(student);
      if (!isLSCommand) {
         messageBoard.setText(student.getProfile().toString() + " added to the roster.");
      }
   }

   /**
    * Executes the code associated with the command "L".
    * @param components the String values needed to execute command L.
    */
   private void executeCommandL(String [] components) {
      if (!checkIfValidSchool(components[1])) {
         messageBoard.setText("School doesn't exist: " + components[1]);
         return;
      }
      int count = 0;
      for (int i = 0; i < roster.getSize(); i++) {
         if (roster.getStudent(i).getMajor().getSchoolNames().equalsIgnoreCase(components[1])) {
            count++;
         }
      }
      if (count == 0) {
         messageBoard.setText("There are no students in " + components[1] + ".");
         return;
      }
      messageBoard.setText("** Students in " + components[1] + " **");
      roster.sortByName();
      for (int i = 0; i < roster.getSize(); i++) {
         if (roster.getStudent(i).getMajor().getSchoolNames().equalsIgnoreCase(components[1])) {
            messageBoard.appendText("\n" + roster.getStudent(i).toString());
         }
      }
      messageBoard.appendText("\n* End of list *");
   }

   /**
    * Executes the code associated with the command "LS".
    * @param commands the String values needed to execute command LS.
    */
   private void executeCommandLS (String [] commands) {
      Scanner loadFile;
      try{
         loadFile = new Scanner(new File(commands[1]));
      } catch (FileNotFoundException e) {
         messageBoard.setText("File not found.");
         return;
      }
      while (loadFile.hasNextLine()) {
         String newInputFullString = loadFile.nextLine();
         String[] newInputs = newInputFullString.split(",");
         if (newInputs[0].equals("R")) {
            addResident(newInputs, true);
         } else if (newInputs[0].equals("N")) {
            addNonResident(newInputs, true);
         } else if (newInputs[0].equals("I")) {
            addInternational(newInputs, true);
         } else if (newInputs[0].equals("T")) {
            addTriState(newInputs, true);
         }
      }
      messageBoard.setText("Students loaded to the roster.");
   }

   /**
    * Executes the code associated with the command "A".
    * @param commands the Strings values needed to create the Student.
    */
   private void executeCommandA(String [] commands) {
      if (commands.length < MINIMUM_ADD_COMMAND_LENGTH) {
         if (commands[0].length() == 2 && commands.length <= 3) {
            messageBoard.setText("Missing data in line command.");
            return;
         }
         messageBoard.setText("Missing data in line command.");
         return;
      }
      if (checkIfValidMajor(commands[4]) && checkIfValidCredits(commands[5])) {
         if (commands[0].charAt(1) == 'N') {
            addNonResident(commands, false);
         } else if (commands[0].charAt(1) == 'R') {
            addResident(commands, false);
         } else if (commands[0].charAt(1) == 'T') {
            addTriState(commands, false);
         } else if (commands[0].charAt(1) == 'I') {
            addInternational(commands, false);
         } else {
            messageBoard.setText("Invalid command");
         }
      }else if (!checkIfValidMajor(commands[4])) {
         messageBoard.setText("Major code invalid: " + commands[4]);
      }
   }


   /**
    * Executes the code associated with the command "R".
    * @param commands the Strings values needed to create the Student.
    */
   private void executeCommandR(String [] commands) {
      Student studentToRemove = createNonResident(commands);
      if (roster.contains(studentToRemove)) {
         roster.remove(studentToRemove);

         EnrollStudent enrollStudent = createEnrollmentStudent(commands);
         String enrollmentMessage = "";
         if (enrollments.contains((enrollStudent))) {
            commands[0] = "D";
            executeCommandD(commands);
            enrollmentMessage = "\nStudent also dropped from enrollment roster.";
         }
         messageBoard.setText(studentToRemove.getProfile().toString() + " removed from the roster." + enrollmentMessage);
      } else {
         messageBoard.setText(studentToRemove.getProfile().toString() + " is not in the roster.");
      }
   }

   /**
    * Executes the code associated with the command "C".
    * @param commands the Strings values needed to create the Student.
    */
   private void executeCommandC(String [] commands) {
      if (checkIfValidMajor(commands[4])) {
         Student studentToChange = createNonResident(commands);
         if (roster.contains(studentToChange)) {
            roster.changeMajor(studentToChange);
            messageBoard.setText(studentToChange.getProfile().toString() + " major changed to " + commands[4]);
         } else {
            messageBoard.setText(studentToChange.getProfile().toString() + " is not in the roster.");
         }
      }else {
         messageBoard.setText("Major code invalid: " + commands[4]);
      }
   }

   /**
    * Executes the code associated with the command "E".
    * @param commands the String values needed to execute command E.
    */
   private void executeCommandE(String [] commands) {
      if (commands.length < 5) {
         messageBoard.setText("Missing data in line command.");
         return;
      }
      try {
         Integer.parseInt(commands[4]);
      } catch (NumberFormatException ex) {
         messageBoard.setText("Credits enrolled is not an integer.");
         return;
      }
      Student student = new NonResident(new Profile(commands[2], commands[1], new Date(commands[3])));
      if (!roster.contains(student)) {
         messageBoard.setText("Cannot enroll: " + commands[1] + " " + commands[2] + " " + commands[3] + " is not in the roster.");
         return;
      }
      if (!checkIfValidCreditHours(commands)) {
         if (roster.getStudent(roster.find(student)) instanceof International intStudent) {
            if (intStudent.isStudyAbroad()) {
               messageBoard.setText("(International student study abroad) " + commands[4] + ": invalid credit hours.");
               return;
            }
            messageBoard.setText("(International student) " + commands[4] + ": invalid credit hours.");
            return;
         } else if (roster.getStudent(roster.find(student)) instanceof Resident) {
            messageBoard.setText("(Resident) " + commands[4] + ": invalid credit hours.");
            return;
         }
         messageBoard.setText("(Non-Resident) " + commands[4] + ": invalid credit hours.");
         return;
      }
      EnrollStudent enrollStudent = createEnrollmentStudent(commands);
      if(enrollments.contains(enrollStudent)) {
         messageBoard.setText(student.getProfile().toString() + " is already enrolled.");
         return;
      }
      enrollments.add(createEnrollmentStudent(commands));
      messageBoard.setText(student.getProfile().toString() + " enrolled " + Integer.parseInt(commands[4]) + " credits");
   }

   /**
    * Executes the code associated with the command "D".
    * @param commands the String values needed to execute command D.
    */
   private void executeCommandD(String [] commands) {
      EnrollStudent student = createEnrollmentStudent(commands);
      if (!enrollments.contains(student)) {
         messageBoard.setText(student.getProfile().toString() + " is not enrolled.");
         return;
      }
      messageBoard.setText(student.getProfile().toString() + " dropped.");
      enrollments.remove(student);
   }

   /**
    * Executes the code associated with the command "S".
    * @param commands the String values needed to execute command S.
    */
   private void executeCommandS(String [] commands) {
      if (commands.length < 4) {
         messageBoard.setText("Missing data in line command.");
         return;
      }
      Student student = new NonResident(new Profile(commands[2], commands[1], new Date(commands[3])));
      if (!roster.contains(student)) {
         messageBoard.setText(student.getProfile().toString() + " is not in the roster.");
         return;
      }
      try {
         Integer.parseInt(commands[4]);
      } catch (NumberFormatException ex) {
         messageBoard.setText("Amount is not an integer.");
         return;
      }
      int index = roster.find(student);
      if (roster.getStudent(index).isResident()) {
         if(Integer.parseInt(commands[4]) > MAXIMUM_SCHOLARSHIP || Integer.parseInt(commands[4]) <= MINIMUM_SCHOLARSHIP) {
            messageBoard.setText(Integer.parseInt(commands[4]) + ": invalid amount.");
            return;
         }
         EnrollStudent newEnrollment = new EnrollStudent(student.getProfile());
         if (!enrollments.contains(newEnrollment)) {
            messageBoard.setText("Student is not currently enrolled.");
            return;
         }
         newEnrollment = enrollments.getStudentAt(enrollments.find(newEnrollment));
         if (newEnrollment.getCreditsEnrolled() < MIN_CREDITS_FULL_TIME) {
            messageBoard.setText(newEnrollment.getProfile().toString() + " part time student is not eligible for the scholarship.");
            return;
         }
         Student replacement = new Resident(roster.getStudent(index).getProfile(),  roster.getStudent(index).getMajor(),  roster.getStudent(index).getCreditCompleted(), Integer.parseInt(commands[4]));
         roster.replace(index, replacement);
         messageBoard.setText(replacement.getProfile().toString() + ": scholarship amount updated.");
      } else {
         messageBoard.setText(roster.getStudent(index).getProfile().toString() + " (" + roster.typeOfStudent(roster.getStudent(index)) + ") is not eligible for the scholarship.");
      }
   }

   /**
    * Prints out every EnrollStudent in the enrollments array.
    */
   private void executeCommandPE() {
      messageBoard.setText(enrollments.print());
   }

   /**
    * Prints out the full profile and the amount of tuition due for every student in the enrollments array.
    */
   private void executeCommandPT() {
      if (enrollments.getSize() == EMPTY) {
         messageBoard.setText("Enrollment roster is empty!");
         return;
      }
      messageBoard.setText("** Tuition due **");
      for(int i = 0; i < enrollments.getSize(); i++) {
         Student student = new NonResident(enrollments.getStudentAt(i).getProfile());
         messageBoard.appendText("\n" + enrollments.getStudentAt(i).getProfile().toString() + " (" + roster.typeOfStudent(roster.getStudent(roster.find(student))) + ") enrolled "
                 + enrollments.getStudentAt(i).getCreditsEnrolled() + " credits: tuition due: $");
         messageBoard.appendText(String.format("%,.2f", roster.getStudent(roster.find(student)).tuitionDue(enrollments.getStudentAt(i).getCreditsEnrolled())));
      }
      messageBoard.appendText("\n* End of tuition due *");
   }

   /**
    * Executes the code associated with the command "SE".
    */
   private void executeCommandSE() {
      messageBoard.setText("The number of credits completed has been updated for each student and everyone has been removed from the enrollment roster.\n");
      messageBoard.appendText("\n** List of students eligible for graduation **");

      for (int i = 0; i < enrollments.getSize(); i++) {
         Student student = new NonResident(enrollments.getStudentAt(i).getProfile());
         roster.updateCreditsCompleted(student, enrollments.getStudentAt(i).getCreditsEnrolled());
      }
      for (int i = 0; i < roster.getSize(); i++) {
         if (roster.getStudent(i).getCreditCompleted() >= CREDITS_FOR_GRADUATION) {
            messageBoard.appendText("\n" + roster.fullStringOfStudent(i));
         }
      }
      messageBoard.appendText("\n* End of students who may graduate *");
      enrollments = new Enrollment();
   }

   /**
    * Manages the additional set of commands that the program should be able to handle.
    * @param commands the String array that contains each individual command in the read line.
    * @param commandsString the full String that represents a command line.
    */
   private void scanForNewCommands(String [] commands, String commandsString) {
      if (commands[0].equals("LS")) {
         executeCommandLS(commands);
      } else if (commands[0].equals("D")) {
         executeCommandD(commands);
      } else if (commands[0].equals("E")) {
         executeCommandE(commands);
      } else if (commands[0].equals("S")) {
         executeCommandS(commands);
      } else if (commands[0].equals("PE")) {
         executeCommandPE();
      } else if (commands[0].equals("PT")) {
         executeCommandPT();
      } else if (commands[0].equals("SE")) {
         executeCommandSE();
      } else {
         messageBoard.setText(commandsString + " is an invalid command!");
      }
   }

   /**
    * Runs the program.
    */
   public void processCommands(String commandsString) {
      String[] commands = commandsString.split("\\s+");
      if (commandsString.length() == 0) {
         //Do not do anything if the input line is blank
      }else if (commands[0].charAt(0) == 'A') {
         executeCommandA(commands);
      }else if (commands[0].equals("R")) {
         executeCommandR(commands);
      } else if (commands[0].equals("P")) {
         messageBoard.setText(roster.print());
      } else if (commands[0].equals("PS")) {
         messageBoard.setText(roster.printByStanding());
      } else if (commands[0].equals("PC")) {
         messageBoard.setText(roster.printBySchoolMajor());
      } else if (commands[0].equals("C")) {
         executeCommandC(commands);
      } else if (commands[0].equals("L")) {
         executeCommandL(commands);
      } else {
         scanForNewCommands(commands, commandsString);
      }
   }
}
