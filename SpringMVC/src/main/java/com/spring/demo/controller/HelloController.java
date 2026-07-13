package com.spring.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @RequestMapping 核心演示控制器。
 *
 * 注解层级：
 *   @Controller          — 声明为 Spring MVC 控制器（会被组件扫描发现）
 *   @RequestMapping      — 类级别：当前控制器下所有方法的公共路径前缀
 *   @GetMapping          — 方法级别：GET 请求快捷注解（等价于 @RequestMapping(method=GET)）
 *   @PostMapping         — 方法级别：POST 请求快捷注解
 */
@Controller
@RequestMapping("/hello")
public class HelloController {

    // ==================== @RequestMapping 基础用法 ====================

    /**
     * 最基本的请求映射：URL = /hello/say
     * 返回逻辑视图名 "hello"，视图解析器拼接为 /WEB-INF/views/hello.jsp
     */
    @RequestMapping("/say")
    public String sayHello(Model model) {
        model.addAttribute("message", "欢迎来到 Spring MVC 世界！");
        model.addAttribute("from", "@RequestMapping 基础用法");
        return "hello";
    }

    /**
     * @GetMapping 快捷注解（仅处理 GET 请求）
     * URL = /hello/get-demo
     */
    @GetMapping("/get-demo")
    public String getDemo(Model model) {
        model.addAttribute("message", "这是 GET 请求的演示");
        model.addAttribute("from", "@GetMapping 快捷注解");
        return "hello";
    }

    /**
     * @PostMapping 快捷注解（仅处理 POST 请求）
     * URL = /hello/post-demo
     */
    @PostMapping("/post-demo")
    public String postDemo(Model model) {
        model.addAttribute("message", "这是 POST 请求的演示");
        model.addAttribute("from", "@PostMapping 快捷注解");
        return "hello";
    }

    // ==================== @RequestMapping 属性详解 ====================

    /**
     * method 属性：限定 HTTP 请求方法
     */
    @RequestMapping(value = "/method-demo", method = RequestMethod.GET)
    public String methodDemo(Model model) {
        model.addAttribute("message", "method = RequestMethod.GET 限定只接受 GET");
        model.addAttribute("from", "method 属性");
        return "hello";
    }

    /**
     * params 属性：限定请求必须包含指定参数
     * 例如：/hello/params-demo?type=vip  才会匹配
     */
    @RequestMapping(value = "/params-demo", params = "type=vip")
    public String paramsDemo(Model model) {
        model.addAttribute("message", "params = 'type=vip' 限定必须携带 ?type=vip");
        model.addAttribute("from", "params 属性");
        return "hello";
    }

    /**
     * headers 属性：限定请求头
     * 例如：必须携带 X-Requested-By 请求头
     */
    @RequestMapping(value = "/headers-demo", headers = "X-Requested-By=SpringMVC")
    public String headersDemo(Model model) {
        model.addAttribute("message", "headers 限定请求头 X-Requested-By=SpringMVC");
        model.addAttribute("from", "headers 属性");
        return "hello";
    }

    /**
     * produces 属性：限定 Accept 请求头（客户端期望的响应类型）
     */
    @RequestMapping(value = "/produces-demo", produces = "text/plain;charset=UTF-8")
    @ResponseBody  // 直接返回字符串，不走视图解析
    public String producesDemo() {
        return "produces = 'text/plain;charset=UTF-8' — 返回纯文本";
    }

    /**
     * consumes 属性：限定 Content-Type 请求头（请求体的数据格式）
     */
    @RequestMapping(value = "/consumes-demo", consumes = "application/json")
    @ResponseBody
    public String consumesDemo() {
        return "consumes = 'application/json' — 仅接受 JSON 请求体";
    }
}
