package com.example.demo;

import com.example.demo.domain.Role;
import com.example.demo.repository.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication3 implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication3.class, args);
    }

    @Autowired
    RoleDao roleDao;
    //RoleDao가 컴포넌트로 주입이됨

    @Override
    public void run(String... args) throws Exception {
        /*Role role = new Role();
        role.setRoleId(7);
        role.setName("ROLE7_");
        roleDao.addRole(role);*/

        boolean flag = roleDao.deleteRole(4);

        Role role = roleDao.getRole(1);
        if(role!=null) {
            System.out.println(role.getRoleId() + " " + role.getName());
        }


    }



}
