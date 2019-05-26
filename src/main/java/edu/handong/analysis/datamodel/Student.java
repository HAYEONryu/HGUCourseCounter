package edu.handong.analysis.datamodel;
import java.util.*;


public class Student {
	private String studentId;
	private ArrayList<Course> coursesTaken = new ArrayList<>(); // List of courses student has taken
	private HashMap<String,Integer> semestersByYearAndSemester = new HashMap<String,Integer>();; 
	                                                         // key: Year-Semester
	                                                         // e.g., 2003-1,
	
	public Student(String studentId) {
		this.studentId = studentId;

	}
	public void addCourse(Course newRecord) {
	//add a Course instance created while reading line to the CourseTaken ArrayList in the Student instance.
		coursesTaken.add(newRecord);
		
	}
	public HashMap<String,Integer> getSemestersByYearAndSemester(){
		int cnt = 1;
		String stemp = "";
		for(int i = 0; i < coursesTaken.size() ; i++) {
			stemp = Integer.toString(coursesTaken.get(i).yeargetter()) + "-" + Integer.toString(coursesTaken.get(i).semgetter());
			if(!semestersByYearAndSemester.containsKey(stemp)) {
				semestersByYearAndSemester.put(stemp, cnt);
				cnt++;
			}
		}		
		// 반복문을 통해 coursesTaken 다 읽으면서
		// 1. 연도 + 학기
		// 2. 해쉬맵에 있는지 확인하고
		// 3. 없으면 해쉬맵에 추가
		// 4. 있으면 그냥 패스		
		
		return semestersByYearAndSemester;
	}
	
	public int getNumCourseInNthSementer(int semester) {
		// coursesTaken, semestersByYearAndSemester 제대로 값이 들어가 있다
		// studentId 들었던 수강 내역 + (년도+학기)가 몇번째 학기인지 계산이 끝난 상황
		// (년도+학기)에 몇개를 들었는지
		
		// coursesTaken 쭉 읽으면서 년도+학기 조합에 count 증가
		
		// 1 2 3 ...
		int _y , _s, value,counter = 0;
		String _ys = "";

		for(int i = 0; i < coursesTaken.size(); i++) {
			_y = coursesTaken.get(i).yeargetter();
			_s = coursesTaken.get(i).semgetter();

			_ys = Integer.toString(_y)+"-"+Integer.toString(_s);

			value = semestersByYearAndSemester.get(_ys);

			if(value == semester) {
				counter++;

			}

		}
		return counter;
	}
	/* Add getter and setter for the field if needed*/

}
