package com.oyvindma.txt2ppt;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.IOException;

import org.apache.poi.hslf.usermodel.SlideShow;
import org.junit.Before;
import org.junit.Test;
public class PptBuilderTest {
	
	PptBuilder sut;
	SlideShow presentation;
	File tmpLocation; 

	private static String SLIDE_WITH_TITLE = "Slide";
	private static String SLIDE_WITH_TITLE_AND_TEXT = SLIDE_WITH_TITLE + "\r\tSlideText"+ "\r\tSlideText";
	
	@Before
	public void setUp(){
		presentation = new SlideShow();
		sut = new PptBuilder(presentation);
//			tmpLocation = File.createTempFile("pptTestFile", "ppt");
			tmpLocation = new File("pptTestFile.ppt");
	}
	
	@Test
	public void shouldCreatePresentationWithOneSlideAndBulletPoints() throws Exception{
		sut.buildPresentation(SLIDE_WITH_TITLE_AND_TEXT);
		assertThat(presentation.getSlides(), notNullValue());
		assertThat(presentation.getSlides().length, is(1));
		assertThat(presentation.getSlides()[0].getTitle(), is(SLIDE_WITH_TITLE));
		assertThat(presentation.getSlides()[0].getTextRuns().length, is(2));
		assertThat(presentation.getSlides()[0].getTextRuns()[1].getRichTextRunAt(0).isBullet(), is(true	));
		assertThat(presentation.getSlides()[0].getTextRuns()[1].getRichTextRunAt(0).getText().length(), greaterThan(0));
	}
	@Test
	public void shouldCreatePresentationWithOneSlide() throws Exception{
		sut.buildPresentation(SLIDE_WITH_TITLE);
		assertThat(presentation.getSlides(), notNullValue());
		assertThat(presentation.getSlides().length, is(1));
		assertThat(presentation.getSlides()[0].getTitle(), is(SLIDE_WITH_TITLE));
		assertThat(presentation.getSlides()[0].getTextRuns().length, is(1));
	}
	@Test
	public void shouldCreatePresentationWithTwoSlides() throws Exception{
		sut.buildPresentation(SLIDE_WITH_TITLE+"\r"+SLIDE_WITH_TITLE);
		assertThat(presentation.getSlides(), notNullValue());
		assertThat(presentation.getSlides().length, is(2));
		assertThat(presentation.getSlides()[0].getTitle(), is(SLIDE_WITH_TITLE));
		assertThat(presentation.getSlides()[1].getTitle(), is(SLIDE_WITH_TITLE));
	}
	
	@Test
	public void shouldWritePresentationToFile() throws IOException{
		sut.writePresentation(tmpLocation);
		
		assertThat(tmpLocation.exists(), is(true));
		assertThat(tmpLocation.isFile(), is(true));
		assertThat(tmpLocation.length(), is(greaterThan(0L)));
		
	}
	@Test
	public void shouldWriteDummyPresentationToFile() throws Exception{
		sut.buildPresentation("Supertittel\r\tDette er ein test\rSlide 2\r\tmed\r\ttre\r\tbullet\r\tpoints");
		sut.writePresentation(tmpLocation);
		
		assertThat(tmpLocation.exists(), is(true));
		assertThat(tmpLocation.isFile(), is(true));
		assertThat(tmpLocation.length(), is(greaterThan(0L)));
		
	}
	
	
}
