package com.tuitionpackage.demo;

/**
 * This class represents a Student which includes their profile, major, and the number of credits they have completed.
 * 
 */
public abstract class Student implements Comparable<Student> {

    private static final int CREDITS_FOR_SOPHOMORE = 30;
    private static final int CREDITS_FOR_JUNIOR = 60;
    private static final int CREDITS_FOR_SENIOR = 90;
    private static final int MAX_CREDITS = 24;
    private static final int MIN_CREDITS = 3;

    private Profile profile;
    private Major major; //Major is an enum type
    private int creditCompleted;

    /**
     * Constructor for when all three parameters are provided.
     * @param profile the profile of the student which includes first name, last name, and date of birth.
     * @param major the major of the student including the major code and the school name.
     * @param creditCompleted the number of credits completed by the student.
     */
    public Student(Profile profile, Major major, int creditCompleted) {
        this.profile = profile;
        this.major = major;
        this.creditCompleted = creditCompleted;
    }

    /**
     * Constructor for when the number of credits is not provided.
     * @param profile the profile of the student which includes first name, last name, and date of birth.
     * @param major the major of the student including the major code and the school name.
     */
    public Student(Profile profile, Major major) {
        this.profile = profile;
        this.major = major;
    }

    /**
     * Constructor for when only the profile is provided.
     * @param profile the profile of the student which includes first name, last name, and date of birth.
     */
    public Student (Profile profile) {
        this.profile = profile;
        this.major = null;
    }

    /**
     * Constructor for when nothing is provided.
     */
    public Student () {
        this.profile = null;
        this.major = null;
    }

    /**
     * Gets the profile associated with the student.
     * @return this student's profile.
     */
    public Profile getProfile() {
        return this.profile;
    }

    /**
     * Gets the major associated with the student.
     * @return this student's major.
     */
    public Major getMajor() {
        return this.major;
    }

    /**
     * Gets the number of credits associated with the student.
     * @return the number of credits completed by this student.
     */
    public int getCreditCompleted() {
        return this.creditCompleted;
    }

    /**
     * Gets the seniority of the student based on the number of credits they have completed.
     * @return a String that represents the student's seniority.
     */
    public String getSeniority() {
        if (creditCompleted < CREDITS_FOR_SOPHOMORE) {
            return "Freshman";
        } else if (creditCompleted < CREDITS_FOR_JUNIOR) {
            return "Sophomore";
        } else if (creditCompleted < CREDITS_FOR_SENIOR) {
            return "Junior";
        } else {
            return "Senior";
        }
    }

    /**
     * An override of the compareTo method.
     * @param student the object to be compared.
     * @return 0 if the students are equal, 1 if this student is lexicographically greater than the student given as a parameter, -1 otherwise.
     */
    @Override
    public int compareTo(Student student) {
        return this.getProfile().compareTo(student.profile);
    }

    /**
     * An override of the equals method.
     * @param object of type Student.
     * @return returns true if the student's profiles are the same, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Student)) {
            return false;
        }
        Student checkStudent = (Student) object;
        return (this.profile.equals(checkStudent.profile));
    }

    /**
     * An override of the toString method.
     * @return a String representation of this student.
     */
    @Override
    public String toString() {
        return profile.toString() + " (" + major.getMajorCode() + " " + major.getMajorName() + " " + major.getSchoolNames() + ") " +
                "credits completed: " + getCreditCompleted() + " (" + getSeniority() + ")";
    }

    /**
     * Calculates the tuition that a student owes for a semester and returns it as a double
     * @param creditsEnrolled how many credits a Student is currently enrolled in for a semester
     * @return the final amount of money due to pay for the semester
     */
    public abstract double tuitionDue(int creditsEnrolled);

    /**
     * Checks if the number of credits that a student is enrolled in is valid.
     * @param creditEnrolled number of credits enrolled.
     * @return true if the number of credits enrolled is valid, false otherwise.
     */
    public boolean isValid(int creditEnrolled) {
        return (MIN_CREDITS <= creditEnrolled && creditEnrolled <= MAX_CREDITS);
    }

    /**
     * Checks if the student is a resident.
     * @return whether a student is a resident.
     */
    public abstract boolean isResident();

}
