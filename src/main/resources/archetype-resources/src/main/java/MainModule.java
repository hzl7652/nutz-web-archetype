#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import org.nutz.mvc.annotation.Encoding;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.annotation.Views;
import org.nutz.mvc.ioc.provider.ComboIocProvider;
import org.nutz.mvc.view.DefaultViewMaker;

@Ok("json")
@Fail("jsp:/common/500.jsp")
@IocBy(type = ComboIocProvider.class, args = { "*org.nutz.ioc.loader.json.JsonLoader", "ioc.js",
		"*org.nutz.ioc.loader.annotation.AnnotationIocLoader", "${package}" })
@Modules(scanPackage = true)
@Encoding(input = "UTF-8", output = "UTF-8")
public class MainModule {

}
