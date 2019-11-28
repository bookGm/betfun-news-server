package io.information.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.NetworkInterface;

@Component
@ConditionalOnProperty(prefix = "idgenerator", name = "workerid")
public class IdWorkerConfig {
    private static final long datacenterIdBits = 5L;
    private static final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    public static long WORKER_ID;
    public static long DATA_CENTER_ID;

    static {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            if (network == null) {
                DATA_CENTER_ID = 1L;
            } else {
                byte[] mac = network.getHardwareAddress();
                if (null != mac) {
                    DATA_CENTER_ID = ((0x000000FF & (long) mac[mac.length - 1]) | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
                    DATA_CENTER_ID = DATA_CENTER_ID % (maxDatacenterId + 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Value("${idgenerator.workerid}")
    public void setWorkerId(long workerId) {
        WORKER_ID = workerId;
    }
}
