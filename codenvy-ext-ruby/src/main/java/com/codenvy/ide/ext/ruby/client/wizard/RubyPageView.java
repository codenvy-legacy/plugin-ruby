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
package com.codenvy.ide.ext.ruby.client.wizard;

import com.codenvy.ide.api.mvp.View;
import com.google.inject.ImplementedBy;

/** @author Vladyslav Zhukovskii */
@ImplementedBy(RubyPageViewImpl.class)
public interface RubyPageView extends View<RubyPageView.ActionDelegate> {
    public interface ActionDelegate {
    }
}
