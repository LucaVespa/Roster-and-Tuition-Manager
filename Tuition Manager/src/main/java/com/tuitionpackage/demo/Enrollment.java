package com.tuitionpackage.demo;

/**
 * This class handles the enrollment array as well as any operations performed on the roster.
 */
public class Enrollment {

   private static final int NOT_FOUND = -1;
   private static final int INITIAL_ARR_SIZE = 10;
   private static final int CAPACITY_INCREASE = 4;
   private static final int STARTING_NUM_OF_STUDENTS = 0;

   private EnrollStudent[] enrollStudents;
   private int size;

   /**
    * Constructor for Enrollment.
    * Creates an array of Students with a default length of 10.
    */
   public Enrollment() {
      this.size = STARTING_NUM_OF_STUDENTS;
      this.enrollStudents = new EnrollStudent[INITIAL_ARR_SIZE];
   }

   /**
    * Gets the size of the enrollment array.
    * @return the number of Students in the enrollment array.
    */
   public int getSize() {
      return size;
   }

   /**
    * Gets the student at the given index in the enrollment roster.
    * @param index the index to be accessed in the roster.
    * @return the EnrollStudent at the given index.
    */
   public EnrollStudent getStudentAt(int index) {
      return enrollStudents[index];
   }

   /**
    * Increases the enrollment array capacity by 4.
    */
   private void grow() {
      EnrollStudent[] enrollStudents2 = new EnrollStudent[size+CAPACITY_INCREASE];
      for ( int i = 0; i < enrollStudents.length; i++ ) {
         enrollStudents2[i] = enrollStudents[i];
      }
      enrollStudents = enrollStudents2;
   }

   /**
    * Adds student to end of array.
    * Does nothing if the student is already in the array.
    * @param enrollStudent the student to be added to the enrollment array.
    * @return true if the student was not already in the array, false otherwise.
    */
   public void add(EnrollStudent enrollStudent) {
      if (contains(enrollStudent)) {
         enrollStudents[find(enrollStudent)] = enrollStudent;
         return;
      }
      if (enrollStudents[enrollStudents.length - 1] != null) {
         grow();
      }
      enrollStudents[size] = enrollStudent;
      size++;
   }

   /**
    * Searches for the given student in enrollment array.
    * @param enrollStudent the student that is searched for in the enrollment array.
    * @return the index of the given student, NOT_FOUND (-1) if the student is not in the enrollment array.
    */
   public int find(EnrollStudent enrollStudent) {
      for ( int i = 0; i < size; i++ ) {
         if ( enrollStudents[i].equals(enrollStudent) ) {
            return i;
         }
      }
      return NOT_FOUND;
   }

   /**
    * Removes a student and moves the last student in the array to replace the deleted student.
    * Does nothing if the student is not in the array.
    * @param enrollStudent the student to be removed.
    */
   public void remove(EnrollStudent enrollStudent) {
      if(size <= 0) {
         return;
      }
      if(size == 1) {
         enrollStudents[0] = null;
         size--;
         return;
      }
      int deleteIndex = find(enrollStudent);
      size--;
      enrollStudents[deleteIndex] = enrollStudents[size];
      enrollStudents[size] = null;
   }

   /**
    * Checks if the student is in enrollment array.
    * @param enrollStudent the student that is searched for in the enrollment array.
    * @return true if the student is in the array, false otherwise.
    */
   public boolean contains(EnrollStudent enrollStudent) {
      for ( int i = 0; i < size; i++ ) {
         if (enrollStudents[i].equals(enrollStudent) ) {
            return true;
         }
      }
      return false;
   }

   /**
    * Return a string that represents the array as is without sorting it.
    * @return the string that represents the whole enrollment roster.
    */
   public String print() {
      if(size == 0) {
         return "Enrollment roster is empty!";
      }
      String returnString = "** Enrollment **";
      for (int i = 0; i < size; i++) {
         returnString = returnString + "\n" + enrollStudents[i].toString();
      }
      returnString = returnString + "\n* End of enrollment *";
      return returnString;
   }
}
