
package file;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class WriteFile {
	public static void main(String[] args) {
		try {
			write();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void write() throws IOException {
		PrintWriter pw = new PrintWriter(new FileWriter("out.txt"));
		Random rand = new Random();
		for (int i = 0; i < 1000000; i++) {
			pw.write(new Integer(rand.nextInt(3)).toString());
		}
	 
		pw.close();
	}
}