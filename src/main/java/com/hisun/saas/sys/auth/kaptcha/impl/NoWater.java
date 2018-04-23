package com.hisun.saas.sys.auth.kaptcha.impl;

import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.util.Configurable;

import java.awt.image.BufferedImage;

/**
 * @author Rocky {rockwithyou@126.com}
 */
public class NoWater extends Configurable implements GimpyEngine {
	public BufferedImage getDistortedImage(BufferedImage baseImage) {
		return baseImage;
	}
}
