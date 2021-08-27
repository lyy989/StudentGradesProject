package studentGrades;

import java.util.Objects;

public class Student {
	private String studentNumber;
	private String name;
	private int android;
	private int java;
	private int kotlin;
	private int total;
	private double avg;
	private String grade;
	
	public Student(String studentNumber, String name, int android, int java, int kotlin, int total, double avg,
			String grade) {
		super();
		this.studentNumber = studentNumber;
		this.name = name;
		this.android = android;
		this.java = java;
		this.kotlin = kotlin;
		this.total = total;
		this.avg = avg;
		this.grade = grade;
	}
	
	public Student(String studentNumber, String name, int android, int java, int kotlin) {
		// TODO Auto-generated constructor stub
		super();
		this.studentNumber = studentNumber;
		this.name = name;
		this.android = android;
		this.java = java;
		this.kotlin = kotlin;
	}

	@Override
	public int hashCode() {
		return Objects.hash(studentNumber);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Student) {
			Student student = (Student)obj;
			return this.studentNumber.equals(student.getStudentNumber());
		}
		return false;
	}

	public String getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAndroid() {
		return android;
	}

	public void setAndroid(int android) {
		this.android = android;
	}

	public int getJava() {
		return java;
	}

	public void setJava(int java) {
		this.java = java;
	}

	public int getKotlin() {
		return kotlin;
	}

	public void setKotlin(int kotlin) {
		this.kotlin = kotlin;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public double getAvg() {
		return avg;
	}

	public void setAvg(double avg) {
		this.avg = avg;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return studentNumber + "\t" + name + "\t" + android + "\t" + java
				+ "\t" + kotlin + "\t" + total + "\t" + String.format("%.2f", avg) + "\t" + grade;
	}
	
}
