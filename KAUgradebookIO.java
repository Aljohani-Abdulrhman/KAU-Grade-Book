import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/*
 * Name: abdulrhman aljohani
 * Id: 1750624
 * CourseNo: 204
 * SectionNumber: DA
 * Assignment title: KAU Grade Book with file I/O
 * Date: 20/septemper/2018
 */
public class KAUgradebookIO {

	// make these variables static to reach it any where in the class
	private static File input;
	private static File output;
	private static Scanner read;
	private static PrintWriter write;
	private static Student[] students;

	public static void main(String[] args) throws FileNotFoundException {

		input = new File("input.txt");
		// check if the file is exists or not
		if (!(input.exists())) {
			System.out.println("The file doesn't exists.");
			System.exit(0);
		}

		output = new File("output.txt");
		read = new Scanner(input);
		write = new PrintWriter(output);

		String course = read.nextLine();
		String instructor = read.nextLine();

		// Declare the size of the array
		students = new Student[read.nextInt()];

		write.println("Welcome to the KAU Grade Book.");

		String command = read.next();
		// make a loop to reach each command
		while (!(command.equals("QUIT"))) {
			write.println("\nCommand: " + command);
			switch (command) {

			case "DISPLAYSTATS":
				displayClassStatistics(course, instructor);
				;
				break;
			case "DISPLAYSTUDENTS":
				displayAllStudent();
				break;
			case "SEARCHBYID":
				searchStudentsById();
				break;
			case "SEARCHBYNAME":
				searchStudentsByName();
				break;
			case "ADDRECORD":
				addRecord();
				break;
			}
			// read next command
			command = read.next();
		}

		// close and make the printWriter work on the file
		write.flush();
		write.close();
		read.close();
	}

	// this method to search the student in the array by name
	private static void searchStudentsByName() {
		String name = read.nextLine().trim(); // make trim to delete any space
		int index = -1;// index of the object
		for (int i = 0; i < Student.getNumOfStudents(); i++) {
			if (name.equals(students[i].getFullName())) {// check if the given name is in the system or not
				index = i;// Assign the object index to index variable
			}
		}

		if (index == -1) {// if we found the name
			write.println("ERROR: there is no record for student (" + name + ")");
		} else {
			write.println(
					// here after found the object we'll use the index to fetch any information we
					// want
					"Student Record for " + students[index].getFullName() + " (ID # " + students[index].getId() + ")");
			for (int j = 0; j < students[index].getExamGrades().length - 1; j++) {// we make length - 1 because we want
																					// the last exam to be in different
																					// name
				write.println("Exam " + (j + 1) + ":			" + students[index].getExamGrades(j));
			}
			write.println("Final Exam:		" + students[index].getExamGrades(2));
			write.println("Final Grade:	" + students[index].getFinalGrade());
			write.println("Letter Grade:	" + students[index].getLetterGrade());
		}
	}

	// this method to add a new student in the grade book
	private static void addRecord() {

		// here we'll take the basic info for the student
		int id = read.nextInt();
		String fullName = read.next() + " " + read.next();
		Student student = new Student(fullName, id);

		// here we put the grade of exams of this student in object to store it and use
		// it later
		for (int i = 0; i < student.getExamGrades().length; i++) {
			student.setExamGrades(i, read.nextInt());
		}

		// calculate the final grade and set the final grade to this student
		double grade = (student.getExamGrades(0) * (0.3)) + (student.getExamGrades(1) * (0.3))
				+ (student.getExamGrades(2) * (0.4));
		grade = Math.round(grade * 100.0) / 100.0;// round up the final grade
		student.setFinalGrade(grade);

		// the size is the length for the loop
		int size = Student.getNumOfStudents() + 1;
		int position = 0;// the index where we put the object in it

		// in this block we check if the index in array of the student is empty or not
		// and i sorted the student by Id
		for (int i = 0; i < size; i++) {
			if (students[i] == null) {// if the place we want to put object in it is empty
				students[i] = student;// we put the student in but before we have check if the id is smallest or not
				student.setLetterGrade(getLetterGrade(i));
				Student.inrementNumOfStudent();// and here we increment the student counter
				position = i;// and assign the position of the object
				break;
			} else if (students[i].getId() > student.getId()) {// if the id in the array is bigger than the new id
																// student i well do
																// Sifting to replace the old id with the new
				position = i;
				// here will sift all object from the place i want to replace to last object to
				// left side
				for (int j = size - 1; j > position; j--) {
					students[j] = students[j - 1];
				}
				students[position] = student;// replace the old place with new student
				student.setLetterGrade(getLetterGrade(position));
				Student.inrementNumOfStudent();
				break;
			} else {// if it's not bigger than the id in the array we'll put it in the next place
				continue;
			}
		}

		// print the4 info we want to show
		write.println(students[position].getFullName() + " (ID# " + students[position].getId()
				+ ") has been added to the KAU Grade Book.");
		write.println("His final grade is " + students[position].getFinalGrade() + " ("
				+ students[position].getLetterGrade() + ")");
	}

