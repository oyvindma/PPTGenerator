/**
 * 
 */
package com.oyvindma.txt2ppt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextBox;
import org.apache.poi.hslf.usermodel.RichTextRun;
import org.apache.poi.hslf.usermodel.SlideShow;

/**
 * @author oasbjorn
 * 
 */
public class PptBuilder {

	private final SlideShow presentation;

	public PptBuilder(SlideShow emptySlideShow) {
		presentation = emptySlideShow;
	}

	public void writePresentation(File tmpLocation) throws IOException  {

		// save changes in a file
		FileOutputStream out;
		out = new FileOutputStream(tmpLocation);
		presentation.write(out);
		out.close();
	}

	public void buildPresentation(String presentationText) throws Exception {
		BufferedReader reader = new BufferedReader(new StringReader(
				presentationText));
		String line = null;
		Slide currentSlide = null;
		TextBox body=new TextBox();
		while ((line = reader.readLine()) != null) {
			if (isSlideTitle(line)) {
				currentSlide = presentation.createSlide();
				currentSlide.addTitle().setText(line);
				body=new TextBox();
			} else {
				if(currentSlide.getTextRuns().length==1){
					configureTextBox(body);
					currentSlide.addShape(body);
					body.setText(StringUtils.removeStart(line, "\t"));
					body.setText(line);
				}else{
					body.setText(body.getText()+"\r"+StringUtils.removeStart(line, "\t"));
				}
			}
		}
	}

	private void configureTextBox(TextBox body) {
		RichTextRun rt = body.getTextRun().getRichTextRuns()[0];
		  rt.setFontSize(18);
		  rt.setBullet(true);
		  rt.setBulletOffset(0);  //bullet offset
		  rt.setTextOffset(10);   //text offset (should be greater than bullet offset)
		  rt.setIndentLevel(1);
		  body.setAnchor(new java.awt.Rectangle(50,150,600, 370));  //position of the text box in the slide
		  body.setHorizontalAlignment(TextBox.AlignCenter);
	}

	private boolean isSlideTitle(String line) {
		return !StringUtils.startsWith(line, "\t");
	}

}
