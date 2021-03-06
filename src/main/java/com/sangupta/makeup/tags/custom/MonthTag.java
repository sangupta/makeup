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
 * Used to convert a number to a given month name.
 * 
 * @author sangupta
 *
 */
public class MonthTag extends AbstractCustomTag {
	
	private static String[] monthNames = {  "January", "February", "March",
											"April", "May", "June", "July",
											"August", "September", "October",
											"November", "December" };

	/**
	 *
	 * @see org.apache.velocity.runtime.directive.Directive#getName()
	 */
	@Override
	public String getName() {
		return "month";
	}

	/**
	 *
	 * @see org.apache.velocity.runtime.directive.Directive#getType()
	 */
	@Override
	public int getType() {
		return LINE;
	}

	@Override
	public boolean doTag() throws IOException {
		Integer month = getArgument(0);
		Boolean zeroIndex = getArgument(1);
		
		if(!zeroIndex) {
			month = month - 1;
		}
		
		writer.write(monthNames[month]);
		return true;
	}
}
