public class Simulation_renderer {

	public static void main(String[] args) {
		int NB_CELLS = 1024;
		int NB_STEPS = 1000;
		byte[] world= null;
		boolean test = true;
		FileHandler myFile = new FileHandler(NB_CELLS);
		DisplayMap myWindow = new DisplayMap(NB_CELLS);

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
