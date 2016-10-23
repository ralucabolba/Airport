package ds.assignment.servlets;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

@WebServlet("/city")
public class CityServlet extends HttpServlet {
	private static final long serialVersionUID = 1213292608219915606L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");

		Double latitude = Double.valueOf(request.getParameter("latitude"));
		Double longitude = Double.valueOf(request.getParameter("longitude"));

		PrintWriter out = response.getWriter();
		out.println(findLocalTime("http://new.earthtools.org/timezone/" + latitude + "/" + longitude));
	}

	private String findLocalTime(String myUrl) {
		HttpURLConnection connection = null;

		try {
			URL url = new URL(myUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			
			InputStream in = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			
			StringBuilder response = new StringBuilder();
			String line;

			while ((line = reader.readLine()) != null) {
				response.append(line);
				response.append("\r");
			}

			reader.close();

			SAXBuilder saxBuilder = new SAXBuilder();
			Document document = saxBuilder.build(new ByteArrayInputStream(response.toString().getBytes()));
			
			Element timezone = document.getRootElement();
			Element localtime = timezone.getChild("localtime");
			
			return localtime.getValue();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
}
