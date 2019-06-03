package edu.handong.analysis.datamodel;

public class Course {
	
	private String courseName;
	private String studentId;
	private String yearMonthGraduated;
	private String firstMajor;
	private String secondMajor;
	private String courseCode;
	private String courseCredit;
	private int yearTaken;
	private int semesterCourseTaken;

	public Course(String line) {
		String strings[] = line.split(",");
		studentId = strings[0];
		yearMonthGraduated = strings[1];
		firstMajor = strings[2];
		secondMajor = strings[3];
		courseCode = strings[4];
		courseName = strings[5];
		courseCredit= strings[6];
		yearTaken =  Integer.parseInt(strings[7]);
		semesterCourseTaken = Integer.parseInt(strings[8]);
		
	}
	public String courseCodegetter() {
		return courseCode;
	}
	public String gradyeargetter() {
		return yearMonthGraduated;
	}
	public int yeargetter() {
		return yearTaken;
	}
	public int semgetter() {
		return semesterCourseTaken;
	}

	public String firstmajorgetter() {
		return firstMajor;
	}
	public String secondmajorgetter() {
		return secondMajor;
	}
	
	public String IDgetter() {
		return studentId;
	}
	public String namegetter() {
		return courseName;
	}
	public String creditgetter() {
		return courseCredit;
	}
	/* Self-define getter and setter if needed*/

}
