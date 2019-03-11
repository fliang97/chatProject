package Server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class FileServer extends Thread{
	private String[] tokens;
	private ServerSocket fileSocket;
	private Server server;
	private String FilePath;

	public FileServer(String[] tokens, ServerSocket fileSocket, Server server) {
		this.tokens = tokens;
		this.fileSocket = fileSocket;
		this.server = server;
	}
	
	public boolean receiveFile(){
		int bytesRead;
	    int current = 0;
	    FileOutputStream fos = null;
	    BufferedOutputStream bos = null;
	    Socket sock = null;
	    int FILE_SIZE = 95551830;
	    String fileName = "sentfrom" + tokens[2] + "to" + tokens[1] + tokens[3];
	    String FILE_TO_RECEIVED = "/Users/liangfujie/Downloads/cs176b/Server_Receive/" + fileName;
	    this.FilePath = FILE_TO_RECEIVED;

	    try {
	        
		    String msg = "sendrequestconfirm\n";
		    List<MultipleConnection> connectionList = server.getConnectionList();
			for(MultipleConnection mc : connectionList) {
				
				if(mc.getLogin().equalsIgnoreCase(tokens[2])) {
					mc.send(msg);
				}
			}
	        
	            sock = fileSocket.accept(); // new
	        
	            byte [] mybytearray  = new byte [FILE_SIZE];
	            InputStream is = sock.getInputStream();
	            fos = new FileOutputStream(FILE_TO_RECEIVED);
	            bos = new BufferedOutputStream(fos);
	            bytesRead = is.read(mybytearray,0,mybytearray.length);
	            current = bytesRead;

	            do {
	                bytesRead =
	                is.read(mybytearray, current, (mybytearray.length-current));
	          if(bytesRead >= 0) current += bytesRead;
	            }while(bytesRead > -1);

	            bos.write(mybytearray, 0 , current);
	            bos.flush();
	            System.out.println("File " + FILE_TO_RECEIVED
	          + " downloaded (" + current + " bytes read)");
	        
	    }catch(Exception e){
	    	e.printStackTrace();
	    	return false;
	    }finally {
	    	try {
	    		if (fos != null) fos.close();
	    		if (bos != null) bos.close();
	    		if (sock != null) sock.close();
	    	}catch(IOException e1) {
	    		e1.printStackTrace();
	    		return false;
	    	}
	      }
	    return true;
	}
	
	public boolean sendFile() {
		FileInputStream fis = null;
	    BufferedInputStream bis = null;
	    OutputStream os = null;
	    Socket sock = null;
	    
	    while(true) {
	    try {
	    	sock = fileSocket.accept();
	    	File myFile = new File (FilePath);
	          byte [] mybytearray  = new byte [(int)myFile.length()];
	          fis = new FileInputStream(myFile);
	          bis = new BufferedInputStream(fis);	
	          bis.read(mybytearray,0,mybytearray.length);
	          os = sock.getOutputStream();
	          System.out.println("Sending " + FilePath + "(" + mybytearray.length + " bytes)");
	          os.write(mybytearray,0,mybytearray.length);
	          os.flush();
	          System.out.println("Done.");
	    }catch(IOException e) {
	    	e.printStackTrace();
	    }finally {
	    	try {
	    		if (bis != null) bis.close();
	            if (os != null) os.close();
	            if (sock!=null) sock.close();
	    	}catch(IOException e) {
	    		e.printStackTrace();
	    	}
	    }
		
		return true;
	    }
	}
}