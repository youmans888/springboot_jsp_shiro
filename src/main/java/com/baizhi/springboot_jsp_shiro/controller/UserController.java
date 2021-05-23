package com.baizhi.springboot_jsp_shiro.controller;

import com.baizhi.springboot_jsp_shiro.entity.User;
import com.baizhi.springboot_jsp_shiro.service.UserService;
import com.baizhi.springboot_jsp_shiro.utils.ValidateImageCodeUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RequestMapping("user")
@Controller
public class UserController {

    @Autowired
    private UserService userService ;


//    验证码方法
    @RequestMapping("getImage")
    public void getImage(HttpSession session, HttpServletResponse response) throws IOException {
        String securityCode = ValidateImageCodeUtils.getSecurityCode();
        BufferedImage image = ValidateImageCodeUtils.createImage(securityCode);
        session.setAttribute("code",securityCode);//存入session作用域中
        ServletOutputStream os = response.getOutputStream() ;
        ImageIO.write(image,"png",os) ;
    }
    //用户认证
    @RequestMapping("register")
    public String register(User user){
        try {
            userService.register(user);
            return "redirect:/login.jsp" ;
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/register.jsp" ;
        }
    }


    @RequestMapping("logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();//退出用户
        return "redirect:/login.jsp" ;
    }


    @RequestMapping("login")
    public String login(String username,String password,String code,HttpSession session){
        //比较验证码
        String codes = (String) session.getAttribute("code");
        try {
        if(codes.equalsIgnoreCase(code)){
            //获取主体对象
            Subject subject = SecurityUtils.getSubject();
                subject.login(new UsernamePasswordToken(username,password));
                return "redirect:/index.jsp" ;

        }else{
            throw new RuntimeException("验证码错误");
        }
        }
         catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("用户名错误");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("密码错误");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return "redirect:/login.jsp" ;
    }
}
