package com.example.demo.repository;

import com.example.demo.domain.Role;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//Spring jdbc 프로그래밍
@Repository
//@Repository 는 @Component이고 컨테이너가 관리하는 Bean이된다.
public class RoleDao {
    //데이터베이스의 입력수정삭제조회 -> Dao
    private final JdbcTemplate jdbcTemplate; // 필드를 final로 선언하면 생성자에 반드시 초기화해야함

    //생성자에 파라미터를 넣어주면 스프링부트가 자동으로 주입한다. 생성자 주입.
    public RoleDao(DataSource dataSource) {
        jdbcTemplate=new JdbcTemplate(dataSource); // DataSource가 있어야만 jdbcTemplate사용할수있어서 넣어줘야한다.
    }

    //Role테이블에 한건 저장
    public boolean addRole(Role role){
        String sql = "insert into role (role_id,name) Values (?,?)";
        int result = jdbcTemplate.update(sql, role.getRoleId(), role.getName());
        //update메소드는 insert , update , delete SQL문을 실행해라라고 할때 사용
        // 즉 위에 sql문을 udpate메소드를 대입하여 실행하여라.
        return result ==1;
    }

    public boolean deleteRole(int roleid){
        String sql="delete from role where role_id=?";
        int result = jdbcTemplate.update(sql, roleid);
        return result==1;
    }

    public Role getRole(int roleId){
        String sql = "SELECT role_id, name FROM role where role_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new RoleRowMapper(), roleId);
            //queryforobject 쿼리가 1개 혹은 0개일떄쓰임
        }catch(Exception e){
            return null;
        }
    }
    
    /*public Role getRole(int roleId){ //람다표현식으로 위의것 치환
     return jdbcTemplate.queryForObject(sql, (rs,rowNum)-> {
            Role role = new Role();
            role.setRoleId(rs.getInt("role_id"));
            role.setName(rs.getString("name"));
            return role;
     },roleId); */
    public List<Role> getRoles(){
        String sql = "select role_id, name from role order by role_id desc";
        //query메소드는 여러건의 결과를 구할때
        return jdbcTemplate.query(sql, (rs,rowNum) ->{
            Role role = new Role();
            role.setRoleId(rs.getInt("role_id"));
            role.setName(rs.getString("name"));
            return role;
        });
    }


}
class RoleRowMapper implements RowMapper<Role> {

    @Override
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
        Role role = new Role();
        role.setRoleId(rs.getInt("role_id"));
        role.setName(rs.getString("name"));
        return role;
    }

}

