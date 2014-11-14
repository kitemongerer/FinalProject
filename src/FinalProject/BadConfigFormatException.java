package FinalProject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class BadConfigFormatException extends Exception {
	private String message;
	public BadConfigFormatException() {
		message = "Error in the formatting of a config file";
		try {
			logErr();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
		}
	}

	public BadConfigFormatException(String badFile) {
		this.message = "Error in the formatting of config file: " + badFile;
		try {
			logErr();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public String toString() {
		return "BadConfigFormatException [" + message + "]";
	}

	private void logErr() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("errorLog.txt", "UTF-8");
		writer.println(message);
		writer.close();
	}

}
