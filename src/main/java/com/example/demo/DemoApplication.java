package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

//@-->Componnent @가붙어있는객체는 스프링 컨테이너가 관리하는 객체이다. Bean이라고부른다
@SpringBootApplication // 내부적으로 configraion 이라는 componnent 둘의 에노테이션 포함하고있음
//이클래스 자체가 설정파일이면서 컴포넌트다..
public class DemoApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@Autowired // 자동으로 주입하게끔해줌 ( 무엇을? 이코드자체에 의해서 만들어진 bin)
	DataSource dataSource; // 스프링부트가 만들어주는 bin(즉 스프링이 관리해주는 데이타소스를 주입해주는것,.

	@Override
	//implements CommandLineRunner에 의해 자동실행run
	//자바의 main 달리 스프링부트에서는 run이 프로그램 시작점이다
	public void run(String... args) throws Exception {
		System.out.println("스프링부트가 관리하는빈사용");
		Connection conn=dataSource.getConnection();
		//위에서 인젝션 (주입)해준 것을통해서 커넥션을 얻어온것
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
