package com.pyg.mapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TestMysql {
    public static void main(String[] args) throws Exception{
        Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.12.168:3306/pinyougoudb?useUnicode=true&characterEncoding=utf8&autoReconnect=true", "root", "root");
        PreparedStatement statement = connection.prepareStatement("select * from tb_brand");
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            System.out.println(resultSet.getInt(1));
        }
    }
}
