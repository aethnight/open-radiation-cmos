package fr.openradiation.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;

import javax.imageio.ImageIO;

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
				this.blacklevel = (long) Array.get(directory.getObject(50714),0);
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
	
	public void setWhiteLevel(int wl){
		this.whitelevel = wl;
	}
	
	public void setBlackLevel(long wb){
		this.blacklevel = wb;
	}

	public double getVersion(){
		return this.version;
	}
	
	public double getWhiteLevel(){
		return this.whitelevel;
	}
	
	public long getBlackLevel(){
		return this.blacklevel;
	}
	
	public String toString(){
		return "Fichier DNG version "+this.getVersion()+", white level = "+this.whitelevel+" , blacklevel = "+this.blacklevel;
	}

	public void convertToPng(String pathOut){
		try{
			BufferedImage buffO = new BufferedImage(this.getW(), this.getH(), BufferedImage.TYPE_INT_RGB);

			File outputfile = new File(pathOut);
			int r = 0;
			int g = 0;
			int b = 0;
			int color = 0;
			int col = (r << 16) | (g << 8) | b;
			System.out.println("conversion to RGB");
			double[] res = new double[1];


			for(int i=0; i < this.getH();i++){
				for(int j=0; j<this.getW();j++ ){
					color =(int) this.getBI().getRaster().getPixel(j, i, res)[0];
					r = color/4;
					g = color/4;
					b = color/4;
					col = (r << 16) | (g << 8) | b;
					//buffO.setRGB(this.getH()-1-i, j, col);
					buffO.setRGB(j, i, col);
				}	

			}
			System.out.println("écriture");
			ImageIO.write(buffO, "PNG", outputfile);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("done");
	}


	public void convertToPngRGB(String pathOut){
		try{
			BufferedImage buffO = new BufferedImage(this.getH(), this.getW(), BufferedImage.TYPE_INT_RGB);

			File outputfile = new File(pathOut);
			int r = 0;
			int g = 0;
			int b = 0;
			int color = 0;
			int col = (r << 16) | (g << 8) | b;
			System.out.println("conversion to RGB non demosaifié");
			double[] res = new double[1];


			for(int i=0; i < this.getH();i++){
				for(int j=0; j<this.getW();j++ ){
					color =(int) this.getBI().getRaster().getPixel(j, i, res)[0];
					if(i%2==0 & j%2==0){
						//red
						r = color/4;
						g = 0;
						b = 0;				
					}
					else if(i%2==1 & j%2==1){
						//blue
						r = 0;
						g = 0;
						b = color/4;
					}
					else{//green
						r = 0;
						g = color/4;
						b = 0;

					}
					col = (r << 16) | (g << 8) | b;
					//buffO.setRGB(this.getH()-1-i, j, col); //remettre à l'endroit
					buffO.setRGB(i, j, col);

				}
			}	
			System.out.println("écriture");
			ImageIO.write(buffO, "PNG", outputfile);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("done");
	}

	public void convertToPngTrueRGB(String pathOut){
		try{
			BufferedImage buffO = new BufferedImage( this.getW()-8,this.getH()-8, BufferedImage.TYPE_INT_RGB);

			File outputfile = new File(pathOut);
			int r = 0;
			int g = 0;
			int b = 0;
			int color = 0;
			int col = (r << 16) | (g << 8) | b;
			System.out.println("conversion to RGB demosaifié");
			for(int i=4; i < this.getH()-8;i++){
				for(int j=4; j<this.getW()-8;j++ ){
					if(i%2==0 && j%2==0){
						//red
						r=this.getColor(j, i)/4;
						g=(this.getColor(j, i-1)+this.getColor(j, i+1)+this.getColor(j-1, i)+this.getColor(j+1, i))/16;
						b=(this.getColor(j-1, i-1)+this.getColor(j+1, i+1)+this.getColor(j-1, i+1)+this.getColor(j+1, i-1))/16;
					}
					else if(i%2==1 && j%2==1){
						//blue
						r=(this.getColor(j-1, i-1)+this.getColor(j+1, i+1)+this.getColor(j-1, i+1)+this.getColor(j+1, i-1))/16;
						g=(this.getColor(j, i-1)+this.getColor(j, i+1)+this.getColor(j-1, i)+this.getColor(j+1, i))/16;
						b=this.getColor(j, i)/4;
					}
					else if(i%2==0 && j%2==1){//green of first line
						r = (this.getColor(j+1, i)+this.getColor(j-1, i))/8;
						g = (this.getColor(j-1, i-1)+this.getColor(j+1, i+1)+this.getColor(j-1, i+1)+this.getColor(j+1, i-1)+this.getColor(j, i))/20;
						b = (this.getColor(j, i+1)+this.getColor(j, i-1))/8;

					}
					else{//green on second line
						r = (this.getColor(j, i+1)+this.getColor(j, i-1))/8;
						g = (this.getColor(j-1, i-1)+this.getColor(j+1, i+1)+this.getColor(j-1, i+1)+this.getColor(j+1, i-1)+this.getColor(j, i))/20;
						b = (this.getColor(j+1, i)+this.getColor(j-1, i))/8;
					}
					col = (r << 16) | (g << 8) | b;
					//buffO.setRGB(this.getH()-1-i, j, col); //remettre à l'endroit
					buffO.setRGB(j, i, col);

				}
			}	
			System.out.println("écriture");
			ImageIO.write(buffO, "PNG", outputfile);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("done");
	}

	public int getColor(int x, int y){
		double[] res = new double[1];
		return (int) this.getBI().getRaster().getPixel(x, y, res)[0];
	}

	public int convertTo8bits(int x){
		double Vmax = this.getWhiteLevel()+1;
		double bit = 0.0;
		while(Vmax!=1){
			Vmax/=2.0;
			bit+=1.0;
		}
		return (int)(x*Math.pow(2, 8.0-bit));
	}
}
