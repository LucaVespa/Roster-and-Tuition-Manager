package com.tuitionpackage.demo;

/**
 * This class extends the NonResident class, representing an International student and whether they are studying abroad.
 * 
 */
public class International extends NonResident{

   private static final double INTERNATIONAL_TUITION = 29737;
   private static final double INTERNATIONAL_UNIVERSITY_FEE = 3268;
   private static final double INTERNATIONAL_HEALTH_FEE = 2650;
   private static final double CREDIT_PER_HOUR = 966;
   private static final double FULL_TIME_ADDITIONAL_LIMIT = 16;

   private boolean isStudyAbroad;

   /**
    * Constructor for when all parameters are provided.
    * @param profile the Profile of the student.
    * @param major the Major of the student.
    * @param creditCompleted the number of credits completed by the student.
    * @param isStudyAbroad whether the student is studying abroad.
    */
   public International(Profile profile,Major major,int creditCompleted, boolean isStudyAbroad) {
      super(profile, major, creditCompleted);
      this.isStudyAbroad = isStudyAbroad;
   }

   /**
    * Constructor for when it is not specified if the student is studying abroad;
    * @param profile the Profile of the student.
    * @param major the Major of the student.
    * @param creditCompleted the number of credits completed by the student.
    */
   public International(Profile profile,Major major,int creditCompleted) {
      super(profile, major, creditCompleted);
      this.isStudyAbroad = false;
   }

   /**
    * Gets whether the student is studying abroad.
    * @return true if the student is studying abroad, false otherwise.
    */
   public boolean isStudyAbroad() {
      return this.isStudyAbroad;
   }

   /**
    * An override of the tuitionDue method in the Student class.
    * @param creditsEnrolled how many credits a Student is currently enrolled in for a semester.
    * @return the total tuition due based on the number of credits enrolled and the discount by state.
    */
   @Override
   public double tuitionDue(int creditsEnrolled) {
      if(isStudyAbroad) {
         return INTERNATIONAL_UNIVERSITY_FEE + INTERNATIONAL_HEALTH_FEE;
      }
      if (creditsEnrolled < FULL_TIME_ADDITIONAL_LIMIT) {
         return INTERNATIONAL_TUITION + INTERNATIONAL_UNIVERSITY_FEE + INTERNATIONAL_HEALTH_FEE;
      }
      return INTERNATIONAL_TUITION + INTERNATIONAL_UNIVERSITY_FEE + INTERNATIONAL_HEALTH_FEE + (CREDIT_PER_HOUR*(creditsEnrolled - FULL_TIME_ADDITIONAL_LIMIT));
   }

   /**
    * Checks if the student is a resident.
    * @return false because the student is not a resident.
    */
   @Override
   public boolean isResident() {
      return false;
   }
}
