package edu.handong.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.math3.geometry.euclidean.twod.Line;

import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysis.utils.NotEnoughArgumentException;
import edu.handong.analysis.utils.Utils;

public class HGUCoursePatternAnalyzer {

	private HashMap<String,Student> students;
	
	/**
	 * This method runs our analysis logic to save the number courses taken by each student per semester in a result file.
	 * Run method must not be changed!!
	 * @param args
	 */
	public void run(String[] args) {
		
		try {
			// when there are not enough arguments from CLI, it throws the NotEnoughArgmentException which must be defined by you.
			if(args.length < 2)
				throw new NotEnoughArgumentException();
		} catch (NotEnoughArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		String dataPath = args[0]; // csv file to be analyzed
		String resultPath = args[1]; // the file path where the results are saved.
		ArrayList<String> lines = Utils.getLines(dataPath, true);
		
		

		students = loadStudentCourseRecords(lines);
		
		// To sort HashMap entries by key values so that we can save the results by student ids in ascending order.
		Map<String, Student> sortedStudents = new TreeMap<String,Student>(students); 
		
		
	
		// Generate result lines to be saved.
		ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);
		
		// Write a file (named like the value of resultPath) with linesTobeSaved.
		Utils.writeAFile(linesToBeSaved, resultPath);
	}
	
	/**
	 * This method create HashMap<String,Student> from the data csv file. 
	 * Key is a student id and the corresponding object is an instance of Student.
	 * The Student instance have all the Course instances taken by the student.
	 * @param lines
	 * @return
	 */
	
	private HashMap<String,Student> loadStudentCourseRecords(ArrayList<String> lines) {
		HashMap<String, Student> hmap = new HashMap<String, Student>();
		
		/*
		for(int q = 0; q < 20; q++) {
			System.out.println("Idx["+q+"] "+lines.get(q));
		}
		*/
		// TODO: Implement this method
		int stunum = 0;
		int j = 0;

		String temps = "";
		
		for( stunum = 0; stunum < lines.size(); stunum=stunum+9)
		{
			String _stuNum = lines.get(stunum);
			
			
			do {
				
				if(j%9 == 0) {
					temps = lines.get(j);
				}
				else {
					temps = temps +"," + lines.get(j);
				}
				
				j++;
			}while(!(j%9 == 0));
			
			Course _c = new Course(temps);

			temps = "";
			

			if(hmap.containsKey(_stuNum)) {
				Student tempS = hmap.get(_stuNum);
				tempS.addCourse(_c);
			}
			else {
				Student newS = new Student(_stuNum);
				newS.addCourse(_c);
				hmap.put(lines.get(stunum), newS);}
		}
		
		return hmap;
	}

	/**
	 * This method generate the number of courses taken by a student in each semester. The result file look like this:
	 * StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester
	 * 0001,14,1,9
     * 0001,14,2,8
	 * ....
	 * 
	 * 0001,14,1,9 => this means, 0001 student registered 14 semeters in total. In the first semeter (1), the student took 9 courses.
	 * 
	 * 
	 * @param sortedStudents
	 * @return
	 */
	
	private ArrayList<String> countNumberOfCoursesTakenInEachSemester(Map<String, Student> sortedStudents) {
		// TODO: Implement this method
		HashMap<String,Integer> semyr = new HashMap<String,Integer>();
		ArrayList<String> finallist = new ArrayList<>();
		
		finallist.add("StudentID");
		finallist.add("TotalNumberOfSemestersRegistered");
		finallist.add("Semester");
		finallist.add("NumCoursesTakenInTheSemester");
		
		String TotalNumberOfSemestersRegistered = "";
		String Semester = "";
		String NumCoursesTakenInTheSemester = "";
	
		for( String stid : sortedStudents.keySet() ){
			Student value = sortedStudents.get(stid);
			semyr = value.getSemestersByYearAndSemester();
			TotalNumberOfSemestersRegistered = Integer.toString(semyr.size());

			for(int sem = 1; sem <= semyr.size(); sem++) {
				Semester = Integer.toString(sem);

				NumCoursesTakenInTheSemester = Integer.toString(value.getNumCourseInNthSementer(sem));	

				finallist.add(stid);
				finallist.add(TotalNumberOfSemestersRegistered);
				finallist.add(Semester);
				finallist.add(NumCoursesTakenInTheSemester);
				}
			}		
		
		
		return finallist; // do not forget to return a proper variable.
	}
}
