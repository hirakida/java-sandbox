package com.example;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class HelloServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloServlet.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("sessionId={}", req.getSession().getId());
        ServletOutputStream out = resp.getOutputStream();
        out.write("Hello!".getBytes());
        out.flush();
        out.close();
    }
}
