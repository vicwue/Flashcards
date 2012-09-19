package com.google.appengine.demos.helloorm;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class HTMLHelper {
	public static void printHeader(HttpServletResponse resp)
			throws IOException {
		// PersistenceStandard ps = PersistenceStandard.get();
		// resp.getWriter().println("<form action=\"/\" method=\"post\">");
		// resp.getWriter().println(ps.name() + " Query for flights: "
		// + "<input name=\"q\" type=\"text\" size=\"100\" "
		// + "value=\"" + (query == null ? "" : query) + "\"/>");
		// resp.getWriter().println("<input type=\"submit\" value=\"Run Query\">");
		resp.getWriter()
		.println("<head>");
				
				resp.getWriter()
				.println(
						"<link href='css/bootstrap.css' rel='stylesheet'><link href='css/bootstrap-responsive.css' rel='stylesheet'><style>body{padding-top:30px;}</style><script src='js/jquery.js'></script><script src='js/bootstrap.js'></script><script src='js/code.js'></script>");
				resp.getWriter()
				.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'></head>");
	}
}
