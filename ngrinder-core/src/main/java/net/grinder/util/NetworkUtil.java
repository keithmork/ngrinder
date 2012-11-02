/*
 * Copyright (C) 2012 - 2012 NHN Corporation
 * All rights reserved.
 *
 * This file is part of The nGrinder software distribution. Refer to
 * the file LICENSE which is part of The nGrinder distribution for
 * licensing details. The nGrinder distribution is available on the
 * Internet at http://nhnopensource.org/ngrinder
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT HOLDERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.grinder.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

/**
 * Common utility for network env.
 * 
 * @author JunHo Yoon
 * @since 3.0
 * 
 */
public abstract class NetworkUtil {
	/**
	 * Get the local host address by trying to connect given host..
	 * 
	 * @param byConnecting
	 *            connecting host
	 * @param port
	 *            connecting port
	 * @return ip form of host address
	 */
	public static String getLocalHostAddress(String byConnecting, int port) {
		Socket s = null;
		Socket s2 = null;

		try {
			s = new Socket();
			SocketAddress addr = new InetSocketAddress(byConnecting, port);
			s.connect(addr, 1000); // 1 seconds timeout
			return s.getLocalAddress().getHostAddress();
		} catch (IOException e) {
			// For safety.
			try {
				s2 = new Socket();
				SocketAddress addr = new InetSocketAddress("www.google.com", 80);
				s2.connect(addr, 1000); // 1 seconds timeout
				return s2.getLocalAddress().getHostAddress();
			} catch (Exception e1) {
				try {
					return InetAddress.getLocalHost().getHostAddress();
				} catch (UnknownHostException e2) {
					return "127.0.0.1";
				}
			}
		} finally {
			NetworkUtil.closeQuitely(s);
			NetworkUtil.closeQuitely(s2);

		}
	}

	/**
	 * Get local host name.
	 * 
	 * @return local host name
	 */
	public static String getLocalHostName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			return "localhost";
		}
	}

	/**
	 * Get the Ip addresses from host name.
	 * 
	 * @param host
	 *            host
	 * @return ips
	 */
	public static InetAddress[] getIpsFromHost(String host) {
		try {
			return InetAddress.getAllByName(host);
		} catch (UnknownHostException e) {
			return new InetAddress[] {};
		}
	}

	/**
	 * Close socket quietly.
	 * 
	 * @param s
	 *            socket
	 */
	public static void closeQuitely(Socket s) {
		if (s != null) {
			try {
				s.close();
			} catch (IOException e) {
			}
		}
	}
}