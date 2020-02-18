package com.ats.hreasy.common;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ats.hreasy.model.Info;
 
 

public class VpsImageUpload {
	
	 
	
	
	//public static final String galleryImage= "/home/devour/apache-tomcat-9.0.12/webapps/mongistoreuploads/";
	public static final String galleryImage= "/home/lenovo/Documents/pdf/";
	 
	public Info saveUploadedImge(MultipartFile file,String uploadPath,String imageName,String[] allowExt,int isResize, int width, int hieght
			, int isCheckSize, int imageSizeMax) throws IOException {

				Info info = new Info();
				
				 try {
					 
					 String[] filetypes = file.getOriginalFilename().split("\\.");
					 String extension = FilenameUtils.getExtension(file.getOriginalFilename());
					 
					 if ( ArrayUtils.contains( allowExt, extension.toLowerCase() ) && filetypes.length==2) {
						
							Path path = Paths.get(uploadPath + imageName);
				
							byte[] bytes = file.getBytes();
				 
								System.out.println("Inside Image Type =1");
				
								//path = Paths.get(uploadPath + imageName);
				
								System.out.println("Path= " + path.toString() + "" +file.getSize());
				
							 
				
							Files.write(path, bytes);
							
							if(isResize==1) {
							 
								Image img = null;
								BufferedImage tempPNG = null;
								 
								File newFilePNG = null;
								 
						        System.out.println("File " + imageName);
						        img = ImageIO.read(new File(uploadPath+imageName));
						        tempPNG = resizeImage(img, width, hieght);
						        
						        newFilePNG = new File(uploadPath+"thumbnail"+imageName);
						       
						        ImageIO.write(tempPNG, extension, newFilePNG);
						       
								System.out.println("DONE");
							} 
						
						info.setError(false);
						info.setMsg("Upload Successfully ");
					 }else {
						 	info.setError(true);
							info.setMsg("Error While Uploading Image");
					 }
						
				 }catch (Exception e) {
					 
					e.printStackTrace();
					info.setError(true);
					info.setMsg("Error While Uploading Image");
				}
			return info;

		}
	
	public Info saveUploadedImgeWithResize(MultipartFile file,String uploadPath,String imageName,String[] allowExt,int isResize, int width, int hieght
			, int isCheckSize, int imageSizeMax) throws IOException {

				Info info = new Info();
				
				 try {
					 String extension = FilenameUtils.getExtension(file.getOriginalFilename());
					 
					 if ( ArrayUtils.contains( allowExt, extension ) ) {
						
							Path path = Paths.get(uploadPath + imageName);
				
							byte[] bytes = file.getBytes();
				 
								System.out.println("Inside Image Type =1");
				
								//path = Paths.get(uploadPath + imageName);
				
								System.out.println("Path= " + path.toString() + "" +file.getSize());
				
							 
				
							Files.write(path, bytes);
							
							if(isResize==1) {
							 
								Image img = null;
								BufferedImage tempPNG = null;
								 
								File newFilePNG = null;
								 
						        System.out.println("File " + imageName);
						        img = ImageIO.read(new File(uploadPath+imageName));
						        tempPNG = resizeImage(img, width, hieght);
						        
						        newFilePNG = new File(uploadPath+"re"+imageName);
						       
						        ImageIO.write(tempPNG, extension, newFilePNG);
						       
								System.out.println("DONE");
							} 
						
						info.setError(false);
						info.setMsg("Upload Successfully ");
					 }else {
						 	info.setError(true);
							info.setMsg("Error While Uploading Image");
					 }
						
				 }catch (Exception e) {
					 
					e.printStackTrace();
					info.setError(true);
					info.setMsg("Error While Uploading Image");
				}
			return info;

		}
	
	public void saveUploadedFiles(MultipartFile file, String filePath, String imageName) throws IOException {

		 

			Path path = Paths.get(filePath + imageName);

			byte[] bytes = file.getBytes();

			 
				System.out.println("Inside Image Type =1");

				path = Paths.get(filePath + imageName);

				System.out.println("Path= " + path.toString());
 

			Files.write(path, bytes);

		}
	
	public static BufferedImage resizeImage(final Image image, int width, int height) {
        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setComposite(AlphaComposite.Src);
        //below three lines are for RenderingHints for better image quality at cost of higher processing time
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();
        return bufferedImage;
    }
	/*public void saveUploadedFiles(MultipartFile file, int imageType, String imageName) throws IOException {

		for (MultipartFile file : files) {

			if (file.isEmpty()) {

				System.out.println("int if");
				continue;

			}

			Path path = Paths.get(galleryImage + imageName);

			byte[] bytes = file.getBytes();

			if (imageType == 1) {
				System.out.println("Inside Image Type =1");

				path = Paths.get(galleryImage + imageName);

				System.out.println("Path= " + path.toString());

			}  

			Files.write(path, bytes);

		}*/
	//}

}
