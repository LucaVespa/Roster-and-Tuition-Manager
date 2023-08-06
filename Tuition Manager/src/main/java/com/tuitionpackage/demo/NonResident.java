package com.tuitionpackage.demo;

/**
 * This class extends the student class and represents someone who is not a resident of New Jersey.
 * 
 */
public class NonResident extends Student{

   private static final double NON_RESIDENT_TUITION = 29737;
   private static final double NON_RESIDENT_UNIVERSITY_FEE_FULL_TIME = 3268;
   private static final double NON_RESIDENT_UNIVERSITY_FEE_PART_TIME = 3268 * 0.8;
   private static final double CREDIT_PER_HOUR = 966;
   private static final double FULL_TIME_MIN_CREDITS = 12;
   private static final double FULL_TIME_ADDITIONAL_LIMIT = 16;
   private static final double START_FEE = 0;

   /**
    * Constructor for when all parameters are provided.
    * @param profile the Profile of the student.
    * @param major the Major of the student.
    * @param creditCompleted the number of credits completed by the student.
    */
   public NonResident(Profile profile,Major major,int creditCompleted) {
      super(profile, major, creditCompleted);
   }

   /**
    * Constructor for when the number of credits completed by the student is not provided.
    * @param profile the Profile of the student.
    * @param major the Major of the student.
    */
   public NonResident(Profile profile,Major major) {
      super(profile, major);
   }

   /**
    * Constructor for when only the profile is provided.
    * @param profile the Profile of the student.
    */
   public NonResident(Profile profile) {
      super(profile);
   }

   /**
    * An override of the tuitionDue method in the Student class.
    * @param creditsEnrolled how many credits a Student is currently enrolled in for a semester.
    * @return the total tuition due based on the number of credits enrolled and the discount by state.
    */
   @Override
   public double tuitionDue(int creditsEnrolled) {
      double totalTuition = START_FEE;
      if (creditsEnrolled < FULL_TIME_MIN_CREDITS) {
         totalTuition = (creditsEnrolled*CREDIT_PER_HOUR) + (NON_RESIDENT_UNIVERSITY_FEE_PART_TIME);
      }
      if ((creditsEnrolled >= FULL_TIME_MIN_CREDITS) && (creditsEnrolled <= FULL_TIME_ADDITIONAL_LIMIT)) {
         totalTuition = (NON_RESIDENT_TUITION) + (NON_RESIDENT_UNIVERSITY_FEE_FULL_TIME);
      }
      if (creditsEnrolled > FULL_TIME_ADDITIONAL_LIMIT) {
         double creditDifference = creditsEnrolled - FULL_TIME_ADDITIONAL_LIMIT;
         totalTuition = (NON_RESIDENT_TUITION) + (creditDifference * CREDIT_PER_HOUR) + (NON_RESIDENT_UNIVERSITY_FEE_FULL_TIME);
      }
      return totalTuition;
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
