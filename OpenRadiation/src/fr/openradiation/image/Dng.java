package fr.openradiation.image;

import java.io.File;
import java.io.IOException;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Tag;

public class Dng extends Img {
	double version; 

	public Dng(File file) throws IOException, ImageProcessingException {
		super(file);
		this.version = 0.0;
		for (Directory directory : this.metadata.getDirectories()) {
			if(directory.containsTag(50706)){	
				this.version = directory.getIntArray(50706)[0]+directory.getIntArray(50706)[1]*0.1;
			}
		}
	}
	
	public Dng(String path, String nom) throws IOException, ImageProcessingException {
		super(path, nom);
		this.version = 0.0;
		for (Directory directory : this.metadata.getDirectories()) {
			if(directory.containsTag(50706)){	
				this.version = directory.getIntArray(50706)[0]+directory.getIntArray(50706)[1]*0.1;
			}
		}
	}

	
	public void setVersion(double version){
		this.version = version;
	}
	
	public double getVersion(){
		return this.version;
	}
	
	public String toString(){
		return "Fichier DNG version "+this.getVersion();
	}
}
