/**
 * 
 */
package org.humanizer.rating;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.humanizer.rating.objects.RatingResult;

import com.google.gson.Gson;

/**
 * @author sonhv
 *  
 * Showing for handling rating/updating search result
 */
@SuppressWarnings("serial")
public class RateServlet extends HttpServlet {
  //private static final Logger log = Logger.getLogger(AuthenServlet.class.getName());
  
  /**
   * @author sonhv
   * GET handling
   * Redirect to POST
   */
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    doPost(req,resp);
  }
  
  /**
   * @author sonhv
   * POST handling
   * Perform rate on a search result
   */
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    HttpSession sess = req.getSession(true);
    String m_url = req.getParameter("url");
    String keyword = req.getParameter("query");
    String task = req.getParameter("task");
    String point = req.getParameter("rating");
    String note = req.getParameter("note");
    String username = (String) sess.getAttribute("username");
    
    Gson json = new Gson();
    RatingResult rating = new RatingResult();
    rating.task_id = task;
    rating.url = m_url;
    rating.rater = username;
    rating.relevance = point;
    rating.note = note;
    rating.query = keyword;
    
    String json_message = json.toJson(rating);
    //String message = URLEncoder.encode(json_message, "UTF-8");
    String message = json_message;
    //1. Perform rate on a keyword 
    try {
      URL url = new URL("http://humanizer.iriscouch.com/ratings/");
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setDoOutput(true);
      connection.setRequestMethod("POST");

      OutputStreamWriter writer =
          new OutputStreamWriter(connection.getOutputStream());
      writer.write(message);
      writer.close();
  
      if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        // OK
      } else {
        // Server returned HTTP error code.
      }
    } catch (MalformedURLException e) {
      // ...
      e.printStackTrace();
    } catch (IOException e) {
      // ...
      e.printStackTrace();
    }
    
    //2. Request rating information, resubmit performed    
    StringBuilder sb = new StringBuilder();
    try {
      URL url = new URL("http://humanizer.iriscouch.com/ratings/_design/api/_view/rating?startkey=%22" + task + "%22&endkey=%22" + task + "%22&include_docs=true");
      //URL url = new URL("http://humanizer.iriscouch.com/raters/49abdb0279e6d16429b4cb3624000ea2");
      //URL url = new URL("http://google.com");
      BufferedReader reader =
          new BufferedReader(new InputStreamReader(url.openStream()));
      
      String line;
      while ((line = reader.readLine()) != null) {
        sb.append(line);
      }
      reader.close();
    } catch (MalformedURLException e) {
      // ...
    } catch (IOException e) {
      // ...
    } 
    
    json = new Gson();
    //TasksByRater rate = json.fromJson(sb.toString(),TasksByRater.class);
    RatingResult rater = new RatingResult();
    if (rater.init(sb.toString(), keyword, task, m_url, username) == true) {
      req.setAttribute("relevance", rater.relevance);
      req.setAttribute("note", rater.note);
    }else{
      req.setAttribute("relevance", "0");
      req.setAttribute("note", "");
    }
    
    req.setAttribute("keyword", keyword);
    req.setAttribute("task", task);
    req.setAttribute("url", m_url);

    RequestDispatcher dispatcher = req.getRequestDispatcher("/rating.jsp");

    if (dispatcher != null) {
      try {
        dispatcher.forward(req, resp);
      } catch (ServletException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    } 
  }  

}
