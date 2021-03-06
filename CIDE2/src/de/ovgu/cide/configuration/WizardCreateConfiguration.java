package de.ovgu.cide.configuration;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.wizard.Wizard;

import de.ovgu.cide.features.IFeatureModel;

public class WizardCreateConfiguration extends Wizard {

	private IProject sourceProject;

	private IFeatureModel featureModel;

	public WizardCreateConfiguration(IProject sourceProject, IFeatureModel fm) {
		super();
		this.sourceProject = sourceProject;
		this.featureModel = fm;
	}

	private AbstractConfigurationPage selectFeaturesPage;

	private WizardPageCreateProject createProjectPage;

	@Override
	public void addPages() {
		selectFeaturesPage = featureModel
				.getConfigurationPage("SelectFeatures");
		selectFeaturesPage.setTitle("Select Features");
		addPage(selectFeaturesPage);
		createProjectPage = new WizardPageCreateProject("CreateProjects",
				sourceProject);
		addPage(createProjectPage);

	}

	public boolean performFinish() {

		CreateConfigurationJob job = new CreateConfigurationJob(sourceProject,
				selectFeaturesPage.getSelectedFeatures(),
				createProjectPage.projectName.getText());
		job.setUser(true);
		job.setPriority(Job.LONG);
		job.schedule();

		return true;
	}
}
