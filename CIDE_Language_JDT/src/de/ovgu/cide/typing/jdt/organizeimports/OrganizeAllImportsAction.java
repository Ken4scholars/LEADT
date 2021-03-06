package de.ovgu.cide.typing.jdt.organizeimports;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import de.ovgu.cide.features.source.ColoredSourceFileIteratorAction;

/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class OrganizeAllImportsAction extends  ColoredSourceFileIteratorAction {
	/**
	 * The constructor.
	 */
	public OrganizeAllImportsAction() {
	}

	protected WorkspaceJob createJob(IProject[] p) {
		return new OrganizeAllImportsJob(p);
	}
}