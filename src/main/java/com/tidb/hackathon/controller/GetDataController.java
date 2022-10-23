package com.tidb.hackathon.controller;

import com.tidb.hackathon.entity.MemberInfo;
import com.tidb.hackathon.exception.BizException;
import com.tidb.hackathon.interceptor.JWTInterceptor;
import com.tidb.hackathon.pojo.DBCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;

@RestController
public class GetDataController {

    private static final Logger logger = LoggerFactory.getLogger(GetDataController.class);

    private static final String URL = "http://localhost:8087/verification";
    @GetMapping(path = "/member/info")
    public MemberInfo getMemberInfo(HttpServletRequest request) throws ClassNotFoundException, SQLException {
        String token = request.getHeader("token");
        if(token == null || "".equals(token.trim()))
            throw new BizException("400", "JWT Invalid");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", token);

        HttpEntity<MultiValueMap<String, Object>> formEntity = new HttpEntity<>(headers);
        ResponseEntity<DBCredential> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, formEntity, DBCredential.class);
        DBCredential dbCredential = responseEntity.getBody();

        // 1.注册驱动
        Class.forName("com.mysql.jdbc.Driver");
        //2.获取链接（mysql8之后的连接）
        String url = "jdbc:mysql://localhost:3306/springdata_jpa?characterEncoding=UTF-8";
        String username = dbCredential.getUsername();
        String password = dbCredential.getPwd();
        Connection conn = DriverManager.getConnection(url, username, password);

        //定义sql
        String sql = "select * from member_info where name = 'steven'";
        // 获取执行sql地对象
        Statement stmt = conn.createStatement();
        // 执行sql
        ResultSet rs = stmt.executeQuery(sql);
        MemberInfo memberInfo = new MemberInfo();
        if(rs != null && rs.next()){

            memberInfo.setName(rs.getString(1));
            memberInfo.setLocation(rs.getString(2));

            rs.close();
        }
        conn.close();

        return memberInfo;
    }
}
