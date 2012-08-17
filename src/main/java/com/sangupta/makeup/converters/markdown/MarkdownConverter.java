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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.sangupta.makeup.converters.Converter;
import com.sangupta.nutz.MarkdownProcessor;

/**
 * Converts markdown format to HTML
 * 
 * @author sangupta
 *
 */
public class MarkdownConverter implements Converter {
	
	private MarkdownProcessor processor = new MarkdownProcessor();
	
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
		try {
			return this.processor.toHtml(content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public String convert(String content, Properties pageProperties) {
		try {
			return this.processor.toHtml(content);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return null;
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

}
