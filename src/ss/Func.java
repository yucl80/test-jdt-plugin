package ss;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IInitializer;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.launching.JavaRuntime;


public class Func {
	
	
	
	public void f3(String projectName) {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject m_project = root.getProject(projectName);
		try {
			// Create the project
			if (!m_project.exists()) {
				m_project.create(null);
			}
			m_project.open(null);
			IJavaProject javaProject = JavaCore.create(m_project);
		 
			IProjectDescription description = m_project.getDescription();
			description.setNatureIds(new String[] { JavaCore.NATURE_ID });
		 
			m_project.setDescription(description, null);
		 
			// need to check to make sure this is right JRE
			IClasspathEntry[] buildPath = {
					JavaCore.newSourceEntry(m_project.getFullPath().append("src")),
					JavaRuntime.getDefaultJREContainerEntry() };
				// set the classpath
			javaProject.setRawClasspath(buildPath, m_project.getFullPath()
					.append("bin"), null);
				// create the src folder
			IFolder folder = m_project.getFolder("src");
			if (!folder.exists())
				folder.create(true, true, null);
				IPackageFragmentRoot srcFolder = javaProject.getPackageFragmentRoot(folder);
			Assert.isTrue(srcFolder.exists());
			
			
			//IPackageFragment fragment = srcFolder.createPackageFragment(pkg_name_from_java_file, true, null);
			//fragment.createCompilationUnit(java_file_name, code_from_java_file, true, null);
			
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}
	
	public void f2(IType unit) throws Exception {		
		IType [] typeDeclarationList = unit.getTypes();
		 
		for (IType typeDeclaration : typeDeclarationList) {
		     // get methods list
		     IMethod [] methodList = typeDeclaration.getMethods();
		 
		     for (IMethod method : methodList) {
		          final List<String> referenceList = new ArrayList<String>();
		          // check each method.
		          String methodName = method.getElementName();
		          if (!method.isConstructor()) {
		              // Finds the references of the method and record references of the method to referenceList parameter.
		           //   JDTSearchProvider.searchMethodReference(referenceList, method, scope, iJavaProject);
		          }
		     }
		}
	}

	public void f1() throws Exception {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		System.out.println("root" + root.getLocation().toOSString());

		IProject[] projects = root.getProjects();

		// process each project
		for (IProject project : projects) {

			System.out.println("project name: " + project.getName());

			if (project.isNatureEnabled("org.eclipse.jdt.core.javanature")) {
				IJavaProject javaProject = JavaCore.create(project);
				IPackageFragment[] packages = javaProject.getPackageFragments();

				// process each package
				for (IPackageFragment aPackage : packages) {

					// We will only look at the package from the source folder
					// K_BINARY would include also included JARS, e.g. rt.jar
					// only process the JAR files
					if (aPackage.getKind() == IPackageFragmentRoot.K_BINARY
							&& aPackage.getElementName().equals("java.lang")) {

						System.out.println("inside of java.lang package");

						for (IClassFile classFile : aPackage.getClassFiles()) {

							System.out.println("----classFile: " + classFile.getElementName());

							// A class file has a single child of type IType.
							// Class file elements need to be opened before they
							// can be navigated. If a class file cannot be
							// parsed, its structure remains unknown.
							// Use IJavaElement.isStructureKnown to determine
							// whether this is the case.

							// System.out.println();
							// classFile.open(null);

							for (IJavaElement javaElement : classFile.getChildren()) {

								if (javaElement instanceof IType) {
									System.out.println("--------IType " + javaElement.getElementName());

									// IInitializer
									IInitializer[] inits = ((IType) javaElement).getInitializers();
									for (IInitializer init : inits) {
										System.out.println("----------------initializer: " + init.getElementName());
									}

									// IField
									IField[] fields = ((IType) javaElement).getFields();
									for (IField field : fields) {
										System.out.println("----------------field: " + field.getElementName());
									}

									// IMethod
									IMethod[] methods = ((IType) javaElement).getMethods();
									for (IMethod method : methods) {
										System.out.println("----------------method: " + method.getElementName());
										System.out.println(
												"----------------method return type - " + method.getReturnType());
									}
								}
							}
						}

					}
				}

			}

		}
	}
}
