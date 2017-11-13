package cn.gson.crm;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.support.MultipartFilter;

import javax.servlet.ServletContext;

/**
 * <p>****************************************************************************</p>
 * <p><b>Copyright © 2010-2017 soho team All Rights Reserved<b></p>
 * <ul style="margin:15px;">
 * <li>Description : 如果是要打成war包，放到tomcat之类的web服务器运行，必须有此类</li>
 * <li>Version     : 1.0</li>
 * <li>Creation    : 2017年07月05日</li>
 * <li>Author      : 郭华</li>
 * </ul>
 * <p>****************************************************************************</p>
 */
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        logger.info("SpringApplicationBuilder....");
        return application.sources(CrmApplication.class);
    }

    @Override
    protected WebApplicationContext createRootApplicationContext(ServletContext servletContext) {
        logger.info("WebApplicationContext....");
        servletContext.addFilter("multipartFilter", new MultipartFilter());
        return super.createRootApplicationContext(servletContext);
    }
}
