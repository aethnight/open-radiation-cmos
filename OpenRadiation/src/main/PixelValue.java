package main;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;

import fr.openradiation.image.Dng;

public class PixelValue {
	
	
	public static void convertToPng(String pathOut, double[][] v){
		try{
			BufferedImage buffO = new BufferedImage(3280, 2464, BufferedImage.TYPE_INT_RGB);
			File outputfile = new File(pathOut);
			int r = 0;
			int g = 0;
			int b = 0;
			int color = 0;
			int col = (r << 16) | (g << 8) | b;
			System.out.println("conversion to grey RGB");
			for(int i=0; i < 2464;i++){
				for(int j=0; j<3280;j++ ){
					color =(int)v[i][j];
					r = color/4;
					g = color/4;
					b = color/4;
					col = (r << 16) | (g << 8) | b;
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
	
	

	public static void main(String[] args) throws IOException, ImageProcessingException, MetadataException {
		/**
		 * Change files paths here
		 */

		/**
		String pathIn = "C:/Users/sncuser/Desktop/tableau.dng";
		String pathOut = "C:/Users/sncuser/Desktop/GROSTEST.png";
		//String pathOut = "C:/Users/sncuser/Desktop/tableau_bis.png";
		//String pathOut = "C:/Users/Pierre/Desktop/tableau_bis.png";
		 **/
		String PathIn = "";
		Dng dng;
		Raster raster;
		double value;
		double[] res = new double[1]; //valeur du getPixel, necessaire pour getPixel
		int[] histo = new int[1024]; //histogramme des valeurs de gris
		double[][] av = new double[2464][3280]; //moyenne de la valeur de chaque pixel sur le pool de sample
		double[][] mini = new double[2464][3280]; //valeur mini de chaque pixel sur le pool de sample
		double[][] max = new double[2464][3280]; //valeur max de chaque pixel sur le pool de sample
		
		//initialisation des tableaux
		for(int i=0;i<1024;i++){
			histo[i]=0;
		}
		for(int i=0;i<2464;i++){
			for(int j=0;j<3280;j++){
				av[i][j]=0;
				mini[i][j]=1024;
				max[i][j]=0;
			}
		}
		
		//on va lire chaque dng pour extraire des données sur chaque pixel
		try{
			for(int i = 68;i<86;i++){
				if(i<10){
					PathIn = "G:/IRSN-STAGE/100_CFV5/DSC_000"+i+".DNG";
				}
				else{PathIn = "G:/IRSN-STAGE/100_CFV5/DSC_00"+i+".DNG";}
				dng = new Dng(PathIn, "titre");
				raster = dng.getBI().getRaster();
				for(int a=0; a < dng.getH();a++){ //2464
					for(int j=0; j<dng.getW();j++ ){ //3280
						value = (double) raster.getPixel(j, a, res)[0];
						av[a][j]+=value/18.0;
						histo[(int) value]+=1;
						if(value<=mini[a][j]){
							mini[a][j]=value;
						}
						if(value>=max[a][j]){
							max[a][j]=value;
						}
					}	
				}
				System.out.println((int)dng.getAv());
			}
			
			double avMin = 0.0;
			double avMax = 0.0;

			for(int i=0;i<2464;i++){;
				for(int j=0;j<3280;j++){
					avMin+=mini[i][j]/(3280.0*2464.0);
					avMax+=max[i][j]/(3280.0*2464.0);
				}
			}
			System.out.println("avMin = "+(int)avMin+", avMax = "+(int)avMax);
			
			//convertToPng("G:/IRSN-STAGE/MinOutput.png",mini);
			//convertToPng("G:/IRSN-STAGE/MaxOutput.png",max);
			//convertToPng("G:/IRSN-STAGE/AvOutput.png",av);
			
			//ecriture du fhichier de sortie
			File ff=new File("G:/IRSN-STAGE/output.txt"); // définir l'arborescence
			ff.createNewFile();
			FileWriter ffw=new FileWriter(ff);
			for(int k=0;k<1024;k++){
					ffw.write(Double.toString(histo[k]));
				ffw.write("\n"); // forcer le passage à la ligne
			}
			ffw.close(); // fermer le fichier à la fin des traitements
			
			
			
		} catch (Exception e) {System.out.println(e.getStackTrace());}
		

		Dng DNG = new Dng("G:/IRSN-STAGE/100_CFV5/DSC_0065.DNG","oui");
		System.out.println("Suppression bruit");
		DNG.setBI(DNG.noiseMinus(DNG.getBI(), av));;
		System.out.println("OK");
		convertToPng("G:/IRSN-STAGE/minusNoise.png",DNG.extractValue());
		

	}


}
