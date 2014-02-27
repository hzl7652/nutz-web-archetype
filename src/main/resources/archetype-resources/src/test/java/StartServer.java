#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import org.eclipse.jetty.server.Server;

/**
 * 使用Jetty运行调试Web应用, 在Console输入回车快速重新加载应用.
 *
 * @author calvin
 */
public class StartServer {

	public static final int PORT = 80;
	public static final String CONTEXT = "/${artifactId}";
	public static final String[] TLD_JAR_NAMES = new String[] { "sitemesh", "spring-webmvc", "shiro-web",
			"springside-core" };

	public static void main(String[] args) throws Exception {
		long startM = System.currentTimeMillis();
		// 设定Spring的profile
		// System.setProperty("spring.profiles.active", "development");

		// 启动Jetty
		Server server = JettyFactory.createServerInSource(PORT, CONTEXT);
		JettyFactory.setTldJarNames(server, TLD_JAR_NAMES);

		try {
			server.start();

			System.out.println("Server running at http://localhost:" + PORT + CONTEXT);
			System.out.println("Hit Enter to reload the application quickly");
			System.out.println((System.currentTimeMillis() - startM) / 1000);
			;
			// 等待用户输入回车重载应用.
			while (true) {
				char c = (char) System.in.read();
				if (c == '${symbol_escape}n') {
					JettyFactory.reloadContext(server);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
