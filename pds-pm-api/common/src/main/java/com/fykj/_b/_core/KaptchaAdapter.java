package com.fykj._b._core;

import java.awt.image.BufferedImage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.code.kaptcha.Producer;

@Component
public class KaptchaAdapter {

	@Autowired
	private Producer producer;
	

	public BufferedImage iamge(String text){
		return producer.createImage(text);
	}
	
	public String text(){
		return producer.createText();
	}
	
	
}
