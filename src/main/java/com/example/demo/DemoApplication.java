package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@Autowired // 자동으로 주입하게끔해줌
	DataSource dataSource; // 스프링부트가 만들어주는 bin
	@Override
	public void run(String... args) throws Exception {
		System.out.println("스프링부트가 관리하는빈사용");
		Connection conn=dataSource.getConnection();

		//JDBC
		PreparedStatement ps=conn.prepareStatement("select deptno,dname from dept");
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			int deptid = rs.getInt("deptno");
			String dname = rs.getString("dname");
			System.out.println(deptid+ " " + dname);
		}
		rs.close();
		ps.close();
		conn.close();

	}
	//DataSource Bean (데이터베이스 커넥션을 얻어와서 그걸이용해서 프로그래밍 가능
	//스프링이 관래하는객체는 스프링 안에서만 사용해야함
	//메인메서드는 스프링이 관리하는게 아님;
	//run으로 실행한 이후만 스프링이 관리한다
	//스프링에서 사용하게끔

}
