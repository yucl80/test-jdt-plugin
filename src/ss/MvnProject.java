package ss;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.m2e.core.MavenPlugin;
import org.eclipse.m2e.core.internal.project.ProjectConfigurationManager;
import org.eclipse.m2e.core.project.IMavenProjectFacade;
import org.eclipse.m2e.core.project.IMavenProjectRegistry;
import org.eclipse.m2e.core.project.LocalProjectScanner;
import org.eclipse.m2e.core.project.MavenProjectInfo;
import org.eclipse.m2e.core.project.MavenUpdateRequest;
import org.eclipse.m2e.core.project.ProjectImportConfiguration;


public class MvnProject {

	public void f6() {
		final List<IProject> mavenProjects = new ArrayList<IProject>();
		for (final IMavenProjectFacade mavenProject : MavenPlugin.getMavenProjectRegistry().getProjects()) {
			mavenProjects.add(mavenProject.getProject());
		}

		final List<IJavaProject> javaProjects = new ArrayList<IJavaProject>();
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		try {
			for (final IJavaProject javaProject : JavaCore.create(root).getJavaProjects()) {
				if (mavenProjects.contains(javaProject.getProject())) {
					javaProjects.add(javaProject);
				}
			}
		} catch (final JavaModelException e) {
			// final IStatus status = new Status(IStatus.ERROR,
			// SourceLookupPlugin.getInstance().getBundle().getSymbolicName(), "Can't
			// retrieve Java projects.", e);
			// SourceLookupPlugin.getInstance().getLog().log(status);
		}

		/*
		 * for (final ISourceContainer container : director.getSourceContainers()) { if
		 * (container.getType().getId().equals(MyMvnSourceContainerTypeDelegate.TYPE_ID)
		 * ) { javaProjects.remove(((MyMvnSourceContainer) container).getJavaProject());
		 * } }
		 */
	}

	private static IPath getFinalArtifactPath(IProject project) throws CoreException {
		/*IMavenProjectRegistry projectManager = MavenPlugin.getMavenProjectRegistry();
		IMavenProjectFacade projectFacade = projectManager.create(project, new NullProgressMonitor());
		MavenProject mavenProject = projectFacade.getMavenProject(new NullProgressMonitor());

		String buildDirectory = mavenProject.getBuild().getDirectory();
		String finalName = mavenProject.getBuild().getFinalName();
		String finalArtifactPath = buildDirectory + "/" + finalName + "." + mavenProject.getPackaging();
		return new Path(finalArtifactPath);
		*/
		return null;
	}

	public void update(IProject project, boolean force, IProgressMonitor monitor) throws CoreException {

		//ProjectConfigurationManager configurationManager = (ProjectConfigurationManager) MavenPlugin.getProjectConfigurationManager();
		// setProperty(IProgressConstants.ACTION_PROPERTY, new
		// OpenMavenConsoleAction());
		// MavenUpdateRequest request = new MavenUpdateRequest(projects, offline,
		// forceUpdateDependencies);
		/*
		 * Map<String, IStatus> updateStatus =
		 * configurationManager.updateProjectConfiguration(request, updateConfiguration,
		 * cleanProjects, refreshFromLocal, monitor); Map<String, Throwable> errorMap =
		 * new LinkedHashMap<String, Throwable>(); ArrayList<IStatus> errors = new
		 * ArrayList<IStatus>(); for(Map.Entry<String, IStatus> entry :
		 * updateStatus.entrySet()) { if(!entry.getValue().isOK()) {
		 * errors.add(entry.getValue()); errorMap.put(entry.getKey(), new
		 * CoreException(entry.getValue())); } } if(errorMap.size() > 0) { //
		 * handleErrors(errorMap); } IStatus status = Status.OK_STATUS; if(errors.size()
		 * == 1) { status = errors.get(0); } else {
		 * 
		 * }
		 */
	}
	
	public static boolean importMavenProject(String parentPomPath, String pomPath) {
		/*MavenProjectInfo parentInfo = null;

		if (parentPomPath != null && !"".equals(parentPomPath)) {
			File parentPomFile = new File(parentPomPath);
			parentInfo = new MavenProjectInfo(parentPomPath, parentPomFile, null, null);
		}

		File pomFile = new File(pomPath);
		MavenProjectInfo mavenProjectInfo = new MavenProjectInfo(pomPath, pomFile, null, parentInfo);

		Collection<MavenProjectInfo> projects = new ArrayList<MavenProjectInfo>();
		projects.add(mavenProjectInfo);

		ProjectImportConfiguration importConfiguration = new ProjectImportConfiguration();
		//ImportMavenProjectsJob job = new ImportMavenProjectsJob(projects, null, importConfiguration);
		//job.setRule(MavenPlugin.getProjectConfigurationManager().getRule());
		//job.schedule();
 */
		return true;
	}

	
	public static IProject importExistingMavenProjects(IPath path, String projectName) throws Exception {
		/*
		  File root = path.toFile(); String location = path.toOSString();
		 * MavenModelManager modelManager = MavenPlugin.getMavenModelManager();
		 * LocalProjectScanner scanner = new LocalProjectScanner(root, location, false,
		 * modelManager); scanner.run(new NullProgressMonitor()); List<MavenProjectInfo>
		 * infos = new ArrayList<MavenProjectInfo>();
		 * infos.addAll(scanner.getProjects()); for(MavenProjectInfo info :
		 * scanner.getProjects()){ infos.addAll(info.getProjects()); }
		 */
		//ImportMavenProjectsJob job = new ImportMavenProjectsJob(infos, new ArrayList<IWorkingSet>(), new ProjectImportConfiguration());
		//job.setRule(MavenPlugin.getProjectConfigurationManager().getRule());
		//job.schedule();
		//IProject project = waitForProject(projectName);
		
		// List results = MavenPlugin.getProjectConfigurationManager().importProjects(
	   //           projects, importConfiguration, new MavenProjectWorkspaceAssigner(workingSets), monitor.newChild(100));
		
		 
		return null;
	}

}
