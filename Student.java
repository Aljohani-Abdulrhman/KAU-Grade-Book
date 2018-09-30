public class Student {

	private String fullName;
	private int id;
	private int[] examGrades = new int[3];
	private double finalGrade;
	private char letterGrade;
	private static int numOfStudents = 0;

	public Student(String fullName, int id) {
		this.fullName = fullName;
		this.id = id;

	}

	public static void inrementNumOfStudent() {
		numOfStudents++;
	}

	public static int getNumOfStudents() {
		return numOfStudents;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int[] getExamGrades() {
		return examGrades;
	}

	public int getExamGrades(int index) {
		return examGrades[index];
	}

	public void setExamGrades(int index, int grade) {
		this.examGrades[index] = grade;
	}

	public double getFinalGrade() {
		return finalGrade;
	}

	public void setFinalGrade(double finalGrade) {
		this.finalGrade = finalGrade;
	}

	public char getLetterGrade() {
		return letterGrade;
	}

	public void setLetterGrade(char letterGrade) {
		this.letterGrade = letterGrade;
	}

}
