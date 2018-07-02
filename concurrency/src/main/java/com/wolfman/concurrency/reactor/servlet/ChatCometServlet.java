package com.wolfman.concurrency.reactor.servlet;

import org.apache.catalina.comet.CometEvent;
import org.apache.catalina.comet.CometProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

public class ChatCometServlet extends HttpServlet implements CometProcessor {

    @Override
    public void event(CometEvent cometEvent) throws IOException, ServletException {




    }

}
