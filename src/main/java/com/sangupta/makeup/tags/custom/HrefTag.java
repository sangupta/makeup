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

package com.sangupta.makeup.tags.custom;

import java.io.IOException;

import com.sangupta.makeup.tags.AbstractCustomTag;

/**
 * Tag to generate an HREF tag using the same URL as the text to the HREF. For example,
 * 
 * <pre>
 * #href("http://www.sangupta.com")
 * </pre>
 * 
 * will generate,
 * 
 * <pre>
 * &lt;a href="http://www.sangupta.com"&gt;http://www.sangupta.com&lt;a&gt;
 * </pre>
 * 
 * @author sangupta
 *
 */
public class HrefTag extends AbstractCustomTag {

	@Override
	public String getName() {
		return "href";
	}

	@Override
	public int getType() {
		return LINE;
	}

	@Override
	public boolean doTag() throws IOException {
		String url = null;
		url = getArgument(0);
		
		if(url != null) {
			writer.write("<a href=\"");
			writer.write(url);
			writer.write("\">");
			writer.write(url);
			writer.write("</a>");
		}
		
		return true;
	}

}
