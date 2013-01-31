package com.cstsolution.sra;


import com.google.gson.Gson;

import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;



//import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;


@SuppressWarnings("serial")
public class RatingServlet extends HttpServlet {
	//private static final Logger log = Logger.getLogger(AuthenServlet.class.getName());
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		//resp.setContentType("text/plain");
		//resp.getWriter().println("Hello, world");
		doPost(req,resp);
	}
	
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
    	
        HttpSession sess = req.getSession(true);
        String url_check = req.getParameter("url");
        String keyword = req.getParameter("keyword");
        String task = req.getParameter("task");
        
        String username = (String) sess.getAttribute("username");
        
        
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
        
        Gson json = new Gson();
        //TasksByRater rate = json.fromJson(sb.toString(),TasksByRater.class);
        RatingResult rater = new RatingResult();
        if (rater.init(sb.toString(), keyword, task, url_check, username) == true){
        	req.setAttribute("relevance", rater.relevance);
        	req.setAttribute("note", rater.note);
        }else{
        	req.setAttribute("relevance", "0");
        	req.setAttribute("note", "");
        }
        //rater.init(sb.toString(),"\""+ keyword +"\"");
        
        req.setAttribute("keyword", keyword);
        req.setAttribute("task", task);
        req.setAttribute("url", url_check);
        
        

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
