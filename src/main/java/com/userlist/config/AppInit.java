package com.userlist.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

// AppInit наследуется от AbstractAnnotationConfigDispatcherServletInitializer,
// благодаря чему Spring при запуске веб-приложения автоматически создаёт и регистрирует DispatcherServlet
public class AppInit extends AbstractAnnotationConfigDispatcherServletInitializer {

    // Корневая конфигурация Spring.
    // Пока отдельный Root ApplicationContext не используется.
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    // Указываем конфигурацию Spring MVC.
    // WebConfig настраивает контроллеры, Thymeleaf, ViewResolver и другие компоненты web-слоя для отображения HTML-шаблонов
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] {
                WebConfig.class
        };
    }

    // Указываем путь, по которому DispatcherServlet принимает запросы.
    // "/" — DispatcherServlet работает от корня нашего веб-приложения.
    @Override
    protected String[] getServletMappings() {
        return new String[] {
                "/"
        };
    }
}
