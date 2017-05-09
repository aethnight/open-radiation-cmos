package fr.openradiation.image;

import java.io.File;
import java.io.IOException;

import com.drew.imaging.ImageProcessingException;

public class Jpg extends Img {
	
	/**
	 * 
	 * @param file
	 * @throws IOException
	 * @throws ImageProcessingException 
	 */

	public Jpg(File file) throws IOException, ImageProcessingException {
		super(file);
		// TODO Auto-generated constructor stub
	}

}

/** to do
 *  comprendre la compression jpeg
 */
