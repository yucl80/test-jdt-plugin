package ss;

import java.util.Arrays;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.m2e.core.MavenPlugin;
import org.eclipse.m2e.core.internal.IMavenConstants;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication {

	@Override
	public Object start(IApplicationContext context) throws Exception {
		f();
		
		// MavenPlugin.getMavenProjectRegistry().getProjects()

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		// Get all projects in the workspace
		IProject[] projects = root.getProjects();
		// Loop over all projects
		for (IProject project : projects) {
			System.out.println(project.getName());
		}
		
		 SearchEngine engine = new SearchEngine();
		 
		

		System.out.println("Hello RCP World!");
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		// nothing to do
	}

	public void f() throws Exception {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("testAddStatements");
		System.out.println("root" + ResourcesPlugin.getWorkspace().getRoot().getLocation().toOSString());
		if (!project.exists()) {
			project.create(null);
		}
		project.open(null);

		// set the Java nature
		IProjectDescription description = project.getDescription();
		description.setNatureIds(new String[] { JavaCore.NATURE_ID,IMavenConstants.NATURE_ID });

		// create the project
		project.setDescription(description, null);
		IJavaProject javaProject = JavaCore.create(project);

		// set the build path
		IClasspathEntry[] buildPath = { JavaCore.newSourceEntry(project.getFullPath().append("src")) };

		javaProject.setRawClasspath(buildPath, project.getFullPath().append("bin"), null);

		// create folder by using resources package
		IFolder folder = project.getFolder("src");
		if (!folder.exists()) {
			folder.create(true, true, null);
		}

		// Add folder to Java element
		IPackageFragmentRoot srcFolder = javaProject.getPackageFragmentRoot(folder);

		// create package fragment
		IPackageFragment package1 = srcFolder.createPackageFragment("org.example", true, null);

		// compilation unit
		String str = "" + "package org.example;\n" + "public class Main {\n"
				+ "	public static void main(String[] args) {\n" + "	}\n" + "	public static void add() {\n"
				+ "		int i = 1;\n" + "		System.out.println(i);\n" + "	}\n" + "}";
		ICompilationUnit unit = package1.createCompilationUnit("Main.java", str, true, null);

		// parse compilation unit
		CompilationUnit astRoot = parse(unit);

		// create a ASTRewrite
		AST ast = astRoot.getAST();

		IType type = astRoot.getTypeRoot().findPrimaryType();

		Arrays.asList(type.getMethods()).forEach(method -> {
			System.out.println(method.getElementName());
		});

		astRoot.accept(new ASTVisitor() {

			@Override
			public void endVisit(MethodInvocation node) {
				System.out.println(node.getName());
				// TODO Auto-generated method stub
				super.endVisit(node);
			}

			// by add more visit method like the following below, then all king of statement
			// can be visited.

		});

	}

	public void test() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		// Get all projects in the workspace
		IProject[] projects = root.getProjects();
		// Loop over all projects
		for (IProject project : projects) {
			try {
				if (project.isNatureEnabled("org.eclipse.jdt.core.javanature")) {

					IPackageFragment[] packages = JavaCore.create(project).getPackageFragments();
					// parse(JavaCore.create(project));
					for (IPackageFragment mypackage : packages) {
						if (mypackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
							for (ICompilationUnit unit : mypackage.getCompilationUnits()) {
								// Now create the AST for the ICompilationUnits
								CompilationUnit parse = parse(unit);
								// MethodVisitor visitor = new MethodVisitor();
								// parse.accept(visitor);

								/*
								 * for (MethodDeclaration method : visitor.getMethods()) {
								 * System.out.print("Method name: " + method.getName() + " Return type: " +
								 * method.getReturnType2()); }
								 */

							}
						}

					}
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Reads a ICompilationUnit and creates the AST DOM for manipulating the Java
	 * source file
	 *
	 * @param unit
	 * @return
	 */

	private static CompilationUnit parse(ICompilationUnit unit) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(unit);
		parser.setResolveBindings(true);
		return (CompilationUnit) parser.createAST(null); // parse
	}
}
