package main;

import java.awt.image.Raster;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;

import fr.openradiation.image.Dng;

public class PixelValue {

	public static void main(String[] args) throws IOException, ImageProcessingException, MetadataException {
		/**
		 * Change files paths here
		 */
		
		/**
		String pathIn = "C:/Users/sncuser/Desktop/tableau.dng";
		String pathOut = "C:/Users/sncuser/Desktop/GROSTEST.png";
		//String pathOut = "C:/Users/sncuser/Desktop/tableau_bis.png";
		//String pathOut = "C:/Users/Pierre/Desktop/tableau_bis.png";

		Dng img = new Dng(pathIn, "titre");
		System.out.println(img.toString());

		Raster raster = img.getBI().getRaster();
		double[] res = new double[1];
		int[] histo = new int[1024];

		for(int i=0; i < img.getH();i++){
			for(int j=0; j<img.getW();j++ ){
				histo[(int) raster.getPixel(j, i, res)[0]]+=1;
			}
		}

		//img.convertToPng(pathOut);
		img.convertToPngTrueRGB(pathOut);
		**/
		String PathIn = "";
		Dng dng;
		Raster raster;
		int seuil = 1000;
		int count =0;
		int value;
		int[] histo = new int[1024];
		int[][] av = new int[2464][3280];
		double[] res = new double[1];
		for(int i = 53;i<54;i++){
			if(i<10){
				PathIn = "G:/IRSN-STAGE/100_CFV5/old/DSC_000"+i+".DNG";
			}
			else{PathIn = "G:/IRSN-STAGE/100_CFV5/old/DSC_00"+i+".DNG";}
			dng = new Dng(PathIn, "titre");
			raster = dng.getBI().getRaster();
			for(int a=0; a < dng.getH();a++){ //2464
				for(int j=0; j<dng.getW();j++ ){ //3280
					value = (int) raster.getPixel(j, a, res)[0];
					histo[value]+=1;
					if(value>=seuil){
						count+=1;
					}
				}	
			}
			//dng.convertToPng("G:/IRSN-STAGE/im_out_"+i+".png");
			System.out.println(dng.getAv());
		}
		System.out.println(count);
		try{
			File ff=new File("G:/IRSN-STAGE/output.txt"); // d�finir l'arborescence
			ff.createNewFile();
			FileWriter ffw=new FileWriter(ff);
			for(int k=0;k<1024;k++){
				if(histo[k]!=0){
					ffw.write(Integer.toString(histo[k]));
				}
				else{ffw.write(Integer.toString(-1));}  // �crire une ligne dans le fichier resultat.txt
				ffw.write("\n"); // forcer le passage � la ligne
			}
			ffw.close(); // fermer le fichier � la fin des traitements
			} catch (Exception e) {}
		
	}


}
