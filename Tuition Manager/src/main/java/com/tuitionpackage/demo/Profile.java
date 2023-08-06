package com.tuitionpackage.demo;

/**
 * This class represents a Profile which includes a first name, last name, and date of birth.
 * 
 */
public class Profile implements Comparable<Profile> {
    private String lname;
    private String fname;
    private Date dob;

    /**
     * Constructor for when all three parameters are provided.
     * @param lname the last name of the person.
     * @param fname the first name of the person.
     * @param dob the date of birth of the person.
     */
    public Profile(String lname, String fname, Date dob) {
        this.lname = lname;
        this.fname = fname;
        this.dob = dob;
    }

    /**
     * Constructor for when no parameters are provided.
     */
    public Profile() {
        this.lname = null;
        this.fname = null;
        this.dob = null;
    }

    /**
     * Gets the last name associated with the profile.
     * @return the person's last name.
     */
    public String getlname() {
        return this.lname;
    }

    /**
     * Gets the first name associated with the profile.
     * @return the person's first name.
     */
    public String getfname() {
        return this.fname;
    }

    /**
     * Gets the date of birth associated with the profile.
     * @return the person's date of birth.
     */
    public Date getDob() {
        return this.dob;
    }

    /**
     * An override of the compareTo method that first checks last name, then first name, then date of birth.
     * @param altProfile the object to be compared.
     * @return 0 if the students are equal, 1 if this student is lexicographically greater than the student given as a parameter, -1 otherwise.
     */
    @Override
    public int compareTo(Profile altProfile) {
        if (this.lname.compareTo(altProfile.getlname()) > 0) {
            return 1;
        }
        if (this.lname.compareTo(altProfile.getlname()) < 0) {
            return -1;
        }
        if (this.fname.compareTo(altProfile.getfname()) > 0) {
            return 1;
        }
        if (this.fname.compareTo(altProfile.getfname()) < 0) {
            return -1;
        }
        if(this.dob.compareTo(altProfile.getDob()) < 0) {
            return 1;
        }
        if(this.dob.compareTo(altProfile.getDob()) > 0) {
            return -1;
        }
        return 0;
    }

    /**
     * An override of the toString method.
     * @return a String representation of this profile.
     */
    @Override
    public String toString() {
        return this.fname + " " + this.lname + " " + this.dob.toString();
    }

    /**
     * An override of the equals method.
     * @param object of type Profile.
     * @return returns true if the student's profiles are the same, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Profile checkProfile)) {
            return false;
        }
        return ((this.lname.equalsIgnoreCase(checkProfile.getlname())) && (this.fname.equalsIgnoreCase(checkProfile.getfname())) && (this.dob.equals(checkProfile.getDob())));
    }
}
