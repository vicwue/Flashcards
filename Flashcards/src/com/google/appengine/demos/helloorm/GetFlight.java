// Copyright 2008 Google Inc. All Rights Reserved.
package com.google.appengine.demos.helloorm;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Max Ross <maxr@google.com>
 */
public class GetFlight extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("text/html");
    HTMLHelper.printHeader(resp);
    String key = req.getParameter("key");
    if (key == null) {
      resp.getWriter().println("No key provided.");
      return;
    }
    Flashcard f;
    if (PersistenceStandard.get() == PersistenceStandard.JPA) {
      f = findJPA(Long.valueOf(key));
    } else {
      f = findJDO(Long.valueOf(key));
    }
    resp.getWriter().println("<body><div class='container-fluid'><div class='row-fluid'> <div class='span12'><form action=\"updateFlight\" method=\"post\">");
    resp.getWriter().println("<input name=\"key\" type=\"hidden\" value=\"" + key + "\"/>");

    resp.getWriter().println("<label>Origin</label><input class='span12'  name=\"orig\" type=\"text\" value=\"" + f.getQuestion() + "\"/>");
    resp.getWriter().println("<label>Destination</label><input class='span12' name=\"dest\" type=\"text\" value=\"" + f.getAnswer() + "\"/>");
 
    resp.getWriter().println("<button type=\"submit\" class='btn btn-block btn-large btn-success'>Update Flight</button>");
 
    resp.getWriter().println("</form>");
    resp.getWriter().println("<form action=\"deleteFlight\" method=\"post\">");
    resp.getWriter().println("<input name=\"key\" type=\"hidden\" value=\"" + key + "\"/>");
    resp.getWriter().println("<button type=\"submit\" class='btn btn-block btn-small' >Delete Flight</button>");
    resp.getWriter().println("</form></div></div></body>");
  }

  private Flashcard findJPA(long key) {
    EntityManager em = EMF.get().createEntityManager();
    try {
      return em.find(Flashcard.class, key);
    } finally {
      em.close();
    }
  }

  private Flashcard findJDO(long key) {
    PersistenceManager pm = PMF.get().getPersistenceManager();
    try {
      return pm.getObjectById(Flashcard.class, key);
    } finally {
      pm.close();
    }
  }
}