	// this method to search by Id
	private static void searchStudentsById() {
		int id = read.nextInt();
		int low = 0;
		int high = Student.getNumOfStudents() - 1;// the number of student in the system
		int index = -1;

		// here we did the binary search and take the index of the object
		while (low <= high) {
			int mid = (low + high) / 2;
			if (id == students[mid].getId()) {
				index = mid;
				break;
			} else if (id < students[mid].getId()) {
				high = mid - 1;
			} else {
				low = mid + 1;
			}
		}

		// if the id not found we'll print error message
		if (index == -1) {
			write.println("ERROR: there is no record for student ID # " + id);
		} else {
			// use index to fetch any info we want to print
			write.println(
					"Student Record for " + students[index].getFullName() + " (ID # " + students[index].getId() + ")");
			for (int j = 0; j < students[index].getExamGrades().length - 1; j++) {
				write.println("Exam " + (j + 1) + ":			" + students[index].getExamGrades(j));
			}
			write.println("Final Exam:		" + students[index].getExamGrades(2));
			write.println("Final Grade:	" + students[index].getFinalGrade());
			write.println("Letter Grade:	" + students[index].getLetterGrade());
		}
	}

	// this method to print all student in the program
	private static void displayAllStudent() {
		if (students[0] == null) {// if there are no student we'll print error massage
			write.println("ERRORE: there are no students currently in the system.");
		} else {
			// here print the information for all student
			write.println("***Class Roster And Grade Sheet***\n");
			for (int i = 0; i < Student.getNumOfStudents(); i++) {
				write.println(
						"Students Record for " + students[i].getFullName() + "(ID # " + students[i].getId() + ") -");
				for (int j = 0; j < students[i].getExamGrades().length - 1; j++) {
					write.println("Exam " + (j + 1) + ":			" + students[i].getExamGrades(j));
				}
				write.println("Final Exam:		" + students[i].getExamGrades(2));
				write.println("Final Grade:	" + students[i].getFinalGrade());
				write.println("Letter Grade:	" + students[i].getLetterGrade());
			}
		}

	}

	// this method to print the info of class like grade and the teacher and son on
	private static void displayClassStatistics(String course, String instructor) {
		write.println("Statistical Result of " + course + " (Instructor: " + instructor + ")");
		int numberOfstudent = Student.getNumOfStudents();
		write.println("Total number Of student records: " + numberOfstudent);

		double percent = Math.round((100.0 / numberOfstudent) * 100.0) / 100.0;// we will make percent of each student
																				// in th4e calss
		double percentA = 0, percentB = 0, percentC = 0, percentD = 0, percentF = 0;
		int totalAGrade = 0, totalBGrade = 0, totalCGrade = 0, totalDGrade = 0, totalFGrade = 0;
		double averageScore = 0;
		double highestScore = 0;
		double lowestScore = 100;
		if (numberOfstudent == 0) {
			averageScore = 0;
			lowestScore = 0;
		} else {
			double totalGrade = 0;
			// we'll Count how many grade is A Or B and son on and average grade and lowest
			// grade
			for (int i = 0; i < Student.getNumOfStudents(); i++) {
				totalGrade += students[i].getFinalGrade();
				if (students[i].getLetterGrade() == 'A') {
					totalAGrade++;
					percentA += percent;
				} else if (students[i].getLetterGrade() == 'B') {
					totalBGrade++;
					percentB += percent;
				} else if (students[i].getLetterGrade() == 'C') {
					totalCGrade++;
					percentC += percent;
				} else if (students[i].getLetterGrade() == 'D') {
					totalDGrade++;
					percentD += percent;
				} else {
					totalFGrade++;
					percentF += percent;
				}

				// highest grade
				if (students[i].getFinalGrade() > highestScore) {
					highestScore = students[i].getFinalGrade();
				}
				// lowest grade
				if (students[i].getFinalGrade() < lowestScore) {
					lowestScore = students[i].getFinalGrade();
				}
			}
			// Calculate the average
			averageScore = totalGrade / numberOfstudent;
		}
		// print the info form the object
		write.println("Average Score: " + averageScore);
		write.println("Highest Score: " + highestScore);
		write.println("Lowest Score: " + lowestScore);
		write.println("Total 'A' Grades: " + totalAGrade + " (" + percentA + "% of class)");
		write.println("Total 'B' Grades: " + totalBGrade + " (" + percentB + "% of class)");
		write.println("Total 'C' Grades: " + totalCGrade + " (" + percentC + "% of class)");
		write.println("Total 'D' Grades: " + totalDGrade + " (" + percentD + "% of class)");
		write.println("Total 'F' Grades: " + totalFGrade + " (" + percentF + "% of class)");

	}

	// this method to get the letter grade
	private static char getLetterGrade(int index) {
		double finalGrade = students[index].getFinalGrade();// get the final grade from the object
		if (finalGrade >= 90) {// Check if the grade in the spisfic range and return the letter in that
								// represnt that range
			return 'A';
		} else if (finalGrade >= 80 && finalGrade < 90) {
			return 'B';
		} else if (finalGrade >= 70 && finalGrade < 80) {
			return 'C';
		} else if (finalGrade >= 60 && finalGrade < 70) {
			return 'D';
		} else {
			return 'F';
		}
	}

}
