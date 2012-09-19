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
public class UpdateFlight extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String key = req.getParameter("key");
    String orig = req.getParameter("orig");
    String dest = req.getParameter("dest");
    if (key == null) {
      resp.getWriter().println("No key provided.");
      return;
    }

    if (orig == null) {
      resp.getWriter().println("No origin provided.");
      return;
    }

    if (dest == null) {
      resp.getWriter().println("No destination provided.");
      return;
    }
    if (PersistenceStandard.get() == PersistenceStandard.JPA) {
      doPostJPA(Long.valueOf(key), orig, dest);
    } else {
      doPostJDO(Long.valueOf(key), orig, dest);
    }
    resp.sendRedirect("/");
  }

  private void doPostJDO(long key, String orig, String dest) {
    PersistenceManager pm = PMF.get().getPersistenceManager();
    try {
      Flashcard f = pm.getObjectById(Flashcard.class, key);
      f.setQuestion(orig);
      f.setAnswer(dest);
    } finally {
      pm.close();
    }
  }

  private void doPostJPA(long key, String orig, String dest) {
    EntityManager em = EMF.get().createEntityManager();
    try {
      Flashcard f = em.find(Flashcard.class, key);
      f.setQuestion(orig);
      f.setAnswer(dest);
    } finally {
      em.close();
    }
  }

}
