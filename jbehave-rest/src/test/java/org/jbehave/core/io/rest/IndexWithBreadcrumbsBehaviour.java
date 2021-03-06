package org.jbehave.core.io.rest;

import static org.apache.commons.lang.StringUtils.join;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Map;

import org.jbehave.core.io.rest.xwiki.IndexFromXWiki;
import org.junit.Test;

public class IndexWithBreadcrumbsBehaviour {

	@Test
	public void canIndexFromPaths() {
		IndexWithBreadcrumbs indexer = new IndexFromXWiki();
		String rootURI = "http://localhost:8080/xwiki/rest/wikis/xwiki/spaces/Main/pages";
		Map<String, Resource> index = indexer.indexResources(rootURI, "src/test/resources", "stories/**");
		assertThat(index.containsKey("a_first"), equalTo(true));
		Resource first = index.get("a_first");
		assertThat(first.getURI(), equalTo(rootURI + "/a_first"));
		assertThat(first.getParentName(), equalTo("stories"));
		assertThat(join(first.getBreadcrumbs(), "/"), equalTo("stories"));
		assertThat(first.getContent(), equalTo("A story"));
		assertThat(index.containsKey("a_second"), equalTo(true));
		Resource second = index.get("a_second");
		assertThat(second.getURI(), equalTo(rootURI + "/a_second"));
		assertThat(second.getParentName(), equalTo("stories"));
		assertThat(join(second.getBreadcrumbs(), "/"), equalTo("stories"));
		assertThat(second.getContent(), equalTo("Another story"));
		Resource third = index.get("a_third");
		assertThat(third.getURI(), equalTo(rootURI + "/a_third"));
		assertThat(third.getParentName(), equalTo("domain"));
		assertThat(join(third.getBreadcrumbs(), "/"), equalTo("stories/domain"));
		assertThat(third.getContent(), equalTo("A third story"));
	}

}
