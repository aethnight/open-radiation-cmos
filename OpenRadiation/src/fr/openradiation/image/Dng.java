package fr.openradiation.image;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.MetadataException;

public class Dng extends Img {
	double version, whitelevel;
	long blacklevel;

	public Dng(File file) throws IOException, ImageProcessingException, MetadataException {
		super(file);
		this.version = 0.0;
		this.whitelevel = 0.0;
		this.blacklevel = 0;
		for (Directory directory : this.metadata.getDirectories()) {
			if(directory.containsTag(50706)){	
				this.version = directory.getIntArray(50706)[0]+directory.getIntArray(50706)[1]*0.1;
			}
			if(directory.containsTag(50717)){	
				this.whitelevel = directory.getDouble(50717);
			}
			if(directory.containsTag(50714)){	
				this.blacklevel = (long) Array.get(directory.getObject(50714),0);;
			}
		}
	}
	
	public Dng(String path, String nom) throws IOException, ImageProcessingException, MetadataException {
		super(path, nom);
		this.version = 0.0;
		for (Directory directory : this.metadata.getDirectories()) {
			if(directory.containsTag(50706)){	
				this.version = directory.getIntArray(50706)[0]+directory.getIntArray(50706)[1]*0.1;
			}
			if(directory.containsTag(50717)){	
				this.whitelevel = directory.getDoubleObject(50717);
			}
			if(directory.containsTag(50714)){
				//a affiner si matrice differente...
				this.blacklevel = (long) Array.get(directory.getObject(50714),0);
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
		return "Fichier DNG version "+this.getVersion()+", white level = "+this.whitelevel+" , blacklevel = "+this.blacklevel;
	}
}
