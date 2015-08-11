/*
 * The MIT License
 *
 * Copyright 2015 Juan Francisco Rodríguez.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.zenilt.kalahweb;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Juan Francisco Rodríguez
 */
public class KalahServlet extends HttpServlet {

    protected Configuration templateConfiguration;

    protected HashMap<String, String> lang;

    @Override
    public void init() throws ServletException {
	try {
	    //fremarker
	    templateConfiguration = new Configuration(Configuration.VERSION_2_3_22);
	    templateConfiguration.setDirectoryForTemplateLoading(new File(getServletContext().getRealPath("/WEB-INF/views")));
	    templateConfiguration.setDefaultEncoding("UTF-8");
	    templateConfiguration.setLocale(Locale.forLanguageTag("en_US"));
	    templateConfiguration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
	    //lang file
	    lang = new HashMap<>();
	    Properties langProperties = new Properties();
	    langProperties.load(getServletContext().getResourceAsStream("/WEB-INF/langs/en.properties"));
	    langProperties.stringPropertyNames().stream().forEach((name) -> {
		lang.put(name, langProperties.getProperty(name));
	    });
	} catch (Exception e) {
	    Logger.getLogger("Kalah").log(Level.WARNING, e.getMessage(), e);
	}
    }

    protected void render(HttpServletRequest request, HttpServletResponse response, Template template, Map root) {
	try {
	    root.put("lang", lang);
	    root.put("url", appUrl(request, "/"));
	    template.process(root, response.getWriter());
	    response.flushBuffer();
	} catch (Exception e) {
	    Logger.getLogger("Kalah").log(Level.WARNING, e.getMessage(), e);
	}
    }

    protected static String appUrl(HttpServletRequest request, String servletURL) {
	String url = request.getScheme();
	url += "://";
	url += request.getServerName();
	url += ("http".equals(request.getScheme()) && request.getServerPort() == 80 || "https".equals(request.getScheme()) && request.getServerPort() == 443 ? "" : ":" + request.getServerPort());
	url += request.getServletContext().getContextPath();
	url += servletURL;
	return url;
    }

}
