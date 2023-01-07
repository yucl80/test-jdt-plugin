package ss;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.launching.JavaRuntime;

public class Np {
	
	public void f4() throws Exception {
		// create a project with name "TESTJDT"
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject("TESTJDT");
		project.create(null);
		project.open(null);
		 
		//set the Java nature
		IProjectDescription description = project.getDescription();
		description.setNatureIds(new String[] { JavaCore.NATURE_ID });
		 
		//create the project
		project.setDescription(description, null);
		IJavaProject javaProject = JavaCore.create(project);
		 
		//set the build path
		IClasspathEntry[] buildPath = {
				JavaCore.newSourceEntry(project.getFullPath().append("src")),
						JavaRuntime.getDefaultJREContainerEntry() };
		 
		javaProject.setRawClasspath(buildPath, project.getFullPath().append(
						"bin"), null);
		 
		//create folder by using resources package
		IFolder folder = project.getFolder("src");
		folder.create(true, true, null);
		 
		//Add folder to Java element
		IPackageFragmentRoot srcFolder = javaProject
						.getPackageFragmentRoot(folder);
		 
		//create package fragment
		IPackageFragment fragment = srcFolder.createPackageFragment(
				"com.programcreek", true, null);
		 
		//init code string and create compilation unit
		String str = "package com.programcreek;" + "\n"
			+ "public class Test  {" + "\n" + " private String name;"
			+ "\n" + "}";
		 
				ICompilationUnit cu = fragment.createCompilationUnit("Test.java", str,
						false, null);
		 
		//create a field
		IType type = cu.getType("Test");
		 
		type.createField("private String age;", null, true, null);
	}
	
	public void f5() {
		IWorkspaceRoot root= ResourcesPlugin.getWorkspace().getRoot();
		 
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		 
		System.out.println("root" + root.getLocation().toOSString());
		 
		Runnable runnable = new Runnable() {
			public void run() {
				try {
					IPath projectDotProjectFile = new Path("/media/flashx/TestProjectImport" + "/.project");
					IProjectDescription projectDescription = workspace.loadProjectDescription(projectDotProjectFile);
					IProject project = workspace.getRoot().getProject(projectDescription.getName());
					//JavaCapabilityConfigurationPage.createProject(project, projectDescription.getLocationURI(),	null);
					//project.create(null);
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		};
		 
		// and now get the workbench to do the work
		//final IWorkbench workbench = PlatformUI.getWorkbench();
		//workbench.getDisplay().syncExec(runnable);
		 
		 
		IProject[] projects = root.getProjects();
		 
		for(IProject project: projects){
			System.out.println(project.getName());
		}
	}

}
