package ds.assignment.utils;

public class ModalUtils {
	public static String createModalForLocaltime(String localtime, String city){
		String header = "<div class='modal fade' id='localtime-modal'>"
				+ "<div class='modal-dialog' role='document'>"
				+ "<div class='modal-content'>"
				+ "<div class='modal-header'>"
				+ "<button type='button' class='close' data-dismiss='modal'"
				+ "aria-label='Close'>"
				+ "<span aria-hidden='true'>&times;</span>"
				+ "</button>"
				+ "<h4 class='modal-title'>Localtime</h4>"
				+ "</div>"
				+ "<div class='modal-body'>";
		
		String content = "<p>In " + city + " it's " + localtime + ".</p>";
		
		String footer = "</div>"
				+ "<div class='modal-footer'>"
				+ "<button type='button' class='btn btn-secondary'"
				+ "data-dismiss='modal'>Close</button>"
				+ "</div>"
				+ "</div>"
				+ "</div>"
				+ "</div>";
		
		return header + content + footer;
	}
}
