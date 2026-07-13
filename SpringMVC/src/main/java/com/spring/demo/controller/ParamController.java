package com.spring.demo.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Map;

/**
 * 请求参数 & 请求头 & Cookie & Session 详解控制器。
 *
 * 演示四种从 HTTP 请求中提取数据的注解：
 *   @RequestParam      — 获取 URL 查询参数或表单数据
 *   @RequestHeader      — 获取请求头
 *   @CookieValue        — 获取 Cookie 值
 *   @SessionAttribute   — 获取 Session 域中的属性
 */
@Controller
@RequestMapping("/param")
public class ParamController {

    // ==================== @RequestParam 详解 ====================

    /**
     * 基础用法：绑定单个参数
     * 访问：/param/basic?name=张三&age=25
     */
    @GetMapping("/basic")
    public String basic(@RequestParam("name") String username,
                        @RequestParam("age") int age,
                        Model model) {
        model.addAttribute("message", "用户名：" + username + "，年龄：" + age);
        model.addAttribute("from", "@RequestParam 基础绑定");
        return "param-result";
    }

    /**
     * 参数名与方法形参一致时可以省略 @RequestParam 注解（但不推荐，可读性差）
     * 访问：/param/simple?name=张三
     */
    @GetMapping("/simple")
    public String simple(String name, Model model) {
        model.addAttribute("message", "省略注解，直接绑定 name = " + name);
        model.addAttribute("from", "@RequestParam 可省略");
        return "param-result";
    }

    /**
     * required 属性：默认为 true，参数缺失时报 400 错误
     * defaultValue 属性：参数缺失时的默认值
     * 访问：/param/default-value（不携带任何参数也不会报错）
     */
    @GetMapping("/default-value")
    public String defaultValue(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                               @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                               Model model) {
        model.addAttribute("message", "page = " + page + "，size = " + size + "（均使用了默认值）");
        model.addAttribute("from", "required + defaultValue");
        return "param-result";
    }

    /**
     * 接受多个同名参数（如复选框）
     * 访问：/param/hobbies?hobby=篮球&hobby=游泳&hobby=编程
     */
    @GetMapping("/hobbies")
    public String hobbies(@RequestParam("hobby") String[] hobbies, Model model) {
        model.addAttribute("message", "你的爱好：" + String.join(", ", hobbies));
        model.addAttribute("from", "@RequestParam 数组绑定");
        return "param-result";
    }

    /**
     * 使用 Map 接收所有参数
     * 访问：/param/all?name=张三&age=25&city=北京
     */
    @GetMapping("/all")
    public String allParams(@RequestParam Map<String, String> allParams, Model model) {
        model.addAttribute("message", "所有参数：" + allParams);
        model.addAttribute("from", "@RequestParam Map 接收全部参数");
        return "param-result";
    }

    // ==================== @RequestHeader 详解 ====================

    /**
     * 获取指定请求头
     */
    @GetMapping("/header")
    public String header(@RequestHeader("User-Agent") String userAgent,
                         @RequestHeader(value = "Accept-Language", required = false, defaultValue = "未知") String lang,
                         Model model) {
        model.addAttribute("message", "User-Agent: " + userAgent + "\nAccept-Language: " + lang);
        model.addAttribute("from", "@RequestHeader 获取请求头");
        return "param-result";
    }

    // ==================== @CookieValue 详解 ====================

    /**
     * 写 Cookie + 读 Cookie 的完整演示
     *
     * Step 1：访问 /param/set-cookie  写入名为 theme 的 Cookie
     * Step 2：访问 /param/get-cookie  读取名为 theme 的 Cookie
     */
    @GetMapping("/set-cookie")
    @ResponseBody
    public String setCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("theme", "dark-mode");
        cookie.setMaxAge(60 * 60);          // 有效期 1 小时
        cookie.setPath("/");                 // 整个应用可用
        cookie.setHttpOnly(true);            // 防止 JS 读取（安全）
        response.addCookie(cookie);
        return "Cookie 'theme=dark-mode' 已写入！<br><a href='get-cookie'>点击读取 Cookie</a>";
    }

    @GetMapping("/get-cookie")
    public String getCookie(@CookieValue(value = "theme", defaultValue = "light-mode") String theme,
                            Model model) {
        model.addAttribute("message", "当前主题 Cookie：theme = " + theme);
        model.addAttribute("from", "@CookieValue 读取 Cookie");
        return "param-result";
    }

    // ==================== @SessionAttribute 详解 ====================

    /**
     * 写入 Session → 读取 Session 的完整演示
     *
     * Step 1：访问 /param/login?username=张三  将用户名写入 Session
     * Step 2：访问 /param/dashboard           从 Session 中读取用户名
     *
     * 注意：@SessionAttribute 只负责读取，写入 Session 需要用 HttpSession
     */
    @GetMapping("/login")
    public String login(@RequestParam("username") String username, HttpSession session, Model model) {
        session.setAttribute("currentUser", username);      // 写入 Session
        model.addAttribute("message", "已登录！Session 中存入 currentUser = " + username);
        model.addAttribute("from", "HttpSession 写入 → @SessionAttribute 读取");
        return "param-result";
    }

    @GetMapping("/dashboard")
    public String dashboard(@SessionAttribute("currentUser") String currentUser, Model model) {
        model.addAttribute("message", "欢迎回来，" + currentUser + "！这是你的控制台。");
        model.addAttribute("from", "@SessionAttribute 读取 Session 属性");
        return "param-result";
    }

    /**
     * 表单页面入口
     */
    @GetMapping("/form")
    public String formPage() {
        return "param-form";
    }

    /**
     * 处理表单提交
     */
    @PostMapping("/submit-form")
    public String submitForm(@RequestParam("username") String username,
                             @RequestParam("email") String email,
                             @RequestParam(value = "remember", defaultValue = "off") String remember,
                             HttpSession session,
                             HttpServletResponse response,
                             Model model) {
        session.setAttribute("currentUser", username);
        if ("on".equals(remember)) {
            Cookie cookie = new Cookie("rememberedUser", username);
            cookie.setMaxAge(7 * 24 * 60 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        model.addAttribute("message", "表单提交成功！用户名：" + username + "，邮箱：" + email);
        model.addAttribute("from", "表单综合演示");
        return "param-result";
    }
}
