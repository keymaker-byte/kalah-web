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
import com.zenilt.kalah.KalahPlayer;
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
@WebServlet(name = "FinishServlet", urlPatterns = {FinishServlet.URL})
public class FinishServlet extends KalahServlet {

    public static final String URL = "/finish";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	try {
	    HttpSession session = request.getSession();
	    KalahGame game = (KalahGame) session.getAttribute("game");
	    Template view = templateConfiguration.getTemplate("finish.html");
	    Map root = new HashMap();
	    root.put("game", game);
	    root.put("reset", appUrl(request, StartServlet.URL));
	    KalahPlayer winer = game.getWinner();
	    if (winer != null) {
		root.put("winner", winer.getName());
	    }
	    render(request, response, view, root);
	} catch (KalahException e) {
	    Logger.getLogger("Kalah").log(Level.WARNING, e.getMessage(), e);
	}
    }

}
