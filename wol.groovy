//@Grab(group='org.eclipse.jetty.aggregate', module='jetty-all', version='8.1.7.v20120910')
//@Grab(group='org.eclipse.jetty.aggregate', module='jetty-all', version='9.0.6.v20130930')
@Grab('org.eclipse.jetty.aggregate:jetty-all')
@Grab('javax.servlet:javax.servlet-api')
@GrabExclude('org.eclipse.jetty.orbit:javax.servlet')
@GrabConfig(systemClassLoader = true)

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import org.eclipse.jetty.server.*
import org.eclipse.jetty.servlet.*
import groovy.servlet.*

def wol () {
    def macAddress = "AA:BB:CC:DD:EE:FF";

    def magicPacket = [ (1..6).collect { 0xff },
        (1..16).collect { macAddress.split(":").collect { Integer.parseInt(it, 16) } } ].flatten()
    println magicPacket.size

    def broadcastAddress = InetAddress.getByAddress([255, 255, 255, 255] as byte[])
    def packet = new DatagramPacket(magicPacket as byte[], magicPacket.size, broadcastAddress, 5555)

    println packet
    //def socket = new DatagramSocket()
    //socket.send(packet)
}

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
wol()
