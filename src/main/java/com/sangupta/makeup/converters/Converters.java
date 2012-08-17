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

package com.sangupta.makeup.converters;

import com.sangupta.makeup.converters.markdown.MarkdownConverter;

/**
 * Utility class to return converters based on type.
 * 
 * @author sangupta
 *
 */
public class Converters {
	
	/**
	 * Find the converter for the given converter type
	 * 
	 * @param converterType
	 * @return
	 */
	public static Converter getConverter(ConverterType converterType) {
		if(converterType == null) {
			throw new IllegalArgumentException("Converter type cannot be null");
		}
		
		switch(converterType) {
			case DoNothing:
				return new DoNothingConverter();
				
			case Markdown:
				return new MarkdownConverter();
		}
		
		throw new IllegalArgumentException("Unknown converter requested");
	}
	
	/**
	 * Find the converter based on filename.
	 * 
	 * @param fileName
	 * @return
	 */
	public static Converter getConverter(String fileName) {
		// TODO: find converters using Converter.getProbableExtensions() method instead
		if(fileName.endsWith(".md") || fileName.endsWith(".markdown")) {
			return new MarkdownConverter();
		}
		
		return new DoNothingConverter();
	}

}
