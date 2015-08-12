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

import com.zenilt.kalah.KalahGame;
import com.zenilt.kalah.KalahMode;
import com.zenilt.kalah.exception.KalahException;
import freemarker.template.Template;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Juan Francisco Rodríguez
 */
@WebServlet(name = "StartServlet", urlPatterns = {StartServlet.URL})
public class StartServlet extends KalahServlet {

    public static final String URL = "/start";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	HttpSession session = request.getSession();
	KalahGame game = new KalahGame(KalahMode.STONES_3);
	session.setAttribute("game", game);
	Template template = templateConfiguration.getTemplate("start.html");
	Map root = new HashMap();
	render(request, response, template, root);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	try {
	    HttpSession session = request.getSession();
	    KalahGame game = (KalahGame) session.getAttribute("game");
	    game.start();
	    session.setAttribute("game", game);
	    response.sendRedirect(appUrl(request, GameServlet.URL));
	} catch (KalahException e) {
	    Logger.getLogger("Kalah").log(Level.WARNING, e.getMessage(), e);
	}
    }

}
