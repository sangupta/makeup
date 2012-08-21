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

package com.sangupta.makeup;

import java.io.File;

import com.sangupta.makeup.converters.Converter;
import com.sangupta.makeup.converters.ConverterType;
import com.sangupta.makeup.converters.DoNothingConverter;
import com.sangupta.makeup.converters.markdown.MarkdownConverter;
import com.sangupta.makeup.layouts.Layout;
import com.sangupta.makeup.layouts.LayoutType;
import com.sangupta.makeup.layouts.velocity.VelocityLayout;
import com.sangupta.makeup.tags.Tag;
import com.sangupta.makeup.tags.custom.CurlTag;
import com.sangupta.makeup.tags.custom.DateTag;
import com.sangupta.makeup.tags.custom.GoogleAnalyticsTag;
import com.sangupta.makeup.tags.custom.HrefTag;
import com.sangupta.makeup.tags.custom.MarkdownTag;
import com.sangupta.makeup.tags.custom.RemoveHeadingTag;
import com.sangupta.makeup.tags.custom.StatcounterTag;

/**
 * @author sangupta
 *
 */
public class Makeup {
	
	/**
	 * Return a new instance of a converter for the given converter type.
	 * 
	 * @param converterType
	 * @return
	 */
	public static Converter getConverter(ConverterType converterType) {
		if(converterType == null) {
			throw new IllegalArgumentException("Converter type cannot be null.");
		}
		
		switch(converterType) {
			case Markdown:
				return new MarkdownConverter();
		}
		
		throw new IllegalArgumentException("Converter cannot be found.");
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

	/**
	 * Return a new instance of the layout for the given layout type.
	 * 
	 * @param layoutType
	 * @return
	 */
	public static Layout getLayout(LayoutType layoutType) {
		if(layoutType == null) {
			throw new IllegalArgumentException("Layout type cannot be null.");
		}
		
		switch(layoutType) {
			case Velocity:
				return new VelocityLayout();
		}
		
		throw new IllegalArgumentException("Layout cannot be found.");
	}
	
	/**
	 * Return a list of all known custom tags that have been built into this library.
	 * 
	 * @return
	 */
	public static Class<? extends Tag>[] getKnownCustomTags() {
		@SuppressWarnings("unchecked")
		Class<? extends Tag>[] customTags = new Class[] {
			CurlTag.class,
			DateTag.class,
			GoogleAnalyticsTag.class,
			HrefTag.class,
			MarkdownTag.class,
			RemoveHeadingTag.class,
			StatcounterTag.class
		};
		
		return customTags;
	}
	
	/**
	 * Register all known custom tags that this library has built inside it
	 * 
	 * @param layout
	 *            the layout over which the tags are registered
	 * 
	 * @throws <code>NullPointerException</code> if layout provided is null
	 */
	public static void registerKnownCustomTags(Layout layout) {
		layout.registerCustomTags(getKnownCustomTags());
	}

	/**
	 * Return a new instance of the layout for the given layout type, and
	 * register all supported internal custom tags.
	 * 
	 * @param layoutType
	 * @param layoutFolders
	 * @return
	 */
	public static Layout getLayoutWithInternalTags(LayoutType layoutType, File[] layoutFolders) {
		Layout layout = getLayout(layoutType);
		
		layout.initialize(layoutFolders, getKnownCustomTags());
		
		return layout;
	}
}
