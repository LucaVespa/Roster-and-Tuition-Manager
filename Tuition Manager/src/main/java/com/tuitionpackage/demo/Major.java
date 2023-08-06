package com.tuitionpackage.demo;

/**
 * This class is an enum that represents the data associated with each Major.
 * 
 */
public enum Major {

    BAIT("33:136", "RBS"),
    CS("01:198", "SAS"),
    MATH("01:640", "SAS"),
    ITI("04:547", "SC&I"),
    EE("14:332", "SOE");

    private final String schoolNames;
    private final String majorCode;

    /**
     * Constructs an enum with the parameters associated with the input.
     * @param majorCode the code associated with the input.
     * @param schoolNames the school name associated with the input.
     */
    Major(String majorCode, String schoolNames) {
        this.majorCode = majorCode;
        this.schoolNames = schoolNames;
    }

    /**
     * Gets the major code.
     * @return majorCode.
     */
    public String getMajorCode() {
        return majorCode;
    }

    /**
     * Gets the school name.
     * @return schoolNames.
     */
    public String getSchoolNames() {
        return schoolNames;
    }

    /**
     * Gets the major name.
     * @return the major name that corresponds to the majorCode.
     */
    public String getMajorName() {
        if (this.majorCode.equalsIgnoreCase("01:198")) {
            return "CS";
        }
        if (this.majorCode.equalsIgnoreCase("01:640")) {
            return "MATH";
        }
        if (this.majorCode.equalsIgnoreCase("14:332")) {
            return "EE";
        }
        if (this.majorCode.equalsIgnoreCase("04:547")) {
            return "ITI";
        }
        if (this.majorCode.equalsIgnoreCase("33:136")) {
            return "BAIT";
        }
        return null;
    }
}