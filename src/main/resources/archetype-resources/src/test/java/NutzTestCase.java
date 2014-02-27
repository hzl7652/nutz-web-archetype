#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import org.junit.ClassRule;
import org.junit.Rule;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.ioc.provider.ComboIocProvider;
import org.nutz.test.NutzTestClassRule;
import org.nutz.test.NutzTestMethodRule;

@IocBy(type = ComboIocProvider.class, args = { "*org.nutz.ioc.loader.json.JsonLoader", "ioc.js",
		"*org.nutz.ioc.loader.annotation.AnnotationIocLoader", "${package}" })
public abstract class NutzTestCase {

	@ClassRule
	public static NutzTestClassRule ntcr = new NutzTestClassRule();

	@Rule
	public NutzTestMethodRule ntr = new NutzTestMethodRule();

	@Inject
	protected Dao dao;
}
