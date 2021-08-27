package studentGrades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DBController {
	public static final Scanner scan = new Scanner(System.in);
	
	public static boolean duplicateCheck(String studentNumber){
		List<Student> list =new  ArrayList<Student>();
		Connection con = DBUtility.getConnection();
		ResultSet rs =null;
		String strQuery = "select * from studenttbl where studentNumber = ?";
		PreparedStatement preparedStatement =null;
		try {
			preparedStatement=con.prepareStatement(strQuery);
			preparedStatement.setString(1, studentNumber);
		
			rs=preparedStatement.executeQuery();
			if(rs.next()!=false) {
				return true;
			}
			
			while(rs.next()) {
				studentNumber =rs.getString(1);
				String name=rs.getString(2);
				int android =rs.getInt(3);
				int java=rs.getInt(4);
				int kotlin =rs.getInt(5);
				int total =rs.getInt(6);
				double avg =rs.getDouble(7);
				String grade =rs.getString(8);
				Student student=new Student(studentNumber, name, android, android, kotlin, total, avg, grade);
				list.add(student);
				System.out.println(student.toString());
			}
			if(list ==null) {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
			}
		}
		return false;
	}

	public static int studentInsert(Student student) {
		
		Connection con = DBUtility.getConnection();
		PreparedStatement preparedStatement = null;
		int count =0;
		String insertQuery = "call procedure_calculate_gradetbl(?,?,?,?,?)";

		
		
		try {
			preparedStatement = con.prepareStatement(insertQuery);
			preparedStatement.setString(1, student.getStudentNumber());
			preparedStatement.setString(2, student.getName());
			preparedStatement.setInt(3, student.getAndroid());
			preparedStatement.setInt(4, student.getJava());
			preparedStatement.setInt(5, student.getKotlin());
			

			count = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
			}
		}
		return count;
	}

	public static void studentNUM_SEARCH(String studentNum) {
		
		Connection con = DBUtility.getConnection();
		ResultSet rs =null;
		String strQuery = "select * from studenttbl where studentNumber = ?";
		PreparedStatement preparedStatement =null;
		
		try {
			preparedStatement=con.prepareStatement(strQuery);
			preparedStatement.setString(1, studentNum);
			
			rs=preparedStatement.executeQuery();
			
			
			while(rs.next()) {
				String studentNumber = rs.getString(1);
				String name = rs.getString(2);
				int android = rs.getInt(3);
				int java = rs.getInt(4);
				int kotlin = rs.getInt(5);
				int total = rs.getInt(6);
				double avg = rs.getDouble(7);
				String grade = rs.getString(8);
				Student student = new Student(studentNumber, name, android, java, kotlin, total, avg, grade);
				System.out.println(student.toString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
			}
		}
		
	}

	public static void studentNAME_SEARCH(String studentName) {
		
		Connection con = DBUtility.getConnection();
		ResultSet rs =null;
		String strQuery = "select * from studenttbl where name = ?";
		PreparedStatement preparedStatement =null;
		
		try {
			preparedStatement=con.prepareStatement(strQuery);
			preparedStatement.setString(1, studentName);
			
			rs=preparedStatement.executeQuery();
			
			
			while(rs.next()) {
				String studentNumber = rs.getString(1);
				String name = rs.getString(2);
				int android = rs.getInt(3);
				int java = rs.getInt(4);
				int kotlin = rs.getInt(5);
				int total = rs.getInt(6);
				double avg = rs.getDouble(7);
				String grade = rs.getString(8);
				Student student = new Student(studentNumber, name, android, java, kotlin, total, avg, grade);
				System.out.println(student.toString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
			}
		}
		
	}

	public static boolean studentUpdate(String studentNumber, int android, int java, int kotlin) {
		Connection con = DBUtility.getConnection();
		PreparedStatement preparedStatement = null;
		int count =0;
		ResultSet rs = null;
		String insertQuery = "select function_calculate_gradetbl(?,?,?,?)";
		try {
			preparedStatement = con.prepareStatement(insertQuery);
			preparedStatement.setString(1, studentNumber);
			preparedStatement.setInt(2, android);
			preparedStatement.setInt(3, java);
			preparedStatement.setInt(4, kotlin);
			rs = preparedStatement.executeQuery();
			if(rs.next()==true) {
				return true;
			}else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
			}
		}
		return false;
	}

	public static int studentDelete(String studentNumber) {
		Connection con = DBUtility.getConnection();
		PreparedStatement preparedStatement = null;
		int count =0;
		String insertQuery = "delete from studenttbl where studentNumber = ?";
		try {
			preparedStatement = con.prepareStatement(insertQuery);
			preparedStatement.setString(1, studentNumber);
			count = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
			}
		}
		return count;
	}

	public static void studentBaseShow() {
		
		Connection con = DBUtility.getConnection();
		ResultSet rs =null;
		String strQuery = "select * from studenttbl";
		PreparedStatement preparedStatement =null;
		try {
			preparedStatement=con.prepareStatement(strQuery);
			rs=preparedStatement.executeQuery();
			
			
			while(rs.next()) {
				String studentNumber = rs.getString(1);
				String name = rs.getString(2);
				int android = rs.getInt(3);
				int java = rs.getInt(4);
				int kotlin = rs.getInt(5);
				int total = rs.getInt(6);
				double avg = rs.getDouble(7);
				String grade = rs.getString(8);
				Student student = new Student(studentNumber, name, android, java, kotlin, total, avg, grade);
				System.out.println(student.toString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
			}
		}
		
	}

	public static void studentNUM_ASCShow() {
		Connection con = DBUtility.getConnection();
		ResultSet rs =null;
		String strQuery = "select * from studenttbl order by studentNumber asc";
		PreparedStatement preparedStatement =null;
		try {
			preparedStatement=con.prepareStatement(strQuery);
			rs=preparedStatement.executeQuery();
			
			
			while(rs.next()) {
				String studentNumber = rs.getString(1);
				String name = rs.getString(2);
				int android = rs.getInt(3);
				int java = rs.getInt(4);
				int kotlin = rs.getInt(5);
				int total = rs.getInt(6);
				double avg = rs.getDouble(7);
				String grade = rs.getString(8);
				Student student = new Student(studentNumber, name, android, java, kotlin, total, avg, grade);
				System.out.println(student.toString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
			}
		}
		
	}

	public static void studentNAME_ASCShow() {
		Connection con = DBUtility.getConnection();
		ResultSet rs =null;
		String strQuery = "select * from studenttbl order by name asc";
		PreparedStatement preparedStatement =null;
		try {
			preparedStatement=con.prepareStatement(strQuery);
			rs=preparedStatement.executeQuery();
			
			
			while(rs.next()) {
				String studentNumber = rs.getString(1);
				String name = rs.getString(2);
				int android = rs.getInt(3);
				int java = rs.getInt(4);
				int kotlin = rs.getInt(5);
				int total = rs.getInt(6);
				double avg = rs.getDouble(7);
				String grade = rs.getString(8);
				Student student = new Student(studentNumber, name, android, java, kotlin, total, avg, grade);
				System.out.println(student.toString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
			}
		}
		
	}
	
	public static void studentAVG_ASCShow() {
		Connection con = DBUtility.getConnection();
		ResultSet rs =null;
		String strQuery = "select * from studenttbl order by avg asc";
		PreparedStatement preparedStatement =null;
		try {
			preparedStatement=con.prepareStatement(strQuery);
			rs=preparedStatement.executeQuery();
			
			
			while(rs.next()) {
				String studentNumber = rs.getString(1);
				String name = rs.getString(2);
				int android = rs.getInt(3);
				int java = rs.getInt(4);
				int kotlin = rs.getInt(5);
				int total = rs.getInt(6);
				double avg = rs.getDouble(7);
				String grade = rs.getString(8);
				Student student = new Student(studentNumber, name, android, java, kotlin, total, avg, grade);
				System.out.println(student.toString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
			}
		}
		
	}

	public static void studentGRADE_ASCShow() {
		Connection con = DBUtility.getConnection();
		ResultSet rs =null;
		String strQuery = "select * from studenttbl order by grade asc";
		PreparedStatement preparedStatement =null;
		try {
			preparedStatement=con.prepareStatement(strQuery);
			rs=preparedStatement.executeQuery();
			
			
			while(rs.next()) {
				String studentNumber = rs.getString(1);
				String name = rs.getString(2);
				int android = rs.getInt(3);
				int java = rs.getInt(4);
				int kotlin = rs.getInt(5);
				int total = rs.getInt(6);
				double avg = rs.getDouble(7);
				String grade = rs.getString(8);
				Student student = new Student(studentNumber, name, android, java, kotlin, total, avg, grade);
				System.out.println(student.toString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
			}
		}
		
	}

	public static void studentdeleteShow() {
		
		Connection con = DBUtility.getConnection();
		ResultSet rs =null;
		String strQuery = "select * from studenttbl_delete";
		PreparedStatement preparedStatement =null;
		try {
			preparedStatement=con.prepareStatement(strQuery);
			rs=preparedStatement.executeQuery();
			
			
			while(rs.next()) {
				String studentNumber = rs.getString(1);
				String name = rs.getString(2);
				int android = rs.getInt(3);
				int java = rs.getInt(4);
				int kotlin = rs.getInt(5);
				int total = rs.getInt(6);
				double avg = rs.getDouble(7);
				String grade = rs.getString(8);
				Student student = new Student(studentNumber, name, android, java, kotlin, total, avg, grade);
				System.out.println(student.toString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
			}
		}
	}

	
}