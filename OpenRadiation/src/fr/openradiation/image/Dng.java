package fr.openradiation.image;

import java.io.File;
import java.io.IOException;

public class Dng extends Img {
	double version; 

	public Dng(File file) throws IOException {
		super(file);
		this.version = 1.0;
		
	}

}
