package com.cstsolution.sra;

import java.io.*;
import com.google.gson.Gson;

import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException; 


//import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;


@SuppressWarnings("serial")
public class ListDetailServlet extends HttpServlet {
	//private static final Logger log = Logger.getLogger(AuthenServlet.class.getName());
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		//resp.setContentType("text/plain");
		//resp.getWriter().println("Hello, world");
		doPost(req, resp);
	}
	
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
    //UserService userService = UserServiceFactory.getUserService();
    //User user = userService.getCurrentUser();
    	
    HttpSession sess = req.getSession(true);
    String keyword = req.getParameter("keyword");
    String task = req.getParameter("task");
    
    String username = (String) sess.getAttribute("username");
    
    //perform get task by username
    StringBuilder sb = new StringBuilder();
    try {
    	URL url = new URL("http://humanizer.iriscouch.com/tasks/_design/api/_view/rater_tasks?startkey=%22" + username + "%22&endkey=%22" + username + "%22&include_docs=true");
        //URL url = new URL("http://humanizer.iriscouch.com/raters/49abdb0279e6d16429b4cb3624000ea2");
    	//URL url = new URL("http://google.com");
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
    	
        
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
    
    Gson json = new Gson();
    //TasksByRater rate = json.fromJson(sb.toString(),TasksByRater.class);
    TasksByRater rater = new TasksByRater();
    rater.init(sb.toString(),"\""+ keyword +"\"");
    req.setAttribute("data", rater.data);
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

    //System.out.print(sb.toString());
    /*
    HttpClient httpclient = new DefaultHttpClient();
    HttpGet httpget = new HttpGet("http://humanizer.iriscouch.com/raters/49abdb0279e6d16429b4cb3624000ea2");
    HttpResponse response = httpclient.execute(httpget);
    HttpEntity entity = response.getEntity();
    if (entity != null) {
        InputStream instream = entity.getContent();
        try {
            // do something useful
        	//read it with BufferedReader
        	BufferedReader br
            	= new BufferedReader(
            		new InputStreamReader(instream));
     
        	StringBuilder sb = new StringBuilder();
     
        	String line;
        	while ((line = br.readLine()) != null) {
        		sb.append(line);
        	}
        	//JSo
            System.out.print(sb.toString());
        } finally {
            instream.close();
        }
    }*/
    
    //list user
    
    

	}	
}
