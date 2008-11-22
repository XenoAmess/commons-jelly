/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.jelly.core;

import junit.framework.TestSuite;

import org.apache.commons.jelly.JellyException;
import org.apache.commons.jelly.Script;
import org.apache.commons.jelly.test.BaseJellyTest;
import org.xml.sax.SAXParseException;

/**
 * Tests for exceptions being raised when an unknown tag is encountered - new
 * code added in response to JELLY-13
 * 
 * @author <a href="mailto:john.spackman@zenesis.com">John Spackman</a>
 */
public class TestUnknownTags extends BaseJellyTest {

	public TestUnknownTags(String name) {
		super(name);
	}

	public static TestSuite suite() throws Exception {
		return new TestSuite(TestUnknownTags.class);
	}

	public void testUnknownTags() throws Exception {
		setUpScript("testUnknownTags.xml");
		try {
			Script script = getJelly().compileScript();
			script.run(getJellyContext(), getXMLOutput());
			System.out.println(getStringOutput());
		}catch(JellyException e) {
			if (e.getCause() instanceof SAXParseException) {
				Throwable cause = e.getCause();
				if (cause.getMessage().indexOf("Unrecognised tag called tag-that-does-not-exist in TagLibrary jelly:test") > -1)
					return;
			}
			throw e;
		}
	}
}
