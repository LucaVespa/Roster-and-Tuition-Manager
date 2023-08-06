package com.tuitionpackage.demo;

/**
 * This class extends the Student class and represents a New Jersey resident and how much money they have received in scholarships, if any.
 * 
 */
public class Resident extends Student {

   private static final double RESIDENT_TUITION = 12536;
   private static final double RESIDENT_UNIVERSITY_FEE_FULL_TIME = 3268;
   private static final double RESIDENT_UNIVERSITY_FEE_PART_TIME = 3268 * 0.8;
   private static final double CREDIT_PER_HOUR = 404;
   private static final double FULL_TIME_MIN_CREDITS = 12;
   private static final double FULL_TIME_ADDITIONAL_LIMIT = 16;
   private static final double START_FEE = 0;

   private int scholarship;

   /**
    * Constructor for when all parameters are provided.
    * @param profile the Profile of the student.
    * @param major the Major of the student.
    * @param creditCompleted the number of credits completed by the student.
    * @param scholarship the amount of scholarship money awarded to the student.
    */
   public Resident(Profile profile,Major major,int creditCompleted, int scholarship) {
      super(profile,  major, creditCompleted);
      this.scholarship = scholarship;
   }

   /**
    * Constructor for when the student is has not recieved a scholarship.
    * @param profile the Profile of the student.
    * @param major the Major of the student.
    * @param creditCompleted the number of credits completed by the student.
    */
   public Resident(Profile profile,Major major,int creditCompleted) {
      super(profile, major, creditCompleted);
   }

   /**
    * Gets the total amount of scholarship money earned.
    * @return the amount of scholarship money awarded to the student.
    */
   public int getScholarship() {
      return this.scholarship;
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
         totalTuition = (creditsEnrolled*CREDIT_PER_HOUR) + (RESIDENT_UNIVERSITY_FEE_PART_TIME);
      }
      if ((creditsEnrolled >= FULL_TIME_MIN_CREDITS) && (creditsEnrolled <= FULL_TIME_ADDITIONAL_LIMIT)) {
         totalTuition = (RESIDENT_TUITION) + (RESIDENT_UNIVERSITY_FEE_FULL_TIME) - scholarship;
      }
      if (creditsEnrolled > FULL_TIME_ADDITIONAL_LIMIT) {
         double creditDifference = creditsEnrolled - FULL_TIME_ADDITIONAL_LIMIT;
         totalTuition =
                 (RESIDENT_TUITION) + (creditDifference * CREDIT_PER_HOUR) + (RESIDENT_UNIVERSITY_FEE_FULL_TIME) - scholarship;
      }
      return totalTuition;
   }

   /**
    * Checks if the student is a resident.
    * @return true because the student is a resident.
    */
   @Override
   public boolean isResident() {
      return true;
   }

}
