import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Simulation_renderer {

	public static void main(String[] args) {
		int NB_CELLS = 1024;
		int NB_STEPS = 1000;
		byte[] world= null;
		boolean test = true;
		FileHandler myFile = new FileHandler(NB_CELLS);
		DisplayMap myWindow = new DisplayMap(NB_CELLS);


		Process p = null;
		try {
			p = Runtime.getRuntime().exec("parallel_program.exe ...");
		} catch (IOException e) {
			e.printStackTrace();
		}
		myWindow.showText("Launching parallel_program.exe");

		BufferedReader cStdoutStream = new BufferedReader(new InputStreamReader(p.getInputStream()));
		BufferedReader cStderrStream = new BufferedReader(new InputStreamReader(p.getErrorStream()));

		String stdout;
		String stderr;
		try {
			while ((stdout = cStdoutStream.readLine())!=null ){
				System.out.println(stdout);
				myWindow.showText(stdout);
			}
			while ((stderr = cStderrStream.readLine())!=null ){
				System.out.println(stderr);
				myWindow.showText(stderr,Color.RED);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}



		for (int i =0;i<NB_STEPS && test ;i++){
			world = myFile.getStep();
			test = world!=null;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			test = test && myWindow.newStep(world);
		}

	} // main

}
