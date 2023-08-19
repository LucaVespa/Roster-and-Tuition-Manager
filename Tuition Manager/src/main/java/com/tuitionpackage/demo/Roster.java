package com.tuitionpackage.demo;

/**
 * This class handles the roster array as well as any operations performed on the roster.
 * 
 */
public class Roster {

    private static final int NOT_FOUND = -1;
    private static final int STARTING_NUM_OF_STUDENTS = 0;
    private static final int STARTING_ROSTER_ARRAY_SIZE = 10; //an arbitrary size to initialize the roster array
    private static final int CAPACITY_INCREASE = 4;

    private Student[] roster;
    private int size;

    /**
     * Constructor for Roster.
     * Creates an array of Students with a default length of 10.
     */
    public Roster() {
        size = STARTING_NUM_OF_STUDENTS;
        roster = new Student[STARTING_ROSTER_ARRAY_SIZE];
    }

    /**
     * Gets the student at the given index.
     * @param index the index that has to be checked in the roster array.
     * @return the Student at the given index.
     */
    public Student getStudent(int index) {
        return roster[index];
    }

    /**
     * Gets the size of the roster.
     * @return the number of Students in the roster array.
     */
    public int getSize() {
        return size;
    }

    /**
     * Sorts the roster lexicographically by name.
     */
    public void sortByName() {
        for ( int i = 0; i < size - 1; i++ ) {
            for ( int j = i + 1; j < size; j++ ) {
                if ( roster[i].compareTo(roster[j]) > 0 ) {
                    Student tempStudent = roster[i];
                    roster[i] = roster[j];
                    roster[j] = tempStudent;
                }
            }
        }
    }

