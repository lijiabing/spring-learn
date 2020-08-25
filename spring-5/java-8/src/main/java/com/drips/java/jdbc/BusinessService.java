package com.drips.java.jdbc;


import java.sql.*;

public class BusinessService {

    public static void main(String[] args) throws Exception {
        Customer customer=new Customer(1L,"test");
        BusinessService businessService=new BusinessService();
        businessService.saveCustomer(customer);
    }

    private String url = "jdbc:mysql://104.36.71.36:3306/drips";
    private String username = "root";
    private String password = "5tgbNHY^";

    public BusinessService()  { }

    public void saveCustomer(Customer customer) throws Exception {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection=DriverManager.getConnection(this.url, this.username, this.password);
            connection.setAutoCommit(false);
            preparedStatement=connection.prepareStatement("insert into customer(id,name) values(?,?)");
            preparedStatement.setLong(1,customer.getId());
            preparedStatement.setString(2,customer.getName());
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            try {
                assert connection != null;
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw e;
        }finally {
            try {
                assert preparedStatement != null;
                preparedStatement.close();
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void query(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException cne){
            cne.printStackTrace();
        }
        String dburl = "jdbc:mysql://104.36.71.36:3306/drips?&useSSL=false&serverTimezone=UTC";
        String sql = "SELECT * FROM products where id < 104";
        try(Connection conn = DriverManager.getConnection(dburl,"root","123456");
            Statement stmt = conn.createStatement();
            ResultSet rst = stmt.executeQuery(sql))

        {
            while (rst.next()){
                System.out.println(rst.getInt(1)+"\t"+
                        rst.getString(2)+"\t"+rst.getString(3)+
                        "\t"+rst.getFloat(4) + "\t" + rst.getInt(5)
                );
            }

        }catch (SQLException se){
            se.printStackTrace();
        }

    }


}
