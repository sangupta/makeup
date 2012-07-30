/**
 *
 * Makeup - HTML generation framework 
 * Copyright (c) 2012, Sandeep Gupta
 * 
 * http://www.sangupta/projects/makeup
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.sangupta.makeup.converters.markdown;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;

import com.sangupta.makeup.converters.Converter;

/**
 * Converts markdown format to HTML
 * 
 * @author sangupta
 *
 */
public class MarkdownConverter implements Converter {
	
	public static PegDownProcessor pegDownProcessor;
	
	public static PegDownProcessor pegDownNonHardWrapProcessor;

	/**
	 * 
	 *
	 * @see com.sangupta.makeup.converters.Converter#getName()
	 */
	@Override
	public String getName() {
		return "Markdown";
	}
	
	/**
	 * Utility function to convert Markdown content into HTML
	 * using the standard processor, with newer flavored constructs
	 * such as hardwraps turned off.
	 * 
	 * @param content
	 * @return
	 */
	public String convert(String content) {
		if(pegDownProcessor == null || pegDownNonHardWrapProcessor == null) {
			initialize();
		}

		return pegDownProcessor.markdownToHtml(content);
	}

	@Override
	public String convert(String content, Properties pageProperties) {
		if(pegDownProcessor == null || pegDownNonHardWrapProcessor == null) {
			initialize();
		}
		
		String hardwrap = pageProperties.getProperty("hardwrap");
		if("true".equalsIgnoreCase(hardwrap)) {
			return pegDownNonHardWrapProcessor.markdownToHtml(content);
		}

		return pegDownProcessor.markdownToHtml(content);
	}

	/**
	 * 
	 *
	 * @see com.sangupta.makeup.converters.Converter#getProbableExtensions()
	 */
	@Override
	public  List<String> getProbableExtensions() {
		List<String> extensions = new ArrayList<String>();
		
		extensions.add(".md");
		extensions.add(".markdown");
		
		return extensions;
	}

	/**
	 * Initialization function
	 */
	private synchronized void initialize() {
		if(pegDownProcessor == null) {
			pegDownProcessor = new PegDownProcessor(Extensions.ALL ^ Extensions.HARDWRAPS);
		}
		
		if(pegDownNonHardWrapProcessor == null) {
			pegDownNonHardWrapProcessor = new PegDownProcessor(Extensions.ALL);
		}
	}

}
