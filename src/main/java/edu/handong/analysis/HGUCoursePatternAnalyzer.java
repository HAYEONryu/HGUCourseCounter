package edu.handong.analysis;

import java.io.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;


import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysis.utils.NotEnoughArgumentException;
import edu.handong.analysis.utils.Utils;

public class HGUCoursePatternAnalyzer {

private HashMap<String,Student> students;

	//private HashMap<String,Student> students;
	
	/**
	 * This method runs our analysis logic to save the number courses taken by each student per semester in a result file.
	 * Run method must not be changed!!
	 * @param args
	 */
	String input ;
	String output ;
	String coursecode ;
	String startyear ;
	String endyear ;
	String all ;
	boolean help=false;
	String analyzer;
	
	Options options = createOption(); 

public void run(String[] args) {				

	try {
			// when there are not enough arguments from CLI, it throws the NotEnoughArgmentException which must be defined by you.
			if(args.length<2)
				throw new NotEnoughArgumentException();
		} catch (NotEnoughArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
	
	if(parseOptions(options, args)){
		if (help){
			printHelp(options);
			System.exit(0);
		}
		if(analyzer.equals("1"))
		{
			 hw5(args);
		}
		
		if(analyzer.equals("2"))
		{
			hw6(args);
		}
	}

}

//--------------------------------------------------------------//	
	private boolean parseOptions(Options options, String[] args) {
		
		CommandLineParser parser = new DefaultParser();
		
		try {
			
			CommandLine cmd = parser.parse(options, args);
			
			input = cmd.getOptionValue("i"); 
			output = cmd.getOptionValue("o");
			analyzer = cmd.getOptionValue("a");
			coursecode = cmd.getOptionValue("c"); 
			startyear = cmd.getOptionValue("s");
			endyear = cmd.getOptionValue("e");
			help = cmd.hasOption("h");
			
		} catch (Exception e) {
			printHelp(options);
			return false;
			}
		
		return true;
	}
	private Options createOption()
	{
		Options options =new Options();
		
		options.addOption(Option.builder("i").longOpt("input")
			   .desc("Set an input file path")
			   .hasArg()
			   .argName("Input path")
	           .required()
	           .build());
		
		options.addOption(Option.builder("o").longOpt("output")
		        .desc("Set an output file path")
		        .hasArg()
		        .argName("Output path")
		        .required()
		        .build());
		
		options.addOption(Option.builder("a").longOpt("analysis")
				   .desc("1: Count courses per semester, 2: Count per course name and year")
				   .hasArg()
				   .argName("Analysis option")
				   .required()
				   .build());
		
		options.addOption(Option.builder("s").longOpt("startyear")
		          .desc("Set the start year for analysis")
		          .hasArg()
		          .argName("Start year for analysis")
		          .required()
		          .build());
		
		options.addOption(Option.builder("e").longOpt("endyear")
		          .desc("Set the end year for analysis")
		          .hasArg()
		          .argName("End year for analysis")
		          .required()
		          .build());
		
		options.addOption(Option.builder("c").longOpt("coursecode")
				.desc("Course code for '-a 2' option")
				.hasArg()
				.argName("course code")
				.build());
		
		options.addOption(Option.builder("h").longOpt("help")
		          .desc("Show a Help page")
		          .argName("Help")
		          .build());
		

		return options;
	}
	
	private void printHelp(Options options)
	{
		HelpFormatter helper = new HelpFormatter();
		String header = "HGU Course Analyzer";
		String footer ="";
		helper.printHelp("HGU Course Counter", header, options, footer, true);
	}
//---------------------------------------------------------------------

private void hw6(String[] args){
	String dataPath = args[0]; // csv file to be analyzed
	String resultPath = args[1]; // the file path where the results are saved.
	coursecode=args[2];
	startyear=args[3];
	endyear=args[4];
	String all =new String();
	

	ArrayList<Course> selectedcourse= new ArrayList<Course>();
	ArrayList<Course> courses =new ArrayList<Course>();
	//파일 불러오기
	ArrayList<String> lines = Utils.getLines(dataPath, true);
	// Generate result lines to be saved.
	ArrayList<String> linesToBeSaved=new ArrayList<String>();							
	linesToBeSaved.add("Year,Semester,CouseCode, CourseName,TotalStudents,StudentsTaken,Rate");
	 
		//코스 코드가 같으면 삽입
		for(String line:lines)
		{
			if(coursecode.equals(line.split(",")[4].trim()))
			{
				selectedcourse.add(new Course(line));
			}
			courses.add(new Course(line));
		}
		
		double rate;
		int year = 0;
		int sem = 0;
		int stuforthisclass = 0;
		int allstu = 0;
		for (int i =Integer.parseInt(startyear); i <= Integer.parseInt(endyear); i++) {
		
			for(sem = 0; sem < 4; sem++) {
			
				for(int k=0;k<selectedcourse.size() ;k++)//selected 과목 하나하나 체크
					{if(selectedcourse.get(k).yeargetter()== year && selectedcourse.get(k).semgetter() == sem)
						{
							stuforthisclass++;
						}
					}//기간 안에 특정 수업을 수강한 학생 수 
		
				for(int k=0;k<courses.size();k++){
					if(courses.get(k).yeargetter() == year && courses.get(k).semgetter()==sem){
						allstu++;
					}
				}// 기간내 전체 학생들 수	
				if(allstu != 0 ) {
					rate = stuforthisclass / allstu;
					all = year + "," + sem + ","+ coursecode +  "," + selectedcourse.get(0).namegetter() + "," +allstu + "," +stuforthisclass+ "," +rate;
					linesToBeSaved.add(all);
				}
			}
		}
		
		// Write a file (named like the value of resultPath) with linesTobeSaved.
		Utils.writeAFile(linesToBeSaved, resultPath);
	}
//---------------------------------------------------------------------
private void hw5(String[] args){
	
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