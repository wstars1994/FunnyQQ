package com.boomzz.test;

import java.io.InputStream;

import com.boomzz.core.login.IQRImageListener;

public class QRImageListener implements IQRImageListener{

	@Override
	public void imageStream(InputStream inputStream) {
		System.out.println("获取到图片资源");
	}

}
