package com.cstsolution.sra;

import com.google.gson.Gson;

import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException; 


//import java.util.logging.Logger;

import javax.servlet.http.*;


@SuppressWarnings("serial")
public class AuthenServlet extends HttpServlet {
	//private static final Logger log = Logger.getLogger(AuthenServlet.class.getName());
	/*public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		//resp.setContentType("text/plain");
		//resp.getWriter().println("Hello, world");
		doPost(req,resp);
	}*/
	
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
    //UserService userService = UserServiceFactory.getUserService();
    //User user = userService.getCurrentUser();
    	
    HttpSession sess = req.getSession(true);
    
    String user = req.getParameter("login-user");
    String password = req.getParameter("login-password");
    System.out.print(user);
    System.out.print(password);
    
    String password2 = password;
    try { 
    	MessageDigest digest = java.security.MessageDigest.getInstance("MD5"); 
    	digest.update(password.getBytes()); 
    	password2 = digest.digest().toString(); 
    	} catch (NoSuchAlgorithmException e) { 

    }
    
    try {
        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
        byte[] array = md.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
       }
        password2 = sb.toString();
    } catch (java.security.NoSuchAlgorithmException e) {
    }    
    

    StringBuilder sb = new StringBuilder();
    try {
        URL url = new URL("http://humanizer.iriscouch.com/raters/_design/api_raters/_view/rater_list?startkey=%22" + user + "%22&endkey=%22" + user + "%22&include_docs=false");
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
    
    System.out.print(sb.toString());
    Gson json = new Gson();
    Rater rate = new Rater();
    rate.init(sb.toString());
    //json.fromJson(sb.toString(),Rater.class);
    
    //check
    if (rate.email == null){
    	resp.sendRedirect("/login.jsp");
    }
    if ((rate.email.equals("\"" + user + "\"")) && (rate.password.equals("\"" + password2 + "\""))){
        sess.setAttribute("username", user);
        //sess.setAttribute("password", password);    	
    	resp.sendRedirect("/list_keyword");
    	
    }else{
    	resp.sendRedirect("/login.jsp");
    	
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
