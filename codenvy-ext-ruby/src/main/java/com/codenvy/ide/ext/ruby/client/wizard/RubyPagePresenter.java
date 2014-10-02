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
package com.codenvy.ide.ext.ruby.client.wizard;

import com.codenvy.api.project.gwt.client.ProjectServiceClient;
import com.codenvy.api.project.shared.dto.ProjectDescriptor;
import com.codenvy.ide.api.event.OpenProjectEvent;
import com.codenvy.ide.api.projecttype.wizard.ProjectWizard;
import com.codenvy.ide.api.wizard.AbstractWizardPage;
import com.codenvy.ide.dto.DtoFactory;
import com.codenvy.ide.ext.ruby.shared.ProjectAttributes;
import com.codenvy.ide.rest.AsyncRequestCallback;
import com.codenvy.ide.rest.DtoUnmarshallerFactory;
import com.codenvy.ide.rest.Unmarshallable;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

/** @author Vladyslav Zhukovskii */
@Singleton
public class RubyPagePresenter extends AbstractWizardPage implements RubyPageView.ActionDelegate {

    private RubyPageView           view;
    private ProjectServiceClient   projectServiceClient;
    private EventBus               eventBus;
    private DtoFactory             dtoFactory;
    private DtoUnmarshallerFactory dtoUnmarshallerFactory;

    @Inject
    public RubyPagePresenter(RubyPageView view, ProjectServiceClient projectServiceClient, EventBus eventBus, DtoFactory dtoFactory,
                             DtoUnmarshallerFactory dtoUnmarshallerFactory) {
        super("Ruby project settings", null);
        this.view = view;
        this.projectServiceClient = projectServiceClient;
        this.eventBus = eventBus;
        this.dtoFactory = dtoFactory;
        this.dtoUnmarshallerFactory = dtoUnmarshallerFactory;
        view.setDelegate(this);
    }

    /** {@inheritDoc} */
    @Nullable
    @Override
    public String getNotice() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isCompleted() {
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public void focusComponent() {
        // nothing to do
    }

    /** {@inheritDoc} */
    @Override
    public void removeOptions() {
        // nothing to do
    }

    /** {@inheritDoc} */
    @Override
    public boolean canSkip() {
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public void go(AcceptsOneWidget container) {
        container.setWidget(view);
        ProjectDescriptor project = wizardContext.getData(ProjectWizard.PROJECT);
        if (project != null) {
            Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
                @Override
                public void execute() {
                    // wait for client perform all actions to continue
                }
            });
        }
    }

    /** {@inheritDoc} */
    @Override
    public void commit(@NotNull final CommitCallback callback) {
        final ProjectDescriptor projectDescriptorToUpdate = dtoFactory.createDto(ProjectDescriptor.class);
        projectDescriptorToUpdate.withProjectTypeId(wizardContext.getData(ProjectWizard.PROJECT_TYPE).getProjectTypeId());

        projectDescriptorToUpdate.setVisibility(getProjectVisibility());
        final String name = wizardContext.getData(ProjectWizard.PROJECT_NAME);
        projectDescriptorToUpdate.setDescription(wizardContext.getData(ProjectWizard.PROJECT_DESCRIPTION));
        final ProjectDescriptor project = wizardContext.getData(ProjectWizard.PROJECT);
        if (project != null) {
            if (project.getName().equals(name)) {
                updateProject(project, projectDescriptorToUpdate, callback);
            } else {
                projectServiceClient.rename(project.getPath(), name, null, new AsyncRequestCallback<Void>() {
                    @Override
                    protected void onSuccess(Void result) {
                        project.setName(name);

                        updateProject(project, projectDescriptorToUpdate, callback);
                    }

                    @Override
                    protected void onFailure(Throwable exception) {
                        callback.onFailure(exception);
                    }
                });
            }
        } else {
            createProject(callback, projectDescriptorToUpdate, name);
        }
    }

    private String getProjectVisibility() {
        Boolean visibility = wizardContext.getData(ProjectWizard.PROJECT_VISIBILITY);
        if (visibility != null && visibility) {
            return "public";
        }

        return "private";
    }

    private void updateProject(final ProjectDescriptor project, ProjectDescriptor projectDescriptorToUpdate,
                               final CommitCallback callback) {
        Unmarshallable<ProjectDescriptor> unmarshaller = dtoUnmarshallerFactory.newUnmarshaller(ProjectDescriptor.class);
        projectServiceClient
                .updateProject(project.getPath(), projectDescriptorToUpdate, new AsyncRequestCallback<ProjectDescriptor>(unmarshaller) {
                    @Override
                    protected void onSuccess(ProjectDescriptor result) {
                        eventBus.fireEvent(new OpenProjectEvent(result.getName()));
                        wizardContext.putData(ProjectWizard.PROJECT, result);
                        callback.onSuccess();
                    }

                    @Override
                    protected void onFailure(Throwable exception) {
                        callback.onFailure(exception);
                    }
                });
    }

    private void createProject(final CommitCallback callback, ProjectDescriptor projectDescriptor, final String name) {
        projectServiceClient
                .createProject(name, projectDescriptor,
                               new AsyncRequestCallback<ProjectDescriptor>(
                                       dtoUnmarshallerFactory.newUnmarshaller(ProjectDescriptor.class)) {
                                   @Override
                                   protected void onSuccess(ProjectDescriptor result) {
                                       eventBus.fireEvent(new OpenProjectEvent(result.getName()));
                                       wizardContext.putData(ProjectWizard.PROJECT, result);
                                       callback.onSuccess();
                                   }

                                   @Override
                                   protected void onFailure(Throwable exception) {
                                       callback.onFailure(exception);
                                   }
                               }
                              );
    }
}
