import java.sql.*;



public class JDBCDemo {

	public static void readRecords() throws SQLException
	{
		String url = "jdbc:mysql://localhost:3306/jdbcdemo";
		String userName = "root";
		String password = "Divya#123!";
		String query = "select * from student";
		Connection con = DriverManager.getConnection(url, userName, password);
		Statement st = con.createStatement();
		
		ResultSet rs = st.executeQuery(query);
		
		while(rs.next())
		{
			System.out.print(rs.getInt(1)+" ");
			System.out.print(rs.getString(2)+" ");
			System.out.println(rs.getFloat(3)+" ");
		}
		
		con.close();
	}
	
	public static void insertPrepare() throws SQLException
	{
		String url = "jdbc:mysql://localhost:3306/jdbcdemo";
		String userName = "root";
		String password = "Divya#123!";
		
		Connection con = DriverManager.getConnection(url, userName, password);
		Statement st = con.createStatement();
		int id=7;
		String name = "nila";
		float gpa = 8.1f;
		//String query = "insert into student values(5,'divya',9.0)";  
		//String query = "insert into student values("+id+",'"+name+"',"+gpa+")";
		String query = "insert into student values(?,?,?)"; // using preparedStatements
		
		PreparedStatement pst = con.prepareStatement(query);
		pst.setInt(1,id);
		pst.setString(2, name);
		pst.setFloat(3, gpa);
		int rs = pst.executeUpdate();
		
		System.out.println("Number of rows Affected: "+rs);
		
		con.close();
	}
	
	public static void insertRecords() throws SQLException
	{
		String url = "jdbc:mysql://localhost:3306/jdbcdemo";
		String userName = "root";
		String password = "Divya#123!";
		
		Connection con = DriverManager.getConnection(url, userName, password);
		Statement st = con.createStatement();
		
		String query = "insert into student values(3,'raj',9.6)";
		int rs = st.executeUpdate(query);
		System.out.println("number of rows affected: "+rs);
		
		con.close();
	}
	public static void delete() throws SQLException
	{
		String url = "jdbc:mysql://localhost:3306/jdbcdemo";
		String userName = "root";
		String password = "Divya#123!";
		
		Connection con = DriverManager.getConnection(url, userName, password);
		Statement st = con.createStatement();
		int id = 3;
		String query = "delete from student where stud_id="+id;
		int rs = st.executeUpdate(query);
		
		System.out.println("Number of Rows Affeccted: "+rs);
		
		con.close();
	}
	
	public static void update() throws SQLException
	{
		String url = "jdbc:mysql://localhost:3306/jdbcdemo";
		String userName = "root";
		String password = "Divya#123!";
		
		Connection con = DriverManager.getConnection(url, userName, password);
		
		int id=4;
		String query = "update student set gpa=10.0 where stud_id=?";
		PreparedStatement pst = con.prepareStatement(query);
		pst.setInt(1, id);
		int rs = pst.executeUpdate();
		System.out.println("Number of Rows Affected: "+rs);
		
		con.close();
	}
	
	public static void sp() throws SQLException
	{
		String url = "jdbc:mysql://localhost:3306/jdbcdemo";
		String userName = "root";
		String password = "Divya#123!";
		
		Connection con = DriverManager.getConnection(url, userName, password);
		
		CallableStatement cst = con.prepareCall("{call disp()}");
		ResultSet rs = cst.executeQuery();
		while(rs.next())
		{
			System.out.print(rs.getInt(1)+" ");
			System.out.print(rs.getString(2)+" ");
			System.out.println(rs.getFloat(3)+" ");
		}
		
		con.close();
		
	}
	
	public static void sp2(int id) throws SQLException
	{
		String url = "jdbc:mysql://localhost:3306/jdbcdemo";
		String username="root";
		String password="Divya#123!";
		Connection con = DriverManager.getConnection(url,username,password);
		
		CallableStatement cst = con.prepareCall("{call getStudById(?)}");
		cst.setInt(1, id);
		
		ResultSet rs = cst.executeQuery();
		
		while(rs.next())
		{
			System.out.print(rs.getInt(1)+" ");
			System.out.print(rs.getString(2)+" ");
			System.out.println(rs.getFloat(3)+" ");
		}
		
		con.close();
	}
	
	public static void sp3(int id) throws SQLException
	{
		String url = "jdbc:mysql://localhost:3306/jdbcdemo";
		String username="root";
		String password="Divya#123!";
		Connection con = DriverManager.getConnection(url,username,password);
		
		CallableStatement cst = con.prepareCall("{call getNameById(?,?)}");
		cst.setInt(1, id);
		cst.registerOutParameter(2, Types.VARCHAR);
		cst.executeUpdate();
		System.out.println(cst.getString(2));
		
		con.close();	
	}
	
	public static void commitdemo() throws SQLException
	{
		String url = "jdbc:mysql://localhost:3306/jdbcdemo";
		String username = "root";
		String password = "Divya#123!";
		Connection con = DriverManager.getConnection(url,username,password);
		
		Statement st = con.createStatement();
		String query1 = "update student set gpa=6 where stud_id=5";
		String quert2 = "update student set gpa=6 where stud_id=1";
		con.setAutoCommit(false);
		int r1 = st.executeUpdate(query1);
		System.out.println("Number of rows affected: "+r1);
		int r2 = st.executeUpdate(quert2);
		System.out.println("Number of rows affected: "+r2);
		if(r1>0 && r2>0)
		{
			con.setAutoCommit(true);
		}
		
		con.close();
		
	}
	
	public static void rollbackDemo() throws SQLException
	{
		String url = "jdbc:mysql://localhost:3306/jdbcdemo";
		String username = "root";
		String password = "Divya#123!";
		Connection con = DriverManager.getConnection(url,username,password);
		
		Statement st = con.createStatement();
		String query1 = "update student set gpa=2 where stud_id=1";
		String quert2 = "update student set gpa=2 where stud_id=2";
		String query3 = "update student set gpa=2 where stud_id=3";
		String quert4 = "update student set gpa=2 where stud_id=5";
		
		con.setAutoCommit(false);
		st.addBatch(query1);
		st.addBatch(quert2);
		st.addBatch(query3);
		st.addBatch(quert4);
		
		int []res = st.executeBatch();
		
		for(int i=0;i<res.length;i++)
		{
			if(res[i]>0)
				continue;
			else
				con.rollback();
		}
		
		con.close();
	}
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		update();
		readRecords();
		insertRecords();
		delete();
		sp2(5);
		sp2(1);
		sp3(6);
		
		commitdemo();
		rollbackDemo();
	}

}
