import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileHandler {


    private int cellSize = 3;
    private byte[] worldMap;
    private String FILENAME = "./world.bin";
    private FileInputStream inputStream;


    public FileHandler(int NB_CELLS){
        worldMap = new byte[NB_CELLS*NB_CELLS* cellSize];
        try{
            inputStream = new FileInputStream(FILENAME);
        }catch (FileNotFoundException ex){
            System.out.println("File "+FILENAME+" not found");
            ex.printStackTrace();
        }
    }
    protected byte[] getStep(){
        int temp;
        try {
            temp = inputStream.read(worldMap);
            if (temp==-1){
                return null;
            }
            return worldMap;
        } catch (IOException e) {
            System.out.println("Unexpected end of file");
            e.printStackTrace();
        }
        return null;
    }
}
