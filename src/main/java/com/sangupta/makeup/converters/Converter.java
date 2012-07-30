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

import java.util.List;
import java.util.Properties;

import com.sangupta.makeup.converters.markdown.MarkdownConverter;

/**
 * Contract for implementations that allow using a new markup language
 * for content. 
 * 
 * The idea of the converter is to convert the content of the file
 * into another form. For example, the {@link MarkdownConverter} converts
 * the file contents from Markdown format to HTML format. This allows
 * support for usage of multiple text syntax-formats like Markdown, Wiki, 
 * Textile etc.
 * 
 * @author sangupta
 *
 */
public interface Converter {
	
	/**
	 * Return the user-understandable name of this converter.
	 * 
	 * @return
	 */
	public String getName();
	
	/**
	 * Convert the given data per the converter's functionality.
	 * 
	 * @param content
	 * @return
	 */
	public String convert(String content, Properties pageProperties);

	/**
	 * Return the list of extension mappings, such as:
	 * change .md to .html
	 * or, .markdown to .html
	 * 
	 * @return
	 */
	public List<String> getProbableExtensions();
	
}
