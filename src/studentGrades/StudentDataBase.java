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
			// 매뉴출력및 번호선택
			
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
				System.out.println("6번까지의 숫자가 있습니다 다시 입력해주세요");
				break;
			}// end of switch
		}
		System.out.println("프로그램 종료");
	}//end of main		
	
	private static void studentInsert() {

		String studentNumber = null;
		String name = null;
		int android = 0;
		int java = 0;
		int kotlin = 0;
		while(true) {
			System.out.println("학번을 입력해주세요 (8자리 숫자)");
			studentNumber = scan.nextLine();
			if(studentNumber.length() != 8) {
				System.out.println("숫자길이가 일치하지 않습니다 8자리 부탁드리겠습니다");
				continue;
			}
			boolean duplicate =DBController.duplicateCheck(studentNumber);	//중복허용 하지않음
			if(duplicate) {
				System.out.println("존재하는 학생번호 입니다.");
				continue;
			}
			break;
		}
		while(true) {
	         System.out.print("이름 입력>>");
	         name = scan.nextLine();
	         boolean inputcheck = Pattern.matches("^[가-힣]*$", name);	//한글 입력
	         if(inputcheck == true) {
	        	 System.out.println("입력성공 했습니다.");
	         }else {
	        	 System.out.println("한글만 입력하세요");
	        	 continue;
	         }
	         if(name.length() > 5 || name.length() < 2) {
	            System.out.println("이름입력 양식을 지켜주세요.");
	            continue;
	         }
	            break;
	         
	         }
		
		while(true) {
			System.out.println("안드로이드 점수를 입력해주세요");
			try {
                android = Integer.parseInt(scan.nextLine());// 정수 입력
            }
             catch(Exception e) {
                System.out.println("숫자만 입력해주세요");
                continue;
             }
			if(android > 100 || android < 0) {
				System.out.println("점수 범위를 벗어났습니다.");
				continue;
			}else {
				break;
			}
		}
		while(true) {
			System.out.println("자바 점수를 입력해주세요");
			try {
                java = Integer.parseInt(scan.nextLine());// 정수 입력
            }
             catch(Exception e) {
                System.out.println("숫자만 입력해주세요");
                continue;
            }
			if(android > 100 || android < 0) {
				System.out.println("점수 범위를 벗어났습니다.");
				continue;
			}else {
				break;
			}
		}
		while(true) {
			System.out.println("코틀린 점수를 입력해주세요");
			try {
                kotlin = Integer.parseInt(scan.nextLine());// 정수 입력
            }
             catch(Exception e) {
                System.out.println("숫자만 입력해주세요");
                continue;
             }
			
			if(android > 100 || android < 0) {
				System.out.println("점수 범위를 벗어났습니다.");
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
	         System.out.println("학생 정보가 입력이 완료되었습니다");
	         
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
			// 매뉴출력및 번호선택
			
			selcetNumber = displaySearchMenu();
			
			switch (selcetNumber) {
			case NUM_SEARCH:
				while(true) {
					System.out.println("검색할 학번을 입력해주세요");
					try {
						studentNum = scan.nextLine();
		            }
		             catch(Exception e) {
		                System.out.println("숫자만 입력해주세요");
		                continue;
		            }
					boolean duplicate =DBController.duplicateCheck(studentNum);
					if(!duplicate) {
						System.out.println("존재하지않는 학생번호 입니다. 다시 입력부탁드립니다");
						continue;
					}else {
						break;
					}
				}
				DBController.studentNUM_SEARCH(studentNum);
				break;
			case NAME_SEARCH:
				
				System.out.println("검색할 이름을 입력해주세요");
				studentName = scan.nextLine();
				DBController.studentNAME_SEARCH(studentName);
				break;
			case SEARCH_FINISH:
				flag = true;
				break;
			default:
				System.out.println("번호가 초과되었습니다");
				break;
			}
		}

	}

	private static void studentUpdate() {
		System.out.println("수정할 학생의 학생번호를 입력하시오");
		String studentNumber = scan.nextLine();
		System.out.println("수정될 학생의 안드로이드 성적를 입력하세요");
		int android = scan.nextInt();
		System.out.println("수정될 학생의 자바 성적를 입력하세요");
		int java = scan.nextInt();
		System.out.println("수정될 학생의 코틀린 성적를 입력하세요오");
		int kotlin = scan.nextInt();
		boolean count =DBController.studentUpdate(studentNumber,android,java,kotlin);
		if(count == true) {
			System.out.println(studentNumber+"학번의 학생점수를 수정하였습니다");
		}else {
			System.out.println(studentNumber+"학번의 학생점수가 수정되지 않았습니다");
		}
	}

	private static void studentDelete() {
		System.out.println("삭제할 학생의 학생번호를 입력하시오");
		String studentNumber = scan.nextLine();
		int count =DBController.studentDelete(studentNumber);
		if(count !=0) {
			System.out.println(studentNumber+"삭제성공");
		}else {
			System.out.println(studentNumber+"삭제실패");
		}
	}

	private static void studentShow() {
		boolean flag = false;
		int selcetNumber = 0;
		final int BASE = 1, NUM_ASC = 2, NAME_ASC = 3, AVG_ASC = 4, GRADE_ASC = 5 , SHOW_FINISH = 6;
		boolean numberInputContitue = false;
		while (!flag) {
			// 매뉴출력및 번호선택
			
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
				System.out.println("번호가 초과되었습니다");
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
	  		System.out.println("1.입력 2.검색 3.수정 4.삭제 5.조회 및 정렬 6.삭제된 데이터조회 7.종료");
	  		System.out.println("------------------------------------------------------");
	  		System.out.println("번호선택>>");
	         try {
	            selectNumber = Integer.parseInt(scan.nextLine());
	         }catch(InputMismatchException e){
	            System.out.println("숫자만 입력해주세요.");
	            continue;
	         }catch(Exception e) {
	            System.out.println("번호없음, 다시 입력해주세요.");
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
			System.out.println("1.학번검색 2.이름검색 3.나가기");
			System.out.println("-----------------------");
			System.out.print("번호선택>>");
			try {
				// 번호선택
				selcetNumber = Integer.parseInt(scan.nextLine()) ;
			} catch (InputMismatchException e) {
				System.out.println("숫자입력요망");
				continue;
			} catch (Exception e) {
				System.out.println("숫자입력요망 재입력요망");
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
			System.out.println("1.기본조회 2.학번정렬 3.이름정렬 4.평균점수 정렬 5.등급정렬 6.나가기");
			System.out.println("----------------------------------------------------");
			System.out.print("번호선택>>");
			try {
				// 번호선택
				selcetNumber = Integer.parseInt(scan.nextLine()) ;
			} catch (InputMismatchException e) {
				System.out.println("숫자입력요망");
				continue;
			} catch (Exception e) {
				System.out.println("숫자입력요망 재입력요망");
				continue;
			}
			break;
		}
		return selcetNumber;

	}
}


