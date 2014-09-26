/*******************************************************************************
 * Copyright (c) 2012-2014 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package com.codenvy.ide.ext.ruby.server.project.type;

import com.codenvy.api.project.server.ProjectTypeDescriptionExtension;
import com.codenvy.api.project.server.ProjectTypeDescriptionRegistry;
import com.codenvy.api.project.shared.AttributeDescription;
import com.codenvy.api.project.shared.ProjectType;
import com.codenvy.ide.Constants;
import com.codenvy.ide.ext.ruby.shared.ProjectAttributes;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

/** @author Vladyslav Zhukovskii */
@Singleton
public class RubyProjectTypeDescriptionExtension implements ProjectTypeDescriptionExtension {

    @Inject
    public RubyProjectTypeDescriptionExtension(ProjectTypeDescriptionRegistry registry) {
        registry.registerDescription(this);
    }

    /** {@inheritDoc} */
    @Override
    public List<ProjectType> getProjectTypes() {
        final List<ProjectType> list = new ArrayList<>(1);
        list.add(new ProjectType(ProjectAttributes.RUBY_ID, ProjectAttributes.RUBY_NAME, ProjectAttributes.RUBY_CATEGORY, null,
                                 ProjectAttributes.RUBY_DEFAULT_RUNNER));
        return list;
    }

    /** {@inheritDoc} */
    @Override
    public List<AttributeDescription> getAttributeDescriptions() {
        final List<AttributeDescription> list = new ArrayList<>(4);
        list.add(new AttributeDescription(Constants.LANGUAGE));
        list.add(new AttributeDescription(Constants.RUNNER_NAME));
        list.add(new AttributeDescription(Constants.RUNNER_ENV_ID));
        return list;
    }
}
