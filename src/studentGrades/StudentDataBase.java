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
			// ¸Å´ºÃâ·Â¹× ¹øÈ£¼±ÅÃ
			
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
				System.out.println("6¹ø±îÁöÀÇ ¼ıÀÚ°¡ ÀÖ½À´Ï´Ù ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä");
				break;
			}// end of switch
		}
		System.out.println("ÇÁ·Î±×·¥ Á¾·á");
	}//end of main		
	
	private static void studentInsert() {

		String studentNumber = null;
		String name = null;
		int android = 0;
		int java = 0;
		int kotlin = 0;
		while(true) {
			System.out.println("ÇĞ¹øÀ» ÀÔ·ÂÇØÁÖ¼¼¿ä (8ÀÚ¸® ¼ıÀÚ)");
			studentNumber = scan.nextLine();
			if(studentNumber.length() != 8) {
				System.out.println("¼ıÀÚ±æÀÌ°¡ ÀÏÄ¡ÇÏÁö ¾Ê½À´Ï´Ù 8ÀÚ¸® ºÎÅ¹µå¸®°Ú½À´Ï´Ù");
				continue;
			}
			boolean duplicate =DBController.duplicateCheck(studentNumber);	//Áßº¹Çã¿ë ÇÏÁö¾ÊÀ½
			if(duplicate) {
				System.out.println("Á¸ÀçÇÏ´Â ÇĞ»ı¹øÈ£ ÀÔ´Ï´Ù.");
				continue;
			}
			break;
		}
		while(true) {
	         System.out.print("ÀÌ¸§ ÀÔ·Â>>");
	         name = scan.nextLine();
	         boolean inputcheck = Pattern.matches("^[°¡-ÆR]*$", name);	//ÇÑ±Û ÀÔ·Â
	         if(inputcheck == true) {
	        	 System.out.println("ÀÔ·Â¼º°ø Çß½À´Ï´Ù.");
	         }else {
	        	 System.out.println("ÇÑ±Û¸¸ ÀÔ·ÂÇÏ¼¼¿ä");
	        	 continue;
	         }
	         if(name.length() > 5 || name.length() < 2) {
	            System.out.println("ÀÌ¸§ÀÔ·Â ¾ç½ÄÀ» ÁöÄÑÁÖ¼¼¿ä.");
	            continue;
	         }
	            break;
	         
	         }
		
		while(true) {
			System.out.println("¾Èµå·ÎÀÌµå Á¡¼ö¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä");
			try {
                android = Integer.parseInt(scan.nextLine());// Á¤¼ö ÀÔ·Â
            }
             catch(Exception e) {
                System.out.println("¼ıÀÚ¸¸ ÀÔ·ÂÇØÁÖ¼¼¿ä");
                continue;
             }
			if(android > 100 || android < 0) {
				System.out.println("Á¡¼ö ¹üÀ§¸¦ ¹ş¾î³µ½À´Ï´Ù.");
				continue;
			}else {
				break;
			}
		}
		while(true) {
			System.out.println("ÀÚ¹Ù Á¡¼ö¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä");
			try {
                java = Integer.parseInt(scan.nextLine());// Á¤¼ö ÀÔ·Â
            }
             catch(Exception e) {
                System.out.println("¼ıÀÚ¸¸ ÀÔ·ÂÇØÁÖ¼¼¿ä");
                continue;
            }
			if(android > 100 || android < 0) {
				System.out.println("Á¡¼ö ¹üÀ§¸¦ ¹ş¾î³µ½À´Ï´Ù.");
				continue;
			}else {
				break;
			}
		}
		while(true) {
			System.out.println("ÄÚÆ²¸° Á¡¼ö¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä");
			try {
                kotlin = Integer.parseInt(scan.nextLine());// Á¤¼ö ÀÔ·Â
            }
             catch(Exception e) {
                System.out.println("¼ıÀÚ¸¸ ÀÔ·ÂÇØÁÖ¼¼¿ä");
                continue;
             }
			
			if(android > 100 || android < 0) {
				System.out.println("Á¡¼ö ¹üÀ§¸¦ ¹ş¾î³µ½À´Ï´Ù.");
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
	         System.out.println("ÇĞ»ı Á¤º¸°¡ ÀÔ·ÂÀÌ ¿Ï·áµÇ¾ú½À´Ï´Ù");
	         
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
			// ¸Å´ºÃâ·Â¹× ¹øÈ£¼±ÅÃ
			
			selcetNumber = displaySearchMenu();
			
			switch (selcetNumber) {
			case NUM_SEARCH:
				while(true) {
					System.out.println("°Ë»öÇÒ ÇĞ¹øÀ» ÀÔ·ÂÇØÁÖ¼¼¿ä");
					try {
						studentNum = scan.nextLine();
		            }
		             catch(Exception e) {
		                System.out.println("¼ıÀÚ¸¸ ÀÔ·ÂÇØÁÖ¼¼¿ä");
		                continue;
		            }
					boolean duplicate =DBController.duplicateCheck(studentNum);
					if(!duplicate) {
						System.out.println("Á¸ÀçÇÏÁö¾Ê´Â ÇĞ»ı¹øÈ£ ÀÔ´Ï´Ù. ´Ù½Ã ÀÔ·ÂºÎÅ¹µå¸³´Ï´Ù");
						continue;
					}else {
						break;
					}
				}
				DBController.studentNUM_SEARCH(studentNum);
				break;
			case NAME_SEARCH:
				
				System.out.println("°Ë»öÇÒ ÀÌ¸§À» ÀÔ·ÂÇØÁÖ¼¼¿ä");
				studentName = scan.nextLine();
				DBController.studentNAME_SEARCH(studentName);
				break;
			case SEARCH_FINISH:
				flag = true;
				break;
			default:
				System.out.println("¹øÈ£°¡ ÃÊ°úµÇ¾ú½À´Ï´Ù");
				break;
			}
		}

	}

	private static void studentUpdate() {
		System.out.println("¼öÁ¤ÇÒ ÇĞ»ıÀÇ ÇĞ»ı¹øÈ£¸¦ ÀÔ·ÂÇÏ½Ã¿À");
		String studentNumber = scan.nextLine();
		System.out.println("¼öÁ¤µÉ ÇĞ»ıÀÇ ¾Èµå·ÎÀÌµå ¼ºÀû¸¦ ÀÔ·ÂÇÏ¼¼¿ä");
		int android = scan.nextInt();
		System.out.println("¼öÁ¤µÉ ÇĞ»ıÀÇ ÀÚ¹Ù ¼ºÀû¸¦ ÀÔ·ÂÇÏ¼¼¿ä");
		int java = scan.nextInt();
		System.out.println("¼öÁ¤µÉ ÇĞ»ıÀÇ ÄÚÆ²¸° ¼ºÀû¸¦ ÀÔ·ÂÇÏ¼¼¿ä¿À");
		int kotlin = scan.nextInt();
		boolean count =DBController.studentUpdate(studentNumber,android,java,kotlin);
		if(count == true) {
			System.out.println(studentNumber+"ÇĞ¹øÀÇ ÇĞ»ıÁ¡¼ö¸¦ ¼öÁ¤ÇÏ¿´½À´Ï´Ù");
		}else {
			System.out.println(studentNumber+"ÇĞ¹øÀÇ ÇĞ»ıÁ¡¼ö°¡ ¼öÁ¤µÇÁö ¾Ê¾Ò½À´Ï´Ù");
		}
	}

	private static void studentDelete() {
		System.out.println("»èÁ¦ÇÒ ÇĞ»ıÀÇ ÇĞ»ı¹øÈ£¸¦ ÀÔ·ÂÇÏ½Ã¿À");
		String studentNumber = scan.nextLine();
		int count =DBController.studentDelete(studentNumber);
		if(count !=0) {
			System.out.println(studentNumber+"»èÁ¦¼º°ø");
		}else {
			System.out.println(studentNumber+"»èÁ¦½ÇÆĞ");
		}
	}

	private static void studentShow() {
		boolean flag = false;
		int selcetNumber = 0;
		final int BASE = 1, NUM_ASC = 2, NAME_ASC = 3, AVG_ASC = 4, GRADE_ASC = 5 , SHOW_FINISH = 6;
		boolean numberInputContitue = false;
		while (!flag) {
			// ¸Å´ºÃâ·Â¹× ¹øÈ£¼±ÅÃ
			
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
				System.out.println("¹øÈ£°¡ ÃÊ°úµÇ¾ú½À´Ï´Ù");
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
	  		System.out.println("1.ÀÔ·Â 2.°Ë»ö 3.¼öÁ¤ 4.»èÁ¦ 5.Á¶È¸ ¹× Á¤·Ä 6.»èÁ¦µÈ µ¥ÀÌÅÍÁ¶È¸ 7.Á¾·á");
	  		System.out.println("------------------------------------------------------");
	  		System.out.println("¹øÈ£¼±ÅÃ>>");
	         try {
	            selectNumber = Integer.parseInt(scan.nextLine());
	         }catch(InputMismatchException e){
	            System.out.println("¼ıÀÚ¸¸ ÀÔ·ÂÇØÁÖ¼¼¿ä.");
	            continue;
	         }catch(Exception e) {
	            System.out.println("¹øÈ£¾øÀ½, ´Ù½Ã ÀÔ·ÂÇØÁÖ¼¼¿ä.");
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
			System.out.println("1.ÇĞ¹ø°Ë»ö 2.ÀÌ¸§°Ë»ö 3.³ª°¡±â");
			System.out.println("-----------------------");
			System.out.print("¹øÈ£¼±ÅÃ>>");
			try {
				// ¹øÈ£¼±ÅÃ
				selcetNumber = Integer.parseInt(scan.nextLine()) ;
			} catch (InputMismatchException e) {
				System.out.println("¼ıÀÚÀÔ·Â¿ä¸Á");
				continue;
			} catch (Exception e) {
				System.out.println("¼ıÀÚÀÔ·Â¿ä¸Á ÀçÀÔ·Â¿ä¸Á");
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
			System.out.println("1.±âº»Á¶È¸ 2.ÇĞ¹øÁ¤·Ä 3.ÀÌ¸§Á¤·Ä 4.Æò±ÕÁ¡¼ö Á¤·Ä 5.µî±ŞÁ¤·Ä 6.³ª°¡±â");
			System.out.println("----------------------------------------------------");
			System.out.print("¹øÈ£¼±ÅÃ>>");
			try {
				// ¹øÈ£¼±ÅÃ
				selcetNumber = Integer.parseInt(scan.nextLine()) ;
			} catch (InputMismatchException e) {
				System.out.println("¼ıÀÚÀÔ·Â¿ä¸Á");
				continue;
			} catch (Exception e) {
				System.out.println("¼ıÀÚÀÔ·Â¿ä¸Á ÀçÀÔ·Â¿ä¸Á");
				continue;
			}
			break;
		}
		return selcetNumber;

	}
}


