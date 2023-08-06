package com.tuitionpackage.demo;

/**
 * This class extends NonResident and represents someone who lives in either New York or Connecticut.
 *
 */
public class TriState extends NonResident{

   private static final double NON_RESIDENT_TUITION = 29737;
   private static final double NON_RESIDENT_UNIVERSITY_FEE_FULL_TIME = 3268;
   private static final double NON_RESIDENT_UNIVERSITY_FEE_PART_TIME = 3268 * 0.8;
   private static final double CREDIT_PER_HOUR = 966;
   private static final double FULL_TIME_MIN_CREDITS = 12;
   private static final double FULL_TIME_ADDITIONAL_LIMIT = 16;
   private static final double START_FEE = 0;
   private static final int NEW_YORK_DISCOUNT = 4000;
   private static final int CONNECTICUT_DISCOUNT = 5000;
   private static final int NO_DISCOUNT = 0;

   private String state;

   /**
    * Constructor for when all parameters are provided.
    * @param profile the Profile of the student.
    * @param major the Major of the student.
    * @param creditCompleted the number of credits completed by the student.
    * @param state the state the student lives in given that they are from the Tri-State area.
    */
   public TriState(Profile profile, Major major, int creditCompleted, String state) {
      super(profile,  major, creditCompleted);
      this.state = state;
   }

   /**
    * Gets the state that the student lives in.
    * @return the student's home state.
    */
   public String getState() {
      return this.state;
   }

   /**
    * Calculates that a discount that a student receives based on which state they live in.
    * @return the discount that a student receives.
    */
   private int discountByState() {
      if (state.equalsIgnoreCase("ny")) {
         return NEW_YORK_DISCOUNT;
      }
      if (state.equalsIgnoreCase("ct")) {
         return CONNECTICUT_DISCOUNT;
      }
      return NO_DISCOUNT;
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
         totalTuition = (NON_RESIDENT_TUITION) + (NON_RESIDENT_UNIVERSITY_FEE_FULL_TIME) - discountByState();
      }
      if (creditsEnrolled > FULL_TIME_ADDITIONAL_LIMIT) {
         double creditDifference = creditsEnrolled - FULL_TIME_ADDITIONAL_LIMIT;
         totalTuition = (NON_RESIDENT_TUITION) + (creditDifference * CREDIT_PER_HOUR) + (NON_RESIDENT_UNIVERSITY_FEE_FULL_TIME) - discountByState();
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
