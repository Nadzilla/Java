package lse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
public class Driver {

	public static void main(String[] args) throws FileNotFoundException {
		
		LittleSearchEngine engine = new LittleSearchEngine();
		engine.makeIndex("docs.txt", "noisewords.txt");
		engine.top5search("ready", "herself");
		
		
		
		
		
		

	}

}
