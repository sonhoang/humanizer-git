package com.cstsolution.sra;

import java.io.*;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
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
public class RateServlet extends HttpServlet {
	//private static final Logger log = Logger.getLogger(AuthenServlet.class.getName());
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		//resp.setContentType("text/plain");
		//resp.getWriter().println("Hello, world");
		doPost(req,resp);
	}
	
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
        try {
        	URL url = new URL("http://humanizer.iriscouch.com/ratings/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
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
        
        
        //resubmit
        //perform get task info by username
        StringBuilder sb = new StringBuilder();
        try {
        	URL url = new URL("http://humanizer.iriscouch.com/ratings/_design/api/_view/rating?startkey=%22" + task + "%22&endkey=%22" + task + "%22&include_docs=true");
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
        
        json = new Gson();
        //TasksByRater rate = json.fromJson(sb.toString(),TasksByRater.class);
        RatingResult rater = new RatingResult();
        if (rater.init(sb.toString(), keyword, task, m_url, username) == true){
        	req.setAttribute("relevance", rater.relevance);
        	req.setAttribute("note", rater.note);
        }else{
        	req.setAttribute("relevance", "0");
        	req.setAttribute("note", "");
        }
        //rater.init(sb.toString(),"\""+ keyword +"\"");
        
        req.setAttribute("keyword", keyword);
        req.setAttribute("task", task);
        req.setAttribute("url", m_url);
        
        

        RequestDispatcher dispatcher = req.getRequestDispatcher("/rating.jsp");

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
