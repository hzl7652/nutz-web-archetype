#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.nutz.test;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.nutz.lang.Lang;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

public class NutzTestMethodRule implements MethodRule {

	private final Log log = Logs.get();

	@Override
	public Statement apply(Statement base, FrameworkMethod method, Object target) {
		return new NutzTestStatement(base, method, target);
	}

	public class NutzTestStatement extends Statement {
		Statement base;
		FrameworkMethod method;
		Object target;
		protected Class<?> klass;

		public NutzTestStatement(Statement base, FrameworkMethod method, Object target) {
			super();
			this.base = base;
			this.method = method;
			this.target = target;
			this.klass = target.getClass();

		}

		@Override
		public void evaluate() throws Throwable {
			// 处理事务回滚问题
			NutTest nutTest = method.getAnnotation(NutTest.class);
			if (nutTest == null) {
				nutTest = (NutTest) NutTestContext.me().mirror.getAnnotation(NutTest.class);
			}
			final boolean needRollback = nutTest != null && nutTest.rollback();
			try {

				// 打印调试信息
				if (log.isDebugEnabled()) {
					log.debug("->" + method + " -> auto-rollback=" + needRollback);
					if (NutTestContext.me().ioc == null) {
						log.debug("@IocBy not found ,run without Ioc support !!");
					} else {
						log.debug("@IocBy found ,run with Ioc support ^_^");
					}
				}
				final Object obj = NutTestContext.me().ioc.get(klass);
				if (needRollback) {
					try {
						Trans.exec(new Atom() {
							@Override
							public void run() {

								try {
									method.invokeExplosively(obj);
								} catch (Throwable e) {
									throw Lang.wrapThrow(e);
								}
								throw JustRollback.me();// 这样,无论原方法是否跑异常,事务模板都能收到异常,并回滚
							}
						});
					} catch (JustRollback e) {
						log.info(String.format("rollback for %s", method.getName()));
					}
				} else {
					// 按传统方法执行,无需通过事务模板
					method.invokeExplosively(obj);
				}
			} finally {
				// 确保Ioc容器被关闭

			}

		}

	}

}
