/*******************************************************************************
 * Copyright (c) 2012-2015 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package com.codenvy.ide.ext.ruby.server.project.type;

import com.codenvy.api.project.server.type.ProjectType;
import com.codenvy.api.project.shared.Constants;
import com.codenvy.ide.ext.ruby.shared.ProjectAttributes;

/**
 * @author Vitaly Parfonov
 */
public class RubyProjectType extends ProjectType {

    public RubyProjectType() {
        super(ProjectAttributes.RUBY_ID, ProjectAttributes.RUBY_NAME, true, false);
        addConstantDefinition(Constants.LANGUAGE, "language", ProjectAttributes.PROGRAMMING_LANGUAGE);
    }
}
