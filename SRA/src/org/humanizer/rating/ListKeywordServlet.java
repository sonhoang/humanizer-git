/**
 * 
 */
package org.humanizer.rating;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.humanizer.rating.objects.TasksByRater;

import com.google.gson.Gson;

/**
 * @author sonhv
 *
 * Listing keyword rating (tasks) for user
 */
public class ListKeywordServlet extends HttpServlet {
	//private static final Logger log = Logger.getLogger(AuthenServlet.class.getName());
	
	/**
	 * @author sonhv
	 * 
	 * GET handling. Forward to POST
	 *  
	 */	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doPost(req, resp);
	}
	
	/**
	 * @author sonhv
	 * 
	 * POST handling
	 * Using username, request task lists from couchDB 
	 */
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
    	
    HttpSession sess = req.getSession(true);
    
    String username = (String) sess.getAttribute("username");    
    StringBuilder sb = new StringBuilder();
    //1. Request tasks list
    try {
    	URL url = new URL("http://humanizer.iriscouch.com/tasks/_design/api/_view/rater_tasks?startkey=%22" + username + "%22&endkey=%22" + username + "%22&include_docs=true");
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
    	String line;
    	while ((line = reader.readLine()) != null) {
    		sb.append(line);
    	}
        reader.close();

    } catch (MalformedURLException e) {
        e.printStackTrace();
    } catch (IOException e) {
    	e.printStackTrace();
    } 
    
    TasksByRater rater = new TasksByRater();
    rater.init(sb.toString());
    req.setAttribute("data", rater.getData());
    
    

    RequestDispatcher dispatcher = req.getRequestDispatcher("/list_keyword.jsp");

    if (dispatcher != null){

    try {
		dispatcher.forward(req, resp);
	} catch (ServletException e) {
		e.printStackTrace();
	}

    } 

	}	
}

