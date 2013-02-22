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
 * Showing results for a keyword rating
 */
@SuppressWarnings("serial")
public class ListDetailServlet extends HttpServlet {
  //private static final Logger log = Logger.getLogger(AuthenServlet.class.getName());
  /**
   * @author sonhv
   * 
   * GET handling
   * Redirect to POST 
   */  
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
     throws IOException {
    doPost(req, resp);
  }
  
  /**
   * @author sonhv
   * 
   * POST handling
   * Listing details for rate 
   */
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
  HttpSession sess = req.getSession(true);
  String keyword = req.getParameter("keyword");
  String task = req.getParameter("task");
  
  String username = (String) sess.getAttribute("username");
  
  //perform get rate list by keyword and task
  StringBuilder sb = new StringBuilder();
  try {
    URL url = new URL("http://humanizer.iriscouch.com/tasks/_design/api/_view/rater_tasks?startkey=%22" + username + "%22&endkey=%22" + username + "%22&include_docs=true");
    BufferedReader reader =
        new BufferedReader(new InputStreamReader(url.openStream()));
    
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
  rater.init(sb.toString(),"\""+ keyword +"\"");
  req.setAttribute("data", rater.getData());
  req.setAttribute("keyword", keyword);
  req.setAttribute("task", task);
  
  RequestDispatcher dispatcher = req.getRequestDispatcher("/list_detail.jsp");

  if (dispatcher != null){
    try {
      dispatcher.forward(req, resp);
    } catch (ServletException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  } 

  }  
}
