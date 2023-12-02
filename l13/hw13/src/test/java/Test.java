import java.sql.*;

public class Test {
    public static void main(String[] args) {
        String databaseName = "master";
        String username = "sa";
        String password = "677099whp";
        String dbURL = "jdbc:sqlserver://localhost:1433;databaseName=" + databaseName;

        try {
            // 1. 加载并注册JDBC驱动
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // 2. 连接到数据库
            Connection conn = DriverManager.getConnection(dbURL, username, password);
            conn.setAutoCommit(false); // 开启事务

            // 3. 读取记录
            String sql = "SELECT * FROM [dbo].[Empl]";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String name = rs.getString("ename");
                System.out.println("Original name: " + name);

                // 4. 修改记录
                name = "Haipeng";
                System.out.println("New name: " + name);

                // 5. 更新数据库中的记录
                sql = "UPDATE [dbo].[Empl] SET ename = ? WHERE empno = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, name);
                pstmt.setInt(2, rs.getInt("empno"));
                pstmt.executeUpdate();

                String sql2 = "SELECT * FROM [dbo].[Empl]";
                ResultSet rs2 = stmt.executeQuery(sql2);
                if (rs2.next()) {
                    String name2 = rs2.getString("ename");
                    System.out.println("Updated name: " + name2);
                }
                // 6. 回滚事务，恢复原始信息
                conn.rollback();
            }

            // 关闭所有资源
            rs.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}