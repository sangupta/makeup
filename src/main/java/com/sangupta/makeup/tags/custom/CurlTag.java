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

import com.sangupta.jerry.http.WebInvoker;
import com.sangupta.jerry.http.WebResponse;
import com.sangupta.makeup.tags.AbstractCustomTag;

/**
 * @author sangupta
 *
 */
public class CurlTag extends AbstractCustomTag {

	@Override
	public String getName() {
		return "curl";
	}

	@Override
	public int getType() {
		return LINE;
	}

	@Override
	public boolean doTag() throws IOException {
		String url = getArgument(0);
		
		if(url != null) {
			WebResponse webResponse = WebInvoker.getResponse(url);
			if(webResponse != null && webResponse.getResponseCode() == 200) {
				String contents = webResponse.getContent();
				
				this.writer.write(contents);
			}
		}
		
		return true;
	}

}
