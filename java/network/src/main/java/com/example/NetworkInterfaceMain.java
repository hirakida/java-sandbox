package com.example;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public final class NetworkInterfaceMain {

    public static void main(String[] args) {
        Enumeration<NetworkInterface> networkInterfaces;
        try {
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            throw new UncheckedIOException(e);
        }

        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress address = addresses.nextElement();
                if (address instanceof Inet4Address) {
                    try {
                        InetAddress inetAddress = InetAddress.getByAddress(address.getAddress());
                        if (inetAddress.isReachable(100)) {
                            System.out.println("HostAddress: " + inetAddress.getHostAddress());
                            System.out.println("HostName: " + inetAddress.getHostName());
                            System.out.println("CanonicalHostName: " + inetAddress.getCanonicalHostName());
                        }
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                }
            }
        }
    }
}
