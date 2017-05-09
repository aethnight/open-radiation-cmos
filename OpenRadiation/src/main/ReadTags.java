package main;

import java.io.File;
import java.io.IOException;

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
		String pathIn = "C:/Users/Pierre/Desktop/tableau.dng";
		File jpegFile = new File(pathIn);
		Metadata metadata = ImageMetadataReader.readMetadata(jpegFile);
		int nbrTag = 0;
		
		for (Directory directory : metadata.getDirectories()) {
			System.out.println(directory.getName());
			if(directory.containsTag(50706)){
				System.out.println(directory.getIntArray(50706)[0]+" "+directory.getIntArray(50706)[1]);
			}
		    for (Tag tag : directory.getTags()) {
		        System.out.println(tag);  
		        //System.out.println(tag.getTagType());
		        nbrTag+=1;    
		    }
		    System.out.println(nbrTag);
		}

	}

}
