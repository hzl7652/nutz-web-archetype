#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.nutz.test;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.nutz.lang.Mirror;
import org.nutz.mvc.annotation.IocBy;

public class NutzTestClassRule implements TestRule {

	@Override
	public Statement apply(final Statement base, final Description description) {
		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				initNutzTestContext(description);

				base.evaluate();

				deposeNutzTestContext();
			}

			private void deposeNutzTestContext() {
				if (NutTestContext.me().ioc != null) {
					try {
						NutTestContext.me().ioc.depose();
					} finally {
						NutTestContext.me().ioc = null;
					}
				}
			}

			private void initNutzTestContext(final Description description) {
				NutTestContext.me().mirror = Mirror.me(description.getTestClass());
				// 检查Ioc支持
				IocBy iocBy = (IocBy) NutTestContext.me().mirror.getAnnotation(IocBy.class);
				if (iocBy != null) {
					NutTestContext.me().ioc = Mirror.me(iocBy.type()).born().create(null, iocBy.args());
				} else {
					NutTestContext.me().ioc = null;
				}
			}

		};
	}

}
