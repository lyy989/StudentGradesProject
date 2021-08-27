package studentGrades;

import java.io.FileReader;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBUtility {
		public static Connection getConnection() {
			Connection con = null;
			
					//Properties driver, url, userID, userPassword read
					try {
					
					Properties properties = new Properties();
					String filePath = StudentDataBase.class.getResource("db.properties").getPath();
					filePath = URLDecoder.decode(filePath,"utf-8");
					properties.load(new FileReader(filePath));
					
					//���ε�
					String drvier = properties.getProperty("DRIVER");
					String url = properties.getProperty("URL");
					String userID = properties.getProperty("userID");
					String userPassword = properties.getProperty("userPassword");
					
					//����̹� �ε��ϱ�
					Class.forName(drvier);
					
					
					//����Ÿ���̽� �����ϱ�
					con = DriverManager.getConnection(url, userID, userPassword);
					
					
					}catch (Exception e) {
						System.out.println("Mysql Database connection fail");
						e.printStackTrace();
					}
			
			return con;
		}
		
	}

