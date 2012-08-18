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

import org.junit.Test;

import com.sangupta.makeup.converters.Converter;
import com.sangupta.makeup.converters.ConverterType;

/**
 * 
 * @author sangupta
 *
 */
public class MakeupTest {
	
	@Test
	public void testConverter() {
		
	}
	
	public static void main(String[] args) {
		Converter c = Makeup.getConverter(ConverterType.Markdown);
		String s = c.convert("```xml\n<hell>\n</hell>\n```", null);
		System.out.println(s);
	}
}
