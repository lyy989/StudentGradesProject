package studentGrades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class StudentDataBase {

	public static final int INSERT = 1, SEARCH = 2, UPDATE = 3, DELETE = 4, SHOWTB = 5, DESHOW=6, FINISH = 7;
	
	public static final Scanner scan = new Scanner(System.in);
	public static void main(String[] args) {
		
		boolean flag = false;
		int selcetNumber = 0;
		boolean numberInputContitue = false;
		while (!flag) {
			// �Ŵ���¹� ��ȣ����
			
			selcetNumber = displayMenu();
			
			switch (selcetNumber) {
			case INSERT:
				studentInsert();
				break;
			case SEARCH:
				studentSearch();
				break;
			case DELETE:
				studentDelete();
				break;
			case UPDATE:
				studentUpdate();
				break;
			case SHOWTB:
				studentShow();
				break;
			case DESHOW:
				studentdeleteShow();
				break;
			case FINISH:
				flag = true;
				break;
			default:
				System.out.println("6�������� ���ڰ� �ֽ��ϴ� �ٽ� �Է����ּ���");
				break;
			}// end of switch
		}
		System.out.println("���α׷� ����");
	}//end of main		
	
	private static void studentInsert() {

		String studentNumber = null;
		String name = null;
		int android = 0;
		int java = 0;
		int kotlin = 0;
		while(true) {
			System.out.println("�й��� �Է����ּ��� (8�ڸ� ����)");
			studentNumber = scan.nextLine();
			if(studentNumber.length() != 8) {
				System.out.println("���ڱ��̰� ��ġ���� �ʽ��ϴ� 8�ڸ� ��Ź�帮�ڽ��ϴ�");
				continue;
			}
			boolean duplicate =DBController.duplicateCheck(studentNumber);	//�ߺ���� ��������
			if(duplicate) {
				System.out.println("�����ϴ� �л���ȣ �Դϴ�.");
				continue;
			}
			break;
		}
		while(true) {
	         System.out.print("�̸� �Է�>>");
	         name = scan.nextLine();
	         boolean inputcheck = Pattern.matches("^[��-�R]*$", name);	//�ѱ� �Է�
	         if(inputcheck == true) {
	        	 System.out.println("�Է¼��� �߽��ϴ�.");
	         }else {
	        	 System.out.println("�ѱ۸� �Է��ϼ���");
	        	 continue;
	         }
	         if(name.length() > 5 || name.length() < 2) {
	            System.out.println("�̸��Է� ����� �����ּ���.");
	            continue;
	         }
	            break;
	         
	         }
		
		while(true) {
			System.out.println("�ȵ���̵� ������ �Է����ּ���");
			try {
                android = Integer.parseInt(scan.nextLine());// ���� �Է�
            }
             catch(Exception e) {
                System.out.println("���ڸ� �Է����ּ���");
                continue;
             }
			if(android > 100 || android < 0) {
				System.out.println("���� ������ ������ϴ�.");
				continue;
			}else {
				break;
			}
		}
		while(true) {
			System.out.println("�ڹ� ������ �Է����ּ���");
			try {
                java = Integer.parseInt(scan.nextLine());// ���� �Է�
            }
             catch(Exception e) {
                System.out.println("���ڸ� �Է����ּ���");
                continue;
            }
			if(android > 100 || android < 0) {
				System.out.println("���� ������ ������ϴ�.");
				continue;
			}else {
				break;
			}
		}
		while(true) {
			System.out.println("��Ʋ�� ������ �Է����ּ���");
			try {
                kotlin = Integer.parseInt(scan.nextLine());// ���� �Է�
            }
             catch(Exception e) {
                System.out.println("���ڸ� �Է����ּ���");
                continue;
             }
			
			if(android > 100 || android < 0) {
				System.out.println("���� ������ ������ϴ�.");
				continue;
			}else {
				break;
			}
		}
//		scan.nextLine();
		Student student = new Student(studentNumber, name, android, java, kotlin);
		Connection con = DBUtility.getConnection();
		String insertQuery = "call procedure_calculate_gradetbl(?,?,?,?,?)";
		PreparedStatement preparedStatement = null;
		
	      int count = 0;
	      
	      try {
	         preparedStatement = con.prepareStatement(insertQuery);
	         preparedStatement.setString(1, student.getStudentNumber());
	         preparedStatement.setString(2, student.getName());
	         preparedStatement.setInt(3, student.getAndroid());
	         preparedStatement.setInt(4, student.getJava());
	         preparedStatement.setInt(5, student.getKotlin());
	         count = preparedStatement.executeUpdate();
	         System.out.println("�л� ������ �Է��� �Ϸ�Ǿ����ϴ�");
	         
	      } catch (SQLException e) {
	         e.printStackTrace();
	      }finally {
	         try {
	            if(preparedStatement != null && !preparedStatement.isClosed()) {
	               preparedStatement.close();
	         }
	            if(con != null && !con.isClosed()) {
	               con.close();
	            }
	      } catch (SQLException e) { }
	   }
		
	}

	private static void studentSearch() {
		boolean flag = false;
		int selcetNumber = 0;
		final int NUM_SEARCH = 1, NAME_SEARCH = 2, SEARCH_FINISH = 3;
		boolean numberInputContitue = false;
		String studentNum = null;
		String studentName = null;
		
		while (!flag) {
			// �Ŵ���¹� ��ȣ����
			
			selcetNumber = displaySearchMenu();
			
			switch (selcetNumber) {
			case NUM_SEARCH:
				while(true) {
					System.out.println("�˻��� �й��� �Է����ּ���");
					try {
						studentNum = scan.nextLine();
		            }
		             catch(Exception e) {
		                System.out.println("���ڸ� �Է����ּ���");
		                continue;
		            }
					boolean duplicate =DBController.duplicateCheck(studentNum);
					if(!duplicate) {
						System.out.println("���������ʴ� �л���ȣ �Դϴ�. �ٽ� �Էº�Ź�帳�ϴ�");
						continue;
					}else {
						break;
					}
				}
				DBController.studentNUM_SEARCH(studentNum);
				break;
			case NAME_SEARCH:
				
				System.out.println("�˻��� �̸��� �Է����ּ���");
				studentName = scan.nextLine();
				DBController.studentNAME_SEARCH(studentName);
				break;
			case SEARCH_FINISH:
				flag = true;
				break;
			default:
				System.out.println("��ȣ�� �ʰ��Ǿ����ϴ�");
				break;
			}
		}

	}

	private static void studentUpdate() {
		System.out.println("������ �л��� �л���ȣ�� �Է��Ͻÿ�");
		String studentNumber = scan.nextLine();
		System.out.println("������ �л��� �ȵ���̵� ������ �Է��ϼ���");
		int android = scan.nextInt();
		System.out.println("������ �л��� �ڹ� ������ �Է��ϼ���");
		int java = scan.nextInt();
		System.out.println("������ �л��� ��Ʋ�� ������ �Է��ϼ����");
		int kotlin = scan.nextInt();
		boolean count =DBController.studentUpdate(studentNumber,android,java,kotlin);
		if(count == true) {
			System.out.println(studentNumber+"�й��� �л������� �����Ͽ����ϴ�");
		}else {
			System.out.println(studentNumber+"�й��� �л������� �������� �ʾҽ��ϴ�");
		}
	}

	private static void studentDelete() {
		System.out.println("������ �л��� �л���ȣ�� �Է��Ͻÿ�");
		String studentNumber = scan.nextLine();
		int count =DBController.studentDelete(studentNumber);
		if(count !=0) {
			System.out.println(studentNumber+"��������");
		}else {
			System.out.println(studentNumber+"��������");
		}
	}

	private static void studentShow() {
		boolean flag = false;
		int selcetNumber = 0;
		final int BASE = 1, NUM_ASC = 2, NAME_ASC = 3, AVG_ASC = 4, GRADE_ASC = 5 , SHOW_FINISH = 6;
		boolean numberInputContitue = false;
		while (!flag) {
			// �Ŵ���¹� ��ȣ����
			
			selcetNumber = displayShowMenu();
			
			switch (selcetNumber) {
			case BASE:
				DBController.studentBaseShow();
				break;
			case NUM_ASC:
				DBController.studentNUM_ASCShow();
				break;
			case NAME_ASC:
				DBController.studentNAME_ASCShow();
				break;
			case AVG_ASC:
				DBController.studentAVG_ASCShow();
				break;
			case GRADE_ASC:
				DBController.studentGRADE_ASCShow();
				break;
			case SHOW_FINISH:
				flag = true;
				break;
			default:
				System.out.println("��ȣ�� �ʰ��Ǿ����ϴ�");
				break;
			}// end of switch
		}

	}

	private static void studentdeleteShow() {
		DBController.studentdeleteShow();
	}

	private static int displayMenu() {
		 int selectNumber = 0;
	      boolean flag = false;
	      while(!flag) {
	         
	    	System.out.println("------------------------------------------------------");
	  		System.out.println("1.�Է� 2.�˻� 3.���� 4.���� 5.��ȸ �� ���� 6.������ ��������ȸ 7.����");
	  		System.out.println("------------------------------------------------------");
	  		System.out.println("��ȣ����>>");
	         try {
	            selectNumber = Integer.parseInt(scan.nextLine());
	         }catch(InputMismatchException e){
	            System.out.println("���ڸ� �Է����ּ���.");
	            continue;
	         }catch(Exception e) {
	            System.out.println("��ȣ����, �ٽ� �Է����ּ���.");
	            continue;
	      }
	         break;
	      }
	      return selectNumber;
	   }
	 
	private static int displaySearchMenu() {
		int selcetNumber = 0;
		boolean flag = false;
		while (!flag) {

			System.out.println("-----------------------");
			System.out.println("1.�й��˻� 2.�̸��˻� 3.������");
			System.out.println("-----------------------");
			System.out.print("��ȣ����>>");
			try {
				// ��ȣ����
				selcetNumber = Integer.parseInt(scan.nextLine()) ;
			} catch (InputMismatchException e) {
				System.out.println("�����Է¿��");
				continue;
			} catch (Exception e) {
				System.out.println("�����Է¿�� ���Է¿��");
				continue;
			}
			break;
		}
		return selcetNumber;

	}

	private static int displayShowMenu() {
		int selcetNumber = 0;
		boolean flag = false;
		while (!flag) {

			System.out.println("----------------------------------------------------");
			System.out.println("1.�⺻��ȸ 2.�й����� 3.�̸����� 4.������� ���� 5.������� 6.������");
			System.out.println("----------------------------------------------------");
			System.out.print("��ȣ����>>");
			try {
				// ��ȣ����
				selcetNumber = Integer.parseInt(scan.nextLine()) ;
			} catch (InputMismatchException e) {
				System.out.println("�����Է¿��");
				continue;
			} catch (Exception e) {
				System.out.println("�����Է¿�� ���Է¿��");
				continue;
			}
			break;
		}
		return selcetNumber;

	}
}