    /**
     * Searches for the given student in roster.
     * @param student the student that is searched for in the roster array.
     * @return the index of the given student, NOT_FOUND (-1) if the student is not in the roster array.
     */
    public int find(Student student) {
        for ( int i = 0; i < roster.length; i++ ) {
            if ( roster[i] != null && roster[i].equals(student) ) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Increases the roster array capacity by 4.
     */
    private void grow() {
        Student[] tempRoster = new Student[roster.length + CAPACITY_INCREASE];
        for ( int i = 0; i < roster.length; i++ ) {
            tempRoster[i] = roster[i];
        }
        roster = tempRoster;
    }

    /**
     * Replaces a student at a given index with the student provided.
     * @param index the index of the student that will be replaced.
     * @param student the student that will be added to the roster in place of the one that was removed.
     */
    public void replace (int index, Student student) {
        roster[index] = student;
    }

    /**
     * Adds student to end of array.
     * Does nothing if the student is already in the array.
     * @param student the student to be added to the roster array.
     * @return true if the student was not already in the array, false otherwise.
     */
    public boolean add(Student student) {
        if ( find(student) != NOT_FOUND ) {
            return false;
        }
        if ( roster[roster.length - 1] != null ) {
            grow();
        }
        roster[size] = student;
        size++;
        return true;
    }

    /**
     * Removes a student and maintains the order of the array.
     * Does nothing if the student is not in the array.
     * @param student the student to be removed.
     * @return true if the student was successfully removed, false otherwise.
     */
    public boolean remove(Student student) {
        int removeIndex = find(student);
        if ( removeIndex == NOT_FOUND ) {
            return false;
        }
        for ( int i = removeIndex; i < roster.length - 1; i++ ) {
            roster[i] = roster[i + 1];
        }
        roster[roster.length - 1] = null;
        size--;
        return true;
    }

    /**
     * Checks if the student is in roster.
     * @param student the student that is searched for in the roster array.
     * @return true if the student is in the array, false otherwise.
     */
    public boolean contains(Student student) {
        return find(student) != NOT_FOUND;
    }

    /**
     * Changes the major of a student.
     * @param student the target student.
     * @return true if the student's major was successfully changed, false otherwise.
     */
    public boolean changeMajor(Student student) {
        int index = find(student);
        if ( index == NOT_FOUND) {
            return false;
        }

        Student tempStudent = roster[index];
         if (tempStudent instanceof Resident newStudent) {
            if(roster[index] instanceof Resident newIndex) {
                roster[index] = new Resident(roster[index].getProfile(), student.getMajor(), roster[index].getCreditCompleted(), newIndex.getScholarship());
            }

        } else if (tempStudent instanceof TriState newStudent) {
            if(roster[index] instanceof TriState newIndex) {
                roster[index] = new TriState(roster[index].getProfile(), student.getMajor(), roster[index].getCreditCompleted(), newIndex.getState());
            }

        } else if (tempStudent instanceof International newStudent) {
            if(roster[index] instanceof International newIndex) {
                roster[index] = new International(roster[index].getProfile(), student.getMajor(), roster[index].getCreditCompleted(), newIndex.isStudyAbroad());
            }
        } else if (tempStudent instanceof NonResident newStudent) {
             roster[index] = new NonResident(roster[index].getProfile(), student.getMajor(), roster[index].getCreditCompleted());

         } else{
            return false;
        }
        return true;
    }

    /**
     * Updates the number of credits a student has completed.
     * @param student the target student whose credit's will be updated.
     * @param newCredits the number of additional credits to be added to the student's total completed credits.
     */
    public void updateCreditsCompleted(Student student, int newCredits) {
        int index = find(student);
        if ( index == NOT_FOUND) {
            return;
        }
        student = roster[index];
        if (student instanceof Resident) {
            if(roster[index] instanceof Resident newIndex) {
                roster[index] = new Resident(roster[index].getProfile(), newIndex.getMajor(), roster[index].getCreditCompleted() + newCredits, newIndex.getScholarship());
            }

        } else if (student instanceof TriState) {
            if(roster[index] instanceof TriState newIndex) {
                roster[index] = new TriState(roster[index].getProfile(), newIndex.getMajor(), roster[index].getCreditCompleted() + newCredits, newIndex.getState());
            }

        } else if (student instanceof International) {
            if(roster[index] instanceof International newIndex) {
                roster[index] = new International(roster[index].getProfile(), newIndex.getMajor(), roster[index].getCreditCompleted() + newCredits, newIndex.isStudyAbroad());
            }
        } else if (student instanceof NonResident) {
            roster[index] = new NonResident(roster[index].getProfile(), roster[index].getMajor(), roster[index].getCreditCompleted() + newCredits);

        }
    }

    /**
     * Returns the student as a string including whether they are a Resident, Non-Resident, International, or from New York or Connecticut.
     * @param index the index of the student in the enrollment array.
     * @return the String representing the student.
     */
    public String fullStringOfStudent(int index) {
        String str = roster[index].toString();

        if (roster[index] instanceof Resident) {
            return str + "(resident)";
        } else if (roster[index] instanceof TriState student) {
            return str + "(non-resident)(tri-state:" + student.getState() + ")";
        } else if (roster[index] instanceof International student) {
            if (!student.isStudyAbroad()) {
                return str + "(non-resident)(international)";
            } else {
                return str + "(non-resident)(international:study abroad)";
            }
        } else if (roster[index] instanceof NonResident) {
            return str + "(non-resident)";
        }
        return str;
    }

    /**
     * Checks if the student is a Resident, Non-Resident, International, or Tri-State student and returns it as a string.
     * @param student the student that will be examined.
     * @return a String representation of the type of student that the target student is.
     */
    public String typeOfStudent(Student student) {
        if (student instanceof International newStudent) {
            if (newStudent.isStudyAbroad()) {
                return "International studentstudy abroad";
            } else {
                return "International student";
            }
        } else if (student instanceof TriState newStudent) {
            return "Tri-state " + newStudent.getState();
        } else if (student instanceof Resident) {
            return "Resident";
        } else if (student instanceof NonResident) {
            return "Non-Resident";
        }
        return "Unknown";
    }

    /**
     * Returns a String representation of the roster sorted by profiles.
     * @return a string representing the list to be outputted
     */
    public String print () {
        if(roster[0] == null) {
            return "Student roster is empty!";
        }
        sortByName();
        String list = "** Student roster sorted by last name, first name, DOB **";
        for (int i = 0; i < size; i++) {
            list = list + "\n" + fullStringOfStudent(i);
        }
        list = list + "\n* End of roster *";
        return list;
    }

    /**
     * Returns a String representation of the roster sorted by school major.
     * @return a string representing the list to be outputted
     */
    public String printBySchoolMajor() {
        if(roster[0] == null) {
            return "Student roster is empty!";
        }
        for ( int i = 0; i < size - 1; i++ ) {
            for ( int j = i + 1; j < size; j++ ) {
                if ( roster[i].getMajor().compareTo(roster[j].getMajor()) > 0 ) {
                    Student tempStudent = roster[i];
                    roster[i] = roster[j];
                    roster[j] = tempStudent;
                }
            }
        }
        String list = "** Student roster sorted by school, major **";
        for (int i = 0; i < size; i++) {
            list = list + "\n" + fullStringOfStudent(i);
        }
        list = list + "\n* End of roster *";
        return list;
    }

    /**
     * Returns a String representation of the roster sorted by standing.
     * @return a string representing the list to be outputted
     */
    public String printByStanding() {
        if(roster[0] == null) {
            return "Student roster is empty!";
        }
        for ( int i = 0; i < size - 1; i++ ) {
            for (int j = i + 1; j < size; j++) {
                if ((roster[i].getSeniority().compareTo(roster[j].getSeniority()) > 0)) {
                    Student tempStudent = roster[i];
                    roster[i] = roster[j];
                    roster[j] = tempStudent;
                }
            }
        }

        String list = "** Student roster sorted by standing **";
        for (int i = 0; i < size; i++) {
            list = list + "\n" + fullStringOfStudent(i);
        }
        list = list + "\n* End of roster *";
        return list;
    }
}
