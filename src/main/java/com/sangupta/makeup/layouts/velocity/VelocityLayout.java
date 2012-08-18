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
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

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
 * @since 0.1
 */
public class VelocityLayout implements Layout {
	
	private final VelocityEngine engine = new VelocityEngine();
	
	private final Set<String> layoutPaths = new HashSet<String>();
	
	private final Set<String> customDirectives = new HashSet<String>();
	
	private final Properties properties = new Properties();
	
	private static final String USER_DIRECTIVES_PROPERTY_NAME = "userdirective";
	
	/**
	 * 
	 * @see com.sangupta.makeup.Layout#initialize(java.io.File[], java.lang.Class<com.sangupta.makeup.Tag>[])
	 */
	public void initialize(File[] layoutFolders, Class<? extends Tag>[] customTags) {
		
		properties.setProperty(VelocityEngine.RESOURCE_LOADER, "file");
		properties.setProperty("file" + VelocityEngine.RESOURCE_LOADER + ".class", FileResourceLoader.class.getName());
		
		// initialize custom tags
		registerCustomTags(customTags, false);

		// initialize folders
		addLayoutFolders(layoutFolders, false);
		
		// rebuild engine
		engine.init(properties);
	}

	/**
	 *
	 * @see com.sangupta.makeup.Layout#addLayoutFolder(java.io.File)
	 */
	@Override
	public boolean addLayoutFolder(File layoutFolder) {
		if(layoutFolder == null) {
			return false;
		}
		
		this.layoutPaths.add(layoutFolder.getAbsolutePath());
		
		// re-init folder paths
		reinitLayoutFolders(true);
		
		return true;
	}

	/**
	 *
	 * @see com.sangupta.makeup.Layout#addLayoutFolders(java.io.File[])
	 */
	@Override
	public boolean addLayoutFolders(File[] layoutFolders) {
		return addLayoutFolders(layoutFolders, true);
	}
	
	private boolean addLayoutFolders(File[] layoutFolders, boolean rebuildEngine) {
		if(layoutFolders == null) {
			return false;
		}
		
		boolean success = true;
		for(File folder : layoutFolders) {
			if(folder == null) {
				success = false;
				continue;
			}
			
			this.layoutPaths.add(folder.getAbsolutePath());
		}
		
		// re-init folder paths
		reinitLayoutFolders(rebuildEngine);
		
		return success;
	}

	/**
	 *
	 * @see com.sangupta.makeup.Layout#registerCustomTag(java.lang.Class)
	 */
	@Override
	public boolean registerCustomTag(Class<? extends Tag> tagClass) {
		if(tagClass == null) {
			return false;
		}
		
		this.customDirectives.add(tagClass.getName());
		
		// reinit directives
		registerCustomTagsAsDirectives(true);
		
		return true;
	}

	/**
	 *
	 * @see com.sangupta.makeup.Layout#registerCustomTags(java.lang.Class<com.sangupta.makeup.Tag>[])
	 */
	@Override
	public boolean registerCustomTags(Class<? extends Tag>[] tagClasses) {
		return registerCustomTags(tagClasses, true);
	}
	
	private boolean registerCustomTags(Class<? extends Tag>[] tagClasses, boolean rebuildEngine) {
		if(tagClasses == null) {
			return false;
		}
		
		boolean success = false;
		for(Class<? extends Tag> tagClass : tagClasses) {
			if(tagClass == null) {
				success = false;
				continue;
			}
			
			this.customDirectives.add(tagClass.getName());
		}
		
		//re-init custom directives
		registerCustomTagsAsDirectives(rebuildEngine);
		
		return success;
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
	 * @see com.sangupta.makeup.Layout#layoutWithTemplateCode(java.lang.String, java.util.Map)
	 */
	@Override
	public String layoutWithTemplateCode(String layoutCode,	Map<String, Object> model) {
		VelocityContext context = new VelocityContext(model);
		StringWriter stringWriter = new StringWriter();
		engine.evaluate(context, stringWriter, "stringtemplate", layoutCode);
		
		return stringWriter.toString();
	}
	
	/**
	 * Re-initialize layout paths based on the ones that have been currently added.
	 * 
	 */
	private void reinitLayoutFolders(boolean rebuildEngine) {
		if(this.layoutPaths.size() == 0) {
			return;
			
		}
		StringBuilder builder = new StringBuilder();
		for(String path : this.layoutPaths) {
			builder.append(path).append(",");
		}
		
		builder.deleteCharAt(builder.length() - 1);
		
		properties.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, builder.toString());
		
		if(rebuildEngine) {
			engine.init(properties);
		}
	}

	/**
	 * Return a string representation of all tag classes
	 * 
	 * @return
	 */
	private void registerCustomTagsAsDirectives(boolean rebuildEngine) {
		if(this.customDirectives.size() == 0) {
			return;
		}
		
		StringBuilder builder = new StringBuilder();
		for(String tag : this.customDirectives) {
			builder.append(tag).append(",");
		}
		builder.deleteCharAt(builder.length() - 1);
		
		properties.setProperty(USER_DIRECTIVES_PROPERTY_NAME, builder.toString());
		
		// rebuild if needed
		if(rebuildEngine) {
			engine.init(properties);
		}
	}

}
