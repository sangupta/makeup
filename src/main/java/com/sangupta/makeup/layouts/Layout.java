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

package com.sangupta.makeup.layouts;

import java.io.File;
import java.util.Map;

import com.sangupta.makeup.tags.Tag;

/**
 * Contract that all layout implementations must follow. This allows to
 * switch between various layout implementation without changing the code.
 * 
 * @author sangupta
 * @since 0.1
 */
public interface Layout {
	
	public void initialize(File[] layoutFolders, Class<? extends Tag>[] customTags);
	
	public boolean addLayoutFolder(File layoutFolder);
	
	public boolean addLayoutFolders(File[] layoutFolders);
	
	public boolean registerCustomTag(Class<? extends Tag> tagClass);
	
	public boolean registerCustomTags(Class<? extends Tag>[] tagClasses);
	
	public String layout(String layoutName, Map<String, Object> model);
	
	public String layoutWithTemplateCode(String layoutCode, Map<String, Object> model);

}
