package fr.openradiation.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;

public class Img {

	String path, nom, s_type;
	int W, H, type;
	BufferedImage buff;
	Metadata metadata;


	public Img(String path, String nom) throws IOException, ImageProcessingException{
		this.path=path;
		this.nom=nom;
		this.metadata = ImageMetadataReader.readMetadata(new File(path));
		this.buff=ImageIO.read(new File(path));
		this.W=this.buff.getWidth();
		this.H=this.buff.getHeight();
		this.type=this.buff.getType();
		this.s_type=this.defineType(this.type);
	}

	public Img(File file) throws IOException, ImageProcessingException{
		this.path="";
		this.nom="";
		this.metadata = ImageMetadataReader.readMetadata(new File(path));
		this.buff=ImageIO.read(file);
		this.W=this.buff.getWidth();
		this.H=this.buff.getHeight();
		this.type=this.buff.getType();
		this.s_type=this.defineType(this.type);
	}

	public String getPath(){
		return this.path;
	}

	public String getNom(){
		return this.nom;
	}

	public int getW(){
		return this.W;
	}

	public int getH(){
		return this.H;
	}

	public int getType(){
		return this.type;
	}

	public BufferedImage getBI(){
		return this.buff;
	}
	
	public String getSType(){
		return this.s_type;
	}

	public String toString(){
		return "Longueur = "+this.getW()+" px, hauteur = "+this.getH()+" px, type "+this.getType();
	}
	
	public void setBI(BufferedImage Bi){
		this.buff=Bi;
	}

	private String defineType(int i){
		switch(i)
		{
		case 1:
			return "TYPE__INT_RGB";
		case 2:
			return "TYPE_INT_ARGB";
		case 3:
			return "TYPE_INT_ARGB_PRE";
		case 4:
			return "TYPE_INT_BGR";
		case 5:
			return "TYPE_3BYTE_BGR";
		case 6:
			return "TYPE_4BYTE_ABGR";
		case 7:
			return "TYPE_4BYTE_ABGR_PRE";
		case 8:
			return "TYPE_USHORT_565_RGB";
		case 9:
			return "TYPE_USHORT_555_RGB";
		case 10:
			return "TYPE_BYTE_GRAY";
		case 11:
			return "TYPE_USHORT_GRAY";
		case 12:
			return "TYPE_BYTE_BINARY";
		case 13:
			return "TYPE_BYTE_BINARY";
		case 14:
			return "TYPE_BYTE_INDEXED";
		default:
			return null;             
		}		
	}


	public double getMax(){
		double[] res = new double[1];
		double max = -1;
		for(int i=0; i < this.getH();i++){
			for(int j=0; j<this.getW();j++ ){
				if(this.getBI().getRaster().getPixel(j, i, res)[0]>max){
					max=this.getBI().getRaster().getPixel(j, i, res)[0];
				}	
			}
		}
		return max;
	}

	public double getMin(){
		double[] res = new double[1];
		double min = 1024; //attention ne fonctionne que dans notre cas du dng 10 bits
		for(int i=0; i < this.getH();i++){
			for(int j=0; j<this.getW();j++ ){
				if(this.getBI().getRaster().getPixel(j, i, res)[0]<min){
					min=this.getBI().getRaster().getPixel(j, i, res)[0];
				}	
			}
		}
		return min;
	}

	public double getAv(){
		double[] res = new double[1];
		double fullS = (double)(this.getH()*this.getW());
		double tot = 0;
		for(int i=0; i < this.getH();i++){
			for(int j=0; j<this.getW();j++ ){
				tot+=this.getBI().getRaster().getPixel(j, i, res)[0]/fullS;
			}
		}
		return tot;
	}




}