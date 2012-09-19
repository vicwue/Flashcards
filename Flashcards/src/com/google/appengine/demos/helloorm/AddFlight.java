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
public class AddFlight extends HttpServlet {
	private HttpServletResponse response;
  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String orig = req.getParameter("orig");
    String dest = req.getParameter("dest");
    Flashcard f = new Flashcard(orig, dest);
    response = resp;
    if (PersistenceStandard.get() == PersistenceStandard.JPA) {
      doPostJPA(f);
    } else {
      doPostJDO(f);
    }
//    resp.sendRedirect("/");
  }

  private void doPostJDO(Flashcard f) throws IOException {
    PersistenceManager pm = PMF.get().getPersistenceManager();
    try {
      pm.makePersistent(f);
    } finally {
      pm.close();
      
      response.sendRedirect("/");
    }
   
  }

  private void doPostJPA(Flashcard f) throws IOException {
    EntityManager em = EMF.get().createEntityManager();
    try {
      em.persist(f);
    } finally {
    	em.refresh(f);
    	em.close();
      
      response.sendRedirect("/getFlight?key="+f.getId());

    }
  }
}
