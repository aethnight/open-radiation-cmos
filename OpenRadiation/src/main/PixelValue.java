package main;

import java.awt.image.Raster;
import java.io.IOException;

import fr.openradiation.image.Img;

public class PixelValue {

	public static void main(String[] args) throws IOException {
		String pathIn = "C:/Users/sncuser/Desktop/tableau.dng";
		String pathOut = "C:/Users/sncuser/Desktop/tableau_bis.png";

		Img img = new Img(pathIn, "titre");
		System.out.println(img.toString());

		Raster raster = img.getBI().getRaster();
		double[] res = new double[1];
		int[] histo = new int[1024];

		for(int i=0; i < img.getH();i++){
			for(int j=0; j<img.getW();j++ ){
				histo[(int) raster.getPixel(j, i, res)[0]]+=1;
			}
		}

		System.out.println("max : "+img.getMax());
		System.out.println("min : "+img.getMin());
		
		img.convertToPng(pathOut);

    }

	
}
