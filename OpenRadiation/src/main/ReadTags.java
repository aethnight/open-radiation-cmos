package main;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

public class ReadTags {
	
	/**
	 * includes a lot of examples and test
	 * @param args
	 * @throws ImageProcessingException
	 * @throws IOException
	 */

	public static void main(String[] args) throws ImageProcessingException, IOException {
		String pathIn = "C:/Users/sncuser/Desktop/calella1.dng";
		File jpegFile = new File(pathIn);
		Metadata metadata = ImageMetadataReader.readMetadata(jpegFile);
		int nbrTag = 0;
		
		for (Directory directory : metadata.getDirectories()) {
			System.out.println(directory.getName());
		    for (Tag tag : directory.getTags()) {
		        System.out.println(tag);  
		        nbrTag+=1;    
		    }
		    if(directory.containsTag(256)){
		    	System.out.println(directory.getObject(256));
		    }
		    System.out.println(nbrTag);
		}

	}

}
