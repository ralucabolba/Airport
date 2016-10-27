package ds.assignment.view;

public class LoginRenderer {
	private static final String URL = "http://localhost:8181/Airport";

	private static final String HTML_HEADER = "<!DOCTYPE html>" + "<html>" + "<head>" + "<title>Airport</title>"
			+ "<link rel='stylesheet' type='text/css' href='" + URL + "/bootstrap/css/bootstrap.min.css'>" + "</head>" + "<body>";

	private static final String HTML_END = "<script src='" + URL
			+ "/bootstrap/js/bootstrap.min.js' type='text/javascript'></script>" + "</body>" + "</html>";
	
	public static String renderLogin(String content){
		StringBuilder out = new StringBuilder();
		
		out.append(HTML_HEADER);
		
		out.append("<div class='container login-container'>"
				+ "<div class='col-sm-12 col-sm-offset-1'>"
				+ "<h1 style='margin-bottom:40px'>Sign in</h1>"
				+ "<form action='login' method='POST'>"
				+ "<div class='form-group'>"
				+ "<label for='username'>Username :</label> <input type='text'"
				+ "name='username' />"
				+ "</div>"
				+ "<div class='form-group'>"
				+ "<label for='password'>Password :</label> <input type='password'"
				+ "name='password'/>"
				+ "</div>"
				+ "<button type='submit' class='btn btn-success'>Sign in</button>"
				+ "</form>"
				+ "</div>"
				+ "</div>");
		
		if(content != null){
			out.append(content);
		}
		
		out.append(HTML_END);
		
		return out.toString();
	}
}
