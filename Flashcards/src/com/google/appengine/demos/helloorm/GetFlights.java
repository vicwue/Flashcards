// Copyright 2008 Google Inc. All Rights Reserved.
package com.google.appengine.demos.helloorm;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetFlights extends HttpServlet {

	private static final String DEFAULT_QUERY = "select from "
			+ Flashcard.class.getName();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doGet(req, resp);
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		long start = System.currentTimeMillis();
		Collection<Flashcard> flashcards;
		String query = DEFAULT_QUERY;
		String customQuery = req.getParameter("q");
		if (customQuery != null) {
			query = customQuery;
		}
		if (PersistenceStandard.get() == PersistenceStandard.JPA) {
			flashcards = queryJPA(query);
		} else {
			flashcards = queryJDO(query);
		}
		HTMLHelper.printHeader(resp);
		resp.getWriter().println(
				"<body><div class='container-fluid'><div class='row-fluid'> <div class='span3'>");
		printAddFlightForm(query, resp);
		resp.getWriter().println(
				"<p>Request time in millis: "
						+ (System.currentTimeMillis() - start)+"</p>");
		resp.getWriter().println("<br>");
		printPersistenceStandardForm(query, resp);
		resp.getWriter().println("</div><div class='span9'><table id='results'>");
		printTableHeader(resp);

		printFlights(resp, flashcards);
		resp.getWriter().println("</table></div>");
		
	}

	private Collection<Flashcard> queryJDO(String query) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			List<Flashcard> flashcards = (List<Flashcard>) pm.newQuery(query).execute();
			// Force all results to be pulled back before we close the entity
			// manager.
			// We could have also called pm.detachCopyAll()
			flashcards.size();
			return flashcards;
		} finally {
			pm.close();
		}
	}

	private List<Flashcard> queryJPA(String query) {
		EntityManager em = EMF.get().createEntityManager();
		try {
			List<Flashcard> flashcards = em.createQuery(query).getResultList();
			// force all results to be pulled back before we close the entity
			// manager
			flashcards.size();
			return flashcards;
		} finally {
			em.close();
		}
	}



	private void printPersistenceStandardForm(String query,
			HttpServletResponse resp) throws IOException {
		PersistenceStandard ps = PersistenceStandard.get();
		resp.getWriter().println("<p>Persistence standard is " + ps.name()+"</p>");

		resp.getWriter().println(
				"<form action=\"updatePersistenceStandard\" method=\"post\">");
		resp.getWriter().println(
				"<button type=\"submit\" class='btn' >Switch to " + ps.getAlternate()
						+ "</button>");
		resp.getWriter().println(
				"<input name=\"persistenceStandard\" type=\"hidden\" value=\""
						+ ps.getAlternate() + "\"/>");
		resp.getWriter().println(
				"<input name=\"q\" type=\"hidden\" value=\"" + query + "\"/>");
		resp.getWriter().println("</form>");
		resp.getWriter().println("</body>");
	}

	private void printAddFlightForm(String query, HttpServletResponse resp)
			throws IOException {
		resp.getWriter().println("<form action=\"addFlight\" method=\"post\">");
		resp.getWriter().println(
				"<label>Origin</label><input class='span12' name=\"orig\" type=\"text\"/>");
		resp.getWriter()
				.println(
						"<label>Destination</label><input class='span12' name=\"dest\" type=\"text\"/>");

		resp.getWriter()
				.println(
						"<button type=\"submit\" class='btn btn-primary btn-block' >Add Flight</button>");
		resp.getWriter().println(
				"<input name=\"q\" type=\"hidden\" value=\"" + query + "\"/>");
		resp.getWriter().println("</form>");
	}

	private void printFlights(HttpServletResponse resp,
			Collection<Flashcard> flashcards) throws IOException {
		if (flashcards.isEmpty()) {
			resp.getWriter().println("<br>No Flights!");
		}
		int index = 1;
		for (Flashcard f : flashcards) {
			resp.getWriter().println("<tr id='row_"+f.getId()+"'>");
			resp.getWriter().println("<td>");
//			resp.getWriter().println(
//					"<a href=row_"+f.getId()\"getFlight?key=" + f.getId() + "\"> " + index++
//							+ "</a>");
			resp.getWriter().println("</td>");
			resp.getWriter().println("<td>");
			resp.getWriter().println(f.getQuestion());
			resp.getWriter().println("</td>");
			resp.getWriter().println("<td>");
			resp.getWriter().println(f.getAnswer());
			resp.getWriter().println("</td>");
			resp.getWriter().println("</tr>"+"</a>");
		}
	}

	private void printTableHeader(HttpServletResponse resp) throws IOException {
		resp.getWriter().println("<thead>");resp.getWriter().println("<tr>");
		resp.getWriter().println("<th>");
		resp.getWriter().println("</th>");
		resp.getWriter().println("<th>");
		resp.getWriter().println("Origin");
		resp.getWriter().println("</th>");
		resp.getWriter().println("<th>");
		resp.getWriter().println("Dest");
		resp.getWriter().println("</th>");
		resp.getWriter().println("</tr>");
		resp.getWriter().println("</thead>");
	}

}
