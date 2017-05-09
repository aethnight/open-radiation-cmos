package main;

import java.io.File;
import java.io.IOException;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

public class ReadTags {

	public static void main(String[] args) throws ImageProcessingException, IOException {
		String pathIn = "C:/Users/sncuser/Desktop/calella2.dng";
		File jpegFile = new File(pathIn);
		Metadata metadata = ImageMetadataReader.readMetadata(jpegFile);
		int nbrTag = 0;
		for (Directory directory : metadata.getDirectories()) {
		    for (Tag tag : directory.getTags()) {
		        System.out.println(tag);  
		        //System.out.println(tag.getTagType());
		        nbrTag+=1;    
		    }
		    System.out.println(nbrTag);
		}
	}

}
