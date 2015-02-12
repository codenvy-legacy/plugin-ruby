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
package com.codenvy.ide.ext.ruby.client;

import com.codenvy.ide.api.extension.Extension;
import com.codenvy.ide.api.icon.Icon;
import com.codenvy.ide.api.icon.IconRegistry;
import com.codenvy.ide.ext.ruby.shared.ProjectAttributes;
import com.google.gwt.resources.client.ClientBundle;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.vectomatic.dom.svg.ui.SVGResource;

/** @author Vladyslav Zhukovskii */
@Singleton
@Extension(title = "Ruby", version = "3.0.0")
public class RubyExtension {
    @Inject
    public RubyExtension(ParserResource parserResource, IconRegistry iconRegistry) {
        iconRegistry.registerIcon(new Icon(ProjectAttributes.RUBY_CATEGORY + ".samples.category.icon", parserResource.rubyCategoryIcon()));
    }

    public interface ParserResource extends ClientBundle {
        @Source("com/codenvy/ide/ext/ruby/client/image/ruby.svg")
        SVGResource rubyCategoryIcon();
    }
}
