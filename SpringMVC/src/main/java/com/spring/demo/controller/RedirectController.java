package com.spring.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

/**
 * 重定向 & 请求转发 演示控制器。
 *
 * 核心区别：
 *   ┌──────────┬────────────────────┬────────────────────┬──────────────┐
 *   │          │    请求转发          │     重定向          │              │
 *   │          │  (forward)          │   (redirect)       │              │
 *   ├──────────┼────────────────────┼────────────────────┼──────────────┤
 *   │ 浏览器URL│  不变               │  变为新 URL         │              │
 *   │ 请求次数 │  1 次               │  2 次              │              │
 *   │ 数据共享 │  request 域共享      │  request 域丢失     │              │
 *   │ 适用场景 │  同一应用内部跳转     │  跨应用/防重复提交   │              │
 *   │ 速度     │  快（服务器内部）     │  慢（客户端重新请求）│              │
 *   └──────────┴────────────────────┴────────────────────┴──────────────┘
 */
@Controller
@RequestMapping("/nav")
public class RedirectController {

    // ==================== 请求转发（forward） ====================

    /**
     * 方式一：返回字符串 "forward:路径"
     *
     * 访问 /nav/forward-demo
     * → 服务器内部转发到 /hello/say（浏览器地址栏不变，仍显示 /nav/forward-demo）
     */
    @GetMapping("/forward-demo")
    public String forwardDemo() {
        // forward: 前缀告诉 Spring MVC 不走视图解析器，而是做请求转发
        return "forward:/hello/say";
    }

    /**
     * 方式二：forward 时携带数据（request 域共享）
     *
     * 访问 /nav/forward-with-data
     * → 转发到 /nav/internal（request 域数据不丢失）
     */
    @GetMapping("/forward-with-data")
    public String forwardWithData(Model model) {
        model.addAttribute("message", "这条数据来自转发源（request 域共享）");
        model.addAttribute("from", "请求转发 - forward: 前缀");
        return "forward:/nav/internal";
    }

    /**
     * 转发目标（实际渲染页面）
     */
    @GetMapping("/internal")
    public String internal(Model model) {
        // 如果 Model 中没有 message（直接访问此 URL），显示默认提示
        if (!model.containsAttribute("message")) {
            model.addAttribute("message", "你直接访问了此页面（没有经过转发）");
            model.addAttribute("from", "直接访问");
        }
        return "target";
    }

    // ==================== 重定向（redirect） ====================

    /**
     * 方式一：返回字符串 "redirect:路径"
     *
     * 访问 /nav/redirect-demo
     * → 浏览器收到 302 + Location 头 → 自动发起 GET /hello/say（地址栏变为 /hello/say）
     */
    @GetMapping("/redirect-demo")
    public String redirectDemo() {
        return "redirect:/hello/say";
    }

    /**
     * 方式二：使用 RedirectView 对象
     */
    @GetMapping("/redirect-view")
    public RedirectView redirectView() {
        RedirectView redirectView = new RedirectView("/hello/say");
        redirectView.setContextRelative(true);  // 相对于当前应用上下文
        return redirectView;
    }

    /**
     * 方式三：重定向时携带参数（URL 参数方式）
     *
     * RedirectAttributes.addAttribute() → 拼接到 URL ? 后面（明码，浏览器可见）
     * RedirectAttributes.addFlashAttribute() → 存入 FlashMap（一次有效，不暴露在 URL）
     */
    @GetMapping("/redirect-with-params")
    public String redirectWithParams(RedirectAttributes redirectAttributes) {
        // addAttribute：会拼接到 URL 上（/hello/say?source=redirect）
        redirectAttributes.addAttribute("source", "redirect");

        // addFlashAttribute：存入 Session 中的 FlashMap，重定向后取出即销毁
        // 注意：目标 Controller 需要用 @ModelAttribute 或从 Model 中读取
        redirectAttributes.addFlashAttribute("flashMsg", "这是一条 flash 消息（仅一次有效）");

        return "redirect:/hello/say";
    }

    // ==================== 对比演示入口 ====================

    @GetMapping("/index")
    public String index() {
        return "nav-index";
    }
}
