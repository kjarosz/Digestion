package com.kjarosz.digestion.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JOptionPane;

public class ErrorLog {
   private final String ERROR_LOG_FILE = "ErrorLog.txt";
   
   private static ErrorLog errorLog;
   
	private BufferedWriter mErrorLog;
	private boolean mIntroWritten;
	
	private ErrorLog() {
      try {
         createLogWriter();
      } catch(IOException e) {
         try {
            createFailsafeLogWriter();
         } catch(IOException ex) {
            /* Failsafe uses System.out as an output stream. If that doesn't
             * work, there is nothing that can be done in the log's favor. */
         }
      }
	}
   
   private void createLogWriter() throws IOException {
      FileWriter fileWriter = new FileWriter(ERROR_LOG_FILE, true /*append mode*/);
      mErrorLog = new BufferedWriter(fileWriter);
      writeIntro();
   }
   
   private void createFailsafeLogWriter() throws IOException {
      OutputStreamWriter stdOutWriter = new OutputStreamWriter(System.out);
      mErrorLog = new BufferedWriter(stdOutWriter);
      writeIntro();
   }
	
	public static ErrorLog getInstance() {
      if(errorLog != null)
         return errorLog;
      
      errorLog = new ErrorLog();
      return errorLog;
	}
	
	public void displayMessageDialog(String message) {
		JOptionPane.showMessageDialog(null, message);
	}
	
	public void writeError(String error) {
		if(mErrorLog == null) {
			System.out.println(error + "\r\n\r\n");
			return;
		}
		
		try {
			if(!mIntroWritten) {
				writeIntro();
				mIntroWritten = true;
			}
			
			mErrorLog.write(error + "\r\n\r\n");
         mErrorLog.flush();
		} catch(IOException ex) {
			displayMessageDialog("Error could not be written to log. It will be displayed in the console.");
			System.out.println(error);
		}
	}
	
	private void writeIntro() throws IOException {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd, yyyy    HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		
		mErrorLog.write("****************************************************************************\r\n");
		mErrorLog.write("*    New ErrorLog Entry\r\n");
		mErrorLog.write("*    Date: " + dateFormatter.format(calendar.getTime()) + "\r\n");
		mErrorLog.write("****************************************************************************\r\n");
	}
}
