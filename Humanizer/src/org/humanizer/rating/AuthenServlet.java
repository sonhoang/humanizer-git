/**
 * 
 */
package org.humanizer.rating;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.humanizer.rating.objects.Rater;


/**
 * @author sonhv
 * 
 * Servlet for user authentication 
 */
@SuppressWarnings("serial")
public class AuthenServlet extends HttpServlet {
  //private static final Logger log = Logger.getLogger(AuthenServlet.class.getName());
  /**
   * @author sonhv
   * 
   * GET handling. Forward to POST
   *  
   */
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    doPost(req,resp);
  }

  /**
   * @author sonhv
   * 
   * POST handling
   * Accept user/pass from client, submit to couchDB and starting to authen 
   */
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
      
    HttpSession sess = req.getSession(true);
    
    //1. Accept user/pass from client
    String user = req.getParameter("login-user");
    String password = req.getParameter("login-password");
    
    String password2 = password;

    //2. MD5 pass for client
    try {
      java.security.MessageDigest md =
          java.security.MessageDigest.getInstance("MD5");
      byte[] array = md.digest(password.getBytes());
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < array.length; ++i) {
        sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
      }
      password2 = sb.toString();
    } catch (java.security.NoSuchAlgorithmException e) {
      e.printStackTrace();
    }    
    
    //3. Authentication to couch DB
    StringBuilder sb = new StringBuilder();
    try {
        URL url = new URL("http://humanizer.iriscouch.com/raters/_design/api_raters/_view/rater_list?startkey=%22" + user + "%22&endkey=%22" + user + "%22&include_docs=false");
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
    
    //4. Json string process
    Rater rater = new Rater();
    rater.init(sb.toString());
    
    //5. check if rater is authenticated
    if (rater.getEmail() == null) {
      resp.sendRedirect("/login.jsp");
    } 
    if ((rater.getEmail().equals(user)) &&
        (rater.getPassword().equals(password2))) {
      sess.setAttribute("username", user);
      resp.sendRedirect("/list_keyword");
    } else {
      resp.sendRedirect("/login.jsp");
    }
  }  
}

