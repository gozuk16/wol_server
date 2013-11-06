@Grab('org.eclipse.jetty.aggregate:jetty-all')
@Grab('javax.servlet:javax.servlet-api')
@GrabExclude('org.eclipse.jetty.orbit:javax.servlet')
@GrabConfig(systemClassLoader = true)

import org.eclipse.jetty.server.*
import org.eclipse.jetty.servlet.*
import groovy.servlet.*

def startJetty() {
    def server = new Server(9090)
    def context = new ServletContextHandler(server, '/', ServletContextHandler.SESSIONS)
    context.resourceBase = '.'
    context.addServlet(GroovyServlet, "*.groovy")
    context.addServlet(TemplateServlet, "*.gsp")
    server.start()
    //server.join()
}

println "start wol-server"
startJetty()
