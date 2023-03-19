package ss;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;

public class Search {
	
	private SearchEngine engine = new SearchEngine();
	private IJavaSearchScope scope = SearchEngine.createWorkspaceScope();
	private SearchPattern pattern = null;
	private SearchParticipant[] participants = new SearchParticipant[] {SearchEngine.getDefaultSearchParticipant()};
	
	/**
	 * Create a search scope that includes a given project and its dependencies.
	 */
	public static IJavaSearchScope searchScope(IJavaProject javaProject, boolean includeBinaries, boolean includeSystemLibs) throws JavaModelException {
		int includeMask =
				IJavaSearchScope.REFERENCED_PROJECTS |
				IJavaSearchScope.SOURCES;
		if (includeBinaries) {
			includeMask = includeMask | IJavaSearchScope.APPLICATION_LIBRARIES;
		}
		if (includeSystemLibs) {
			includeMask = includeMask | IJavaSearchScope.SYSTEM_LIBRARIES; 
		}
		return SearchEngine.createJavaSearchScope(new IJavaElement[] {javaProject}, includeMask);
	}
	
	public IJavaSearchScope workspaceScope(boolean includeBinaries) {
		if (includeBinaries) {
			return SearchEngine.createWorkspaceScope();
		} else {
			List<IJavaProject> projects = new ArrayList<>();
			for (IProject p : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
				try {
					if (p.isAccessible() && p.hasNature(JavaCore.NATURE_ID)) {
						IJavaProject jp = JavaCore.create(p);
						projects.add(jp);
					}
				} catch (Exception e) {
					//logger.log(e);
				}
			}
			int includeMask = IJavaSearchScope.SOURCES;
			return SearchEngine.createJavaSearchScope(projects.toArray(new IJavaElement[projects.size()]), includeMask);
		}
	}

}
