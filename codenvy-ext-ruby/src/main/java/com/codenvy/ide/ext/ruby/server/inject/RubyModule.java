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
package com.codenvy.ide.ext.ruby.server.inject;

import com.codenvy.api.project.server.type.ProjectType;
import com.codenvy.ide.ext.ruby.server.project.type.RubyProjectType;
import com.codenvy.inject.DynaModule;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

/** @author Vladyslav Zhukovskii */
@DynaModule
public class RubyModule extends AbstractModule {
    /** {@inheritDoc} */
    @Override
    protected void configure() {
        Multibinder.newSetBinder(binder(), ProjectType.class).addBinding().to(RubyProjectType.class);
    }
}
