package fr.openradiation.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Img {

	String path, nom;
	int W, H, type;
	BufferedImage buff;

	public Img(String path, String nom) throws IOException{
		this.path=path;
		this.nom=nom;
		this.buff=ImageIO.read(new File(path));
		this.W=this.buff.getWidth();
		this.H=this.buff.getHeight();
		this.type=this.buff.getType();
	}

	public Img(File file) throws IOException{
		this.path="";
		this.nom="";
		this.buff=ImageIO.read(file);
		this.W=this.buff.getWidth();
		this.H=this.buff.getHeight();
		this.type=this.buff.getType();
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

	public String toString(){
		return "Longueur = "+this.getW()+" px, hauteur = "+this.getH()+" px, type "+this.getType();
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
		double min = 1024;
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
	
	
}