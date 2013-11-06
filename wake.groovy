//@Grab('org.eclipse.jetty.aggregate:jetty-all')
//@Grab('javax.servlet:javax.servlet-api')
//@GrabExclude('org.eclipse.jetty.orbit:javax.servlet')
//@GrabConfig(systemClassLoader = true)

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
//import org.eclipse.jetty.server.*
//import org.eclipse.jetty.servlet.*
//import groovy.servlet.*

def wol (macaddress) {
//    def macaddress = "AA:BB:CC:DD:EE:FF";

    def magicPacket = [ (1..6).collect { 0xff },
        (1..16).collect { macaddress.split(":").collect { Integer.parseInt(it, 16) } } ].flatten()

    def broadcastAddress = InetAddress.getByAddress([255, 255, 255, 255] as byte[])
    def packet = new DatagramPacket(magicPacket as byte[], magicPacket.size, broadcastAddress, 5555)

    def socket = new DatagramSocket()
    socket.send(packet)
}

macaddress = params['mac']
packet = wol(macaddress)

println """
<html>
    <head>
        <title>Wake on Lan Server</title>
    </head>
    <body>
        <h1>Wake on Lan Server</h1>
        ${macaddress} を起動しました。<br />
    </body>
</html>
"""
