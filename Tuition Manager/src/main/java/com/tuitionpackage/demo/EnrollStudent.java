package com.tuitionpackage.demo;


/**
 * This class represents a student enrolled in a semester which includes their profile and the number of credits they are taking during the semester.
 * 
 */
public class EnrollStudent {

   private Profile profile;
   private int creditsEnrolled;

   /**
    * A constructor for when both the profile and the number of credits enrolled are provided.
    * @param profile the Profile of the student.
    * @param creditsEnrolled the number of credits that this student is currently enrolled in.
    */
   public EnrollStudent(Profile profile, int creditsEnrolled) {
      this.profile = profile;
      this.creditsEnrolled = creditsEnrolled;
   }

   /**
    * A constructor for when only the profile is provided.
    * @param profile the Profile of the student;
    */
   public EnrollStudent(Profile profile) {
      this.profile = profile;
   }

   /**
    * Gets the profile associated with the student.
    * @return this student's profile.
    */
   public Profile getProfile() {
      return profile;
   }

   /**
    * Gets the number of credits that this student is currently enrolled it.
    * @return the student's current number of credits for the semester.
    */
   public int getCreditsEnrolled() {
      return creditsEnrolled;
   }

   /**
    * An override of the equals method.
    * @param object of type enrollStudent.
    * @return returns true if the student's profiles are the same, false otherwise.
    */
   @Override
   public boolean equals(Object object) {
      if (!(object instanceof EnrollStudent checkStudent)) {
         return false;
      }
      return this.profile.equals(checkStudent.getProfile());
   }

   /**
    * An override of the toString method.
    * @return a String representation of this student that includes the current number of credits enrolled.
    */
   @Override
   public String toString() {
      return this.profile.toString() + ": credits enrolled: " + this.creditsEnrolled;
   }
}
