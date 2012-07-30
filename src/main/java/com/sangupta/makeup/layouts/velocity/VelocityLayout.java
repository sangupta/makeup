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

package com.sangupta.makeup.layouts.velocity;

import java.io.File;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.loader.FileResourceLoader;

import com.sangupta.makeup.layouts.Layout;
import com.sangupta.makeup.tags.Tag;

/**
 * Implementation of {@link Layout} based on Apache Velocity engine.
 * 
 * @author sangupta
 * @since 1.0
 */
public class VelocityLayout implements Layout {
	
	private final VelocityEngine engine = new VelocityEngine();
	
	/**
	 * 
	 * @see com.sangupta.makeup.Layout#initialize(java.io.File[], java.lang.Class<com.sangupta.makeup.Tag>[])
	 */
	public void initialize(File[] layoutFolders, Class<? extends Tag>[] customTags) {
		Properties properties = new Properties();
		
		properties.setProperty(VelocityEngine.RESOURCE_LOADER, "file");
		properties.setProperty("file" + VelocityEngine.RESOURCE_LOADER + ".class", FileResourceLoader.class.getName());
		
		// initialize folders
		if(layoutFolders != null && layoutFolders.length > 0) {
			String templatePath = null;
			if(layoutFolders.length > 1) {
				StringBuilder builder = new StringBuilder();
				for(File folder : layoutFolders) {
					builder.append(folder.getAbsolutePath()).append(",");
				}
				builder.deleteCharAt(builder.length() - 1);

				templatePath = builder.toString();
			} else {
				templatePath = layoutFolders[0].getAbsolutePath();
			}
			
			properties.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, templatePath);
		}

		// initialize custom tags
		if(customTags != null && customTags.length > 0) {
			final String directives = getCustomTagsAsDirectives(customTags);
			properties.setProperty("userdirective", directives);
		}

		engine.init(properties);
	}

	/**
	 * Return a string representation of all tag classes
	 * 
	 * @return
	 */
	private String getCustomTagsAsDirectives(Class<? extends Tag>[] customTags) {
		if(customTags == null || customTags.length == 0) {
			return "";
		}
		
		StringBuilder builder = new StringBuilder();
		for(Class<? extends Tag> tag : customTags) {
			builder.append(tag.getName()).append(",");
		}
		builder.deleteCharAt(builder.length() - 1);
		
		return builder.toString();		
	}


	/**
	 *
	 * @see com.sangupta.makeup.Layout#addLayoutFolder(java.io.File)
	 */
	@Override
	public boolean addLayoutFolder(File layoutFolder) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 *
	 * @see com.sangupta.makeup.Layout#addLayoutFolders(java.io.File[])
	 */
	@Override
	public boolean addLayoutFolders(File[] layoutFolders) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 *
	 * @see com.sangupta.makeup.Layout#registerCustomTag(java.lang.Class)
	 */
	@Override
	public boolean registerCustomTag(Class<? extends Tag> tagClass) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 *
	 * @see com.sangupta.makeup.Layout#registerCustomTags(java.lang.Class<com.sangupta.makeup.Tag>[])
	 */
	@Override
	public boolean registerCustomTags(Class<? extends Tag>[] tagClasses) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 *
	 * @see com.sangupta.makeup.Layout#layout(java.lang.String, java.util.Map)
	 */
	@Override
	public String layout(String layoutName, Map<String, Object> model) {
		Template template = null;
		
		try {
			template = engine.getTemplate(layoutName);
		} catch(ResourceNotFoundException e) {
			e.printStackTrace();
		} catch(ParseErrorException e) {
			e.printStackTrace();
		}
		
		if(template == null) {
			return null; 
		}
		
		VelocityContext context = new VelocityContext(model);
		
		// generate the final output
		StringWriter stringWriter = new StringWriter();
		template.merge(context, stringWriter);
		
		// return the build up data
		return stringWriter.toString();
	}

	/**
	 *
	 * @see com.sangupta.makeup.Layout#layoutWithTemplate(java.lang.String, java.util.Map)
	 */
	@Override
	public String layoutWithTemplate(String layoutCode,	Map<String, Object> model) {
		VelocityContext context = new VelocityContext(model);
		StringWriter stringWriter = new StringWriter();
		engine.evaluate(context, stringWriter, "stringtemplate", layoutCode);
		
		return stringWriter.toString();
	}

}
